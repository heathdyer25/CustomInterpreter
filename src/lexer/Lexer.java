package lexer;

import java.util.ArrayList;
import java.util.List;

import static main.Util.*;

/**
 * Lexer class for 417 language.
 */
public class Lexer {
    /**
     * Lambda keyword
     */
    public static final String KEYWORD_LAMBDA = "lambda";
    /**
     * Alt lambda keyword
     */
    public static final String KEYWORD_LAMBDA_ALT = "λ";
    /**
     * Lambda keyword
     */
    public static final String KEYWORD_DEFINITION = "def";
    /**
     * Lambda keyword
     */
    public static final String KEYWORD_COND = "cond";
    /**
     * Lambda keyword
     */
    public static final String KEYWORD_LET = "let";

    /**
     * String holding source code
     */
    private String source;
    /**
     * List of possible tokens
     */
    private List<Token> tokens;
    /**
     * Current position of lexer in source string
     */
    private int current;
    /**
     * Current line number of lexer in source string
     */
    private int line;

    /**
     * Constructs a new lexer for the interpreter
     */
    public Lexer() {
    }

    /**
     * Initializes lexer back to default values
     */
    private void init(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
        current = 0;
        line = 1;
    }

    /**
     * Returns the character at the given position from the source string.
     * If the index is invalid, returns null character.
     *
     * @param pos Position it peek
     * @return Character at that position
     */
    private char peekChar(int pos) {
        if (pos < 0 || pos >= source.length()) {
            return '\0';
        }
        return source.charAt(pos);
    }

    /**
     * Matches position in source string to EOF token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateEOF(int pos) {
        return (peekChar(pos) == '\0');
    }

    /**
     * Matches position in source string to Quote for String tokens.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateQuote(int pos) {
        return (peekChar(pos) == '\"');
    }

    /**
     * Matches position in source string to comma token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateComma(int pos) {
        return (peekChar(pos) == ',');
    }

    /**
     * Matches position in source string to Semicolon token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateSemicolon(int pos) {
        return (peekChar(pos) == ';');
    }

    /**
     * Matches position in source string to Newline token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateNewline(int pos) {
        return (peekChar(pos) == '\n');
    }

    /**
     * Matches position in source string to Return character token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateReturn(int pos) {
        return (peekChar(pos) == '\r');
    }

    /**
     * Matches position in source string to space token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateSpace(int pos) {
        return (peekChar(pos) == ' ');
    }

    /**
     * Matches position in source string to Tab character token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateTab(int pos) {
        return (peekChar(pos) == '\t');
    }

    /**
     * Matches position in source string to Open parenthesis token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateOpenParen(int pos) {
        return (peekChar(pos) == '(');
    }

    /**
     * Matches position in source string to close parenthesis token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateCloseParen(int pos) {
        return (peekChar(pos) == ')');
    }

    /**
     * Matches position in source string to open brace token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateOpenBrace(int pos) {
        return (peekChar(pos) == '{');
    }

    /**
     * Matches position in source string to close brace token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateCloseBrace(int pos) {
        return (peekChar(pos) == '}');
    }

    /**
     * Matches position in source string to Minus character.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateMinus(int pos) {
        return (peekChar(pos) == '-');
    }

    /**
     * Matches position in source string to Plus character.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicatePlus(int pos) {
        return (peekChar(pos) == '+');
    }

    /**
     * Matches position in source string to equals character.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateEquals(int pos) {
        return (peekChar(pos) == '=');
    }

    /**
     * Matches position in source string to Arrow token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateArrow(int pos) {
        return (peekChar(pos) == '=' && peekChar(pos + 1) == '>');
    }

    /**
     * Matches position in source string to Digit.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateDigit(int pos) {
        return Character.isDigit(peekChar(pos));
    }

    /**
     * Matches position in source string to EOF token.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateWhitespace(int pos) {
        return (predicateNewline(pos) || predicateSpace(pos) || predicateTab(pos) || predicateReturn(pos));
    }

    /**
     * Matches position in source string to start of Comment.
     *
     * @param pos Position of source string to match
     * @return True if matched, false if not.
     */
    private boolean predicateComment(int pos) {
        return (peekChar(pos) == '/' && peekChar(pos + 1) == '/');
    }

    /**
     * Checks for delimiter predicates at pos in the source string and
     * returns boolean result.
     *
     * @param pos Position in source string
     * @return True if valid match, false not.
     */
    private boolean predicateDelimiter(int pos) {
        return (predicateWhitespace(pos)
                || predicateOpenParen(pos) || predicateCloseParen(pos)
                || predicateOpenBrace(pos) || predicateCloseBrace(pos)
                || predicateComma(pos) || predicateSemicolon(pos)
                || predicateArrow(pos) || predicateEquals(pos)
                || predicateComment(pos) || predicateEOF(pos));
    }


    /**
     * Lexers the source string into a list of tokens. List of tokens is
     * to be used by the parser to create an AST.
     *
     * @return Returns list of tokens for parser
     */
    public List<Token> lex(String source) {
        init(source);
        while (current < source.length()) {
            tokens.add(lexToken());
        }
        return tokens;
    }

    /**
     * Reads the next token from the current position in the source string. Returns
     * as a token object. Returns an error token if an issue has occurred.
     *
     * @return Next Token in source
     */
    private Token lexToken() {
        int start = current;
        if (predicateOpenParen(current)) {
            current++;
            return new Token(TokenType.TOKEN_OPENPAREN, start, 1, line, source.substring(start, current));
        }
        if (predicateCloseParen(current)) {
            current++;
            return new Token(TokenType.TOKEN_CLOSEPAREN, start, 1, line, source.substring(start, current));
        }
        if (predicateOpenBrace(current)) {
            current++;
            return new Token(TokenType.TOKEN_OPENBRACE, start, 1, line, source.substring(start, current));
        }
        if (predicateCloseBrace(current)) {
            current++;
            return new Token(TokenType.TOKEN_CLOSEBRACE, start, 1, line, source.substring(start, current));
        }
        if (predicateWhitespace(current)) {
            return lexWhitespace();
        }
        if (predicateDigit(current) || predicateMinus(current) || predicatePlus(current)) {
            return lexInteger();
        }
        if (predicateQuote(current)) {
            return lexString();
        }
        if (predicateComma(current)) {
            current++;
            return new Token(TokenType.TOKEN_COMMA, start, 1, line, source.substring(start, current));
        }
        if (predicateSemicolon(current)) {
            current++;
            return new Token(TokenType.TOKEN_SEMICOLON, start, 1, line, source.substring(start, current));
        }
        if (predicateArrow(current)) {
            current += 2;
            return new Token(TokenType.TOKEN_ARROW, start, 2, line, source.substring(start, current));
        }
        if (predicateEquals(current)) {
            current++;
            return new Token(TokenType.TOKEN_EQUALS, start, 1, line, source.substring(start, current));
        }
        if (predicateComment(current)) {
            return lexComment();
        }
        if (predicateEOF(current)) {
            current++;
            return new Token(TokenType.TOKEN_EOF, start, 1, line, source.substring(start, current));
        }
        return lexIdentifier();
    }

    /**
     * Reads the next token when it is an integer. Returns
     * as a token object.
     *
     * @return Token with type Integer or Error type
     */
    private Token lexInteger() {
        // Called lexInteger when we weren't  supposed to
        if (!predicateDigit(current) && !predicateMinus(current) && !predicatePlus(current)) {
            throw new RuntimeException("Tried to lex Integer that did not start with digit, plus, or minus.");
        }
        int start = current;
        while (!predicateDelimiter(current)) {
            current++;
        }
        // Integer is only plus or minus?
        if ((predicateMinus(start) || predicatePlus(start)) && current - start == 1) {
            return new Token(TokenType.TOKEN_BAD_INTCHAR, start, current - start, line, source.substring(start, current));
        }
        // Something went wrong...
        if (current == start) {
            throw new RuntimeException("Tried to lex integer when we shouldn't have...");
        }
        // Identifier exceeds max set length
        if (current - start > MAX_INTLEN) {
            return new Token(TokenType.TOKEN_BAD_INTLEN, start, current - start, line, source.substring(start, current));
        }
        return new Token(TokenType.TOKEN_INTEGER, start, current - start, line, source.substring(start, current));
    }

    /**
     * Compares string against possible keywords. If not a match, it's an identifier.
     * Returns corresponding token type.
     *
     * @param str String to compare
     * @return Token type of identifier
     */
    private TokenType isKeyword(String str) {
        return switch (str) {
            case KEYWORD_LAMBDA -> TokenType.TOKEN_LAMBDA;
            case KEYWORD_LAMBDA_ALT -> TokenType.TOKEN_LAMBDA_ALT;
            case KEYWORD_DEFINITION -> TokenType.TOKEN_DEFINITION;
            case KEYWORD_COND -> TokenType.TOKEN_COND;
            case KEYWORD_LET -> TokenType.TOKEN_LET;
            default -> TokenType.TOKEN_IDENTIFIER;
        };
    }

    /**
     * Reads an identifier and returns as token. Will also return
     * keywords as their respective type of token.
     *
     * @return Token with type Identifier or some keyword
     */
    private Token lexIdentifier() {
        StringBuilder string = new StringBuilder();
        int start = current;
        while (!predicateDelimiter(current)) {
            string.append(peekChar(current));
            current++;
        }
        // Called when supposed to be delimiter?
        if (current == start) {
            throw new RuntimeException("Tried to lex identifier when we shouldn't have");
        }
        // Identifier exceeds max set length
        if (current - start > MAX_IDLEN) {
            return new Token(TokenType.TOKEN_BAD_IDLEN, start, current - start, line, source.substring(start, current));
        }
        return new Token(isKeyword(string.toString()), start, current - start, line, source.substring(start, current));
    }

    /**
     * Starts lexing a string from the source content at the current position.
     * Returns string as Token including quotations.
     *
     * @return String as token
     */
    private Token lexString() {
        // String doesn't start with quote?
        if (!predicateQuote(current)) {
            throw new RuntimeException("Tried to lex string that did not start with quote");
        }
        //start building string
        StringBuilder str = new StringBuilder();
        str.append(peekChar(current));
        int start = current++;
        // loop to read characters, account for escapes
        while (peekChar(current) != '\0') {
            if (predicateQuote(current)) {
                str.append(peekChar(current));
                break; // We found end of string
            }
            if ((peekChar(current) == '\\' && (peekChar(current + 1) != '\0'))) {
                switch (peekChar(++current)) {
                    case 'n':
                        str.append('\n');
                        break;
                    case 't':
                        str.append('\t');
                        break;
                    case '"':
                        str.append('\"');
                        break;
                    case '\\':
                        str.append('\\');
                        break;
                    default:
                        str.append(peekChar(current));
                        break; // Unhandled escape, treat literally
                }
            } else {
                str.append(peekChar(current));
            }
            current++;
        }
        //String too long?
        if (current - start > MAX_STRLEN) {
            return new Token(TokenType.TOKEN_BAD_STRLEN, start, current - start, line, str.toString());
        }
        // String ends with EOF
        if (predicateEOF(current)) {
            return new Token(TokenType.TOKEN_BAD_STREOF, start, ++current - start, line, str.toString());
        }
        // String ends with quote?
        if (predicateQuote(current)) {
            return new Token(TokenType.TOKEN_STRING, start, ++current - start, line, str.toString());
        }
        // Some unhandled string condition
        return new Token(TokenType.TOKEN_PANIC, start, current - start, line, str.toString());
    }

    /**
     * Reads whitespace from the current position from the source string
     * and returns a token.
     *
     * @return Token white type TOKEN_WS
     */
    private Token lexWhitespace() {
        int start = current;
        while (predicateWhitespace(current)) {
            if (predicateNewline(current)) line++;
            current++;
        }
        // Called when no whitespace?
        if (current == start) {
            return new Token(TokenType.TOKEN_PANIC, start, 0, line, source.substring(start, 0));
        }
        return new Token(TokenType.TOKEN_WS, start, current - start, line, source.substring(start, current));
    }

    /**
     * Reads comment from the current position from until a new line is encountered or EOF
     *
     * @return Token white type TOKEN_COMMENT
     */
    private Token lexComment() {
        if (!predicateComment(current)) {
            throw new RuntimeException("lexComment() called went it shouldn't have been");
        }
        int start = current;
        current += 2;
        while (!predicateNewline(current) && !predicateEOF(current)) {
            current++;
        }
        //Something went wrong
        if (current == start) {
            throw new RuntimeException("lexComment() called went it shouldn't have been");
        }
        return new Token(TokenType.TOKEN_COMMENT, start, current - start, line, source.substring(start, current));
    }
}
