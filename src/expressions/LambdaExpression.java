package expressions;

import interpreter.Environment;

import java.util.Objects;


/**
 * Lambda expression type for interpreter. Parameters list contains zero or more identifiers.
 *
 * @author Heath Dyer
 */
public class LambdaExpression extends Expression {
    /**
     * List of parameters for lambda expression
     */
    private ParametersExpression parameters;
    /**
     * Function block for lambda expression
     */
    private BlockExpression block;
    /**
     * Environment to evaluate in
     */
    private Environment currentEnv;

    /**
     * Constructs new Lambda expression given the Parameters and Block
     * for the lambda.
     *
     * @param parameters parameters of lambda
     * @param block      Block expression for lambda
     */
    public LambdaExpression(ParametersExpression parameters, BlockExpression block) {
        super(ExpressionType.LAMBDA);
        setParameters(parameters);
        setBlock(block);
        setEnvironment(null);
    }

    /**
     * Sets list of parameters for lambda expression
     *
     * @param parameters List of parameters to set
     */
    public void setParameters(ParametersExpression parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("Lambda expression parameters cannot be null.");
        }
        this.parameters = parameters;
    }

    /**
     * Returns list of parameters for lambda expression
     *
     * @return List of parameters
     */
    public ParametersExpression getParameters() {
        return this.parameters;
    }

    /**
     * Sets the lambda expression function block
     *
     * @param block BlockExpression to set
     */
    public void setBlock(BlockExpression block) {
        if (block == null) {
            throw new IllegalArgumentException("Lambda expression function block cannot be null.");
        }
        this.block = block;
    }

    /**
     * Gets environment to evaluate lambda function in
     *
     * @return environment to evaluate lambda function in
     */
    public Environment getEnvironment() {
        return currentEnv;
    }

    /**
     * Sets environment to evaluate the lambda function in
     *
     * @param environment Environment to evaluate lambda function in
     */
    public void setEnvironment(Environment environment) {
        this.currentEnv = environment;
    }

    /**
     * Returns function block of lambda expression
     *
     * @return Returns BlockExpression of lambda function
     */
    public BlockExpression getBlock() {
        return this.block;
    }

    @Override
    public String toString() {
        return "{\"" + LAMBDA_KEYWORD + "\":[" + parameters.toString() + "," + block.toString() + "]}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(block, parameters);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;

    }


}
