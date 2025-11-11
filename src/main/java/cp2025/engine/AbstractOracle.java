package cp2025.engine;

import cp2025.engine.Datalog.*;

/**
 * A type for components capable of performing derivations of statements outside
 * a Datalog program. Implementations of this interface encapsulate extensions
 * of Datalog with an additional mechanism by which derivability of particular
 * statements may be determined (e.g., by accessing an external database of facts
 * or by a direct check in some semantics assigned to Datalog constants).
 *
 * <p>Instances of {@link AbstractOracle} are used by derivation engines
 * implemented as {@link AbstractDeriver} to determine whether derivability of
 * a statement can be obtained using a mechanism foreign to Datalog derivations,
 * and to perform the derivation in that case.</p>
 */
public interface AbstractOracle {
    /**
     * Determines whether this oracle can decide the derivability of statements
     * that involve a given predicate.
     *
     * This method should return immediately (without performing any extensive
     * or blocking computation).
     *
     * @param predicate the {@link Predicate} to check.
     * @return {@code true} if this oracle can decide derivability of the given predicate;
     *         {@code false} otherwise.
     */
    boolean isCalculatable(Predicate predicate);

    /**
     * Performs the actual derivation for the specified statement.
     *
     * This method may block, for example if it involves complex reasoning,
     * I/O operations, or interaction with external systems.
     *
     * @param statement the {@link Atom} representing the statement to be evaluated.
     * @return {@code true} if the statement is derivable using the oracle;
     *         {@code false} if the statement is non-derivable, according to the oracle.
     * @throws InterruptedException if the calling thread is interrupted.
     */
    boolean calculate(Atom statement) throws InterruptedException;
}
