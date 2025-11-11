package cp2025;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import cp2025.engine.AbstractOracle;
import cp2025.engine.AbstractDeriver;
import cp2025.engine.Datalog;
import cp2025.engine.NullOracle;
import cp2025.engine.ParallelDeriver;
import cp2025.engine.Parser;


public class CorrectnessTests {
    // static AbstractDeriver testedDeriver = new SimpleDeriver();
    static AbstractDeriver testedDeriver = new ParallelDeriver(4);

    @Test
    public void testBasic() throws IOException {
        String contents = """
                    Constants: a, b
                    Rules:    blue(b) :- .
                    Queries:  blue(b), blue(a)
                """;
        checkDeriver(Parser.parseProgram(contents), List.of(true, false), testedDeriver);
    }

    @ParameterizedTest
    @CsvSource({
        "examples/example1.d, 1",
        "examples/example2.d, 1",
        "examples/example3.d, 11",
        "examples/example4.d, 1110",
        "examples/example4a.d, 111",
        "examples/example4b.d, 0",
        "examples/example5.d, 1111",
        "examples/example5a.d, 1010",
        "examples/example5b.d, 0011",
        "examples/example6.d, 0100",
        "examples/example6a.d, 1",
        "examples/example6b.d, 0",
        "examples/example7.d, 0",
        "examples/example8.d, 1",
        "examples/example8a.d, 1",
        "examples/example9.d, 1",
        "examples/exampleA.d, 11",
        "examples/exampleB.d, 0",
        "examples/file.d, 11",
        "examples/10_FailedBodyTriesNextAssignment.d, 01",
        "examples/11_CycledBodyTriesNextAssignment.d, 1",
        "examples/12_CycledBodyMayBeDerivable.d, 11",
        "examples/13_LongCycledBodyMayBeDerivable.d, 111",
        "examples/14_BasicTransitivity.d, 100011",
    })
    public void testFiles(String path, String expectedResults) throws IOException {
        checkDeriver(Parser.parseProgram(Path.of(path)), stringToBool(expectedResults), testedDeriver);
    }

    @Test
    public void testBasicReachability() throws IOException {
        String contents = """
                    Constants: a, b, c, d, e
                    Rules:    reach(a) :- .
                              reach(b) :- reach(a).
                              reach(c) :- reach(b).
                              reach(d) :- reach(e).
                              reach(e) :- reach(d).
                    Queries: reach(a), reach(b), reach(c), reach(d), reach(e)
                """;
        checkDeriver(Parser.parseProgram(contents), List.of(true, true, true, false, false), testedDeriver);
    }

    @Test
    public void testBasicTransitivity() throws IOException {
        String contents = """
                    Constants: a, b, c, d, e
                    Rules:    arc(a, b) :- .
                              arc(b, c) :- .
                              arc(d, e) :- .
                              arc(e, d) :- .
                              reach(X, Y) :- arc(X, Y).
                              reach(X, Y) :- arc(X, Z), reach(Z, Y).
                    Queries: reach(a,c), reach(c,a), reach(a,d), reach(e,b), reach(d,e), reach(e,d)
                """;
        checkDeriver(Parser.parseProgram(contents), List.of(true, false, false, false, true, true), testedDeriver);
    }

    public void checkDeriver(Datalog.Program program, List<Boolean> expectedResults,
            AbstractDeriver deriver) {
        try {
            AbstractOracle oracle = new NullOracle();
            var resultsMap = deriver.derive(program, oracle);
            List<Boolean> results = program.queries().stream().map(resultsMap::get).toList();
            assertEquals(expectedResults, results);
        } catch (InterruptedException e) {
            fail("Derivation was interrupted");
        }
    }

    /** Convert a string of '1' and '0' characters to a list of booleans. */
    private List<Boolean> stringToBool(String strings) {
        return strings.chars()
                .mapToObj(c -> c == '1' ? true : false)
                .toList();
    }
}
