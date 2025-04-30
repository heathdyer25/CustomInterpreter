/**
 *
 */
package expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * List of parameters for lambda expressions. Can contain
 * zero or more identifier expressions.
 * @author Heath Dyer
 *
 */
public class ParametersExpression extends Expression {
    /** List of identifiers for parameters */
    List<IdentifierExpression> parameters;

    /**
     * Constructs new parameters expression with empty list of parameters
     * @param parameters List of identifier expressions to set
     */
    public ParametersExpression() {
        super(ExpressionType.PARAMETERS);
        this.parameters = new ArrayList<IdentifierExpression>();
    }

    /**
     * Constructs new parameters expression given list of parameters
     * @param parameters List of identifier expressions to set
     */
    public ParametersExpression(List<IdentifierExpression> parameters) {
        super(ExpressionType.PARAMETERS);
        setParameters(parameters);
    }

    /**
     * Returns the list of parameters
     * @return List of parameters
     */
    public List<IdentifierExpression> getParameters() {
        return parameters;
    }

    /**
     * Sets list of identifier expressions for parameters
     * @param parameters List of parameters to set
     */
    public void setParameters(List<IdentifierExpression> parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("List of parameters for lambda cannot be null.");
        }
        this.parameters = parameters;
    }

    /**
     * Adds a new parameter to the list of identifiers
     * @param identifier IdentifierExpression to add to list of parameters
     */
    public void addParameter(IdentifierExpression identifier) {
        parameters.add(identifier);
    }

    @Override
    public String toString() {
        String string = "{\"" + PARAMETERS_KEYWORD + "\":[";
        for (int i = 0; i < parameters.size(); i++) {
            string += parameters.get(i).toString();
            if (i < parameters.size() - 1) {
                string += ",";
            }
        }
        string += "]}";
        return string;
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return false;
    }


}
