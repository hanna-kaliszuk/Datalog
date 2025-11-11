package cp2025.engine;

/**
 * An implementation of {@link AbstractOracle} that performs no actual
 * calculations. This class follows the <em>Null Object</em> design pattern — providing
 * a safe, do-nothing implementation of the {@link AbstractOracle} interface.
 * It makes it possible to use a generic {@link AbstractDeriver} so that it
 * just implements plain Datalog derivability, without extensions.
 *
 * <p>{@code NullOracle} always reports that it serves as an oracle for
 * no predicate, and throws an exception if an oracle calculation is attempted.</p>
 */
public class NullOracle implements AbstractOracle {

    /**
     * @param predicate the {@link Datalog.Predicate} to check for evaluability.
     * @return always {@code false}.
     */
    @Override
    public boolean isCalculatable(Datalog.Predicate predicate) {
        return false;
    }

    /**
     * @param atom the {@link Datalog.Atom} representing the statement to evaluate.
     * @return this method never returns, it always throws.
     * @throws UnsupportedOperationException always thrown to indicate
     *         that calculation is not supported.
     */
    @Override
    public boolean calculate(Datalog.Atom atom) {
        throw new UnsupportedOperationException("NullOracle cannot calculate any atoms.");
    }
}
