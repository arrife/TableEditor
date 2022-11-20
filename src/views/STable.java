package views;


import models.Cell;
import models.STableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class STable extends JTable {
    public STable(List<List<String>> rowData) {
        super(new STableModel(rowData));
    }

    @Override
    public void setModel(TableModel dataModel) {
        super.setModel(dataModel);

        this.addPropertyChangeListener("tableCellEditor", propertyChangeEvent -> {
            int row = this.getSelectedRow();
            int column = this.getSelectedColumn();
            Component editor = this.getEditorComponent();
            if (editor instanceof JTextField && this.dataModel instanceof STableModel
                    && propertyChangeEvent.getNewValue() != null) {
                Cell cell = (Cell) this.dataModel.getValueAt(row, column);
                if (cell.isExpression()) {
                    ((JTextField) editor).setText(((STableModel) this.dataModel).getExpressionAt(row, column));
                }
            }
        });


    }

    @Override
    public String getToolTipText(MouseEvent event) {
        java.awt.Point p = event.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        if (rowIndex >= 0 && colIndex > 0) {
            return getValueAt(rowIndex, colIndex).toString();
        } else {
            return super.getToolTipText(event);
        }
    }


}
