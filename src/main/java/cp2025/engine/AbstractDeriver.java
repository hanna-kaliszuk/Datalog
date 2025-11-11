package cp2025.engine;

import java.util.Map;

/**
 *  The type of a component capable of deriving logical conclusions (answers)
 *  for a given Datalog program extended with an oracle.
 *
 *  <p>This interface abstracts the derivation mechanism itself, allowing
 *  different algorithms or strategies (e.g., deterministic recursive
 *  derivation, deterministic derivation without explicit recursion,
 *  concurrent derivation, etc.) to be implemented behind a common contract.</p>
 */
public interface AbstractDeriver {
    /**
     * For all queries in the input, derive their answers.
     *
     * @param input the {@link Datalog.Program} containing constants, rules,
     *              and queries to evaluate.
     * @param oracle an {@link AbstractOracle} used as an oracle for
     *                   derivability of calculatable statements.
     * @return a {@link Map} mapping each {@link Datalog.Atom} query in the
     *         {@code input} to a {@link Boolean} value — {@code true} if the query is
     *         derivable, {@code false} otherwise.
     * @throws InterruptedException if the derivation process is interrupted
     *                              (by an external thread cancellation request).
     */
    Map<Datalog.Atom, Boolean> derive(Datalog.Program input, AbstractOracle oracle)
            throws InterruptedException;
}
