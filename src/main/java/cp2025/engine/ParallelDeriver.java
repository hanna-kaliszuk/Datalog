package cp2025.engine;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

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

        @Override
        public void run() {
            // wątek działa tak długo, jak długo nie zostanie przetworzona cała kolejka i wszystkie wątki nie zakończą swojego działania
            // albo dopóki nie otrzyma przerwania od głównego wątku
            if (Thread.interrupted()) {
                return;
            }
            try {
                while (!toProcess.isEmpty()) { // dopóki nie wszystkie atomy zostały przetworzone
                    Atom atom = toProcess.take(); // bierzemy kolejny atom do przetworzenia
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
        BlockingQueue<Atom> toProcess; // kolejka atomów do przetworzenia przez wątki robocze



        return Map.of();
    }
}
