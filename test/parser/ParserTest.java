package parser;

import expressions.*;
import lexer.Lexer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



public class ParserTest {
    private Lexer lexer;
    private Parser parser;

    @BeforeEach
    public void init() {
        lexer = new Lexer();
        parser = new Parser();
    }

    /**
     * Test parsing basic integers
     */
    @Test
    public void parseInteger() {
        Expression exp = parser.parse(lexer.lex("123"));
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(123, ((IntegerExpression) exp).getValue());

        exp = parser.parse(lexer.lex("-120"));
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(-120, ((IntegerExpression) exp).getValue());

        exp = parser.parse(lexer.lex("+130"));
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(130, ((IntegerExpression) exp).getValue());

        exp = parser.parse(lexer.lex("000"));
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(0, ((IntegerExpression) exp).getValue());

        assertThrows(Exception.class, () -> {
            parser.parse(lexer.lex("+"));
        });

        assertThrows(Exception.class, () -> {
            parser.parse(lexer.lex("-"));
        });
    }

    /**
     * Test parsing basic strings
     */
    @Test
    public void parseString() {
        Expression exp = parser.parse(lexer.lex("\"testing\""));
        assertEquals(ExpressionType.STRING, exp.getType());
        assertEquals("testing", ((StringExpression) exp).getValue());

        //test bad string
        assertThrows(Exception.class, () -> {
            parser.parse(lexer.lex("\"testing"));
        });
    }

    /**
     * Test parsing basic identifiers
     */
    @Test
    public void parseIdentifier() {
        Expression exp = parser.parse(lexer.lex("testing"));
        assertEquals(ExpressionType.IDENTIFIER, exp.getType());
        assertEquals("testing", ((IdentifierExpression) exp).getName());
    }

    /**
     * Test parsing application of procedure
     */
    @Test
    public void parseApplication() {
        Expression exp = parser.parse(lexer.lex("add(1, 2)"));
        System.out.println(exp.toString());
        assertEquals(ExpressionType.APPLICATION, exp.getType());
    }


}
