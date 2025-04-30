package parser;

import expressions.*;

import java.util.List;

/**
 * Desugar function will remove any syntactic sugar from AST
 * for proper evaluation.
 *
 * @author Heath Dyer
 */
public abstract class Desugar {

    /**
     * Recursively desugar an expression for each of its subcomponents
     *
     * @param exp Expression to desugar
     * @return Returns desugared expression
     */
    public static Expression desugar(Expression exp) {
        switch (exp.getType()) {
            // atom types -> nothing to do
            case IDENTIFIER:
            case STRING:
            case INTEGER:
            case BOOLEAN:
            case PROCEDURE:
            case PARAMETERS:
                return exp;
            // form types -> must examine components
            case ASSIGNMENT:
                AssignmentExpression assign = (AssignmentExpression) exp;
                desugar(assign.getExpression());
                return assign;
            case APPLICATION:
                ApplicationExpression app = (ApplicationExpression) exp;
                List<Expression> args = app.getArguments();
                for (Expression arg : args) {
                    desugar(arg);
                }
                return app;
            case LAMBDA:
                LambdaExpression lambda = (LambdaExpression) exp;
                desugar(lambda.getBlock());
                return lambda;
            case COND:
                ConditionalExpression cond = (ConditionalExpression) exp;
                List<ClauseExpression> clauses = cond.getClauses();
                for (Expression clause : clauses) {
                    desugar(clause);
                }
                return cond;
            case BLOCK:
                BlockExpression block = (BlockExpression) exp;
                List<Expression> contents = block.getBlock();
                for (int i = 0; i < contents.size(); i++) {
                    Expression e = contents.get(i);
                    // here is where we desugar let
                    if (e.getType() == ExpressionType.LET) {
                        LetExpression let = (LetExpression) e;
                        List<Expression> letContents = let.getBlock().getBlock();
                        //we have reached something to desugar
                        if (letContents.size() == 1 && letContents.get(0).getType() == ExpressionType.DUMMY) {
                            //remove dummy expression
                            letContents.remove(0);
                            // now we move remaining elements to new block
                            if (i + 1 < contents.size()) {
                                letContents.addAll(contents.subList(i + 1, contents.size()));
                                contents.removeAll(contents.subList(i + 1, contents.size()));
                            }
                            // now desugar the new block
                            desugar(let.getBlock());
                            return block; //no need to continue at this point
                        }
                    } else {
                        desugar(e);
                    }
                }
                return block;
            case LET:
                LetExpression let = (LetExpression) exp;
                desugar(let.getExpression());
                desugar(let.getBlock());
                return let;
            case DEFINITION:
                DefinitionExpression def = (DefinitionExpression) exp;
                desugar(def.getExpression());
                return def;
            case CLAUSE:
                ClauseExpression clause = (ClauseExpression) exp;
                desugar(clause.getTest());
                desugar(clause.getConsequent());
                return clause;
            // Types that should never be reached
            case LIST:
            case ARGLIST:
            case DUMMY:
            default:
                throw new RuntimeException("Tried desugaring unhandled type: " + exp.getType().toString());
        }
    }
}
