package cp2025.engine;

import cp2025.engine.Datalog.*;
import java.util.*;
import java.util.concurrent.*;

public class ParallelDeriver implements AbstractDeriver {
    private final int numWorkers;

    public ParallelDeriver(int numWorkers) {
        this.numWorkers = numWorkers;
    }

    @Override
    public Map<Datalog.Atom, Boolean> derive(Datalog.Program input, AbstractOracle oracle) throws InterruptedException {
        Map<Predicate, List<Rule>> predicateToRules = input.rules().stream().collect(java.util.stream.Collectors.groupingBy(rule -> rule.head().predicate()));
        ConcurrentHashMap<Atom, Boolean> knownStatements = new ConcurrentHashMap<>();
        ConcurrentHashMap<Atom, Set<Thread>> activeComputation = new ConcurrentHashMap<>();
        ConcurrentLinkedQueue<Atom> taskQueue = new ConcurrentLinkedQueue<>();
        taskQueue.addAll(input.queries());
        CountDownLatch latch = new CountDownLatch(numWorkers);

        List<Thread> workers = new ArrayList<>();
        for (int i = 0; i < numWorkers; i++) {
            workers.add(new Thread(new Worker(input, oracle, taskQueue, predicateToRules, knownStatements,
                    activeComputation, latch)));
        }
        for (Thread t : workers) {
            t.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            for (Thread t : workers) t.interrupt();
            throw e;
        }

        Map<Atom, Boolean> results = new HashMap<>();
        for (Atom query : input.queries()) {
            results.put(query, knownStatements.getOrDefault(query, false));
        }
        return results;
    }

    private class Worker implements Runnable {
        private final Program program;
        private final AbstractOracle oracle;
        private final Queue<Atom> taskQueue;
        private final Map<Predicate, List<Rule>> predicateToRules;
        private final ConcurrentHashMap<Atom, Boolean> knownStatements;
        private final ConcurrentHashMap<Atom, Set<Thread>> activeComputation;
        private final Set<Atom> localInProgress;
        private final CountDownLatch latch;

        public Worker(Program program, AbstractOracle oracle, Queue<Atom> queue,
                      Map<Predicate, List<Rule>> predicateToRules,
                      ConcurrentHashMap<Atom, Boolean> knownStatements,
                      ConcurrentHashMap<Atom, Set<Thread>> activeComputation, CountDownLatch latch) {
            this.program = program;
            this.oracle = oracle;
            this.taskQueue = queue;
            this.predicateToRules = predicateToRules;
            this.knownStatements = knownStatements;
            this.activeComputation = activeComputation;
            this.localInProgress = new HashSet<>();
            this.latch = latch;
        }

        private record DerivationResult(boolean derivable, Set<Atom> failedStatements) {
        }

        @Override
        public void run() {
            try {
                while(true) {
                    if (Thread.interrupted()) // jezeli ktos kazal watkowi umrzec, to umiera
                        return;

                    // pobieramy zadanie do wykonania jezeli jeszcze jakies jest, a jak nie to umieramy
                    Atom task = taskQueue.poll();
                    if (task == null) break;

                    // tutaj mamy jakis atom do policzenia, wiec to robimy
                    try {
                        localInProgress.clear(); // na wszelki wypadek to czyscimy
                        DerivationResult result = deriveStatement(task);

                        if (result.derivable || result.failedStatements.isEmpty()) {
                            knownStatements.putIfAbsent(task, result.derivable);
                        }
                    } catch (InterruptedException e) {
                        // gdy wychodzimy z deriveStatement przez wyjatek, to moze to byc z 2 powodow:
                        // 1. wyszlismy, bo ktos inny szybciej obliczyl nasze zadanie
                        if (knownStatements.containsKey(task)) {
                            //nic
                        } else { // tutaj dostalismy jakies "duze" przerwanie wiec watek musi umrzec
                            break;
                        }
                    }
                }
            } finally { // jak juz wszystko sie obroci, to dajemy znac, ze wyslalismy sygnal
                latch.countDown();
            }
        }

        private DerivationResult deriveStatement (Atom atom) throws InterruptedException {


            Boolean cachedResult = knownStatements.get(atom);
            if (cachedResult != null)
                return new DerivationResult(cachedResult, Set.of());

            if (Thread.interrupted()) // jak ktos kazal przestac, to przestajemy
                throw new InterruptedException();

            // patrzymy, czy nie jestesmy w petli
            if (localInProgress.contains(atom))
                return new DerivationResult(false, Set.of(atom));

            // rejestrujemy sie jako aktywnie liczacy ten watek
            Set<Thread> myThreadSet = activeComputation.computeIfAbsent(atom, k -> ConcurrentHashMap.newKeySet());
            myThreadSet.add(Thread.currentThread());

            DerivationResult result;
            try {
                localInProgress.add(atom);

                // patrzymy czy da sie wyliczyc bezposrenio z wyroczni
                if (oracle.isCalculatable(atom.predicate())) {
                    boolean val = oracle.calculate(atom);
                    result = new DerivationResult(val, Set.of());
                } else { // jezeli nie, to zaczynamy normalne obliczenia
                    result = deriveNewStatement(atom);
                }

                if (result.derivable || result.failedStatements.isEmpty()) { // mamy wynik deterministyczny (niezwiazany z petla)
                    Boolean existing = knownStatements.putIfAbsent(atom, result.derivable);

                    if (existing == null) { // my zapisalismy wynik - przerywamy pozostalych
                        // usuwamy zapytanie z aktualnie obliczanych i przerywamy wszystkich liczacych
                        Set<Thread> threads = activeComputation.remove(atom);

                        if (threads != null) {
                            for (Thread t : threads) {
                                if (t != Thread.currentThread())
                                    t.interrupt();
                            }
                        }
                    } else {
                        result = new DerivationResult(existing, Set.of());
                    }
                }
                return result;
            } catch (InterruptedException e) {
                Boolean existing = knownStatements.get(atom);
                if (existing != null) {
                    Thread.interrupted();
                    return new DerivationResult(existing, Set.of());
                } else {
                    throw e;
                }
            } finally {
                localInProgress.remove(atom);
                myThreadSet.remove(Thread.currentThread());
                    // pracuje dalej
                    if (myThreadSet.isEmpty()) { // jestem ostatnim watkiem pracujacym nad tym zapytaniem
                        activeComputation.remove(atom, myThreadSet); // wiec sprzatam mape
                    }
            }
        }

        private DerivationResult deriveNewStatement (Atom atom) throws InterruptedException {
            List<Rule> rules = predicateToRules.get(atom.predicate());

            if (rules == null)
                return new DerivationResult(false, Set.of(atom));

            Set<Atom> failedStatements = new HashSet<>();

            for (Rule rule : rules) {
                Optional<List<Atom>> partiallyAssignedBody = Unifier.unify(rule, atom);
                if (partiallyAssignedBody.isEmpty())
                    continue;

                List<Variable> variables = Datalog.getVariables(partiallyAssignedBody.get());
                FunctionGenerator<Variable, Constant> iterator = new FunctionGenerator<>(variables, program.constants());

                for (Map<Variable, Constant> assignment : iterator) {
                    List<Atom> assignedBody = Unifier.applyAssignment(partiallyAssignedBody.get(), assignment);
                    DerivationResult result = deriveBody(assignedBody);

                    if (result.derivable) {
                        return new DerivationResult(true, Set.of());
                    }
                    failedStatements.addAll(result.failedStatements);
                }
            }

            if (!failedStatements.isEmpty()) {
                failedStatements.add(atom);
            }
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
