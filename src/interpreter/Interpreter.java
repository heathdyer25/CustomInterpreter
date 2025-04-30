package interpreter;

import expressions.*;
import procedures.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Interpreter functions for evaluating expressions.
 * Based off Jamie Jennings lecture code.
 *
 * @author Heath Dyer
 */
public class Interpreter {
    /**
     * If true, enables tracing during evaluation
     */
    private boolean tracing;
    /**
     * If false, enables dynamic scoping
     */
    private boolean lexicalScope;

    /**
     * Constructor for Interpreter class. Initializes environment and
     * built in expressions. Tracing false by default
     */
    public Interpreter() {
        this(false, true);
    }

    /**
     * Constructor for Interpreter class. Allows you to specify if you
     * want to enable tracing or not.
     *
     * @param tracing
     */
    public Interpreter(boolean tracing, boolean lexicalScope) {
        this.tracing = tracing;
        this.lexicalScope = lexicalScope;
    }

    /**
     * Initializes the current environment of the interpreter with default bindings
     */
    public Environment getInitialEnv() {
        //create initial env
        Environment initialEnv = new Environment();
        //initialize environment variables
        bind(new IdentifierExpression("x"), new IntegerExpression(10), initialEnv);
        bind(new IdentifierExpression("v"), new IntegerExpression(5), initialEnv);
        bind(new IdentifierExpression("i"), new IntegerExpression(1), initialEnv);
        bind(new IdentifierExpression("true"), new BooleanExpression(true), initialEnv);
        bind(new IdentifierExpression("false"), new BooleanExpression(false), initialEnv);
        //initialize math procedures
        bind(new IdentifierExpression("add"), new ProcedureExpression(MathProcedures::add), initialEnv);
        bind(new IdentifierExpression("sub"), new ProcedureExpression(MathProcedures::sub), initialEnv);
        bind(new IdentifierExpression("mul"), new ProcedureExpression(MathProcedures::mul), initialEnv);
        bind(new IdentifierExpression("div"), new ProcedureExpression(MathProcedures::div), initialEnv);
        bind(new IdentifierExpression("mod"), new ProcedureExpression(MathProcedures::mod), initialEnv);
        //initialize comparison procedures
        bind(new IdentifierExpression("equals?"), new ProcedureExpression(LogicProcedures::eq), initialEnv);
        bind(new IdentifierExpression("greaterThan?"), new ProcedureExpression(LogicProcedures::greaterThan), initialEnv);
        bind(new IdentifierExpression("lessThan?"), new ProcedureExpression(LogicProcedures::lessThan), initialEnv);
        bind(new IdentifierExpression("zero?"), new ProcedureExpression(LogicProcedures::isZero), initialEnv);
        bind(new IdentifierExpression("or?"), new ProcedureExpression(LogicProcedures::or), initialEnv);
        bind(new IdentifierExpression("and?"), new ProcedureExpression(LogicProcedures::and), initialEnv);
        bind(new IdentifierExpression("not?"), new ProcedureExpression(LogicProcedures::not), initialEnv);
        //initialize io procedures
        bind(new IdentifierExpression("print"), new ProcedureExpression(IOProcedures::print), initialEnv);
        bind(new IdentifierExpression("fail"), new ProcedureExpression(IOProcedures::fail), initialEnv);
        bind(new IdentifierExpression("readInput"), new ProcedureExpression(IOProcedures::readInput), initialEnv);
        bind(new IdentifierExpression("readLine"), new ProcedureExpression(IOProcedures::readLine), initialEnv);
        bind(new IdentifierExpression("readFile"), new ProcedureExpression(IOProcedures::readFile), initialEnv);
        //initialize String procedures
        bind(new IdentifierExpression("concat"), new ProcedureExpression(StringProcedures::concat), initialEnv);
        bind(new IdentifierExpression("charAt"), new ProcedureExpression(StringProcedures::charAt), initialEnv);
        bind(new IdentifierExpression("substring"), new ProcedureExpression(StringProcedures::substring), initialEnv);
        bind(new IdentifierExpression("length"), new ProcedureExpression(StringProcedures::length), initialEnv);
        bind(new IdentifierExpression("isDigit?"), new ProcedureExpression(StringProcedures::isDigit), initialEnv);
        bind(new IdentifierExpression("isLetter?"), new ProcedureExpression(StringProcedures::isLetter), initialEnv);
        bind(new IdentifierExpression("parseInt"), new ProcedureExpression(StringProcedures::parseInt), initialEnv);
        //list procedures
        bind(new IdentifierExpression("cons"), new ProcedureExpression(ListProcedures::cons), initialEnv);
        bind(new IdentifierExpression("head"), new ProcedureExpression(ListProcedures::head), initialEnv);
        bind(new IdentifierExpression("tail"), new ProcedureExpression(ListProcedures::tail), initialEnv);
        bind(new IdentifierExpression("isEmpty?"), new ProcedureExpression(ListProcedures::isEmpty), initialEnv);
        bind(new IdentifierExpression("reverse"), new ProcedureExpression(ListProcedures::reverse), initialEnv);
        bind(new IdentifierExpression("append"), new ProcedureExpression(ListProcedures::append), initialEnv);
        bind(new IdentifierExpression("map"), new ProcedureExpression(ListProcedures::map), initialEnv);
        //general
        bind(new IdentifierExpression("type"), new ProcedureExpression(GeneralProcedures::getType), initialEnv);
        //return initial env
        return initialEnv;
    }

    /**
     * Binds new expression in current environment
     *
     * @param identifier Identifier to bind as key
     * @param exp        Expression to bind as value
     * @param currentEnv environment to bind in
     */
    private void bind(IdentifierExpression identifier, Expression exp, Environment currentEnv) {
        currentEnv.getEnvironment().put(identifier, exp);
    }

    /**
     * Extends environment given a list of identifiers and list of expressions to bind
     * to those identifiers, returns new root environment.
     *
     * @param identifiers List of identifiers to bind
     * @param exps        List of expressions to bind
     * @param currentEnv  environment to extend
     * @return Returns new root environment node
     * @throws IllegalArgumentException Number of arguments must match number of params
     */
    private Environment extend(List<IdentifierExpression> identifiers, List<Expression> exps, Environment currentEnv) {
        //check for null params
        if (identifiers == null || exps == null) {
            throw new IllegalArgumentException("Could not extend environment. Cannot have null identifiers list or null expressions list.");
        }
        //check number of identifiers is number  of expressions
        if (identifiers.size() != exps.size()) {
            throw new IllegalArgumentException("Lambda function called with wrong number of arguments.");
        }
        //create new environment and add bindings
        Environment node = new Environment();
        for (int i = 0; i < identifiers.size(); i++) {
            bind(identifiers.get(i), exps.get(i), currentEnv);
        }
        //update parent reference
        node.setParent(currentEnv);
        //return new env
        return node;
    }

    /**
     * Extends envs with only one new binding in the given environment. Returns
     * new environment root node.
     *
     * @param identifier Identifier to add
     * @param exp        Expression to bind
     * @param currentEnv evnrionment to bind in
     * @return New environment root node
     */
    private Environment extend(IdentifierExpression identifier, Expression exp, Environment currentEnv) {
        //check for null params
        if (identifier == null || exp == null) {
            throw new IllegalArgumentException("Could not extend environment. Cannot have null identifier or null expression.");
        }
        //create new environment and add binding
        Environment node = new Environment();
        bind(identifier, exp, node);
        //update parent reference
        node.setParent(currentEnv);
        //return new current env
        return node;
    }

    /**
     * Extends environment with new empty environment
     *
     * @param currentEnv env to extend
     * @return Returns extended environment
     */
    private Environment extend(Environment currentEnv) {
        //create new environment
        Environment node = new Environment();
        node.setParent(currentEnv);
        return node;
    }

    /**
     * Looks up expression in list of environments starting from given environment node.
     *
     * @param identifier Identifier to look for
     * @param currentEnv Environment node to begin lookup from
     * @return Returns bound expression
     */
    private static Expression lookup(IdentifierExpression identifier, Environment currentEnv) {
        while (currentEnv != null) {
            if (currentEnv.getEnvironment().containsKey(identifier)) {
                return currentEnv.getEnvironment().get(identifier);
            }
            currentEnv = currentEnv.getParent();
        }
        throw new RuntimeException("Unbound identifier: \"" + identifier.getName() + "\".");
    }

    /**
     * Looks up identifier in assignment expression in list of environments
     * and changes the binding, then returns the new value bound to the identifier. If the
     * binding does not exist, throws error.
     *
     * @param exp Assignment expression to bind
     * @param env Environment to bind in
     * @return Returns new expression bound to identifier
     */
    private Expression performAssignment(AssignmentExpression exp, Environment env) {
        Expression evalExp = evaluate(exp.getExpression(), env);
        while (env != null) {
            if (env.getEnvironment().containsKey(exp.getIdentifier())) {
                bind(exp.getIdentifier(), evalExp, env);
                return evalExp;
            }
            env = env.getParent();
        }
        throw new RuntimeException("Unbound identifier: \"" + exp.getIdentifier().getName() + "\".");
    }

    /**
     * Performs definition expression in given environment.
     *
     * @param def Definition expression to perform.
     * @param env Environment to perform definition in.
     * @return Returns value of the definition
     */
    private Expression applyDefinition(DefinitionExpression def, Environment env) {
        Expression evalExp = evaluate(def.getExpression(), env);
        while (env != null) {
            //need to replace dummy binding
            if (env.getEnvironment().containsKey(def.getIdentifier()) && env.getEnvironment().get(def.getIdentifier()).equals(new DummyExpression())) {
                if (lexicalScope && evalExp.getType() == ExpressionType.LAMBDA) {
                    ((LambdaExpression) evalExp).setEnvironment(env);
                }
                bind(def.getIdentifier(), evalExp, env);
                return evalExp;
            }
            env = env.getParent();
        }
        throw new RuntimeException("Unable to find dummy binding for def expression.");
    }

    /**
     * Applies the given application expression and returns the resulting expression.
     *
     * @param exp Application expression to apply
     * @return Returns result of function application
     */
    private Expression applyFunction(ApplicationExpression exp, Environment env) {
        //get operator
        Expression operator = evaluate(exp.getArguments().get(0), env);
        //get operands
        List<Expression> operands = new LinkedList<Expression>();
        for (Expression argument : exp.getArguments().subList(1, exp.getArguments().size())) {
            operands.add(evaluate(argument, env));
        }
        //is build in procedure application?
        if (operator.getType() == ExpressionType.PROCEDURE) {
            return ((ProcedureExpression) operator).apply(operands);
        }
        //is lambda function application?
        if (operator.getType() == ExpressionType.LAMBDA) {
            LambdaExpression lambda = (LambdaExpression) operator;
            //extend environment, if lexical scope && not anonymous function use saved labmda env, otherwise use current env
            Environment extendedEnv = extend(lambda.getParameters().getParameters(), operands,
                    lexicalScope && lambda.getEnvironment() != null ? lambda.getEnvironment() : env);
            return evaluate(lambda.getBlock(), extendedEnv);
        }
        throw new RuntimeException("Not a built in procedure " + exp.toString());
    }

    /**
     * Apply conditional statement by evaluating all conditional statements and returning the consequent for the first
     * cause where the test returns true. If no test returns true, returns false.
     *
     * @param exp CondtionalExpression to evaluate
     * @return Returns consequent of the first clause where the test returns true, else  false
     */
    private Expression applyConditional(ConditionalExpression exp, Environment env) {
        //for each clause, evaluate eval expression if true depending on expression type
        for (ClauseExpression clause : exp.getClauses()) {
            Expression test = evaluate(clause.getTest(), env);
            if (test.getType() != ExpressionType.BOOLEAN) {
                throw new RuntimeException("Expected boolean but clause test evaluated as " + test.getType().toString() + ".");
            }
            if (((BooleanExpression) test).getValue()) {
                return evaluate(clause.getConsequent(), env);
            }
        }
        //return false if no expression eval expression returns true
        return new BooleanExpression(false);
    }

    /**
     * Evaluates let expression by extending the environment with the new binding
     * from the let expression, and then evaluating the block of the let expression
     * within that new scope.
     *
     * @param exp Let expression to evaluate
     * @return Returns result of evaluating let expression
     */
    private Expression applyLet(LetExpression exp, Environment env) {
        Expression evalExp = evaluate(exp.getExpression(), env);
        Environment extendedEnv = extend(exp.getIdentifier(), evalExp, env);
        //if lambda expression, we must save the environment while binding the lambda function
        if (lexicalScope && evalExp.getType() == ExpressionType.LAMBDA) {
            ((LambdaExpression) evalExp).setEnvironment(extendedEnv);
        }
        return evaluate(exp.getBlock(), extendedEnv);
    }

    /**
     * Executes a block expression by evaluating every expression in the block and returns the
     * value of the last expression in the block. If the function block is empty,
     * returns BooleanExpression with value false.
     *
     * @param block BlockExpression to evaluate
     * @return Returns false if block is empty, otherwise returns last expression in the block
     */
    private Expression executeBlock(BlockExpression block, Environment env) {
        //return false if empty
        Expression result = new BooleanExpression(false);
        //first go through and bind all def identifiers to dummy values
        Environment newEnv = extend(env);
        for (Expression exp : block.getBlock()) {
            if (exp.getType() == ExpressionType.DEFINITION) {
                bind(((DefinitionExpression) exp).getIdentifier(), new DummyExpression(), newEnv);
            }
        }
        //evaluate every exp in block
        for (Expression exp : block.getBlock()) {
            result = evaluate(exp, newEnv);
        }
        //return last expression
        return result;
    }

    /**
     * Evaluates expressions and returns the result.
     *
     * @param exp Expression to evaluate
     * @return Returns evaluated expression
     */
    public Expression evaluate(Expression exp, Environment env) {
        //prints tracing statement
        if (tracing) {
            System.out.println("Evaluating " + exp.getType().toString() + " expression: " + exp.toString());
        }
        //based on expression type, evaluate expression
        switch (exp.getType()) {
            //is number?
            case INTEGER:
                return (IntegerExpression) exp;
            //is string?
            case STRING:
                return (StringExpression) exp;
            //is boolean?
            case BOOLEAN:
                return (BooleanExpression) exp;
            //is a lambda expression or function?
            case LAMBDA:
                return (LambdaExpression) exp;
            //case is list?
            case LIST:
                return (ListExpression) exp;
            //is build in operator?
            case PROCEDURE:
                return (ProcedureExpression) exp;
            //is block expression?
            case BLOCK:
                return executeBlock((BlockExpression) exp, env);
            //is conditional expression?
            case COND:
                return applyConditional((ConditionalExpression) exp, env);
            //is let expression?
            case LET:
                return applyLet((LetExpression) exp, env);
            //is definition expression?
            case DEFINITION:
                return applyDefinition((DefinitionExpression) exp, env);
            //is assignment?
            case ASSIGNMENT:
                return performAssignment((AssignmentExpression) exp, env);
            //is application of built in function?
            case APPLICATION:
                return applyFunction((ApplicationExpression) exp, env);
            //is symbol/identifier?
            case IDENTIFIER:
                return lookup((IdentifierExpression) exp, env);
            case DUMMY:
                throw new RuntimeException("Interpreter tried evaluating dummy value bound during def expression.");
                //otherwise unknown expression type
            default:
                throw new RuntimeException("Expression " + exp.getType().toString() + " is supported by the JSON deserializer but not the interpreter.");
        }
    }
}
