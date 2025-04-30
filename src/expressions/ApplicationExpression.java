package expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Application expression type for interpreter. The first argument is assumed to be
 * the function (either built in function or lambda) and the rest of the args
 * will be passed into the function
 *
 * @author Heath Dyer
 */
public class ApplicationExpression extends Expression {
    /**
     * List of application arguments
     */
    private List<Expression> arguments;

    /**
     * Constructs new integer expression
     */
    public ApplicationExpression(List<Expression> arguments) {
        super(ExpressionType.APPLICATION);
        setArguments(arguments);
    }

    /**
     * Constructs new application expression
     */
    public ApplicationExpression() {
        super(ExpressionType.APPLICATION);
        setArguments(new ArrayList<Expression>());
    }

    /**
     * Sets arguments of application expression. Arguments list cannot be null or empty.
     *
     * @param arguments List of arguments for application
     * @throws IllegalArgumentException Throws if argument list is null or empty
     */
    public void setArguments(List<Expression> arguments) {
        if (arguments == null) {
            throw new IllegalArgumentException("Application arguments cannot be null.");
        }
        this.arguments = arguments;
    }

    /**
     * Returns list of application arguments
     *
     * @return Returns list of application arguments
     */
    public List<Expression> getArguments() {
        return this.arguments;
    }

    /**
     * Adds an Expression to argument list of ApplicationExpression
     *
     * @param argument Expression to add
     */
    public void addArgument(Expression argument) {
        if (argument == null) {
            throw new IllegalArgumentException("Application arguments cannot be null or empty.");
        }
        this.arguments.add(argument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arguments);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return false;
    }

    @Override
    public String toString() {
        String string = "{\"" + APPLICATION_KEYWORD + "\":[";
        for (int i = 0; i < arguments.size(); i++) {
            string += arguments.get(i).toString();
            if (i < arguments.size() - 1) {
                string += ",";
            }
        }
        string += "]}";
        return string;
    }

}
