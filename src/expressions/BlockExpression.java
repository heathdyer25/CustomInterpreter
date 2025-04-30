package expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Block expression type for interpreter. Stores a list of expressions as its body.
 *
 * @author Heath Dyer
 */
public class BlockExpression extends Expression {
    /**
     * List of expressions contained in the block
     */
    private List<Expression> block;

    /**
     * Constructor for block expression. Creates a new empty block expression.
     */
    public BlockExpression() {
        super(ExpressionType.BLOCK);
        setBlock(new ArrayList<>());
    }

    /**
     * Constructor for block expression. Creates new block with input list of expressions
     *
     * @param block List of expressions contained in new block
     */
    public BlockExpression(List<Expression> block) {
        super(ExpressionType.BLOCK);
        setBlock(block);
    }

    /**
     * Creates a new block expression with a predefined list of expressions
     *
     * @param block List of expressions to set to block
     * @throws IllegalArgumentException List of expressions in block cannot be null
     */
    public void setBlock(List<Expression> block) {
        if (block == null) {
            throw new IllegalArgumentException("Block cannot be null.");
        }
        this.block = block;
    }

    /**
     * Returns the list expressions contained in block.
     *
     * @return Returns list of expressions contained in block
     */
    public List<Expression> getBlock() {
        return this.block;
    }

    public void addToBlock(Expression expression) {
        block.add(expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(block);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("{\"" + BLOCK_KEYWORD + "\":[");
        for (int i = 0; i < block.size(); i++) {
            string.append(block.get(i).toString());
            if (i < block.size() - 1) {
                string.append(",");
            }
        }
        string.append("]}");
        return string.toString();
    }


}
