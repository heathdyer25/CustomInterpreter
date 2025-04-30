package lexer;

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

    public int getLength() {
        return this.len;
    }

    public int getPosition() {
        return this.pos;
    }

    public int getLine() {
        return this.line;
    }

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
