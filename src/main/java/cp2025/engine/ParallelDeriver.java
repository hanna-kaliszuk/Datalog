package cp2025.engine;

import cp2025.engine.Datalog.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelDeriver implements AbstractDeriver {
    private final int numWorkers;
    // 1. A global flag to signal actual shutdown vs optimization interrupts
    private final AtomicBoolean shutdownSignal = new AtomicBoolean(false);

    public ParallelDeriver(int numWorkers) {
        this.numWorkers = numWorkers;
    }

    @Override
    public Map<Datalog.Atom, Boolean> derive(Datalog.Program input, AbstractOracle oracle) throws InterruptedException {
        Map<Predicate, List<Rule>> predicateToRules = input.rules().stream()
                .collect(java.util.stream.Collectors.groupingBy(rule -> rule.head().predicate()));
        ConcurrentHashMap<Atom, Boolean> knownStatements = new ConcurrentHashMap<>();
        ConcurrentHashMap<Atom, Set<Thread>> activeComputation = new ConcurrentHashMap<>();
        ConcurrentLinkedQueue<Atom> taskQueue = new ConcurrentLinkedQueue<>();
        taskQueue.addAll(input.queries());
        CountDownLatch latch = new CountDownLatch(numWorkers);

        // Reset the signal for this run
        shutdownSignal.set(false);

        List<Thread> workers = new ArrayList<>();
        for (int i = 0; i < numWorkers; i++) {
            workers.add(new Thread(new Worker(input, oracle, taskQueue, predicateToRules, knownStatements,
                    activeComputation, latch, shutdownSignal)));
        }

        for (Thread t : workers) {
            t.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            // 2. Set the signal BEFORE interrupting workers so they know to stop
            shutdownSignal.set(true);
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
        private final AtomicBoolean shutdownSignal;

        public Worker(Program program, AbstractOracle oracle, Queue<Atom> queue,
                      Map<Predicate, List<Rule>> predicateToRules,
                      ConcurrentHashMap<Atom, Boolean> knownStatements,
                      ConcurrentHashMap<Atom, Set<Thread>> activeComputation,
                      CountDownLatch latch,
                      AtomicBoolean shutdownSignal) {
            this.program = program;
            this.oracle = oracle;
            this.taskQueue = queue;
            this.predicateToRules = predicateToRules;
            this.knownStatements = knownStatements;
            this.activeComputation = activeComputation;
            this.localInProgress = new HashSet<>();
            this.latch = latch;
            this.shutdownSignal = shutdownSignal;
        }

        private record DerivationResult(boolean derivable, Set<Atom> failedStatements) {
        }

        @Override
        public void run() {
            try {
                Atom task = null;
                while(true) {
                    // Check for global shutdown
                    if (shutdownSignal.get()) return;

                    // Only poll a new task if we finished the previous one
                    if (task == null) {
                        task = taskQueue.poll();
                    }

                    // If queue is empty and we have no current task, we are done
                    if (task == null) break;

                    try {
                        localInProgress.clear();
                        DerivationResult result = deriveStatement(task);

                        if (result.derivable || result.failedStatements.isEmpty()) {
                            knownStatements.putIfAbsent(task, result.derivable);
                        }

                        // Task completed successfully, clear variable so we poll next time
                        task = null;

                    } catch (InterruptedException e) {
                        // 3. Robust Interruption Handling
                        if (knownStatements.containsKey(task)) {
                            // Case A: Another thread finished this task.
                            // Treat as success, clear task variable to pick up a new one.
                            task = null;
                        } else if (shutdownSignal.get()) {
                            // Case B: The main thread told us to shut down.
                            return;
                        } else {
                            // Case C: STALE INTERRUPT.
                            // We received an interrupt meant for a previous task (or a race condition),
                            // but the current 'task' is not done yet.
                            // We MUST retry 'task'. Do NOT set task = null.

                            // Clear the interrupted status so we can continue processing
                            Thread.interrupted();
                        }
                    }
                }
            } finally {
                latch.countDown();
            }
        }

        private DerivationResult deriveStatement (Atom atom) throws InterruptedException {
            Boolean cachedResult = knownStatements.get(atom);
            if (cachedResult != null)
                return new DerivationResult(cachedResult, Set.of());

            if (Thread.interrupted() || shutdownSignal.get())
                throw new InterruptedException();

            if (localInProgress.contains(atom))
                return new DerivationResult(false, Set.of(atom));

            Set<Thread> myThreadSet = activeComputation.computeIfAbsent(atom, k -> ConcurrentHashMap.newKeySet());
            myThreadSet.add(Thread.currentThread());

            DerivationResult result;
            try {
                localInProgress.add(atom);

                if (oracle.isCalculatable(atom.predicate())) {
                    boolean val = oracle.calculate(atom);
                    result = new DerivationResult(val, Set.of());
                } else {
                    result = deriveNewStatement(atom);
                }

                if (result.derivable || result.failedStatements.isEmpty()) {
                    Boolean existing = knownStatements.putIfAbsent(atom, result.derivable);

                    if (existing == null) {
                        // We solved it first. Remove from active computation and interrupt others.
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
                    // Swallow exception if result exists (cleanup happens in finally)
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