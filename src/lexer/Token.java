package lexer;

public class Token {
    /** Type of token */
    private final TokenType type;
    /** Position of the token in the source string */
    private final int pos;
    /** Length of the token */
    private final int len;

    /**
     * Constructs a new token given its type and value
     * @param type Type of token
     * @param pos Position in the string of the token
*      @param len Length of the token
     */
    public Token(TokenType type, int pos, int len) {
        this.type = type;
        this.pos = pos;
        this.len = len;
    }

    /**
     * Returns the type of the token
     * @return Returns token type
     */
    public TokenType getType() {
        return type;
    }

    public int getLength() {
        return len;
    }

    public int getPosition() {
        return pos;
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
