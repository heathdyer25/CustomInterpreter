/**
 *
 */
package expressions;

import java.util.Objects;

/**
 * Expression object for boolean types
 * @author Heath Dyer
 *
 */
public class BooleanExpression extends Expression {
    /** Value of boolean expression */
    private boolean value;

    /** Constructs new boolean expression with given value */
    public BooleanExpression(boolean value) {
        super(ExpressionType.BOOLEAN);
        setValue(value);
    }

    /**
     * Sets value of the boolean
     * @param value Value to set boolean
     */
    public void setValue(boolean value) {
        this.value = value;
    }

    /**
     * Returns value of the boolean
     * @return Returns value of boolean
     */
    public boolean getValue() {
        return this.value;
    }


    @Override
    public String toString() {
        return value ? "true" : "false";

    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BooleanExpression other = (BooleanExpression) obj;
        return other.getValue() == this.getValue();
    }


}
