package lexer;

/**
 * Holds all possible token types for the lexer.
 * @author Heath Dyer
 */
public enum TokenType {
    OPENPAREN,
    CLOSEPAREN,
    OPENBRACE,
    CLOSEBRACE,
    COMMA,
    SEMICOLON,
    COMMENT,
    WS,
    IDENTIFIER,
    INTEGER,
    STRING,
    EOF,
    LAMBDA,
    /**
     * Keywords: (List must start with LAMBDA.)
     */
    LAMBDA_ALT,
    DEFINITION,
    COND,
    /**
     * Important that arrow precedes equals (a prefix)
     */
    ARROW,
    EQUALS,
    LET,
    /**
     * Error types
     */
    PANIC,
    BAD_WS,
    BAD_COMMENT,
    BAD_IDCHAR,
    BAD_IDLEN,
    BAD_STREOF,
    BAD_STRLEN,
    BAD_STRESC,
    BAD_STRCHAR,
    BAD_INTCHAR,
    BAD_INTLEN,
    BAD_CHAR,
    NTOKENS,
}
