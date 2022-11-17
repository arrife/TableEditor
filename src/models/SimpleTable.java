package models;


import javax.swing.*;
import java.awt.*;

public class SimpleTable extends JTable {
    public SimpleTable(Object[][] rowData) {
        super(new ExtendedTableModel(rowData));

        this.addPropertyChangeListener("tableCellEditor", propertyChangeEvent -> {
            int row = this.getSelectedRow();
            int column = this.getSelectedColumn();
            Component editor = this.getEditorComponent();
            if (editor instanceof JTextField && this.dataModel instanceof ExtendedTableModel
                    && propertyChangeEvent.getNewValue() != null) {
                ((JTextField) editor).setText(((ExtendedTableModel) this.dataModel).getExpressionAt(row, column));
            }
        });

    }

}
