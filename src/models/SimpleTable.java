package models;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class SimpleTable extends JTable {
    public SimpleTable(Object[][] rowData, String[] columnNames) {
        super(new AbstractTableModel() {
            public String getColumnName(int column) {
                return columnNames[column];
            }

            public int getRowCount() {
                return rowData.length;
            }

            public int getColumnCount() {
                return columnNames.length;
            }

            public Object getValueAt(int row, int col) {
                return rowData[row][col];
            }

            public boolean isCellEditable(int row, int column) {
                return true;
            }

            public void setValueAt(Object value, int row, int col) {
                ((Cell) rowData[row][col]).setValue(value);
                this.fireTableCellUpdated(row, col);
            }
        });
    }

}
