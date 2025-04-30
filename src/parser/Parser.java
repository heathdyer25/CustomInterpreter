package parser;

import expressions.*;
import lexer.Token;
import lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser class for interpreter. Takes a list of tokens produced by
 * the lexer and turns it into Expressions, a readable format for our
 * interpreter to evaluate.
 * @author Heath Dyer
 */
public class Parser {
    /** List of tokens for parser methods */
    private List<Token> tokens;
    /** Index of current token in list */
    private int current;

    /**
     * Constructs new parser class
     */
    public Parser() {}

    /**
     * Parses the list of tokens against the grammar and returns
     * nested expressions
     * @param tokens List of tokens to pars
     * @return Returned expression to evaluate.
     */
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
        while (token.getType() == TokenType.WS || token.getType() == TokenType.COMMENT) {
            current++;
            if (current >= tokens.size()) {
                return null;
            }
            token = tokens.get(current);
        }
        return token;
    }

    /**
     * Peaks ahead to the next token. If the token is whitespace or comment,
     * it will be consumed and the next token will be consumed.
     * @return Returns the next token ahead
     */
    private Token peekAhead() {
        int i = current + 1;
        //make sure there is token to peek
        if (i >= tokens.size()) {
            return null;
        }
        Token token = tokens.get(i);
        // skip the
        while (token.getType() == TokenType.WS || token.getType() == TokenType.COMMENT) {
            if (++i >= tokens.size()) {
                return null;
            }
            token = tokens.get(i);
        }
        return token;
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
                current++;
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
            throw new RuntimeException("Bad input. No tokens to parse.");
        }
        Expression exp = switch (token.getType()) {
            // Start of parameters
            case OPENPAREN:
                yield parseParameters();
                // Start of block
            case OPENBRACE:
                yield parseBlock();
                // Keywords, identifiers, and literal values
            case LAMBDA:
            case LAMBDA_ALT:
                yield parseLambda();
            case COND:
                yield parseCond();
            case DEFINITION:
                yield parseDefinition();
            case LET:
                yield parseLet();
            case IDENTIFIER:
                Token p = peekAhead();
                if (p != null && p.getType() == TokenType.EQUALS) {
                    yield parseAssignment();
                }
                yield parseIdentifier();
            case STRING:
                yield parseString();
            case INTEGER:
                yield parseInteger();
                // Unexpected Syntax Encountered
            case EQUALS:
                throw new RuntimeException("Spurious equals sign encountered at line " + token.getLine());
            case ARROW:
                throw new RuntimeException("Spurious arrow encountered at line " + token.getLine());
            case CLOSEBRACE:
                throw new RuntimeException("Spurious closing brace encountered at line " + token.getLine());
            case CLOSEPAREN:
                throw new RuntimeException("Spurious closing parenthesis encountered at line " + token.getLine());
            case COMMA:
                throw new RuntimeException("Spurious comma encountered at line " + token.getLine());
            case SEMICOLON:
                throw new RuntimeException("Spurious semicolon encountered at line " + token.getLine());
                // If we reach this some token went unhandled
            default:
                throw new RuntimeException("Error while parsing expression... some unhandled token type: " + token.getType().toString());
        };
        // check if expression is an application
        token = peekToken();
        if (token != null && token.getType() == TokenType.OPENPAREN) {
            //parse application
            exp = parseApplication(exp);
        }
        return exp;
    }

    /**
     * Parses application expression assuming valid grammar. Takes first expression
     * assumed to be a function. Returns an ApplicationExpression.
     * @param exp Function to apply
     * @return Parsed application expression
     */
    private ApplicationExpression parseApplication(Expression exp) {
        // consume open parenthesis
        match(TokenType.OPENPAREN);
        // get argument list if any
        List<Expression> args = parseArguments();
        // consume close paren
        match(TokenType.CLOSEPAREN);
        // make sure passed expression is first expression
        args.add(0, exp);
        return new ApplicationExpression(args);
    }

    /**
     * Assuming valid format, parses a list of arguments for
     * function applications.
     *
     * @return Returns list of arguments for application
     */
    private List<Expression> parseArguments() {
        List<Expression> args = new ArrayList<>();
        Token token = peekToken();
        // We are already at end of arguments list, empty args list
        if (token != null && token.getType() == TokenType.CLOSEPAREN) {
            return args;
        }
        // We have at least one token
        Expression exp = parseExpression();
        args.add(exp);
        token = peekToken();
        while (token != null && token.getType() != TokenType.CLOSEPAREN) {
            // should be comma separating
            match(TokenType.COMMA);
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
        match(TokenType.LAMBDA, TokenType.LAMBDA_ALT);
        // Check for Open paren
        match(TokenType.OPENPAREN);
        // if close paren
        Token token = peekToken();
        ParametersExpression params;
        // Check for param list
        if (token != null && token.getType() != TokenType.CLOSEPAREN) {
            params = parseParameters();
        }
        // otherwise empty
        else {
            params = new ParametersExpression();
        }
        // Check for close paren
        match(TokenType.CLOSEPAREN);
        // Now read the block that follows
        BlockExpression block = parseBlock();
        // Construct expression and return
        return new LambdaExpression(params, block);
    }

    private ConditionalExpression parseCond() {
        // Check for cond keyword
        match(TokenType.COND);
        //At least one clause, else invalid
        ConditionalExpression cond = new ConditionalExpression();
        cond.addClause(parseClause());
        // get all clauses
        Token token = peekToken();
        while (token != null && token.getType() == TokenType.OPENPAREN) {
            cond.addClause(parseClause());
            token = peekToken();
        }
        return cond;
    }

    /**
     * Parses a block into an expression assuming valid grammar.
     * @return parsed BlockExpression
     */
    private BlockExpression parseBlock() {
        // Check for open brace
        match(TokenType.OPENBRACE);
        // Match expressions until close brace
        BlockExpression block = new BlockExpression();
        Token token = peekToken();
        //Maybe empty block?
        if (token != null && token.getType() == TokenType.CLOSEBRACE) {
            match(TokenType.CLOSEBRACE);
            return block;
        }
        // at least one expression in block
        block.addToBlock(parseExpression());
        token = peekToken();
        while (token != null && token.getType() == TokenType.SEMICOLON) {
            match(TokenType.SEMICOLON);
            block.addToBlock(parseExpression());
            token = peekToken();
        }
        // Consume close brace
        match(TokenType.CLOSEBRACE);
        return block;
    }

    /**
     * Matches assignment and returns expression assuming valid.
     * @return Returns new assignment expression
     */
    private AssignmentExpression parseAssignment() {
        // Check for identifier
        IdentifierExpression id = parseIdentifier();
        // Check for equals and consume
        match(TokenType.EQUALS);
        // Get expression to set to
        Expression exp = parseExpression();
        return new AssignmentExpression(id, exp);
    }

    /**
     * Matches definition and returns expression assuming valid.
     * @return Returns new definition expression
     */
    private DefinitionExpression parseDefinition() {
        // Check for definition
        match(TokenType.DEFINITION);
        // Check for identifier
        IdentifierExpression id = parseIdentifier();
        // Check for equals
        match(TokenType.EQUALS);
        // Parse some expression
        Expression exp = parseExpression();
        return new DefinitionExpression(id, exp);
    }


    /**
     * Parses a parameter list into an expression. Assumes valid grammar.
     * @return Parsed ParametersExpression
     */
    private ParametersExpression parseParameters() {
        // Check for IDENTIFIER
        ParametersExpression params = new ParametersExpression();
        params.addParameter(parseIdentifier());
        //Check for more ids?
        Token token = peekToken();
        while (token != null && token.getType() == TokenType.COMMA) {
            //consume comma
            match(TokenType.COMMA);
            // consume id
            params.addParameter(parseIdentifier());
            token = peekToken();
        }
        return params;
    }

    /**
     * Assuming valid clause is present, parses clause
     * and returns as Expression
     * @return clause expression to be used with conditionals
     */
    private ClauseExpression parseClause() {
        // Check for open paren
        match(TokenType.OPENPAREN);
        //get some eval exp
        Expression evalExp = parseExpression();
        // Check for arrow
        match(TokenType.ARROW);
        // Match some return exp
        Expression returnExp = parseExpression();
        // Check for close paren
        match(TokenType.CLOSEPAREN);
        // return new boolean expression
        return new ClauseExpression(evalExp, returnExp);
    }

    /**
     * Parses let expression against grammar. If no block is present,
     * a block with a dummy expression is used and will be removed through
     * desugaring.
     * @return Returns LetExpression
     */
    private LetExpression parseLet() {
        // Check for open paren
        match(TokenType.LET);
        // get identifier
        IdentifierExpression id = parseIdentifier();
        // parse equals
        match(TokenType.EQUALS);
        // get final exp
        Expression exp = parseExpression();
        // is there a block?
        Token token = peekToken();
        if (token != null && token.getType() == TokenType.OPENBRACE) {
            return new LetExpression(id, exp, parseBlock());
        }
        // if not create a new block with dummy expression so we know to desugar
        BlockExpression block = new BlockExpression();
        block.addToBlock(new DummyExpression());
        return new LetExpression(id, exp, block);
    }

    /**
     * Matches identifier and returns expression assuming valid.
     * @return Returns new identifier expression
     */
    private IdentifierExpression parseIdentifier() {
        Token token = peekToken();
        match(TokenType.IDENTIFIER);
        assert token != null;
        return new IdentifierExpression(token.getValue());
    }

    /**
     * To be called when the next token is an integer. Will consume the token
     * and return as expression
     * @return Returns current integer token as expression
     */
    private IntegerExpression parseInteger() {
        Token token = peekToken();
        match(TokenType.INTEGER);
        try {
            assert token != null;
            return new IntegerExpression(Long.parseLong(token.getValue()));
        } catch (Exception e) {
            throw new Error("Error converting TOKEN_INTEGER to number at line " + token.getLine());
        }
    }

    /**
     * To be called when the next token is a string. Will consume the token
     * and return as expression
     * @return Returns current string token as expression
     */
    private StringExpression parseString() {
        Token token = peekToken();
        match(TokenType.STRING);
        assert token != null;
        String value = token.getValue();
        if (value.length() < 2 || value.charAt(0) != '"' || value.charAt(value.length() - 1) != '"') {
            throw new RuntimeException("Parsed String where token was not surrounded by quotations");
        }
        return new StringExpression(token.getValue().substring(1, value.length() - 1));
    }
}
