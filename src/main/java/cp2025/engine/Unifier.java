package cp2025.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cp2025.engine.Datalog.Atom;
import cp2025.engine.Datalog.Constant;
import cp2025.engine.Datalog.Element;
import cp2025.engine.Datalog.Rule;
import cp2025.engine.Datalog.Variable;

/**
 * Utility class for finding and applying variable assignments (unification).
 */
public class Unifier {

    /**
     * Assign variables in the rule's head to match the query statement,
     * returning the resulting partially assigned rule body.
     * <p>
     * Variables in the body that did not occur in head remain unchanged
     * (they don't need renaming, because `query` is a statement, without variables).
     *
     * @param rule the rule to assign variables for.
     * @param query the statement that the head of the rule is unified with.
     * @return atoms of the rule body obtained by application of the unifying
     *         assignment, or {@code Optional.empty()} if there is no match
     *         (no possible assignment that makes the head equal to the query).
     * @throws IllegalArgumentException if `query` is not a statement (has variables).*
     */
    public static Optional<List<Atom>> unify(Rule rule, Atom query) {
        // Find partial assignment that turn rule head into query. If there is none, return Optional.empty().
        Optional<Map<Variable, Constant>> assignmentOpt = unify(rule.head(), query);
        if (assignmentOpt.isEmpty())
            return Optional.empty();
        Map<Variable, Constant> assignment = assignmentOpt.get();

        // Apply this partial assignment to the body.
        List<Atom> partiallyAssignedBody = rule.body().stream()
                .map(atom -> applyAssignment(atom, assignment)).toList();
        return Optional.of(partiallyAssignedBody);
    }

    /**
     * Apply a variable assignment to an atom, returning a new atom.
     * <p>
     * Unassigned variables remain unchanged (they don't need renaming, since
     * only constants are assigned to variables in the unification).
     *
     * @param atom to apply the given {@code assignment} to.
     * @param assignment being applied.
     * @return {@code atom} after application of the assignment.
     */
    public static Atom applyAssignment(Atom atom,
                                           Map<Variable, Constant> assignment) {
        return new Atom(atom.predicate(), atom.elements().stream().map(elem -> {
            if (elem instanceof Variable var && assignment.containsKey(var)) {
                return assignment.get(var);
            } else {
                return elem;
            }
        }).toList());
    }

    /**
     * Apply a variable assignment to a list of atoms, returning a new list of atoms.
     *
     * @param atoms to apply the assignment to.
     * @param assignment that is applied.
     * @return {@code atoms} after assignment.
     */
    public static List<Atom> applyAssignment(
            List<Atom> atoms,
            Map<Variable, Constant> assignment) {
        return atoms.stream().map(atom -> applyAssignment(atom, assignment)).toList();
    }

    /**
     * Return the assignment that applied to `head` gives `query` (or return
     * Optional.empty() if there is no such assignment).
     *
     * @param head an atom which is adapted by variable substitution to match {@code query}.
     * @param query a statement to which {@code head} should match after assignment.
     * @return the assignment that unifies {@code head} and {@code query}, or Optional.empty().
     * @throws IllegalArgumentException if {@code query} is not
     *          a statement (has variables).
     * @throws IllegalArgumentException if {@code query} and {@code head}
     *         have different arity, despite having the same predicate.
     */
    public static Optional<Map<Variable, Constant>> unify(Atom head, Atom query) {
        // Check predicate and arity match.
        if (!head.predicate().equals(query.predicate()))
            return Optional.empty();
        if (head.elements().size() != query.elements().size())
            throw new IllegalArgumentException(
                    "Rule head and query use same predicate but different arity.");

        // Match rule head to query, collecting variable assignments.
        Map<Variable, Constant> assignment = new HashMap<>();
        for (int i = 0; i < head.elements().size(); i++) {
            Element ruleElem = head.elements().get(i);
            Element queryElem = query.elements().get(i);
            if (!(queryElem instanceof Constant)) {
                throw new IllegalArgumentException(
                        "Only statements are allowed as queries (no variables).");
            }
            Constant queryConst = (Constant) queryElem;

            switch (ruleElem) {
            case Constant ruleConst -> {
                // Constants in the rule head must match the query exactly.
                if (!ruleConst.id().equals(queryConst.id()))
                    return Optional.empty();
            }
            case Variable ruleVar -> {
                // Variables in the rule head get assigned to the corresponding query element.
                // Existing assignments must be consistent.
                Constant existingAssignment = assignment.get(ruleVar);
                if (existingAssignment == null) {
                    assignment.put(ruleVar, queryConst);
                } else {
                    if (!existingAssignment.id().equals(queryConst.id()))
                        return Optional.empty();
                }
            }
            }
        }
        return Optional.of(assignment);
    }
}
