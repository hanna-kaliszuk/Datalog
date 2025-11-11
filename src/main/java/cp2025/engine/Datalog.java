package cp2025.engine;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import static java.lang.Character.*;

/**
 * Core Datalog structures: elements, predicates, atoms, rules, and programs.
 * <p>
 * These are immutable records with built-in equals, hashCode, and toString methods.
 * <p>
 * We only add some utility functions and override {@code toString} methods to
 * provide a more compact representation.
 */
public class Datalog {
    /**
     * Element, a.k.a. term: either a constant or a variable.
     */
    public sealed interface Element permits Constant, Variable {

        /**
         * Create an {@code Element} from an identifier string.
         * <p>
         * The type is determined by the first character:
         * uppercase indicates a {@code Variable}, lowercase a {@code Constant}.
         * The string must conform to the naming rules for Datalog elements
         * (all uppercase or all lowercase letters, digits and underscores).
         *
         * @param id the string to interpret as {@code Element}.
         * @return {@code Element} represented by the input string.
         * @throws IllegalArgumentException if the identifier is null or empty,
         *  or does not conform to the naming rules.
         */
        static Element fromString(String id) {
            if (id == null || id.isEmpty()) {
                throw new IllegalArgumentException("Identifier cannot be null or empty");
            }
            Element element =
                Character.isUpperCase(id.charAt(0))
                ? new Variable(id)
                : new Constant(id);
            Datalog.Program.validate(element);
            return element;
        }

        /**
         * @return the string representation of the {@code Element}.
         */
        String id();
    }

    /**
     * Specific subtype of {@code Element}s used for constants.
     * @param id string representation of the {@code Element}.
     */
    public record Constant(String id) implements Element {}

    /**
     * Specific subtype of {@code Element}s used for variables.
     * @param id string representation of the {@code Element}.
     */
    public record Variable(String id) implements Element {}

    /** Predicate, a.k.a. predicate symbol or a relation (symbol). */
    public record Predicate(String id) {}

    /** Atom, a.k.a. literal. Statements are atoms without variables. */
    public record Atom(Predicate predicate, List<Element> elements) {
        @org.jetbrains.annotations.NotNull
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(predicate.id()).append("(");
            for (int i = 0; i < elements.size(); i++)
                sb.append(i > 0 ? ", " : "").append(elements.get(i).id());
            sb.append(")");
            return sb.toString();
        }
    }

    /**
     * Rule, a.k.a. (Horn) clause or implication. A rule with an empty body is
     * sometimes known as a fact.
     *
     * @param head a.k.a. conclusion.
     * @param body a.k.a. premises.
     */
    public record Rule(Atom head, List<Atom> body) {
        @Override
        public @NotNull String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(head).append(" :- ");
            for (int i = 0; i < body.size(); i++)
                sb.append(i > 0 ? ", " : "").append(body.get(i));
            return sb.toString();
        }
    }

    /**
     * Representation of a program that consists of {@code constants},
     * {@code rules} and {@code queries}.
     *
     * @param constants in the program.
     * @param rules in the program.
     * @param queries in the program.
     */
    public record Program(List<Constant> constants, List<Rule> rules, List<Atom> queries) {

        /**
         * @return string representation of the current program in the format
         *         parsable by the program parser.
         */
        @Override
        public @NotNull String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Constants:");
            for (int i = 0; i < this.constants.size(); i++)
                sb.append(i == 0 ? " " : ", ").append(this.constants.get(i).id);
            sb.append("\n");

            sb.append("Rules:\n");
            for (Rule rule : this.rules)
                sb.append("\t").append(rule).append(".\n");

            sb.append("Queries:\n");
            for (int i = 0; i < this.queries.size(); i++) {
                Atom query = this.queries.get(i);
                sb.append("\t").append(query);
                sb.append(i < this.queries.size() - 1 ? ", " : "").append("\n");
            }

            return sb.toString();
        }

        /** Validate the program: check format of variable and constant names,
         * inconsistent arities, undeclared constants, variables in queries.
         *
         * @throws IllegalArgumentException in case any of the
         *         validity conditions is not met.
         */
        public void validate() {
            for (Constant c : constants())
                validate(c);

            HashMap<Predicate, Integer> predicateArities = new HashMap<>();

            for (Rule r : rules()) {
                validate(r.head(), predicateArities, true);
                for (Atom a : r.body())
                    validate(a, predicateArities, true);
            }

            for (Atom q : queries())
                validate(q, predicateArities, false);
        }

        /** Check that the given {@code atom} complies to the rules for
         * atoms:
         * <ul>
         * <li> its predicate name complies to the format of predicate names,</li>
         * <li> all constants used in it are declared in the program,</li>
         * <li> if variables are not allowed – that they do not occur,</li>
         * <li> all uses of a predicate have the same arity.</li>
         * </ul>
         *
         * @param atom to check validity conditions for.
         * @param predicateArities map with so far encountered arities of predicates.
         * @param allowVariables flag that enables for variables the possibility
         *                       to occur.
         * @throws IllegalArgumentException in case any of the validity
         *         conditions is not met.
         */
        private void validate(Atom atom, HashMap<Predicate, Integer> predicateArities,
                boolean allowVariables) {
            // Check names of all predicates and elements.
            validate(atom.predicate());
            for (Element e : atom.elements())
                validate(e);

            // Check that all constants are declared.
            for (Element e : atom.elements())
                if (e instanceof Constant c && !constants.contains(c))
                    throw new IllegalArgumentException("Undeclared constant: " + c);

            // Check for variables in queries if not allowed.
            if (!allowVariables)
                for (Element e : atom.elements())
                    if (e instanceof Variable)
                        throw new IllegalArgumentException(
                                "Queries must be statements (no variables): " + atom);

            // Check arity is consistent in all uses of the predicate.
            int arity = atom.elements().size();
            Integer existingArity = predicateArities.get(atom.predicate());
            if (existingArity == null) {
                predicateArities.put(atom.predicate(), arity);
            } else if (existingArity != arity) {
                throw new IllegalArgumentException("Predicate " + atom.predicate().id()
                        + " used with different arities: " + existingArity + " and " + arity);
            }
        }

        /**
         * Check that the string representation of the given {@code element}
         * complies with naming conventions for constants and variables
         * (contains only letters, digits and underscores; starts with a letter;
         *  constants are lowercase, variables are uppercase).
         *
         * @param element to check compliance for.
         */
        private static void validate(Element element) {
            String id = element.id();
            if (id == null || id.isEmpty())
                throw new IllegalArgumentException("Element id cannot be null or empty");
            if (!Character.isLetter(id.charAt(0)))
                throw new IllegalArgumentException("Element id must start with a letter: " + id);
            java.util.function.IntPredicate isValidElementChar = switch (element) {
            case Constant ignored -> (ch -> isLowerCase(ch) || isDigit(ch) || ch == '_');
            case Variable ignored -> (ch -> isUpperCase(ch) || isDigit(ch) || ch == '_');
            };

            if (!id.chars().allMatch(isValidElementChar))
                throw new IllegalArgumentException(
                        "Element id must all uppercase or all lowercase, digits and underscores: "
                                + id);
        }

        /**
         * Check that the string representation of the given {@code predicate}
         * complies with naming conventions for predicates
         * (contains only letters, digits and underscores; starts with a lowercase letter).
         *
         * @param predicate to check.
         */
        private static void validate(Predicate predicate) {
            String id = predicate.id();
            if (id == null || id.isEmpty())
                throw new IllegalArgumentException("Predicate identifier cannot be null or empty");
            if (!Character.isLowerCase(id.charAt(0)))
                throw new IllegalArgumentException(
                        "Predicate id must start with a lowercase letter: " + id);
            if (!id.chars().allMatch(ch -> Character.isLetterOrDigit(ch) || ch == '_'))
                throw new IllegalArgumentException(
                        "Predicate id must contain only letters, digits, or underscores: " + id);
        }
    }

    /**
     * Get the list of all distinct variables occurring in a list of atoms.
     * @param atoms the list of atoms to retrieve variables from.
     * @return the list of variables that occur in the list of atoms, deduplicated.
     * @throws RuntimeException in case the operation cannot be performed.
     */
    public static List<Variable> getVariables(List<Atom> atoms) {
        return atoms.stream().flatMap(atom -> atom.elements().stream())
                .filter(elem -> elem instanceof Variable).map(elem -> (Variable) elem).distinct()
                .toList();
    }
}
