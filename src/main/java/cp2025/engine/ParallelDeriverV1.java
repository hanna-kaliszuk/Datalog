package cp2025.engine;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import cp2025.engine.Datalog.*;

public class ParallelDeriverV1 implements AbstractDeriver {
    private final int numWorkers;

    public ParallelDeriverV1(int numWorkers) { this.numWorkers = numWorkers; }

    @Override
    public Map<Atom, Boolean> derive(Program input, AbstractOracle oracle)
            throws InterruptedException {

        return Map.of();
    }

    private class Worker implements Runnable {
        private final Program input;
        private final AbstractOracle oracle;
        private final Atom initialTask;
        private final Map<Predicate, List<Rule>> predicateToRules;
        private ConcurrentHashMap<Atom, Boolean> knownStatements; // wspoldzielona mapa dla obliczonych juz wartosci
        private ConcurrentHashMap<Atom, Set<Thread>> activeComputations; // wspoldzielona mapa do pilnowania, ktore
        // watki zajmuja sie obliczeniem danej wartosci
        private Set<Atom> localInProgressStatements; // kazdy watek samodzielnie pilnuje, czy nie trafia na petle w
        // wyliczaniu danego atomu

        public Worker(Program program, AbstractOracle oracle, Atom task, Map<Predicate, List<Rule>> predicateToRules,
                      ConcurrentHashMap<Atom, Boolean> knownStatements,
                      ConcurrentHashMap<Atom, Set<Thread>> activeComputations) {
            this.input = program;
            this.oracle = oracle;
            this.initialTask = task;
            this.predicateToRules = predicateToRules;
            this.knownStatements = knownStatements;
            this.activeComputations = activeComputations;
            this.localInProgressStatements = new HashSet<>();
        }

        private record DerivationResult(boolean derivable, Set<Atom> failedStatements) {}

        private DerivationResult deriveStatement(Atom atom) throws InterruptedException {
            // 0. sprawdzamy, czy nie bylo przerwania
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }

            // 1. sprawdzamy, czy dany atom nie zostal juz obliczony albo czy nie jest bezposrednio w wyroczni
            if (knownStatements.containsKey(atom))
                return new DerivationResult(knownStatements.get(atom), Set.of());

            if (oracle.isCalculatable(atom.predicate())) {
                boolean res = oracle.calculate(atom);
                return new DerivationResult(res, Set.of());
            }

            // 2. sprawdzamy, czy atom jest juz w naszym lokalnym zbiorze. Jezeli jest, to mamy doczynienia z cyklem
            // -> zwracamy falsz
            if (localInProgressStatements.contains(atom))
                return new DerivationResult(false, Set.of(atom));

            // 3. zapisujemy siebie jako watek liczacy atom w activeComputations
            activeComputations.computeIfAbsent(atom, k -> ConcurrentHashMap.newKeySet())
                    .add(Thread.currentThread());

            try {
                // 4. sprawdzamy jeszcze raz, czy ktos w miedzyczasie nie obliczyl tego atomu
                if (knownStatements.containsKey(atom))
                    return new DerivationResult(knownStatements.get(atom), Set.of());

                // 5. dodajemy sobie do lokalnego zbioru wyliczanych atomow
                localInProgressStatements.add(atom);

                // 6. obliczamy wynik
                DerivationResult result = deriveNewStatement(atom);

                // 7. obslugujemy sukces
                // jezeli udalo nam sie doprowadzic obliczenia do konca (tj. wynik jest pewny (TRUE lub FALSE i pusty
                // zbior FAILEDSTATEMENTS)), to oznacza to, ze inni moga przerwac swoje obliczenia
                if (result.derivable || result.failedStatements.isEmpty()) {
                    boolean val = result.derivable;

                    // zapisujemy wynik
                    knownStatements.put(atom, val);

                    // powiadamiamy innych
                    Set<Thread> computingThreads = activeComputations.get(atom);
                    if (computingThreads != null) {
                        for (Thread t : computingThreads) {
                            if (t != Thread.currentThread())
                                t.interrupt();
                        }
                    }
                }

                return result;
            } catch (InterruptedException e){
                // jezeli zostalismy przerwani, jest to jedna z 2 mozliwosci
                // A - zostalismy przerwani, bo jakis inny watek obliczyl nasze zapytanie
                // B - przerwanie wynikajace np z zakonczenia programu

                // PRZYPADEK A - przerwal nas inny watek
                if (knownStatements.containsKey(atom)) {
                    Thread.interrupted(); // czyscimy flage przerwania
                    return new DerivationResult(knownStatements.get(atom), Set.of());
                }

                // PRZYPADEK B
                // przekazujemy wyjatek dalej w gore
                throw e;
            }
        }

        private DerivationResult deriveNewStatement(Atom atom) throws InterruptedException {
            // chcemy obliczyc atom na podstawie "rules"
            List<Rule> rules = predicateToRules.get(atom.predicate());

            if (rules == null) {
                return new DerivationResult(false, Set.of(atom));
            }

            Set<Atom> failedStatements = new HashSet<>();

            for (Rule rule : rules) {
                Optional<List<Atom>> partiallyAssignedBody = Unifier.unify(rule, atom);

                if (partiallyAssignedBody.isEmpty())
                    continue;

                List<Variable> variables = Datalog.getVariables(partiallyAssignedBody.get());
                FunctionGenerator<Variable, Constant> iterator = new FunctionGenerator<>(variables, input.constants());

                for (Map<Variable, Constant> assignment : iterator) {
                    List<Atom> assignedBody = Unifier.applyAssignment(partiallyAssignedBody.get(), assignment);
                    DerivationResult result = deriveBody(assignedBody);

                    if (result.derivable) {
                        return new DerivationResult(true, Set.of());
                    }
                    failedStatements.addAll(result.failedStatements);
                }
            }

            failedStatements.add(atom);
            return new DerivationResult(false, failedStatements);
        }

        private DerivationResult deriveBody(List<Atom> body) throws InterruptedException {
            for (Atom statement : body) {
                DerivationResult result = deriveStatement(statement);

                if (!result.derivable)
                    return new DerivationResult(false, result.failedStatements);
            }

            return new DerivationResult(true, Set.of());
        }


        @Override
        public void run() {
            try {
                deriveStatement(this.initialTask);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
