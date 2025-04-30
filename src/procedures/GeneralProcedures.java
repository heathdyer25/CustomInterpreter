package procedures;

import expressions.Expression;
import expressions.StringExpression;

import java.util.List;

/**
 * @author Heath Dyer
 * <p>
 * Built in general procedures for the interpreter. Each procedure must take List<Expressions>
 * as its parameter and return an Expression as its result.
 */
public abstract class GeneralProcedures {

    /**
     * Returns type of expression as string
     *
     * @param arguments List of arguments (1 expression)
     * @return Returns expression type
     */
    public static Expression getType(List<Expression> arguments) {
        // Check for one argument
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure getType must have exactly 1 argument.");
        }
        return new StringExpression(arguments.get(0).getTypeName());
    }
}
