package models;

import services.Address;
import services.TermParser;
import services.functions.Term;
import services.functions.Value;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.util.HashSet;

public class Cell {
    protected HashSet<Address> arguments;
    protected Object value;
    protected TableModelListener argumentsListener;
    private String expression;
    private Term termTree;

    public Cell(Object value) {
        setValue(value);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        if (TermParser.isExpression(value)) {
            expression = (String) value;
            parseExpression();
        } else {
            expression = null;
            termTree = null;
            arguments = null;
            this.value = Value.of(value);
        }
    }

    private void parseExpression() {
        if (expression != null) {
            arguments = new HashSet<>();
            try {
                termTree = TermParser.parse(expression);
                for (String link : termTree.links) {
                    arguments.add(new Address(link));
                }
                this.value = "=<term>";
            } catch (IOException e) {
                termTree = null;
                this.value = String.format("Parse error: %s", e.getMessage());
            }
        }
    }

    public void calculate(AbstractTableModel dm) {
        if (termTree != null) {
            try {
                this.value = termTree.eval(dm);
            } catch (IOException e) {
                this.value = String.format("Calculation error: %s", e.getMessage());
            }
        }
    }

    public boolean dependsOn(int firstRow, int lastRow, int column) {
        return arguments.stream().anyMatch(address -> address.belongTo(firstRow, lastRow, column));
    }

    public String getExpression() {
        if (expression != null) {
            return expression;
        }
        return value.toString();
    }
}
