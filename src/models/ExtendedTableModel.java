package models;

import javax.swing.table.AbstractTableModel;

public class ExtendedTableModel extends AbstractTableModel {
    Cell[][] data;
    String[] columnNames;

    public ExtendedTableModel(Object[][] data, String[] columnNames) {
        int n = data.length;
        int m = data[0].length;
        this.data = new Cell[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.data[i][j] = new Cell(data[i][j]);
            }
        }
        this.columnNames = columnNames;
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int row, int column) {
        return data[row][column].getValue();
    }

    public String getExpressionAt(int row, int column) {
        return data[row][column].getExpression();
    }

    public boolean isCellEditable(int row, int column) {
        return true;
    }

    public void setValueAt(Object value, int row, int column) {
        data[row][column].setValue(value);
        this.fireTableCellUpdated(row, column);
    }
}
