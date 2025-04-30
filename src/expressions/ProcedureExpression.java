/**
 *
 */
package expressions;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Operator expression for interpreter. Used for storing built in application functions
 * @author Heath Dyer
 *
 */
public class ProcedureExpression extends Expression {
    /** Function implementation as Java function (List of expressions as arguments, expression as output ) */
    private Function<List<Expression>, Expression> function;

    /**
     * Constructs new operation expressions
     * @param function Function to take as parameter
     */
    public ProcedureExpression(Function<List<Expression>, Expression> function) {
        super(ExpressionType.PROCEDURE);
        setFunction(function);
    }

    /**
     * Sets the function of the operation expressions
     * @param function Function to set
     */
    public void setFunction(Function<List<Expression>, Expression> function) {
        this.function = function;
    }

    /**
     * Returns operator function
     * @return function to return
     */
    public Function<List<Expression>, Expression> getFunction() {
        return this.function;
    }

    /**
     * Applies the operator function to a list of arguments
     * @param arguments Arguments to apply the function to
     * @return Returns the result of the function application
     */
    public Expression apply(List<Expression> arguments) {
        return function.apply(arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(function);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString() {
        return "{\"" + PROCEDURE_KEYWORD + "\":" + function.toString() + "}";
    }

}
