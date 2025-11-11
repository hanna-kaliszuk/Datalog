package cp2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import cp2025.engine.Datalog;
import cp2025.engine.Datalog.Atom;
import cp2025.engine.Datalog.Constant;
import cp2025.engine.Datalog.Predicate;
import cp2025.engine.Datalog.Rule;
import cp2025.engine.Datalog.Variable;
import cp2025.engine.Parser;

public class ParserTests {
    @Test
    public void testAtomEquality() {
        Atom a1 = new Atom(new Predicate("parent"), List.of(new Constant("c"), new Variable("X")));
        Atom a2 = new Atom(new Predicate("parent"), List.of(new Constant("c"), new Variable("Y")));
        Atom a3 = new Atom(new Predicate("parent"), List.of(new Constant("c"), new Constant("c")));
        Atom a4 = new Atom(new Predicate("parent"), List.of(new Variable("X"), new Variable("X")));
        Atom a1b = new Atom(new Predicate("parent"), List.of(new Constant("c"), new Variable("X")));

        assertEquals(a1, a1b);
        assertNotEquals(a1, a2);
        assertNotEquals(a1, a3);
        assertNotEquals(a1, a4);
    }

    @Test
    public void testProgramParsing() throws Exception {
        Datalog.Program program = Parser.parseProgram("""
                    Constants: c, c_2
                    Rules:
                        blue(c_2) :- .
                        nice(X, c_2) :- blue(X), blue(c).
                    Queries: nice(c, c_2), blue(c_2)
                """);

        Datalog.Program programExpected = new Datalog.Program(
                List.of(new Constant("c"), new Constant("c_2")),
                List.of(new Rule(new Atom(new Predicate("blue"), List.of(new Constant("c_2"))),
                        List.of()),
                        new Rule(
                                new Atom(new Predicate("nice"),
                                        List.of(new Variable("X"), new Constant("c_2"))),
                                List.of(new Atom(new Predicate("blue"), List.of(new Variable("X"))),
                                        new Atom(new Predicate("blue"),
                                                List.of(new Constant("c")))))),
                List.of(new Atom(new Predicate("nice"),
                        List.of(new Constant("c"), new Constant("c_2"))),
                        new Atom(new Predicate("blue"), List.of(new Constant("c_2")))));

        assertEquals(programExpected.constants(), program.constants());
        assertEquals(programExpected.rules(), program.rules());
        assertEquals(programExpected.queries(), program.queries());
        assertEquals(programExpected, program);
    }

    @Test
    public void testProgramParsingAndPrinting() throws IOException {
        // For all examples in the examples/ directory, parse and then print them,
        // and check that the printed version can be parsed back to the same program.
        List<Path> files = Files.list(Paths.get("examples/"))
                .filter(path -> path.toString().endsWith(".d")).toList();
        for (Path path : files) {
            try {
                Datalog.Program program1 = Parser.parseProgram(path);
                String printed = program1.toString();
                Datalog.Program program2 = Parser.parseProgram(printed);
                assertEquals(program1, program2,
                        "Parsing/printing/parsing cycle failed for " + path);
            } catch (Exception e) {
                fail("Failed to read/print/reread example file: " + path);
            }
        }
    }
}