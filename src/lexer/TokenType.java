package lexer;

/**
 * Holds all possible token types for the lexer
 */
public enum TokenType {
    TOKEN_OPENPAREN,
    TOKEN_CLOSEPAREN,
    TOKEN_OPENBRACE,
    TOKEN_CLOSEBRACE,
    TOKEN_COMMA,
    TOKEN_SEMICOLON,
    TOKEN_COMMENT,
    TOKEN_WS,
    TOKEN_IDENTIFIER,
    TOKEN_INTEGER,
    TOKEN_STRING,
    TOKEN_EOF,
    TOKEN_LAMBDA,
    /** Keywords: (List must start with LAMBDA.) */
    TOKEN_LAMBDA_ALT,
    TOKEN_DEFINITION,
    TOKEN_COND,
    /** Important that arrow precedes equals (a prefix) */
    TOKEN_ARROW,
    TOKEN_EQUALS,
    TOKEN_LET,
    /** Error types */
    TOKEN_PANIC,
    TOKEN_BAD_WS,
    TOKEN_BAD_COMMENT,
    TOKEN_BAD_IDCHAR,
    TOKEN_BAD_IDLEN,
    TOKEN_BAD_STREOF,
    TOKEN_BAD_STRLEN,
    TOKEN_BAD_STRESC,
    TOKEN_BAD_STRCHAR,
    TOKEN_BAD_INTCHAR,
    TOKEN_BAD_INTLEN,
    TOKEN_BAD_CHAR,
    TOKEN_NTOKENS,
}
