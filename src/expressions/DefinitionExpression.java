/**
 *
 */
package expressions;

import java.util.Objects;

/**
 * Expression to store function definitions for interpreter
 * @author Heath Dyer
 *
 */
public class DefinitionExpression extends Expression {
    /** Identifier associated with function definition  */
    private IdentifierExpression identifier;
    /** Expression associated with function definition */
    private Expression expression;

    /**
     * Constructs a new definition expression given an identifier and associated expression.
     * @param identifier Identifier for function
     * @param expression associated Expression of function
     */
    public DefinitionExpression(IdentifierExpression identifier, Expression expression) {
        super(ExpressionType.DEFINITION);
        setIdentifier(identifier);
        setExpression(expression);
    }

    /**
     * Sets identifier expression of function definition
     * @param identifier Identifier to set
     */
    public void setIdentifier(IdentifierExpression identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Definition identifier cannot be null.");
        }
        this.identifier = identifier;
    }

    /**
     * Returns identifier for function definition
     * @return returns function definition Identifier
     */
    public IdentifierExpression getIdentifier() {
        return this.identifier;
    }

    /**
     * Sets lambda expression of function definition
     * @param lambda Lambda expression to set
     */
    public void setExpression(Expression expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Definition expression cannot be null.");
        }
        this.expression = expression;
    }

    /**
     * Gets lambda expression for function definition
     * @return Returns lambda expression
     */
    public Expression getExpression() {
        return this.expression;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, expression);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "{\"" + DEF_KEYWORD + "\":[" + this.getIdentifier().toString()
                + "," + this.getExpression().toString() + "]}";
    }


}
