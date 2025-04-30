package lexer;

/**
 * Token class for Lexer. Represents meaningful lexical tokens
 * for our parser to use.
 * @author Heath Dyer
 */
public class Token {
    /**
     * Type of token
     */
    private final TokenType type;
    /**
     * Position of the token in the source string
     */
    private final int pos;
    /**
     * Length of the token
     */
    private final int len;
    /**
     * Line number token occurs on
     */
    private final int line;
    /**
     * Contents of token
     */
    private final String value;

    /**
     * Constructs a new token given its type and value
     *
     * @param type Type of token
     * @param pos  Position in the string of the token
     * @param len  Length of the token
     * @param line Line number of token in source code
     * @param value Contents of string
     */
    public Token(TokenType type, int pos, int len, int line, String value) {
        this.type = type;
        this.pos = pos;
        this.len = len;
        this.line = line;
        this.value = value;
    }

    /**
     * Returns the type of the token
     *
     * @return Returns token type
     */
    public TokenType getType() {
        return this.type;
    }

    /**
     * Returns length of token in characters
     * @return length of token in characters
     */
    public int getLength() {
        return this.len;
    }

    /**
     * Gets start position of token in the source string.
     * @return Start position of token in source string
     */
    public int getPosition() {
        return this.pos;
    }

    /**
     * Gets line number of token in the source string.
     * @return Line number of token in source string
     */
    public int getLine() {
        return this.line;
    }

    /**
     * Gets string contents of token.
     * @return String contents of token
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", pos=" + pos +
                ", len=" + len +
                '}';
    }
}
