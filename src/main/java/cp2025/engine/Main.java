package cp2025.engine;

import java.util.Map;

/**
 * Example program for running the Datalog engine.
 * Run with:
 *  java -cp target/classes:lib/antlr-4.13.2-complete.jar cp2025.engine.Main < examples/example1.d
 */
public class Main {

    /**
     * Parses a {@link Datalog.Program} from standard input,
     * creates a {@link NullOracle} and a {@link ParallelDeriverV1},
     * runs {@link AbstractDeriver#derive(Datalog.Program, AbstractOracle)}
     * and then prints the results: the status of derivability of queried atoms.
     *
     * @param args system command line, ignored.
     */
    public static void main(String[] args) {
        //noinspection CommentedOutCode
        try {
            //        String contents = """
            //                    Constants: d, e
            //                    Rules:    reach(d) :- reach(e).
            //                              reach(e) :- reach(d).
            //                    Queries: reach(d)
            //                """;
            String contents = new String(System.in.readAllBytes());
            Datalog.Program program = Parser.parseProgram(contents);
            AbstractOracle oracle = new NullOracle();

            System.out.print(program);

            //AbstractDeriver deriver = new SimpleDeriver();
            AbstractDeriver deriver = new ParallelDeriver(5);
            try {
                Map<Datalog.Atom, Boolean> results = deriver.derive(program, oracle);
                for (Datalog.Atom query : program.queries()) {
                    Boolean result = results.get(query);
                    System.out.printf("Query %s: %s%n", query.toString(),
                            result != null && result ? "is derivable" : "is not derivable");
                }
            } catch (InterruptedException e) {
                System.err.println("Derivation was interrupted");
            }
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
