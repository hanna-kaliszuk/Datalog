package cp2025.engine;

import java.util.*;

import cp2025.engine.Datalog.*;


public class ParallelDeriver implements AbstractDeriver {
    private final int numWorkers;

    public ParallelDeriver(int numWorkers) { this.numWorkers = numWorkers; }

    @Override
    public Map<Atom, Boolean> derive(Program input, AbstractOracle oracle)
            throws InterruptedException {

        return Map.of();
    }
}
