package cp2025.engine;

import cp2025.engine.Datalog.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class ParallelDeriver implements AbstractDeriver {
    private final int numWorkers;

    public ParallelDeriver(int numWorkers) {
        this.numWorkers = numWorkers;
    }

    @Override
    public Map<Datalog.Atom, Boolean> derive(Datalog.Program input, AbstractOracle oracle) throws InterruptedException {
        Map<Predicate, List<Rule>> predicateToRules = input.rules().stream().collect(java.util.stream.Collectors.groupingBy(rule -> rule.head().predicate()));
        ConcurrentHashMap<Atom, Boolean> knownStatements = new ConcurrentHashMap<>();
        ConcurrentHashMap<Atom, Set<Thread>> activeComputations = new ConcurrentHashMap<>();
        ConcurrentLinkedQueue<Atom> taskQueue = new ConcurrentLinkedQueue<>();
        taskQueue.addAll(input.queries());
        CountDownLatch latch = new CountDownLatch(numWorkers);

        List<Thread> workers = new ArrayList<>();
        for (int i = 0; i < numWorkers; i++) {
            workers.add(new Thread(new Worker(input, oracle, taskQueue, predicateToRules, knownStatements,
                    activeComputations, latch)));
        }
        for (Thread t : workers) {
            t.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            for (Thread t : workers)
                t.interrupt();
            throw e;
        }

        Map<Atom, Boolean> results = new HashMap<>();
        for (Atom query : input.queries()) {
            results.put(query, knownStatements.getOrDefault(query, false));
        }

        return results;
    }

    private class Worker implements Runnable {
        private final Program input;
        private final AbstractOracle oracle;
        private final Queue<Atom> taskQueue;
        private final Map<Predicate, List<Rule>> predicateToRules;
        private final ConcurrentHashMap<Atom, Boolean> knownStatements;
        private final ConcurrentHashMap<Atom, Set<Thread>> activeComputations;
        private final Set<Atom> localInProgressStatements;
        private final CountDownLatch latch;

        public Worker(Program program, AbstractOracle oracle, Queue<Atom> queue, Map<Predicate, List<Rule>> predicateToRules,
                      ConcurrentHashMap<Atom, Boolean> knownStatements, ConcurrentHashMap<Atom, Set<Thread>> activeComputations,
                      CountDownLatch latch) {
            this.input = program;
            this.oracle = oracle;
            this.taskQueue = queue;
            this.predicateToRules = predicateToRules;
            this.knownStatements = knownStatements;
            this.activeComputations = activeComputations;
            this.localInProgressStatements = new HashSet<>();
            this.latch = latch;
        }

        private record DerivationResult(boolean derivable, Set<Atom> failedStatements) {
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (Thread.interrupted()) return;

                    Atom task = taskQueue.poll();
                    if (task == null) break;

                    try {
                        localInProgressStatements.clear();
                        deriveStatement(task);
                    } catch (InterruptedException e) {
                        // jezeli zlapalismy wyjatek to moze on wystapic z 2 powodow
                        // sytuacja A - przerwal nas inny watek, bo policzyl to co chcialismy obliczyc
                        // sytuacja B - globalny interrupt

                        // sprawdzamy, w ktorej sytuacji jestesmy
                        if (knownStatements.containsKey(task)) {
                            // sytuacja A
                            // nic sie nie dzieje, czyscimy flage przerwania i idziemy dalej
                            Thread.interrupted();
                        } else { // konczymy swoje dzialanie
                            return;
                        }
                    }
                }
            } finally {
                latch.countDown();
            }
        }

        private DerivationResult deriveStatement(Atom atom) throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();

            // jezeli jest juz policzone, to po prostu zwracamy wynik
            if (knownStatements.containsKey(atom))
                return new DerivationResult(knownStatements.get(atom), Set.of());

            // jezeli moge obliczyc bezposrednio z wyroczni to to robie
            if (oracle.isCalculatable(atom.predicate())) {
                boolean result = oracle.calculate(atom);
                knownStatements.put(atom, result);
                return new DerivationResult(result, Set.of());
            }

            // sprawdzamy, czy nie ma cyklu - czy ten sam watek nie chcial juz wczesniej tego policzyc
            if (localInProgressStatements.contains(atom))
                return new DerivationResult(false, Set.of(atom));

            // rejestrujemy sie w globalnie aktywnie obliczanych jako odpowiedzialni za wyliczanie tego atomu
            activeComputations.computeIfAbsent(atom, k -> ConcurrentHashMap.newKeySet())
                    .add(Thread.currentThread());
            DerivationResult result = null;

            try {
                // sprawdzamy, czy ktos w miedzyczasie juz tego nie obliczyl
                if (knownStatements.containsKey(atom))
                    return new DerivationResult(knownStatements.get(atom), Set.of());

                // jezeli nie, to zaczynamy obliczenia
                // zapisujemy u siebie, ze zajmujemy sie tym atomem
                localInProgressStatements.add(atom);

                try {
                    result = deriveNewStatement(atom);

                    // po obliczeniu mozemy usunac z lokalnie aktywnych

                    // sprawdzamy jak nam poszlo i dajemy znac innym, ze wynik jest gotowy do uzycia
                    // obliczenia zostaly doprowadzone do konca, gdy wynik jest pewny <=> TRUE lub (FALSE i puste FAILEDSTATEMENTS)
                    if (result.derivable || result.failedStatements.isEmpty()) {
                        knownStatements.put(atom, result.derivable);

                        // usuwamy zapytanie z aktualnie obliczanych i przerywamy wszystkich liczacych
                        Set<Thread> threads = activeComputations.remove(atom);

                        if (threads != null) {
                            for (Thread t : threads) {
                                if (t != Thread.currentThread())
                                    t.interrupt();
                            }
                        }
                    }

                    return result;
                } catch (InterruptedException e) {
                    if (knownStatements.containsKey(atom)) {
                        Thread.interrupted();
                        return new DerivationResult(knownStatements.get(atom), Set.of());
                    } else {
                        throw e;
                    }
                }
            } finally {
                localInProgressStatements.remove(atom);

                Set<Thread> threads = activeComputations.get(atom);
                if (threads != null) {
                    threads.remove(Thread.currentThread()); // usuwam sie z pracujacych nad tym zapytaniem, reszta
                    // pracuje dalej
                    if (threads.isEmpty()) { // jestem ostatnim watkiem pracujacym nad tym zapytaniem
                        activeComputations.remove(atom, threads); // wiec sprzatam mape
                    }
                }
            }
        }

        private DerivationResult deriveNewStatement(Atom atom) throws InterruptedException {
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
    }
}