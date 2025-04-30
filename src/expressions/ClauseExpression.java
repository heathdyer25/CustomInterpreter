package expressions;

import java.util.Objects;

/**
 * Clause expression type for conditional statements. Has an expression to evaluate, and
 * expression to return if the evaluation expression results true.
 *
 * @author Heath Dyer
 */
public class ClauseExpression extends Expression {
    /**
     * Expression in clause to evaluate
     */
    private Expression test;
    /**
     * Expression in cause to return
     */
    private Expression consequent;

    /**
     * Constructs new clause for conditional statements
     *
     * @param evalExp   Expression for clause to evaluate
     * @param returnExp Expression for clause to return
     */
    public ClauseExpression(Expression evalExp, Expression returnExp) {
        super(ExpressionType.CLAUSE);
        setTest(evalExp);
        setConsequent(returnExp);
    }

    /**
     * Sets clause evaluation expression
     *
     * @param evalExp Evalutation expression to set
     */
    public void setTest(Expression test) {
        if (test == null) {
            throw new IllegalArgumentException("The evaluation expression for a clause cannot be null.");
        }
        this.test = test;
    }

    /**
     * Returns clause evaluation expression
     *
     * @return Returns evaluation expression
     */
    public Expression getTest() {
        return this.test;
    }

    /**
     * Return expression of clause to set
     *
     * @param returnExp Reutrns clause return expression
     */
    public void setConsequent(Expression consequent) {
        if (consequent == null) {
            throw new IllegalArgumentException("The return expression for a clause cannot be null.");
        }
        this.consequent = consequent;
    }

    /**
     * Returns return expression of clause
     *
     * @return Returns return expression of clause
     */
    public Expression getConsequent() {
        return this.consequent;
    }

    @Override
    public String toString() {
        return "{\"" + CLAUSE_KEYWORD + "\":[" + test.toString() + "," + consequent.toString() + "]}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(consequent, test);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return false;
    }


}
