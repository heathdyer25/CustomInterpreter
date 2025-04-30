/**
 *
 */
package expressions;

import java.util.Objects;

/**
 * Expression type to represent assignments in our language
 * @author Heath Dyer
 *
 */
public class AssignmentExpression extends Expression {
    /** Identifier to bind to */
    private IdentifierExpression identifier;
    /** Expression to bind */
    private Expression expression;

    /**
     * Constructor for assignment expression given identifier and value to bind
     * @param identifier Identifier to bind
     * @param value Value to bind
     */
    public AssignmentExpression(IdentifierExpression identifier, Expression value) {
        super(ExpressionType.ASSIGNMENT);
        setIdentifier(identifier);
        setExpression(value);
    }

    /**
     * Gets the assignment expressions identifier expression
     * @return Returns assignments identifier expression
     */
    public IdentifierExpression getIdentifier() {
        return identifier;
    }

    /**
     * Sets assignment expression's identifier expression
     * @param identifier Sets identifier expression
     */
    public void setIdentifier(IdentifierExpression identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Assignment identifier cannot be null.");
        }
        this.identifier = identifier;
    }

    /**
     * Gets the value in the assignment expression to bind
     * @return Returns value for assignment expression
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * Sets the value in the assignment expression
     * @param expression Value to set
     */
    public void setExpression(Expression expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Assignment expression cannot be null.");
        }
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "{\"" + ASSIGNMENT_KEYWORD + "\":[" + this.getIdentifier().toString()
                + "," + this.getExpression().toString() + "]}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, expression);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }


}
