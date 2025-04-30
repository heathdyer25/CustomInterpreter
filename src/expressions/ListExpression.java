package expressions;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * List expression type to be used with build in list functions
 *
 * @author Heath Dyer
 */
public class ListExpression extends Expression {
    /**
     * List of expressions
     */
    private final List<Expression> expressions;

    /**
     * Constructs new ListExpression with blank list of expressions
     *
     */
    public ListExpression() {
        super(ExpressionType.LIST);
        this.expressions = new LinkedList<>();
    }

    /**
     * Constructs new list of expression with list of expressions
     *
     * @param expressions List of expressions to set
     */
    public ListExpression(List<Expression> expressions) {
        super(ExpressionType.LIST);
        this.expressions = new LinkedList<>(expressions);
    }

    /**
     * Gets list of expressions
     *
     * @return List of expressions
     */
    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{\"" + LIST_KEYWORD + "\":[");
        for (int i = 0; i < expressions.size(); i++) {
			str.append(expressions.get(i).toString());
            if (i < expressions.size() - 1) {
				str.append(",");
            }
        }
		str.append("]}");
        return str.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(expressions);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }


}
