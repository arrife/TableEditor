package models;


import javax.swing.*;
import java.awt.*;

public class SimpleTable extends JTable {
    public SimpleTable(Object[][] rowData, String[] columnNames) {
        super();
        this.setModel(new ExtendedTableModel(rowData, columnNames));

        this.addPropertyChangeListener("tableCellEditor", propertyChangeEvent -> {
            int i = this.getSelectedRow();
            int j = this.getSelectedColumn();
            Component editor = this.getEditorComponent();
            if (editor instanceof JTextField && this.dataModel instanceof ExtendedTableModel
                    && propertyChangeEvent.getNewValue() != null) {
                ((JTextField) editor).setText(((ExtendedTableModel) this.dataModel).getExpressionAt(i, j));
            }
        });

    }

}
