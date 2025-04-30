package procedures;

import expressions.*;

import java.util.List;

/**
 * @author Heath Dyer
 * <p>
 * Built in string procedures for the interpreter. Each procedure must take List<Expressions>
 * as its parameter and return an Expression as its result.
 */
public abstract class StringProcedures {

    /**
     * Concatenates all string arguments together and returns expression
     *
     * @param arguments List of expressions
     * @return Returns new string expression
     */
    public static Expression concat(List<Expression> arguments) {
        //check at least two arguments
        if (arguments.size() < 2) {
            throw new IllegalArgumentException("Procedure concat requires at least two arguments.");
        }
        StringBuilder concat = new StringBuilder();
        // Check that all arguments are IntegerExpressions
        for (Expression exp : arguments) {
            if (!(exp.getType() == ExpressionType.STRING)) {
                throw new IllegalArgumentException("Procedure concat arguments must be of type String.");
            }
            concat.append(((StringExpression) exp).getValue());
        }
        return new StringExpression(concat.toString());
    }

    /**
     * Returns 1 character string of character in string at specified index.
     * If out of bounds returns -1.
     *
     * @param arguments Takes 1 string arg and 1 int arg
     * @return Returns resultant 1 char string expression
     */
    public static Expression charAt(List<Expression> arguments) {
        //check for two arguments
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure charAt requires two arguments.");
        }
        //check for correct types
        if (arguments.get(0).getType() != ExpressionType.STRING || arguments.get(1).getType() != ExpressionType.INTEGER) {
            throw new IllegalArgumentException("Procedure charAt takes 1 string argument and 1 integer argument.");
        }
        //check for out of bounds
        try {
            return new StringExpression(Character.toString(((StringExpression) arguments.get(0)).getValue().charAt((int) ((IntegerExpression) arguments.get(1)).getValue())));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Index " + ((IntegerExpression) arguments.get(1)).getValue() + " out of bounds for string " + ((StringExpression) arguments.get(0)).getValue() + ".");
        }
    }

    /**
     * Gets substring of string from start index to end index
     *
     * @param arguments String to check, start index, end index
     * @return Returns resultant substring
     */
    public static Expression substring(List<Expression> arguments) {
        //check for two arguments
        if (arguments.size() != 3) {
            throw new IllegalArgumentException("Procedure substring requires 3 arguments.");
        }
        //check for correct types
        if (arguments.get(0).getType() != ExpressionType.STRING || arguments.get(1).getType() != ExpressionType.INTEGER
                || arguments.get(2).getType() != ExpressionType.INTEGER) {
            throw new IllegalArgumentException("Procedure substring takes 1 string argument and 2 integer arguments.");
        }
        int start = (int) ((IntegerExpression) arguments.get(1)).getValue();
        int end = (int) ((IntegerExpression) arguments.get(2)).getValue();
        String str = ((StringExpression) arguments.get(0)).getValue();
        if (start < 0 || end > str.length() || start > end) {
            throw new IllegalArgumentException("Invalid substring indices.");
        }
        return new StringExpression(str.substring(start, end));
    }

    /**
     * Returns length of string expression
     *
     * @param arguments string expression
     * @return Integer expression of length of string expression
     */
    public static Expression length(List<Expression> arguments) {
        //check for one arguments
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure length requires two arguments.");
        }
        //check for correct types
        if (arguments.get(0).getType() != ExpressionType.STRING) {
            throw new IllegalArgumentException("Procedure length takes 1 string argument.");
        }
        return new IntegerExpression(((StringExpression) arguments.get(0)).getValue().length());
    }

    /**
     * Checks if string is a digit (assumes is one character)
     *
     * @param arguments String to check
     * @return Returns boolean expression
     */
    public static Expression isDigit(List<Expression> arguments) {
        //check for one arguments
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure isDigit? requires two arguments.");
        }
        //check for correct types
        if (arguments.get(0).getType() != ExpressionType.STRING) {
            throw new IllegalArgumentException("Procedure isDigit? takes 1 string argument.");
        }
        //check if string is one character
        String val = ((StringExpression) arguments.get(0)).getValue();
        if (val.length() != 1) {
            throw new IllegalArgumentException("String " + val + " is not 1 character.");
        }
        return new BooleanExpression(Character.isDigit(val.charAt(0)));

    }

    /**
     * Checks if string is a letter (assumes is one character)
     *
     * @param arguments String to check
     * @return Returns boolean expression
     */
    public static Expression isLetter(List<Expression> arguments) {
        //check for one arguments
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure isLetter? requires two arguments.");
        }
        //check for correct types
        if (arguments.get(0).getType() != ExpressionType.STRING) {
            throw new IllegalArgumentException("Procedure isLetter? takes 1 string argument.");
        }
        //check if string is one character
        String val = ((StringExpression) arguments.get(0)).getValue();
        if (val.length() != 1) {
            throw new IllegalArgumentException("String " + val + " is not 1 character.");
        }
        return new BooleanExpression(Character.isLetter(val.charAt(0)));
    }

    /**
     * Parses string to integer, returns false if cannot
     *
     * @param arguments List of expressions to parse (should be one string)
     * @return IntegerExpression with integer value or false BooleanExpresison
     */
    public static Expression parseInt(List<Expression> arguments) {
        //check for one arguments
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure isLetter? requires two arguments.");
        }
        //check for correct types
        if (arguments.get(0).getType() != ExpressionType.STRING) {
            throw new IllegalArgumentException("Procedure isLetter? takes 1 string argument.");
        }
        //parse to integer
        try {
            return new IntegerExpression(Integer.parseInt(((StringExpression) arguments.get(0)).getValue()));
        }
        // false if can't be parsed
        catch (NumberFormatException e) {
            return new BooleanExpression(false);
        }
    }
}
