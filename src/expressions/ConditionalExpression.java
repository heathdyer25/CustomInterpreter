/**
 *
 */
package expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conditional Expression for interpreter to evaluate
 * @author Heath Dyer
 *
 */
public class ConditionalExpression extends Expression {
    /** Clauses of the conditional expression to evaluate */
    private List<ClauseExpression> clauses;

    /**
     * Constructs a new conditional expression with an empty list of clauses
     */
    public ConditionalExpression() {
        super(ExpressionType.COND);
        setClauses(new ArrayList<ClauseExpression>());
    }

    /**
     * Constructs a new conditional expression given a list of clauses
     * @param clauses List of clauses for conditional
     */
    public ConditionalExpression(List<ClauseExpression> clauses) {
        super(ExpressionType.COND);
        setClauses(clauses);
    }

    /**
     * Sets the conditional expressions list of clauses
     * @param clauses List of clauses to set
     */
    public void setClauses(List<ClauseExpression> clauses) {
        if (clauses == null) {
            throw new IllegalArgumentException("Conditional expression cannot have null list of clauses.");
        }
        this.clauses = clauses;
    }

    /**
     * Returns list of clauses for conditional expression
     * @return Returns list of ClauseExpressions
     */
    public List<ClauseExpression> getClauses() {
        return this.clauses;
    }

    public void addClause(ClauseExpression clause) {
        clauses.add(clause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clauses);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return false;
    }

    @Override
    public String toString() {
        String string = "{\"" + COND_KEYWORD + "\":[";
        for (int i = 0; i < clauses.size(); i++) {
            string += clauses.get(i).toString();
            if (i < clauses.size() - 1) {
                string += ",";
            }
        }
        string += "]}";
        return string;
    }

}
