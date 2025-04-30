package main;

import expressions.Expression;
import interpreter.Interpreter;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;

import java.io.IOException;
import java.util.List;

import static main.Util.fail;
import static main.Util.getInput;
import static parser.Desugar.desugar;

/**
 * Main class of interpreter program.
 *
 * @author Heath Dyer
 */
public class Main {
    /**
     * Argument to specify program help
     */
    private static final String ARG_HELP = "-h";
    /**
     * Argument to display version
     */
    private static final String ARG_VERSION = "-v";
    /**
     * Argument to specify program help
     */
    private static final String ARG_TRACING = "-t";
    /**
     * Argument to enable dynamic scoping
     */
    private static final String ARG_DYNAMIC = "-d";
    /**
     * Argument to disable final expression print
     */
    private static final String ARG_NO_PRINT = "-np";
    /**
     * Help message for program usage
     */
    public static final String HELP_MSG =
            """
                    Usage: ./run.sh <options>
                    
                      The program (input) is read from stdin.
                    
                      Options:
                        -h    print this help message
                        -v    displays version
                        -d    enable dynamic scoping (default lexical)
                        -t    enable tracing during interpreter evaluation
                        -np   disables printing final expression to terminal\s
                    
                      Examples:
                        ./run.sh -h
                        ./parse < example.417 | ./run.sh
                        ./parse < example.417 | ./run.sh -t
                    
                    """;
    /**
     * Version message
     */
    public static final String VERSION_MSG = "Version 1.7.0";

    /**
     * Lexer instance
     */
    private static Lexer lexer;
    /**
     * Parser instance
     */
    private static Parser parser;
    /**
     * Interpreter instance
     */
    private static Interpreter interpreter;

    /**
     * Initializes instances of lexer, parser, and interpreter.
     * @param tracing Tracing enabled?
     * @param lexical Lexical scoping enabled?
     */
    public static void init(boolean tracing, boolean lexical) {
        lexer = new Lexer();
        parser = new Parser();
        interpreter = new Interpreter(tracing, lexical);
    }

    /**
     * Full interpreter pipeline. String input is given to the lexer which returns a list of tokens.
     * The tokens are given to the parser who parses into some valid expression. The expression is desugared and
     * given to the interpreter for evaluation.
     *
     * @param input String input from source code
     * @return Evaluated expression
     */
    public static Expression interpret(String input) {
        List<Token> tokens = lexer.lex(input);
        Expression exp = parser.parse(tokens);
        desugar(exp);
        return interpreter.evaluate(exp, interpreter.getInitialEnv());
    }

    /**
     * Main function of Interpreter application
     *
     * @param args Arguments of main from terminal
     */
    public static void main(String[] args) {
        boolean tracing = false;
        boolean lexicalScope = true;
        boolean print = true;
        //Check for any program arguments
        for (String arg : args) {
            //help argument
            switch (arg) {
                case ARG_HELP -> {
                    System.out.println(HELP_MSG);
                    System.exit(0);
                }
                //version argument
                case ARG_VERSION -> {
                    System.out.println(VERSION_MSG);
                    System.exit(0);
                }
                //tracing argument
                case ARG_TRACING -> tracing = true;
                //dynamic scoping argument
                case ARG_DYNAMIC -> lexicalScope = false;
                //no print argument
                case ARG_NO_PRINT -> print = false;
                //otherwise invalid argument
                default -> fail("Invalid argument \"" + arg + "\"\n" + HELP_MSG);
            }
        }

        //Get input from standard in
        String input = null;
        try {
            input = getInput();
        } catch (IOException e) {
            fail("IOException: Failure while reading program input.");
        }
        if (input == null || input.isBlank()) {
            fail("IOException: No input detected.");
        }

        // Try to interpret;
        Expression eval = null;
        try {
            init(tracing, lexicalScope);
            eval = interpret(input);
        } catch (StackOverflowError e) {
            fail("Stack over flow error. Is there infinite recursion in your program?");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        //if value, print to console
        if (eval != null) {
            //if we are printing final expression to terminal
            if (print) {
                System.out.println(eval);
            }
        } else {
            fail("Something went wrong... expression evaluated as null.");
        }

    }
}