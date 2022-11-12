package services;

import services.functions.Term;

import java.util.HashSet;

public class Expression {
    private final String expression;
    private HashSet<String> arguments;
    private final Term termTree;
    private final Object value;

    public  Expression(Object expression) {
        if (expression instanceof String) {
            this.expression = (String) expression;
            termTree = TermParser.parse(this.expression);
            value = termTree.eval();
        } else {
            this.expression = null;
            termTree = null;
            value = expression;
        }
    }

    public Object getValue() {
        return value;
    }
}
