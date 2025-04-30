package expressions;

import java.util.Objects;

/**
 * Let expression to bind new identifiers within corresponding block
 *
 * @author Heath Dyer
 */
public class LetExpression extends Expression {
    /**
     * Identifier to bind
     */
    private IdentifierExpression identifier;
    /**
     * Expression to bind to identifier
     */
    private Expression expression;
    /**
     * Code block where new variable is valid
     */
    private BlockExpression block;

    /**
     * Constructs new LetExpression given identifier, expression, and block.
     *
     * @param identifier Identifier to bind
     * @param expression Expression to bind to identifier
     * @param block      Block to evaluate within
     */
    public LetExpression(IdentifierExpression identifier, Expression expression, BlockExpression block) {
        super(ExpressionType.LET);
        setIdentifier(identifier);
        setExpression(expression);
        setBlock(block);
    }

    /**
     * Sets the identifier in the let expression
     *
     * @param identifier Identifier to set
     */
    public void setIdentifier(IdentifierExpression identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Let expression identifier cannot be null.");
        }
        this.identifier = identifier;
    }

    /**
     * Gets identifier for let expression
     *
     * @return Returns the identifier in the let expression
     */
    public IdentifierExpression getIdentifier() {
        return identifier;
    }

    /**
     * Sets the expression to bind to identifier in let expression
     *
     * @param expression Expression to bind in let expression
     */
    public void setExpression(Expression expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Let expression bound expression cannot be null.");
        }
        this.expression = expression;
    }

    /**
     * Gets the expression set in the let expression
     *
     * @return Returns Expression to bind in let expression
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * Sets block of let expression
     *
     * @param block BlockExpression to set as block
     */
    public void setBlock(BlockExpression block) {
        this.block = block;
    }

    /**
     * Gets the block expression of let expression
     *
     * @return Returns BlockExpression of let expression
     */
    public BlockExpression getBlock() {
        return this.block;
    }

    @Override
    public int hashCode() {
        return Objects.hash(block, expression, identifier);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString() {
        return "{\"" + LET_KEYWORD + "\":[" + identifier.toString() + "," + expression.toString() + "," + block.toString() + "]}";
    }

}
