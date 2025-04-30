/**
 *
 */
package expressions;


/**
 * Abstract super class for working with expressions in the interpreter. Each expression
 * has a type from Enum Type and must implement equals methods.
 *
 * @author Heath Dyer
 *
 */
public abstract class Expression {
    //ATOM TYPE KEYWORDS
    /** Keyword for Identifier type */
    public static final String IDENTIFIER_KEYWORD = "Identifier";
    /** Keyword for String type */
    public static final String STRING_KEYWORD = "String";
    /** Keyword for Lambda type */
    public static final String INTEGER_KEYWORD = "Integer";
    /** Keyword for Boolean type */
    public static final String BOOL_KEYWORD = "Boolean";

    //FORM TYPE KEYWORDS
    /** Keyword for Assignment type */
    public static final String ASSIGNMENT_KEYWORD = "Assignment";
    /** Keyword for String type */
    public static final String LAMBDA_KEYWORD = "Lambda";
    /** Keyword for Cond type */
    public static final String COND_KEYWORD = "Cond";
    /** Keyword for Def type */
    public static final String DEF_KEYWORD = "Def";
    /** Keyword for Let type */
    public static final String LET_KEYWORD = "Let";
    /** Keyword for Application type */
    public static final String APPLICATION_KEYWORD = "Application";
    /** Keyword for Block type */
    public static final String BLOCK_KEYWORD = "Block";

    //OTHER KEYWORDS
    /** Keyword for lambda function parameters */
    public static final String PARAMETERS_KEYWORD = "Parameters";
    /** Keyword for clause of a conditional statement*/
    public static final String CLAUSE_KEYWORD = "Clause";
    /** Keyword for argument list of a lambda function */
    public static final String ARGLIST_KEYWORD = "Arglist";
    /** Keyword for procedure, built in functions for language */
    public static final String PROCEDURE_KEYWORD = "Procedure";
    /** Keyword for list, built in data structure for language */
    public static final String LIST_KEYWORD = "List";

    /** Array of reserved keywords for identifiers */
    public static final String[] RESERVED_KEYWORDS = {"lambda", "λ", "cond", "def", "let", "=>"};

    /** Type of token */
    private ExpressionType expressionType;

    /**
     * Constructor for new Token
     * @throws IllegalArgumentException if Token values are invalid
     */
    public Expression(ExpressionType type) {
        setType(type);
    }

    /**
     * Returns Type of token
     * @return Type of token
     */
    public ExpressionType getType() {
        return expressionType;
    }

    /**
     * Gets name of expression type in ENUM
     * @return Returns name of expression type in enum
     */
    public String getTypeName() {
        return expressionType.toString();
    }


    /**
     * Sets type of token
     * @param type Type of token
     * @throws IllegalArgumentException
     */
    public void setType(ExpressionType type) {
        if (type == null) {
            throw new IllegalArgumentException("Expression type cannot be null.");
        }
        this.expressionType = type;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

}
