package models;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ExtendedTableModel extends AbstractTableModel {
    ArrayList<ArrayList<Cell>> data;
    String[] columnNames;

    public ExtendedTableModel(Object[][] data) {
        int columnCount = 0;
        for (Object[] row : data) {
            if (columnCount < row.length) {
                columnCount = row.length;
            }
        }

        this.columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            this.columnNames[i] = getColumnName(i);
        }

        this.data = new ArrayList<>();
        for (Object[] dataRow : data) {
            ArrayList<Cell> row = new ArrayList<>();
            this.data.add(row);
            for (int i = 0; i < columnCount; i++) {
                if (i < dataRow.length) {
                    row.add(new Cell(dataRow[i]));
                } else {
                    row.add(new Cell(""));
                }
            }
        }
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int row, int column) {
        return data.get(row).get(column).getValue();
    }

    public String getExpressionAt(int row, int column) {
        return data.get(row).get(column).getExpression();
    }

    public boolean isCellEditable(int row, int column) {
        return true;
    }

    public void setValueAt(Object value, int row, int column) {
        Cell cell = data.get(row).get(column);
        cell.setValue(value);
        this.updateArgumentsListener(row, column);
        cell.calculate(ExtendedTableModel.this);
        this.fireTableCellUpdated(row, column);
    }

    protected TableModelListener createArgumentsListener(int row, int column) {
        Cell cell = data.get(row).get(column);
        return tableModelEvent -> {
            boolean argumentsChanged = cell.dependsOn(tableModelEvent.getFirstRow(), tableModelEvent.getLastRow(),
                    tableModelEvent.getColumn());
            if (argumentsChanged) {
                cell.calculate(ExtendedTableModel.this);
                ExtendedTableModel.this.fireTableCellUpdated(row, column);
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
}
