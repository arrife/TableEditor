package models;

import services.TermParser;
import services.functions.Term;

import java.util.HashSet;

public class Cell {
    private Object value;
    private boolean isExpression;
    private Term expression;
    private HashSet<String> arguments;

    public Cell(Object value) {
        setValue(value);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        isExpression = TermParser.isExpression(value);
        if (isExpression) {
            expression = TermParser.parse((String) value);
            this.value = expression.eval();
        } else {
            expression = null;
            this.value = value;
        }
    }

    public String getExpression() {
        if (isExpression) {
            return String.format("=%s", expression.toString());
        }
        return getValue().toString();
    }
}
