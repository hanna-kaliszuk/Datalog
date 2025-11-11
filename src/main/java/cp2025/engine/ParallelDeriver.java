package cp2025.engine;

import java.util.*;
import java.util.concurrent.*;

import cp2025.engine.Datalog.*;

public class ParallelDeriver implements AbstractDeriver {
    private final int numWorkers;

    public ParallelDeriver(int numWorkers) { this.numWorkers = numWorkers; }

    private static class Worker implements Runnable {
        private BlockingQueue<Atom> toProcess; // współdzielona kolejka atomów do przerobienia
        private ConcurrentHashMap<Atom, CompletableFuture<Boolean>> knownStatements; // współdzielona mapa do wpisywania policzonych w trakcie wyników
        private ConcurrentHashMap<Atom, Thread> liders; // współdzielona mapa liderów dla każdego obecnie obliczanego atomu
        private Program program;
        private AbstractOracle oracle;

        public Worker(BlockingQueue<Atom> toProcess, ConcurrentHashMap<Atom, CompletableFuture<Boolean>> knownStatements,
                      ConcurrentHashMap<Atom, Thread> liders, Program program, AbstractOracle oracle) {
            this.toProcess = toProcess;
            this.knownStatements = knownStatements;
            this.liders = liders;
            this.program = program;
            this.oracle = oracle;
        }

        private boolean deriveBody(List<Atom> body) throws InterruptedException {
            for (Atom a : body) {
                CompletableFuture<Boolean> newFuture = new CompletableFuture<>();
                CompletableFuture<Boolean> existingFuture = knownStatements.computeIfAbsent(a, at -> new CompletableFuture<>());
                boolean IamLeader = (existingFuture == newFuture);

                if (IamLeader) {
                    toProcess.put(a); // no ktoś się tym zajmie kiedyś
                }

                try {
                    if (!existingFuture.get()) {
                        return false;
                    }
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        }

        @Override
        public void run() {
            // wątek działa tak długo, jak długo nie zostanie przetworzona cała kolejka i wszystkie wątki nie zakończą swojego działania
            // albo dopóki nie otrzyma przerwania od głównego wątku
            if (Thread.interrupted()) {
                return;
            }
            try {
                while (true) { // dopóki nie wszystkie atomy zostały przetworzone
                    Atom atom = null; // bierzemy kolejny atom do przetworzenia
                    try {
                        atom = toProcess.take();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    CompletableFuture<Boolean> newFuture = new CompletableFuture<>();
                    CompletableFuture<Boolean> existingFuture = knownStatements.computeIfAbsent(atom, a -> newFuture);
                    boolean IamLeader = (existingFuture == newFuture); // jeżeli to my jesteśmy liderami, to zanczy
                    // że existingFuture będzie nowo utworzonym CompletableFuturem

                    if (IamLeader) {
                        if (oracle.isCalculatable(atom.predicate())) {
                            // obliczamy tą wartość i wkładamy do mapy
                            boolean value = oracle.calculate(atom);
                            existingFuture.complete(value); // wstawiamy wynik i odblokowywujemy inne wątki czekające
                            // na wynik
                        } else {
                            // samodzielnie wyprowadzamy szukaną wartość
                            // szukamy reguł o tym samym predykacie co atom
                            Atom finalAtom = atom;
                            List<Rule> rules = program.rules().stream().filter(r -> r.head().predicate().equals(finalAtom.predicate())).toList();
                            if (rules.isEmpty()) {
                                existingFuture.complete(false);
                                continue; //??????????? czy return????
                            }

                            for (Rule rule : rules) {
                                // dla każdej reguły sprawdzamy, czy jej ciało jest prawdziwe
                                Optional<List<Atom>> partiallyAssignedBody = Unifier.unify(rule, atom);
                                if (partiallyAssignedBody.isEmpty())
                                    continue;

                                List<Variable> variables = Datalog.getVariables(partiallyAssignedBody.get());
                                FunctionGenerator<Variable, Constant> iterator = new FunctionGenerator<>(variables,
                                        program.constants());

                                for (Map<Variable, Constant> assignment : iterator) {
                                    List<Atom> assignedBody = Unifier.applyAssignment(partiallyAssignedBody.get(),
                                            assignment);
                                    boolean result = deriveBody(assignedBody);
                                    if (result) {
                                        existingFuture.complete(true);
                                        break;
                                    }
                                }
                            }
                            existingFuture.complete(false);
                        }
                    } else {
                        // czekamy na wynik obliczony przez lidera
                        try {
                            boolean value = existingFuture.get(); // blokujemy się w oczekiwaniu na wynik dostarczany
                            // przez lidera
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Map<Atom, Boolean> derive(Program input, AbstractOracle oracle)
            throws InterruptedException {
        ConcurrentHashMap<Atom, CompletableFuture<Boolean>> knownStatements = new ConcurrentHashMap<>(); // tutaj wątki
        // wpisują wyliczone przez siebie wartości tak, żeby nie liczyć ich niepotrzebnie wiele razy
        ConcurrentHashMap<Atom, Thread> liders = new ConcurrentHashMap<>(); // tutaj wątek, który jako pierwszy rozpoczyna
        // liczenie wartości dla danego atomu wpisuje siebie jako lidera = wiadomo, kto jest "odpowiedzialny" za ten atom
        BlockingQueue<Atom> toProcess = new LinkedBlockingQueue<>(); // kolejka atomów do przetworzenia przez wątki robocze

        for (Atom query : input.queries()) {
            toProcess.put(query);
        }

        List<Thread> workers = new ArrayList<>();
        for (int i = 0; i < numWorkers; i++) {
            Thread worker = new Thread(new Worker(toProcess, knownStatements, liders, input, oracle));
            workers.add(worker);
            worker.start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        Map<Atom, Boolean> results = new HashMap<>();
        for (Atom q : input.queries()) {
            try {
                results.put(q, knownStatements.get(q).get());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }


        return results;
//        return Map.of();
    }
}
