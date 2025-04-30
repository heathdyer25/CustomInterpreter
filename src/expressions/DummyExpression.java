package expressions;

import java.util.Objects;

/**
 * Dummy expression for definition expression usage
 *
 * @author Heath Dyer
 */
public class DummyExpression extends Expression {

    /**
     * Constructs new dummy value
     */
    public DummyExpression() {
        super(ExpressionType.DUMMY);
    }

    @Override
    public String toString() {
        return "{Dummy Value}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(ExpressionType.DUMMY);
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass();
    }


}
