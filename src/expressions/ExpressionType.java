/**
 *
 */
package expressions;

/**
 * Types of expressions allowed by the interpreter
 * @author Heath Dyer
 *
 */
public enum ExpressionType {
    // form types
    ASSIGNMENT,
    APPLICATION, //function application
    LAMBDA, //lambda expression
    COND, //conditional statements
    BLOCK, //function block
    LET, //identifier declaration statement
    DEFINITION, //function declaration
    // atom types
    IDENTIFIER, //identifier expression
    STRING, //string expression
    INTEGER, //64 bit signed integer (java long)
    BOOLEAN, //boolean expression
    // other
    PARAMETERS, //list of parameters for lambda function
    ARGLIST, //list of arguments for function application
    CLAUSE, //clause for conditional statements
    PROCEDURE, // for built in function types
    LIST, //not supported by parser but supported by functions
    DUMMY //used for def expression implementation
}
