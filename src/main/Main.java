package main;

import lexer.Lexer;
import lexer.Token;

import java.io.IOException;
import java.util.List;

import static main.Util.fail;
import static main.Util.getInputFromFile;

/**
 * Main class of interpreter program.
 * @author Heath Dyer
 *
 */
public class Main {
    /** Argument to specify program help */
    private static final String ARG_HELP = "-h";
    /** Argument to display version */
    private static final String ARG_VERSION = "-v";
    /** Argument to specify program help */
    private static final String ARG_TRACING = "-t";
    /** Argument to enable dynamic scoping */
    private static final String ARG_DYNAMIC = "-d";
    /** Argument to disable final expression print */
    private static final String ARG_NO_PRINT = "-np";
    /** Help message for program usage */
    private static final String HELP_MSG =
            "Usage: ./run.sh <options>\n\n"
                    + "  The program (input) is read from stdin.\n\n"
                    + "  Options:\n"
                    + "    -h    print this help message\n"
                    + "    -v    displays version\n"
                    + "    -d    enable dynamic scoping (default lexical)\n"
                    + "    -t    enable tracing during interpreter evaluation\n"
                    + "    -np   disables printing final expression to terminal \n\n"
                    + "  Examples:\n"
                    + ""
                    + "    ./run.sh -h\n"
                    + "    ./parse < example.417 | ./run.sh\n"
                    + "    ./parse < example.417 | ./run.sh -t\n\n";
    /** Version message */
    private static final String VERSION_MSG = "Version 1.6.0";

    public static void main(String[] args) {
        String input = null;
        try {
            input = getInputFromFile("test-files/cp6ex3.417");
        } catch (IOException e) {
            fail("IOException: Failure while opening file.");
        }


        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.lex();
        for (Token token : tokens) {
            lexer.printToken(token);
        }
    }
}