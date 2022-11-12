package models;

import services.Expression;

import java.util.HashSet;

public class Cell {
    private Expression value;
    private HashSet<String> arguments;

    public Cell(Object value) {
        setValue(value);
    }

    public void setValue(Object value) {
        this.value = new Expression(value);
    }

    public Object getValue() {
        return value.getValue();
    }
}
