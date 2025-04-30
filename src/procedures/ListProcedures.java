package procedures;

import expressions.*;

import java.util.Collections;
import java.util.List;

/**
 * @author Heath Dyer
 * <p>
 * Built in list procedures for the interpreter. Each procedure must take List<Expressions>
 * as its parameter and return an Expression as its result.
 */
public abstract class ListProcedures {

    /**
     * Adds element to front of list, if only one arg creates new list
     *
     * @param arguments Element to add, list
     * @return Returns that list
     */
    public static Expression cons(List<Expression> arguments) {
        //if no arguments, get empty list
        if (arguments.size() == 0) {
            return new ListExpression();
        }
        // Check for exactly two arguments
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure cons must have exactly 2 arguments.");
        }
        //check that second param is list
        if (arguments.get(1).getType() != ExpressionType.LIST) {
            throw new IllegalArgumentException("Procedure cons takes 1 expression and 1 list.");
        }
        ListExpression list = new ListExpression(((ListExpression) arguments.get(1)).getExpressions());
        list.getExpressions().add(0, arguments.get(0));
        return list;
    }

    /**
     * Returns head of list
     *
     * @param arguments List to get head of
     * @return Returns expression at head of list
     */
    public static Expression head(List<Expression> arguments) {
        // Check for exactly one argument
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure head must have exactly 1 argument.");
        }
        if (arguments.get(0).getType() != ExpressionType.LIST) {
            throw new IllegalArgumentException("Expected a list for head operation.");
        }
        return ((ListExpression) arguments.get(0)).getExpressions().get(0);
    }

    /**
     * Returns tail of list (list with everything but first element)
     *
     * @param arguments List to get tail of
     * @return Returns tail of list as list expression
     */
    public static Expression tail(List<Expression> arguments) {
        // Check for exactly one argument
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure tail must have exactly 1 argument.");
        }
        if (arguments.get(0).getType() != ExpressionType.LIST) {
            throw new IllegalArgumentException("Expected a list for tail operation.");
        }
        List<Expression> elements = ((ListExpression) arguments.get(0)).getExpressions();
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("Cannot get tail of an empty list.");
        }
        return new ListExpression(elements.subList(1, elements.size()));  // Exclude head
    }

    /**
     * Checks for empty list
     *
     * @param arguments List of expression
     * @return Returns true if list is emtpy, false if not
     */
    public static Expression isEmpty(List<Expression> arguments) {
        // Check for exactly one argument
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure isEmpty? must have exactly 1 argument.");
        }
        if (arguments.get(0).getType() != ExpressionType.LIST) {
            throw new IllegalArgumentException("Expected a list for isEmpty? operation.");
        }
        return new BooleanExpression(((ListExpression) arguments.get(0)).getExpressions().isEmpty());
    }

    /**
     * Reverses order of list and returns new list
     *
     * @param arguments List of expressions (1 list)
     * @return
     */
    public static Expression reverse(List<Expression> arguments) {
        // Check for exactly one argument
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Procedure reverse must have exactly 1 argument.");
        }
        if (arguments.get(0).getType() != ExpressionType.LIST) {
            throw new IllegalArgumentException("Expected a list for reverse operation.");
        }
        //get list and reverse order
        ListExpression newList = new ListExpression(((ListExpression) arguments.get(0)).getExpressions());
        Collections.reverse(newList.getExpressions());
        //return new list
        return newList;
    }

    /**
     * Maps list of arguments onto procedure
     *
     * @param arguments Procedure and argument list
     * @return Returns result of applied procedure
     */
    public static Expression map(List<Expression> arguments) {
        // Check for exactly two argument
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure map must have exactly 2 arguments.");
        }
        //check arg1 is procedure
        if (arguments.get(0).getType() != ExpressionType.PROCEDURE) {
            throw new IllegalArgumentException("Expected a procedure for map operation.");
        }
        //check arg 2 is list of expressions
        if (arguments.get(1).getType() != ExpressionType.LIST) {
            throw new IllegalArgumentException("Expected a list for map operation.");
        }
        //apply procedure and return result
        ProcedureExpression procedure = (ProcedureExpression) arguments.get(0);
        List<Expression> expressions = ((ListExpression) arguments.get(1)).getExpressions();
        return procedure.apply(expressions);
    }

    /**
     * Add arguments in first list to front of second list
     *
     * @param arguments Two lists
     * @return return resultant list
     */
    public static Expression append(List<Expression> arguments) {
        // Check for exactly two argument
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Procedure append must have exactly 2 argument.");
        }
        //check args are lists
        if (arguments.get(0).getType() != ExpressionType.LIST && arguments.get(1).getType() != ExpressionType.LIST) {
            throw new IllegalArgumentException("Expected 2 arguments of type list for append.");
        }
        List<Expression> add = ((ListExpression) arguments.get(0)).getExpressions();
        ListExpression appendedList = new ListExpression(((ListExpression) arguments.get(1)).getExpressions());
        Collections.reverse(add);
        for (Expression e : add) {
            appendedList.getExpressions().add(0, e);
        }
        return appendedList;
    }

}
