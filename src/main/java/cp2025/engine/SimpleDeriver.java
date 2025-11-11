package cp2025.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import cp2025.engine.Datalog.Atom;
import cp2025.engine.Datalog.Constant;
import cp2025.engine.Datalog.Predicate;
import cp2025.engine.Datalog.Program;
import cp2025.engine.Datalog.Rule;
import cp2025.engine.Datalog.Variable;

/**
 * A straightforward single-threaded, deterministic implementation of
 * {@link AbstractDeriver} that evaluates queries in a Datalog program by
 * recursively attempting to derive each goal statement using the program's rules.
 *
 * <p>{@code SimpleDeriver} performs derivation in a naive, depth-first manner.</p>
 *
 * <p>This implementation is thread-interruptible: long-running derivations
 * may throw {@link InterruptedException} if the current thread is interrupted.</p>
 */
public class SimpleDeriver implements AbstractDeriver {

    /**
     * Derives all queries in the given Datalog program using the provided
     * oracle.
     *
     * <p>The method constructs an internal {@link SimpleDeriverState} object to manage
     * derivation state (known results, recursion tracking, etc.), then processes each
     * query atom individually.</p>
     *
     * @param input the {@link Datalog.Program} containing facts, rules, and
     *              queries to evaluate.
     * @param oracle the {@link AbstractOracle} used to directly
     *                   evaluate calculatable predicates.
     * @return a map from each {@link Datalog.Atom} query to a {@code boolean}
     *         indicating whether it is derivable.
     * @throws InterruptedException if the thread running this method is interrupted.
     */
    @Override
    public Map<Atom, Boolean> derive(Program input, AbstractOracle oracle)
            throws InterruptedException {
        SimpleDeriverState state = new SimpleDeriverState(input, oracle);

        Map<Atom, Boolean> results = new HashMap<>();
        for (Atom query : input.queries()) {
            SimpleDeriverState.DerivationResult result = state.deriveStatement(query);
            results.put(query, result.derivable);
        }
        return results;
    }

    /**
     * A class that maintains the internal state of the derivation process.
     *
     * <p>{@code SimpleDeriverState} encapsulates all data needed for the
     * recursive derivation of statements within a single {@link SimpleDeriver}
     * run, including:</p>
     * <ul>
     *     <li>the original {@link Datalog.Program} and {@link AbstractOracle},</li>
     *     <li>a cache of statements for which derivability is determined,</li>
     *     <li>a mapping from predicate symbols to applicable rules,</li>
     *     <li>a set of currently active (in-progress) derivations to detect cycles.</li>
     * </ul>
     *
     * <p>The class is not thread-safe and is designed to be used within
     * a single-threaded derivation context.</p>
     */
    private static class SimpleDeriverState {
        private final Program input;
        private final AbstractOracle oracle;

        /**
         * An immutable map for fast access to all rules with a given head predicate.
         */
        private final Map<Predicate, List<Rule>> predicateToRules;

        /**
         * A map of statements for which derivability is determined (derivable
         * or not derivable).
         */
        private final Map<Atom, Boolean> knownStatements = new HashMap<>();

        /**
         * Set of statements currently being processed (to detect cycles).
         */
        private final Set<Atom> inProgressStatements = new HashSet<>();

        /**
         * Creates a new state for the derivation of a specific Datalog program.
         *
         * @param input the Datalog program being processed.
         * @param oracle the oracle used for calculatable predicates.
         */
        public SimpleDeriverState(Program input, AbstractOracle oracle) {
            this.input = input;
            this.oracle = oracle;

            // Build the predicateToRules map.
            this.predicateToRules = input.rules().stream().collect(
                    java.util.stream.Collectors.groupingBy(rule -> rule.head().predicate()));
        }

        /**
         * Represents the result of attempting to derive a statement.
         *
         * @param derivable whether the statement was successfully derived.
         *  If true, the statement is derivable.
         *  If false, the statement is not derivable without using
         *  in-progress statements in the derivation.
         * @param failedStatements a set of statements that are known to be non-derivable
         *                         if all in-progress statements are non-derivable.
         */
        private record DerivationResult(boolean derivable, Set<Atom> failedStatements) {}


        /**
         * Determines whether the given goal statement can be derived (from the
         * current program and using the current oracle) in a way that avoids
         * using statements that are in-progress at the moment of calling.
         *
         * @param goal the statement to derive.
         * @return a {@link DerivationResult}, see {@link DerivationResult}.
         * @throws InterruptedException if the derivation process is
         *         interrupted.
         */
        public DerivationResult deriveStatement(Atom goal) throws InterruptedException {
            // Check if we already know the result for this statement.
            if (knownStatements.containsKey(goal))
                return new DerivationResult(knownStatements.get(goal), Set.of());

            // Check if we got a cancellation request.
            if (Thread.interrupted())
                throw new InterruptedException("Derivation process was interrupted.");

            // Check if the statement is calculatable.
            if (oracle.isCalculatable(goal.predicate())) {
                boolean result = oracle.calculate(goal);
                knownStatements.put(goal, result);
                return new DerivationResult(result, Set.of());
            }

            // Check for cycles, to avoid infinite loops.
            if (inProgressStatements.contains(goal)) {
                // Return false but do not store the result (we may find a different derivation later).
                return new DerivationResult(false, Set.of(goal));
            }
            inProgressStatements.add(goal);

            // Try to actually derive the statement using rules.
            DerivationResult result = deriveNewStatement(goal);

            inProgressStatements.remove(goal);

            if (result.derivable) {
                knownStatements.put(goal, true);
            } else {
                // We can only deduce non-derivability when there are no in-progress statements
                // (at the top of the recursion).
                if (inProgressStatements.isEmpty())
                    for (Atom s : result.failedStatements)
                        knownStatements.put(s, false);
            }
            return result;
        }

        /**
         * Attempts to derive a "new" statement.
         * Here "new" means that:
         * <ul>
         * <li>the derivability of the statement is not yet known;</li>
         * <li>the statement is not calculatable by the oracle; and</li>
         * <li>the statement was not in-progress when originally requested in deriveStatement()
         *     (but has been added to inProgressStatements since then).</li>
         *
         * @param goal the statement to derive.
         * @return a {@link DerivationResult} representing whether the goal is derivable.
         * @throws InterruptedException if the process is interrupted.
         */
        private DerivationResult deriveNewStatement(Atom goal) throws InterruptedException {
            List<Rule> rules = predicateToRules.get(goal.predicate());
            if (rules == null)
                return new DerivationResult(false, Set.of(goal));

            Set<Atom> failedStatements = new HashSet<>();

            for (Rule rule : rules) {
                Optional<List<Atom>> partiallyAssignedBody = Unifier.unify(rule, goal);
                if (partiallyAssignedBody.isEmpty())
                    continue;

                List<Variable> variables = Datalog.getVariables(partiallyAssignedBody.get());
                FunctionGenerator<Variable, Constant> iterator = new FunctionGenerator<>(variables,
                        input.constants());
                for (Map<Variable, Constant> assignment : iterator) {
                    List<Atom> assignedBody = Unifier.applyAssignment(partiallyAssignedBody.get(),
                            assignment);
                    DerivationResult result = deriveBody(assignedBody);
                    if (result.derivable)
                        return new DerivationResult(true, Set.of());
                    failedStatements.addAll(result.failedStatements);
                }
            }

            failedStatements.add(goal);
            return new DerivationResult(false, failedStatements);
        }

        /**
         * Derive all statements in an assigned body.
         *
         * Returns failure as soon as any statement in the body fails (i.e. is
         * found to not be derivable without using inProgressStatements in the
         * derivation). Returns success otherwise.
         *
         * @param body a list of statements forming the body of a rule.
         * @return a {@link DerivationResult} indicating success or failure.
         * @throws InterruptedException if the derivation process is interrupted.
         */
        private DerivationResult deriveBody(List<Atom> body) throws InterruptedException {
            for (Atom statement : body) {
                DerivationResult result = deriveStatement(statement);
                if (!result.derivable)
                    return new DerivationResult(false, result.failedStatements);
            }
            return new DerivationResult(true, Set.of());
        }
    }
}
