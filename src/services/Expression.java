package services;

import services.functions.Term;

import java.util.HashSet;

public class Expression {
    private HashSet<String> arguments;
    private final Term expression;
    private final Object value;

    public Expression(Object value) {
        if (value instanceof String && TermParser.isExpression(value)) {
            this.expression = TermParser.parse((String) value);
            this.value = this.expression.eval();
        } else {
            this.expression = null;
            this.value = value;
        }
    }

    public Object getValue() {
        return value;
    }

    public Term getExpression() {
        return expression;
    }
}
