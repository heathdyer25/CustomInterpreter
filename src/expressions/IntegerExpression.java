package expressions;

import java.util.Objects;

/**
 * Expression type integer for interpreter. Stored in long 64 bit integer value
 *
 * @author Heath Dyer
 */
public class IntegerExpression extends Expression {
    /**
     * Value of integer
     */
    private long value;

    /**
     * Constructs new integer expression
     *
     * @param value value of integer expression
     */
    public IntegerExpression(long value) {
        super(ExpressionType.INTEGER);
        setValue(value);
    }

    /**
     * Sets the value of the integer
     *
     * @param value
     */
    public void setValue(long value) {
        this.value = value;
    }

    /**
     * Returns value of integer
     *
     * @return Returns value of integer
     */
    public long getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof IntegerExpression))
            return false;
        IntegerExpression other = (IntegerExpression) obj;
        return value == other.value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
