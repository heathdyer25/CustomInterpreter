import expressions.Expression;
import expressions.ExpressionType;
import expressions.IntegerExpression;
import expressions.StringExpression;
import interpreter.Interpreter;
import lexer.Lexer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;

import java.io.IOException;

import static main.Util.getInputFromFile;
import static org.junit.jupiter.api.Assertions.*;
import static parser.Desugar.desugar;

public class InterpreterTest {
    private static Lexer lexer;
    private static Parser parser;
    private static Interpreter interpreter;

    @BeforeEach
    public void init() {
        lexer = new Lexer();
        parser = new Parser();
        interpreter = new Interpreter();
    }

    public static Expression interpret(String input) {
        return interpreter.evaluate(desugar(parser.parse(lexer.lex(input))), interpreter.getInitialEnv());
    }

    @Test
    public void testCP2() {
        Expression exp = interpret("123");
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(123, ((IntegerExpression) exp).getValue());

        exp = interpret("x");
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(10, ((IntegerExpression) exp).getValue());

        exp = interpret("v");
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(5, ((IntegerExpression) exp).getValue());

        exp = interpret("i");
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(1, ((IntegerExpression) exp).getValue());

        exp = interpret("000");
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(0, ((IntegerExpression) exp).getValue());

        exp = interpret("-120");
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(-120, ((IntegerExpression) exp).getValue());

        exp = interpret("add(1, 2)");
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(3, ((IntegerExpression) exp).getValue());

        exp = interpret("add(x, 2)");
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(12, ((IntegerExpression) exp).getValue());

        exp = interpret("add");
        assertEquals(ExpressionType.PROCEDURE, exp.getType());

        exp = interpret("add(sub(x, 1), 2)");
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(11, ((IntegerExpression) exp).getValue());

        exp = interpret("\"hi\"");
        assertEquals(ExpressionType.STRING, exp.getType());
        assertEquals("hi", ((StringExpression) exp).getValue());

        assertThrows(Exception.class, () -> {
            interpret("(-120.0)");
        });

        assertThrows(Exception.class, () -> {
            interpret("(y)");
        });

        assertThrows(Exception.class, () -> {
            interpret("add(add, 1)");
        });
    }

    @Test
    public void testCP3() {
        String file1 = null;
        String file2 = null;
        String file3 = null;
        String file4 = null;
        String file5 = null;
        try {
            file1 = getInputFromFile("./test-files/cp3ex1.417");
            file2 = getInputFromFile("./test-files/cp3ex2.417");
            file3 = getInputFromFile("./test-files/cp3ex3.417");
            file4 = getInputFromFile("./test-files/cp3ex4.417");
            file5 = getInputFromFile("./test-files/cp3ex5.417");
        } catch (IOException e) {
            fail("Failed to load test files for test CP3");
        }
        Expression exp = interpret(file1);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(18, ((IntegerExpression) exp).getValue());

        exp = interpret(file2);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(1, ((IntegerExpression) exp).getValue());

        exp = interpret(file3);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(90, ((IntegerExpression) exp).getValue());

        exp = interpret(file4);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(3628800, ((IntegerExpression) exp).getValue());

        exp = interpret(file5);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(720, ((IntegerExpression) exp).getValue());
    }

    @Test
    public void testCP4() {
        String file1 = null;
        String file2 = null;
        String file3 = null;
        String file4 = null;
        try {
            file1 = getInputFromFile("./test-files/cp4ex1.417");
            file2 = getInputFromFile("./test-files/cp4ex2.417");
            file3 = getInputFromFile("./test-files/cp4ex3.417");
            file4 = getInputFromFile("./test-files/cp4ex4.417");
        } catch (IOException e) {
            fail("Failed to load test files for test CP3");
        }
        Expression exp = interpret(file1);
        assertEquals(ExpressionType.LAMBDA, exp.getType());
        assertEquals("{\"Lambda\":[{\"Parameters\":[{\"Identifier\": \"a\"},{\"Identifier\": \"b\"}]},{\"Block\":[{\"Identifier\": \"b\"}]}]}", exp.toString());

        exp = interpret(file2);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(12, ((IntegerExpression) exp).getValue());

        exp = interpret(file3);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(5, ((IntegerExpression) exp).getValue());

        exp = interpret(file4);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(4, ((IntegerExpression) exp).getValue());
    }

    @Test
    public void testCP5() {
        String file1 = null;
        String file2 = null;
        String file3 = null;
        String file4 = null;
        try {
            file1 = getInputFromFile("./test-files/cp5ex1.417");
            file2 = getInputFromFile("./test-files/cp5ex2.417");
            file3 = getInputFromFile("./test-files/cp5ex3.417");
            file4 = getInputFromFile("./test-files/cp5ex4.417");
        } catch (IOException e) {
            fail("Failed to load test files for test CP3");
        }
        Expression exp = interpret(file1);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(6, ((IntegerExpression) exp).getValue());

        exp = interpret(file2);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(6, ((IntegerExpression) exp).getValue());

        String finalFile = file3;
        assertThrows(Exception.class, () -> {
            interpret(finalFile);
        });

        exp = interpret(file4);
        assertEquals(ExpressionType.STRING, exp.getType());
        assertEquals("D", ((StringExpression) exp).getValue());

        //TODO: add tests for dynamic scoping
    }

    @Test
    public void testCP6() {
        String file1 = null;
        String file2 = null;
        String file3 = null;
        String file4 = null;
        try {
            file1 = getInputFromFile("./test-files/cp6ex1.417");
            file2 = getInputFromFile("./test-files/cp6ex2.417");
            file3 = getInputFromFile("./test-files/cp6ex3.417");
            file4 = getInputFromFile("./test-files/cp6ex4.417");
        } catch (IOException e) {
            fail("Failed to load test files for test CP3");
        }
        Expression exp = interpret(file1);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(11, ((IntegerExpression) exp).getValue());

        exp = interpret(file2);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(4, ((IntegerExpression) exp).getValue());

        exp = interpret(file3);
        assertEquals(ExpressionType.INTEGER, exp.getType());
        assertEquals(5040, ((IntegerExpression) exp).getValue());

        exp = interpret(file4);
        assertEquals(ExpressionType.STRING, exp.getType());
        assertEquals("x is 333\ny is 333\nz is 333\n", ((StringExpression) exp).getValue());
    }

}
