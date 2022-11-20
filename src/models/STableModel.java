package models;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class STableModel extends AbstractTableModel {
    private final ArrayList<ArrayList<Cell>> data;
    private final ArrayList<String> columnNames;

    public STableModel(List<List<String>> data) {
        int columnCount = 0;
        for (List<String> row : data) {
            if (columnCount < row.size()) {
                columnCount = row.size();
            }
        }

        this.columnNames = new ArrayList<>();
        this.columnNames.add("");
        for (int i = 0; i < columnCount; i++) {
            this.columnNames.add(super.getColumnName(i));
        }

        this.data = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            ArrayList<Cell> row = new ArrayList<>();
            this.data.add(row);
            row.add(new Cell(i));
            for (int j = 0; j < columnCount; j++) {
                if (j < data.get(i).size()) {
                    row.add(new Cell(data.get(i).get(j)));
                } else {
                    row.add(new Cell(""));
                }
            }
        }

        calculateAll();
    }

    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public Object getValueAt(int row, int column) {
        return data.get(row).get(column);
    }

    public String getExpressionAt(int row, int column) {
        return data.get(row).get(column).getExpression();
    }

    public boolean isCellEditable(int row, int column) {
        return (column != 0);
    }

    public void setValueAt(Object value, int row, int column) {
        Cell cell = data.get(row).get(column);
        cell.setValue(value);
        this.updateArgumentsListener(row, column);
        cell.calculate(STableModel.this);
        this.fireTableCellUpdated(row, column);
    }

    public void calculateAll() {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 1; j < getColumnCount(); j++) {
                data.get(i).get(j).calculate(STableModel.this);
            }
        }
    }

    protected TableModelListener createArgumentsListener(int row, int column) {
        Cell cell = data.get(row).get(column);
        return tableModelEvent -> {
            boolean argumentsChanged = cell.dependsOn(tableModelEvent.getFirstRow(), tableModelEvent.getLastRow(),
                    tableModelEvent.getColumn());
            if (argumentsChanged) {
                cell.calculate(STableModel.this);
                STableModel.this.fireTableCellUpdated(row, column);
            }
        };
    }

    private void updateArgumentsListener(int row, int column) {
        Cell cell = data.get(row).get(column);
        if (cell.argumentsListener != null) {
            this.removeTableModelListener(cell.argumentsListener);
        }
        if (cell.arguments != null && cell.arguments.size() > 0) {
            cell.argumentsListener = this.createArgumentsListener(row, column);
            this.addTableModelListener(cell.argumentsListener);
        } else {
            cell.argumentsListener = null;
        }
    }

    public ArrayList<ArrayList<Cell>> getData() {
        return data;
    }

    public void addRow() {
        ArrayList<Cell> row = new ArrayList<>();
        row.add(new Cell(getRowCount()));
        for (int i = 1; i < getColumnCount(); i++) {
            row.add(new Cell(""));
        }
        data.add(row);
        fireTableStructureChanged();
    }

    public void addColumn() {
        columnNames.add(super.getColumnName(getColumnCount()));
        for (int i = 0; i < getRowCount(); i++) {
            data.get(i).add(new Cell(""));
        }
        fireTableStructureChanged();
    }
}
