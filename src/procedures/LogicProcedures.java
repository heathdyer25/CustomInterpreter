package procedures;

import expressions.BooleanExpression;
import expressions.Expression;
import expressions.ExpressionType;
import expressions.IntegerExpression;

import java.util.List;


/**
 * @author Heath Dyer
 * <p>
 * Built in IO procedures for the interpreter. Each procedure must take List<Expressions>
 * as its parameter and return an Expression as its result.
 */
public abstract class LogicProcedures {

    /**
     * Checks equality of two expressions.
     *
     * @param arguments Takes List<Expressions> as arguments
     * @return Returns Returns BooleanExpression true if equal, false if not
     * @throws IllegalArgumentException Equals/ eq() must have exactly 2 arguments
     */
    public static Expression eq(List<Expression> arguments) {
        // Check for exactly two arguments
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure equals? must have exactly 2 arguments.");
        }
        //check if equal and return boolean operator
        if (arguments.get(0).equals(arguments.get(1))) {
            return new BooleanExpression(true);
        }
        return new BooleanExpression(false);
    }

    /**
     * Checks if the first integer argument is less than the second integer argument
     *
     * @param arguments List of arguments as expressions
     * @return Returns true or false BooleanExpression
     * @throws IllegalArgumentException Throws if not two arguments or arguments not integers
     */
    public static Expression lessThan(List<Expression> arguments) {
        // Check for exactly two arguments
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure lessThan? must have exactly 2 arguments.");
        }
        //check that both arguments are integers
        for (Expression exp : arguments) {
            if (!(exp.getType() == ExpressionType.INTEGER)) {
                throw new IllegalArgumentException("Procedure lessThan? arguments must be of type integer.");
            }
        }
        return ((IntegerExpression) arguments.get(0)).getValue() < ((IntegerExpression) arguments.get(1)).getValue() ? new BooleanExpression(true) : new BooleanExpression(false);
    }

    /**
     * Checks if the first integer argument is greater than the second integer argument
     *
     * @param arguments List of arguments as expressions
     * @return Returns true or false BooleanExpression
     * @throws IllegalArgumentException Throws if not two arguments or arguments not integers
     */
    public static Expression greaterThan(List<Expression> arguments) {
        // Check for exactly two arguments
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure greaterThan? must have exactly 2 arguments.");
        }
        //check that both arguments are integers
        for (Expression exp : arguments) {
            if (!(exp.getType() == ExpressionType.INTEGER)) {
                throw new IllegalArgumentException("Procedure greaterThan? arguments must be of type integer.");
            }
        }
        return ((IntegerExpression) arguments.get(0)).getValue() > ((IntegerExpression) arguments.get(1)).getValue() ? new BooleanExpression(true) : new BooleanExpression(false);
    }

    /**
     * Checks if an expression is equal to zero. Returns true if zero, otherwise false
     *
     * @param arguments Takes list of expressions as arguments
     * @return Returns BooleanExpression true if zero, false if not
     * @throws IllegalArgumentException zero?() must take exactly one expression
     */
    public static Expression isZero(List<Expression> arguments) {
        // Check for one argument
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure zero? must have exactly 1 argument.");
        }
        //check if argumnet is integer type
        if (arguments.get(0).getType() != ExpressionType.INTEGER) {
            throw new IllegalArgumentException("Procedure zero? argument must evalaute as integer type.");
        }
        IntegerExpression integer = (IntegerExpression) arguments.get(0);
        return new BooleanExpression(integer.getValue() == 0);
    }

    /**
     * Performs or operation on two or more boolean expressions
     *
     * @param arguments Boolean arguments to test on
     * @return Returns true if or. false if not
     */
    public static Expression or(List<Expression> arguments) {
        // Check for two argument
        if (arguments.size() < 2) {
            throw new IllegalArgumentException("Procedure or? must have at least 2 arguments.");
        }
        // iterate through all arguments and check for one of them true
        for (int i = 0; i < arguments.size(); i++) {
            //check if argument is boolean type
            if (arguments.get(i).getType() != ExpressionType.BOOLEAN) {
                throw new IllegalArgumentException("Procedure or? arguments must evalaute as boolean type.");
            }
            //check if true
            if (((BooleanExpression) arguments.get(i)).getValue() == true) {
                return new BooleanExpression(true);
            }
        }
        return new BooleanExpression(false);
    }

    /**
     * Performs and operation on two or more boolean expressions
     *
     * @param arguments Boolean arguments to test on
     * @return Returns true if and. false if not
     */
    public static Expression and(List<Expression> arguments) {
        // Check for two argument
        if (arguments.size() < 2) {
            throw new IllegalArgumentException("Procedure and? must have at least 2 arguments.");
        }
        // iterate through all arguments and check for one of them true
        for (int i = 0; i < arguments.size(); i++) {
            //check if argument is boolean type
            if (arguments.get(i).getType() != ExpressionType.BOOLEAN) {
                throw new IllegalArgumentException("Procedure and? arguments must evalaute as boolean type.");
            }
            //check if true
            if (((BooleanExpression) arguments.get(i)).getValue() == false) {
                return new BooleanExpression(false);
            }
        }
        return new BooleanExpression(true);
    }

    /**
     * Checks if an expression is equal to zero. Returns true if zero, otherwise false
     *
     * @param arguments Takes list of expressions as arguments
     * @return Returns BooleanExpression true if zero, false if not
     * @throws IllegalArgumentException zero?() must take exactly one expression
     */
    public static Expression not(List<Expression> arguments) {
        // Check for one argument
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure not? must have exactly 1 argument.");
        }
        //check if argumnet is integer type
        if (arguments.get(0).getType() != ExpressionType.BOOLEAN) {
            throw new IllegalArgumentException("Procedure not? argument must evalaute as boolean type.");
        }
        //check if true
        if (((BooleanExpression) arguments.get(0)).getValue()) {
            return new BooleanExpression(false);
        }
        return new BooleanExpression(true);
    }

}
