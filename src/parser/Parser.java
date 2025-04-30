package parser;

import expressions.*;
import lexer.Token;
import lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int current;

    public Parser() {

    }

    public Expression parse(List<Token> tokens) {
        this.tokens = tokens;
        current = 0;
        return parseExpression();
    }

    /**
     * Peeks to the next token without consuming. If the token is whitespace or comment,
     * it will be consumed and the next token will be consumed.
     *
     * @return Returns next non-whitespace/non-comment token without consuming
     */
    private Token peekToken() {
        //make sure there is token to peek
        if (current >= tokens.size()) {
            return null;
        }
        Token token = tokens.get(current);
        // skip the
        while (token.getType() == TokenType.TOKEN_WS || token.getType() == TokenType.TOKEN_COMMENT) {
            readToken();
            if (current >= tokens.size()) {
                return null;
            }
            token = tokens.get(current);
            ;
        }
        return token;
    }

    private Token peekAhead(int count) {
        int i = current + count;
        //make sure there is token to peek
        if (i >= tokens.size()) {
            return null;
        }
        Token token = tokens.get(i);
        // skip the
        while (token.getType() == TokenType.TOKEN_WS || token.getType() == TokenType.TOKEN_COMMENT) {
            if (++i >= tokens.size()) {
                return null;
            }
            token = tokens.get(i);
            ;
        }
        return token;
    }

    /**
     * Consume and return current token
     *
     * @return Returns current token and skips to next
     */
    private Token readToken() {
        if (current >= tokens.size()) {
            return null;
        }
        return tokens.get(current++);
    }

    /**
     * Matches the next token's type against the tokens type given. If there is a match,
     * returns true. Else, throws error.
     *
     * @param types Any number of token types to check
     */
    private void match(TokenType... types) {
        // Build a readable error message if no match
        StringBuilder expected = new StringBuilder();
        for (int i = 0; i < types.length; i++) {
            expected.append(types[i]);
            if (i < types.length - 1) {
                expected.append(" or ");
            }
        }
        //Get token
        Token token = peekToken();
        // No token given
        if (token == null) {
            throw new RuntimeException("Expected " + expected + " but there were no more tokens");
        }
        for (TokenType type : types) {
            if (token.getType() == type) {
                readToken();
                return;
            }
        }
        throw new RuntimeException("Expected " + expected + " but was " + token.getType() + " at line " + token.getLine());
    }

    /**
     * Parse expression following the grammar FORM | ATOM
     *
     * @return Returns parsed expression
     */
    private Expression parseExpression() {
        Token token = peekToken();
        if (token == null) {
            throw new RuntimeException("Bad input. Not tokens to parse");
        }
        Expression exp = switch (token.getType()) {
            // Start of parameters
            case TOKEN_OPENPAREN:
                yield parseParameters();
                // Start of block
            case TOKEN_OPENBRACE:
                yield parseBlock();
                // Keywords, identifiers, and literal values
            case TOKEN_LAMBDA:
            case TOKEN_LAMBDA_ALT:
                yield parseLambda();
            case TOKEN_COND:
                yield parseCond();
            case TOKEN_DEFINITION:
                yield parseDefinition();
            case TOKEN_LET:
                yield parseLet();
            case TOKEN_IDENTIFIER:
                Token p = peekAhead(1);
                if (p != null && p.getType() == TokenType.TOKEN_EQUALS) {
                    yield parseAssignment();
                }
                yield parseIdentifier();
            case TOKEN_STRING:
                yield parseString();
            case TOKEN_INTEGER:
                yield parseInteger();
                // Unexpected Syntax Encountered
            case TOKEN_EQUALS:
                throw new RuntimeException("Spurious equals sign encountered at line " + token.getLine());
            case TOKEN_ARROW:
                throw new RuntimeException("Spurious arrow encountered at line " + token.getLine());
            case TOKEN_CLOSEBRACE:
                throw new RuntimeException("Spurious closing brace encountered at line " + token.getLine());
            case TOKEN_CLOSEPAREN:
                throw new RuntimeException("Spurious closing parenthesis encountered at line " + token.getLine());
            case TOKEN_COMMA:
                throw new RuntimeException("Spurious comma encountered at line " + token.getLine());
            case TOKEN_SEMICOLON:
                throw new RuntimeException("Spurious semicolon encountered at line " + token.getLine());
                // If we reach this some token went unhandled
            default:
                throw new RuntimeException("Error while parsing expression... some unhandled token type: " + token.getType().toString());
        };
        // check if expression is an application
        token = peekToken();
        if (token != null && token.getType() == TokenType.TOKEN_OPENPAREN) {
            //parse application
            exp = parseApplication(exp);
        }
        return exp;
    }


    private ApplicationExpression parseApplication(Expression exp) {
        // consume openparen
        match(TokenType.TOKEN_OPENPAREN);
        // get arglist if any
        List<Expression> args = parseArguments();
        // consume close paren
        match(TokenType.TOKEN_CLOSEPAREN);
        // make sure passed expression is first expression
        args.add(0, exp);
        return new ApplicationExpression(args);
    }

    /**
     * ARGLIST := EXP (',' EXP)*
     *
     * @return
     */
    private List<Expression> parseArguments() {
        List<Expression> args = new ArrayList<Expression>();
        Token token = peekToken();
        // We are already at end of arguments list, empty args list
        if (token.getType() == TokenType.TOKEN_CLOSEPAREN) {
            return args;
        }
        // We have at least one token
        Expression exp = parseExpression();
        args.add(exp);
        token = peekToken();
        while (token.getType() != TokenType.TOKEN_CLOSEPAREN) {
            // should be comma separating
            match(TokenType.TOKEN_COMMA);
            // now we parse next expression
            args.add(parseExpression());
            // move to next token
            token = peekToken();
        }
        return args;
    }

    /**
     * Attempts to parse lambda expression. If invalid, the program will fail.
     *
     * @return Returns new lambda expression.
     */
    private LambdaExpression parseLambda() {
        // Check for lambda
        match(TokenType.TOKEN_LAMBDA, TokenType.TOKEN_LAMBDA_ALT);
        // Check for Open paren
        match(TokenType.TOKEN_OPENPAREN);
        // Check for param list
        ParametersExpression params = parseParameters();
        // Check for close paren
        match(TokenType.TOKEN_CLOSEPAREN);
        // Now read the block that follows
        BlockExpression block = parseBlock();
        // Construct expression and return
        return new LambdaExpression(params, block);
    }

    private ConditionalExpression parseCond() {
        // Check for cond keyword
        match(TokenType.TOKEN_COND);
        //At least one clause, else invalid
        ConditionalExpression cond = new ConditionalExpression();
        cond.addClause(parseClause());
        // get all clauses
        Token token = peekToken();
        while (token != null && token.getType() == TokenType.TOKEN_OPENPAREN) {
            cond.addClause(parseClause());
            token = peekToken();
        }
        return cond;
    }

    private BlockExpression parseBlock() {
        // Check for open brace
        match(TokenType.TOKEN_OPENBRACE);
        // Match expressions until close brace
        BlockExpression block = new BlockExpression();
        Token token = peekToken();
        //Maybe empty block?
        if (token.getType() == TokenType.TOKEN_CLOSEBRACE) {
            match(TokenType.TOKEN_CLOSEBRACE);
            return block;
        }
        // at least one expression in block
        block.addToBlock(parseExpression());
        token = peekToken();
        while (token != null && token.getType() == TokenType.TOKEN_SEMICOLON) {
            match(TokenType.TOKEN_SEMICOLON);
            block.addToBlock(parseExpression());
            token = peekToken();
        }
        // Consume close brace
        match(TokenType.TOKEN_CLOSEBRACE);
        return block;
    }

    /**
     * Matches assignment and returns expression assuming valid.
     *
     * @return Returns new assignment expression
     */
    private AssignmentExpression parseAssignment() {
        // Check for identifier
        IdentifierExpression id = parseIdentifier();
        // Check for equals and consume
        match(TokenType.TOKEN_EQUALS);
        // Get expression to set to
        Expression exp = parseExpression();
        return new AssignmentExpression(id, exp);
    }

    /**
     * Matches definition and returns expression assuming valid.
     *
     * @return Returns new definition expression
     */
    private DefinitionExpression parseDefinition() {
        // Check for definition
        match(TokenType.TOKEN_DEFINITION);
        // Check for identifier
        IdentifierExpression id = parseIdentifier();
        // Check for equals
        match(TokenType.TOKEN_EQUALS);
        // Parse some expression
        Expression exp = parseExpression();
        return new DefinitionExpression(id, exp);
    }


    private ParametersExpression parseParameters() {
        // Check for IDENTIFIER
        ParametersExpression params = new ParametersExpression();
        params.addParameter(parseIdentifier());
        //Check for more ids?
        Token token = peekToken();
        while (token != null && token.getType() == TokenType.TOKEN_COMMA) {
            //consume comma
            match(TokenType.TOKEN_COMMA);
            // consume id
            params.addParameter(parseIdentifier());
            token = peekToken();
        }
        return params;
    }

    /**
     * Assuming valid clause is present, parses clause
     * and returns as Expression
     *
     * @return clause expression to be used with conditionals
     */
    private ClauseExpression parseClause() {
        // Check for open paren
        match(TokenType.TOKEN_OPENPAREN);
        //get some eval exp
        Expression evalExp = parseExpression();
        // Check for arrow
        match(TokenType.TOKEN_ARROW);
        // Match some return exp
        Expression returnExp = parseExpression();
        // Check for close paren
        match(TokenType.TOKEN_CLOSEPAREN);
        // return new boolean expression
        return new ClauseExpression(evalExp, returnExp);
    }

    private LetExpression parseLet() {
        // Check for open paren
        match(TokenType.TOKEN_LET);
        // get identifier
        IdentifierExpression id = parseIdentifier();
        // parse equals
        match(TokenType.TOKEN_EQUALS);
        // get final exp
        Expression exp = parseExpression();
        // is there a block?
        Token token = peekToken();
        if (token != null && token.getType() == TokenType.TOKEN_OPENBRACE) {
            return new LetExpression(id, exp, parseBlock());
        }
        // if not create a new block with dummy expression so we know to desugar
        BlockExpression block = new BlockExpression();
        block.addToBlock(new DummyExpression());
        return new LetExpression(id, exp, block);
    }

    /**
     * Matches identifier and returns expression assuming valid.
     *
     * @return Returns new identifier expression
     */
    private IdentifierExpression parseIdentifier() {
        Token token = peekToken();
        match(TokenType.TOKEN_IDENTIFIER);
        return new IdentifierExpression(token.getValue());
    }

    /**
     * To be called when the next token is an integer. Will consume the token
     * and return as expression
     *
     * @return Returns current integer token as expression
     */
    private IntegerExpression parseInteger() {
        Token token = peekToken();
        match(TokenType.TOKEN_INTEGER);
        try {
            return new IntegerExpression(Long.parseLong(token.getValue()));
        } catch (Exception e) {
            throw new Error("Error converting TOKEN_INTEGER to number at line " + token.getLine());
        }
    }

    /**
     * To be called when the next token is a string. Will consume the token
     * and return as expression
     *
     * @return Returns current string token as expression
     */
    private StringExpression parseString() {
        Token token = peekToken();
        match(TokenType.TOKEN_STRING);
        String value = token.getValue();
        if (value.length() < 2 || value.charAt(0) != '"' || value.charAt(value.length() - 1) != '"') {
            throw new RuntimeException("Parsed String where token was not surrounded by quotations");
        }
        return new StringExpression(token.getValue().substring(1, value.length() - 1));
    }

}
