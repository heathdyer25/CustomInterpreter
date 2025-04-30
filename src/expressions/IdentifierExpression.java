package expressions;

import java.util.Objects;

/**
 * Identifier expressions to associate with other expressions
 *
 * @author Heath Dyer
 */
public class IdentifierExpression extends Expression {
    /**
     * Name of the identifier
     */
    private String name;

    /**
     * Constructs new Indentifier with given name
     *
     * @param name Name for identifier
     */
    public IdentifierExpression(String name) {
        super(ExpressionType.IDENTIFIER);
        setName(name);
    }

    /**
     * Sets the name of the identifier
     *
     * @param name Name to set
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Identifier name cannot be null");
        }
        for (String reservedKeyword : RESERVED_KEYWORDS) {
            if (name.equals(reservedKeyword)) {
                throw new IllegalArgumentException("Identifier \"" + name + "\" is a reserved keyword.");
            }
        }
        this.name = name;
    }

    /**
     * Returns name of the identifier
     *
     * @return Name of identifier
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "{\"" + IDENTIFIER_KEYWORD + "\": \"" + name + "\"}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof IdentifierExpression other))
            return false;
        return Objects.equals(name, other.name);
    }

}
