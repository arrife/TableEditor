package models;

import services.Expression;

import java.util.HashSet;

public class Cell {
    private Expression value;
    private HashSet<String> arguments;

    public void setValue(Object value) {
        this.value = new Expression(value);
    }
}
