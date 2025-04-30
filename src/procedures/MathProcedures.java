/**
 *
 */
package procedures;

import expressions.Expression;
import expressions.ExpressionType;
import expressions.IntegerExpression;

import java.util.List;

/**
 * @author Heath Dyer
 * Built in math procedures for the interpreter. Each procedure must take List<Expressions>
 * as its parameter and return an Expression as its result.
 */
public abstract class MathProcedures {

    /**
     * Adds two Integer expressions together from list of expressions
     * @param arguments Takes List<Expressions> as arguments
     * @return Returns resulting added result as expression
     * @throws IllegalArgumentException Add must have exactly 2 arguments of expression type integer
     */
    public static Expression add(List<Expression> arguments) {
        // Check for exactly two arguments
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure add must have exactly 2 arguments.");
        }
        // Check that all arguments are IntegerExpressions
        long val = 0;
        for (Expression exp : arguments) {
            if (!(exp.getType() == ExpressionType.INTEGER)) {
                throw new IllegalArgumentException("Procedure add arguments must be of type integer.");
            }
            try {
                val = Math.addExact(val, ((IntegerExpression) exp).getValue());
            } catch (ArithmeticException e) {
                throw new ArithmeticException("Detected integer overflow in function add.");
            }
        }
        // Return integer expression with value
        return new IntegerExpression(val);
    }

    /**
     * Subtracts second integer expression from the first integer expression from list of expressions
     * @param arguments Takes List<Expressions> as arguments
     * @return Returns resulting subtracted result as expression
     * @throws IllegalArgumentException Sub must have exactly 2 arguments of expression type integer
     */
    public static Expression sub(List<Expression> arguments) {
        // Check for exactly two arguments
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure sub must have exactly 2 arguments.");
        }
        // Check that all arguments are IntegerExpressions
        for (Expression exp : arguments) {
            if (!(exp.getType() == ExpressionType.INTEGER)) {
                throw new IllegalArgumentException("Procedure sub arguments must be of type integer.");
            }
        }
        long val;
        try {
            val = Math.subtractExact(((IntegerExpression) arguments.get(0)).getValue(), ((IntegerExpression) arguments.get(1)).getValue());
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Detected integer overflow in procedure sub.");
        }
        // Return integer expression with value
        return new IntegerExpression(val);
    }

    /**
     * Multiplies together two integers from list of expressions
     * @param arguments Takes List<Expressions> as arguments
     * @return Returns resulting multiplied result as expression
     * @throws IllegalArgumentException Mul must have exactly 2 arguments of expression type integer
     */
    public static Expression mul(List<Expression> arguments) {
        // Check for exactly two arguments
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure mul must have exactly 2 arguments.");
        }
        // Check that all arguments are IntegerExpressions
        for (Expression exp : arguments) {
            if (!(exp.getType() == ExpressionType.INTEGER)) {
                throw new IllegalArgumentException("Procedure mul arguments must be of type Integer.");
            }
        }
        long val;
        try {
            val = Math.multiplyExact(((IntegerExpression) arguments.get(0)).getValue(), ((IntegerExpression) arguments.get(1)).getValue());
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Detected integer overflow in procedure mul.");
        }
        // Return integer expression with value
        return new IntegerExpression(val);
    }

    /**
     * Divides second integer arg from the first integer arg
     * @param arguments Arguments to apply
     * @return Returns resultant integer expression
     * @throws IllegalArgumentException if there are not 2 arguments or they are not integers
     */
    public static Expression div(List<Expression> arguments) {
        // Check for exactly two arguments
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure div must have exactly 2 arguments.");
        }
        // Check that all arguments are IntegerExpressions
        for (Expression exp : arguments) {
            if (!(exp.getType() == ExpressionType.INTEGER)) {
                throw new IllegalArgumentException("Procedure div arguments must be of type Integer.");
            }
        }
        //divide and return
        return new IntegerExpression(((IntegerExpression) arguments.get(0)).getValue() / ((IntegerExpression) arguments.get(1)).getValue());
    }

    /**
     * Performs modulo (remainder of devision of second arg from first)
     * @param arguments Arguments to apply
     * @return Returns resultant integer expression
     * @throws IllegalArgumentException if there are not 2 arguments or they are not integers
     */
    public static Expression mod(List<Expression> arguments) {
        // Check for exactly two arguments
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure mod must have exactly 2 arguments.");
        }
        // Check that all arguments are IntegerExpressions
        for (Expression exp : arguments) {
            if (!(exp.getType() == ExpressionType.INTEGER)) {
                throw new IllegalArgumentException("Procedure mod arguments must be of type Integer.");
            }
        }
        //modulo and return
        return new IntegerExpression(((IntegerExpression) arguments.get(0)).getValue() % ((IntegerExpression) arguments.get(1)).getValue());
    }


}
