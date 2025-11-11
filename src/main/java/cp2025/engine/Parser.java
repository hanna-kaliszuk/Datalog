package cp2025.engine;

import java.io.IOException;
import java.io.Reader;
// import java.util.BitSet;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
// import org.antlr.v4.runtime.atn.ATNConfigSet;
// import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import cp2025.datalog.DatalogLexer;
import cp2025.datalog.DatalogParser;

/** A wrapper around the ANTLR-generated parser to easily produce valid
 * {@code Datalog.Program} objects from string, stream, or file inputs.
 *
 *  <p>This class is stateless and thread-safe.</p>
 */
public class Parser {

    /**
     * Parses a Datalog program from a raw text string.
     *
     * @param input a string containing the Datalog source code.
     * @return a {@link Datalog.Program} instance representing the parsed program.
     * @throws IOException if an I/O error occurs while reading the input.
     * @throws ParseCancellationException if the input contains syntax errors.
     * @throws IllegalArgumentException if the parsed program is invalid (see {@link Datalog.Program#validate()}).
     */
    public static Datalog.Program parseProgram(String input)
            throws IOException, ParseCancellationException, IllegalArgumentException {
        return parseProgram(new java.io.StringReader(input));
    }

    /**
     * Parses a Datalog program from a file located at the specified path.
     * The file is read using UTF-8 encoding.
     *
     * @param filePath the path to the file containing the Datalog source code.
     * @return a {@link Datalog.Program} instance representing the parsed program.
     * @throws IOException if the file cannot be read.
     * @throws ParseCancellationException if the file contains syntax errors.
     * @throws IllegalArgumentException if the parsed program is invalid (see {@link Datalog.Program#validate()}).
     */
    public static Datalog.Program parseProgram(Path filePath)
            throws IOException, ParseCancellationException, IllegalArgumentException {
        try (Reader reader = Files.newBufferedReader(filePath,
                java.nio.charset.StandardCharsets.UTF_8)) {
            return parseProgram(reader);
        }
    }

    /**
     * Parses a Datalog program from a {@link Reader} input stream.
     *
     * <p>This method uses an ANTLR-generated {@link DatalogLexer} and {@link DatalogParser}
     * to build an abstract syntax tree (AST), which is then converted into a
     * {@link Datalog.Program} representation used internally by the system.</p>
     *
     * @param input a {@link Reader} providing the Datalog program source.
     * @return a {@link Datalog.Program} instance representing the parsed program.
     * @throws IOException if an I/O error occurs during reading.
     * @throws ParseCancellationException if syntax errors are encountered.
     * @throws IllegalArgumentException if the parsed program is invalid (see {@link Datalog.Program#validate()}).
     */
    public static Datalog.Program parseProgram(Reader input)
            throws IOException, ParseCancellationException, IllegalArgumentException {
        DatalogLexer lexer = new DatalogLexer(CharStreams.fromReader(input));
        lexer.addErrorListener(new ThrowingErrorListener());
        DatalogParser parser = new DatalogParser(new CommonTokenStream(lexer));
        parser.addErrorListener(new ThrowingErrorListener());
        cp2025.datalog.Absyn.Program ast = (cp2025.datalog.Absyn.Program) parser
                .start_ProgramDef().result;
        return parse(ast);
    }

    /**
     * Converts an abstract syntax tree (AST) of a Datalog program into an internal
     * {@link Datalog.Program} object used by {@link AbstractDeriver}.
     *
     * @param ast the ANTLR-generated AST of the parsed program.
     * @return a validated {@link Datalog.Program} instance.
     */
    private static Datalog.Program parse(cp2025.datalog.Absyn.Program ast) {
        var program = new Datalog.Program(parse((cp2025.datalog.Absyn.Constants) ast.constantsdef_),
                parse((cp2025.datalog.Absyn.Rules) ast.rulesdef_),
                parse((cp2025.datalog.Absyn.Queries) ast.queriesdef_));
        program.validate();
        return program;
    }

    /**
     * Parses a list of constants from a raw AST representation to
     * the representation used by {@link AbstractDeriver}.
     *
     * @param constants the AST node representing constant definitions.
     * @return a list of {@link Datalog.Constant} objects.
     */
    private static List<Datalog.Constant> parse(cp2025.datalog.Absyn.Constants constants) {
        return constants.listlident_.stream().map(Datalog.Constant::new).toList();
    }

    /**
     * Parses a list of rule definitions from a raw AST representation to
     * the representation used by {@link AbstractDeriver}.
     *
     * @param rules the AST node representing Datalog rules.
     * @return a list of {@link Datalog.Rule} objects.
     */
    private static List<Datalog.Rule> parse(cp2025.datalog.Absyn.Rules rules) {
        return rules.listruledef_.stream().map((r) -> parse((cp2025.datalog.Absyn.Rule) r))
                .toList();
    }

    /**
     * Parses a single Datalog rule from a raw AST representation to
     * the representation used by {@link AbstractDeriver}.
     *
     * @param r the AST node representing a single rule.
     * @return a {@link Datalog.Rule} instance containing the head and body atoms.
     */
    private static Datalog.Rule parse(cp2025.datalog.Absyn.Rule r) {
        Datalog.Atom head = parse((cp2025.datalog.Absyn.Atom) r.atomdef_);
        List<Datalog.Atom> body = r.listatomdef_.stream()
                .map((q) -> parse((cp2025.datalog.Absyn.Atom) q)).toList();
        return new Datalog.Rule(head, body);
    }

    /**
     * Parses a Datalog atom (predicate with arguments) from a raw AST
     * representation to the representation used by {@link AbstractDeriver}.
     *
     * @param atom the AST node representing a Datalog atom.
     * @return a {@link Datalog.Atom} instance.
     */
    private static Datalog.Atom parse(cp2025.datalog.Absyn.Atom atom) {
        Datalog.Predicate predicate = parse(atom.predicatedef_);
        List<Datalog.Element> elements = atom.listelement_.stream().map(Parser::parse)
                .toList();
        return new Datalog.Atom(predicate, elements);
    }

    /**
     * Parses a Datalog element, which can be a constant or a variable, from
     * a raw AST representation to the representation used by {@link AbstractDeriver}.
     *
     * @param arg the AST node representing an element.
     * @return a {@link Datalog.Element} instance.
     */
    private static Datalog.Element parse(cp2025.datalog.Absyn.Element arg) {
        return switch (arg) {
        case cp2025.datalog.Absyn.ConstElement a -> new Datalog.Constant(a.lident_);
        case cp2025.datalog.Absyn.VarElement a -> new Datalog.Variable(a.uident_);
        default -> throw new IllegalArgumentException("Unknown Element type" + arg);
        };
    }

    /**
     * Parses a predicate definition from a raw AST representation to the
     * representation used by {@link AbstractDeriver}.
     *
     * @param predicate the AST node representing a predicate.
     * @return a {@link Datalog.Predicate} instance.
     */
    private static Datalog.Predicate parse(cp2025.datalog.Absyn.PredicateDef predicate) {
        return switch (predicate) {
        case cp2025.datalog.Absyn.LPredicate p -> new Datalog.Predicate(p.lident_);
        case cp2025.datalog.Absyn.LSPredicate p -> new Datalog.Predicate(p.lsident_);
        default -> throw new IllegalArgumentException("Unknown PredicateDef type" + predicate);
        };
    }

    /**
     * Parses a list of queries from a raw AST representation to the
     * representation used by {@link AbstractDeriver}.
     *
     * @param queries the AST node representing queries.
     * @return a list of {@link Datalog.Atom} objects representing query statements.
     */
    private static List<Datalog.Atom> parse(cp2025.datalog.Absyn.Queries queries) {
        return queries.liststatementdef_.stream()
                .map((q) -> parse((cp2025.datalog.Absyn.Statement) q)).toList();
    }

    /**
     * Parses a single query statement from a raw AST representation to the
     *      * representation used by {@link AbstractDeriver}.
     *
     * @param statement the AST node representing a query.
     * @return a {@link Datalog.Atom} corresponding to the query.
     */
    private static Datalog.Atom parse(cp2025.datalog.Absyn.Statement statement) {
        Datalog.Predicate predicate = parse(statement.predicatedef_);
        List<Datalog.Element> elements = statement.listlident_.stream()
                .map(Datalog.Element::fromString).toList();
        return new Datalog.Atom(predicate, elements);
    }

    /**
     * Custom ANTLR error listener that throws a {@link ParseCancellationException}
     * immediately when a syntax error is encountered, halting the parsing process.
     */
    private static class ThrowingErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> r, Object o, int line, int column, String msg,
                RecognitionException e) throws ParseCancellationException {
            throw new ParseCancellationException("line " + line + ":" + column + " " + msg);
        }
    }
}
