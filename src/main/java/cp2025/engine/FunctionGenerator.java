package cp2025.engine;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Iterable over all functions (in the set-theoretic sense) from a given domain to a given range (codomain).
 */
public class FunctionGenerator<D, R> implements Iterable<Map<D, R>> {

    /** A list that represents the domain of generated functions. */
    private final List<D> domain;
    /** A list that represents the range (codomain) of generated functions. */
    private final List<R> range;

    /**
     * Construct the iterable over the functions.
     *
     * @param domain is the domain of the generated functions.
     * @param range is the range of the generated functions.
     */
    public FunctionGenerator(List<D> domain, List<R> range) {
        this.domain = domain;
        this.range = range;
    }

    /**
     * @return a fresh new iterator over all the functions.
     */
    @Override
    public @NotNull Iterator<Map<D, R>> iterator() { return new FunctionIterator(); }

    /**
     * Iterator over all functions from the {@code domain} to the {@code range}.
     */
    private class FunctionIterator implements Iterator<Map<D, R>> {
        /**
         * Represents the next function to be returned by the iterator,
         * as a mapping from positions in the {@code domain} list
         * to positions in the {@code range} list.
         */
        private final int[] nextIndices;
        /** Flag that indicates whether there remains functions to be generated. */
        private boolean hasNext;

        /**
         * Create a fresh new iterator.
         */
        public FunctionIterator() {
            this.nextIndices = new int[domain.size()];
            this.hasNext = domain.isEmpty() || !range.isEmpty();
        }

        /**
         * @return {@code true} iff there remains some function to be
         *         returned by the iterator.
         */
        @Override
        public boolean hasNext() { return hasNext; }

        /**
         * Generate and return the next function, advancing the iterator.
         * @throws NoSuchElementException iff there is no next function, that is,
         *       iff {@link #hasNext()} would return {@code false}.
         */
        @Override
        public Map<D, R> next() {
            if (!hasNext)
                throw new NoSuchElementException("No more functions to iterate.");

            // The algorithm works by representing each function as a number in a positional number system.
            // The basis (range of digits) of the system is the size of {@code range} list.
            // The number of digits is the size of {@code domain} list.
            // The digits are stored in {@code nextIndices} array.
            // Advancing to the next function corresponds to incrementing this number.
            // Exhausting all functions corresponds to incrementing past the maximum number
            // representable with this many digits.
            // Once all functions have been generated, the {@code hasNext} flag is put down.

            // Build the function from nextIndices.
            Map<D, R> function = IntStream.range(0, domain.size()).boxed().collect(Collectors
                    .toUnmodifiableMap(domain::get, i -> range.get(nextIndices[i])));

            // Update indices for next function.
            for (int i = 0; i < nextIndices.length; i++) {
                if (nextIndices[i] < range.size() - 1) {
                    nextIndices[i]++;
                    break;
                } else {
                    nextIndices[i] = 0;
                    if (i == nextIndices.length - 1) {
                        hasNext = false;
                        break;
                    }
                }
            }

            // If there is no domain, we only have the empty function, once.
            if (nextIndices.length == 0)
                hasNext = false;

            return function;
        }
    }
}
