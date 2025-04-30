/**
 *
 */
package procedures;

import expressions.DummyExpression;
import expressions.Expression;
import expressions.ExpressionType;
import expressions.StringExpression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Heath Dyer
 *
 * Built in IO procedures for the interpreter. Each procedure must take List<Expressions>
 * as its parameter and return an Expression as its result.
 */
public abstract class IOProcedures {

    /**
     * Prints list of arguments concatenated together with a newline character.
     * @param arguments Takes of arguments as list of expressions to print
     * @return Returns argument printed as StringExpression
     */
    public static Expression print(List<Expression> arguments) {
        StringBuilder print = new StringBuilder();
        for (Expression arg : arguments) {
            if (arg.getType() == ExpressionType.STRING) {
                print.append(((StringExpression) arg).getValue());
            } else {
                print.append(arg.toString());
            }
        }
        System.out.println(print.toString());
        return new StringExpression(print.toString());
    }

    /**
     * Exits the program with error status, prints to std error all arguments
     * @param arguments List of arguments to print to std err
     * @return Returns dummy value but will never be reached
     */
    public static Expression fail(List<Expression> arguments) {
        // Check for no arguments
        if (arguments.size() == 0) {
            System.exit(1);
        }
        StringBuilder message = new StringBuilder();
        // Check that all arguments are IntegerExpressions
        for (Expression exp : arguments) {
            if (exp.getType() == ExpressionType.STRING) {
                message.append(((StringExpression) exp).getValue());
            } else {
                message.append(exp.toString());
            }
        }
        System.err.println(message.toString());
        System.exit(1);
        return new DummyExpression();
    }

    /**
     * Gets all input from terminal (for piping a file mainly)
     * @param arguments Takes no arguments
     * @return returns input from terminal as string
     */
    public static Expression readInput(List<Expression> arguments) {
        // Check for no arguments
        if (arguments.size() != 0) {
            throw new IllegalArgumentException("Procedure input does not take any arguments.");
        }
        //Get all input from standard in
        BufferedReader inputReader = null;
        String input = "";
        try {
            inputReader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = inputReader.readLine()) != null) {
                input += line;
            }
            if (inputReader != null) {
                inputReader.close();
            }
        }
        //Catch IO errors
        catch (IOException e) {
            throw new RuntimeException("Error occured while reading input from terminal.");
        }
        //return as expression
        return new StringExpression(input);
    }

    /**
     * Reads a single line of input from the terminal.
     * @param arguments Takes no arguments
     * @return Returns the line of input as a StringExpression
     */
    public static Expression readLine(List<Expression> arguments) {
        // Check for no arguments
        if (arguments.size() != 0) {
            throw new IllegalArgumentException("Procedure readLine does not take any arguments.");
        }
        // Get a single line of input from standard in
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            input = inputReader.readLine();
        }
        // Catch IO errors
        catch (IOException e) {
            throw new RuntimeException("Error occurred while reading a line from terminal.");
        }
        // Return the input as a StringExpression
        return new StringExpression(input);
    }

    /**
     * Reads the contents of a file and returns it as a StringExpression.
     * @param arguments Takes a single argument: the path to the file.
     * @return Returns the contents of the file as a StringExpression.
     */
    public static Expression readFile(List<Expression> arguments) {
        // Ensure that the procedure takes exactly one argument (the file path)
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure readFile takes exactly one argument: the file path.");
        }

        // Get the file path from the first argument
        String filePath = ((StringExpression) arguments.get(0)).getValue();
        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n"); // Append each line with a newline
            }
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while reading the file: " + filePath);
        }

        // Return the file content as a StringExpression
        return new StringExpression(fileContent.toString());
    }

}
