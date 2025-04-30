package interpreter;

import expressions.Expression;
import expressions.IdentifierExpression;

import java.util.HashMap;
import java.util.Map;

/**
 * Environment class that acts as linked list of maps of bindings
 *
 * @author Heath Dyer
 */
public class Environment {
    /**
     * The map of identifiers to expressions
     */
    private Map<IdentifierExpression, Expression> env;
    /**
     * The parent environment
     */
    private Environment parent;

    /**
     * Creates a new environment node with null parent
     */
    public Environment() {
        setEnvironment(new HashMap<IdentifierExpression, Expression>());
        setParent(null);
    }

    /**
     * Sets the parent node
     *
     * @param parent Parent to set
     */
    public void setParent(Environment parent) {
        this.parent = parent;
    }

    /**
     * Gets the parent node
     *
     * @return Returns parent node
     */
    public Environment getParent() {
        return this.parent;
    }

    /**
     * Sets environment map
     *
     * @param env Environment map to set
     */
    public void setEnvironment(Map<IdentifierExpression, Expression> env) {
        if (env == null) {
            throw new IllegalArgumentException("Environment node cannot have null environment map.");
        }
        this.env = env;
    }

    /**
     * Gets the current environment map
     *
     * @return Returns map of identifier to expressions
     */
    public Map<IdentifierExpression, Expression> getEnvironment() {
        return this.env;
    }
}