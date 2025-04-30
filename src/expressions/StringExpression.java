package expressions;

import java.util.Objects;

/**
 * String Expression for Interpreter. Stores generic string value
 *
 * @author Heath Dyer
 */
public class StringExpression extends Expression {
    /**
     * Stored string
     */
    private String value;

    /**
     * Constructs new string expression with given string value
     *
     * @param value Value to set string as
     */
    public StringExpression(String value) {
        super(ExpressionType.STRING);
        setValue(value);
    }

    /**
     * Sets value of string
     * @param value String value to set for expression
     * @throws IllegalArgumentException if given string value is null
     */
    public void setValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("String expression value cannot be null.");
        }
        this.value = value;
    }

    /**
     * Returns string
     * @return Returns string
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof StringExpression other))
            return false;
        return Objects.equals(value, other.value);
    }

}
