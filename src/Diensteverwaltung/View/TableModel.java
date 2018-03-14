package Diensteverwaltung.View;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel{
    @Override
    public int getRowCount() {
        return 100;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int row, int column) {
        if ( column == 0){
            return "" + row;
        }
        else if (column == 1){
            return  "" + ( row * row);
        }
        else if (column == 2) {
            return "" + (row * row * row);
        }
        else
            return "" + ( row * row * row * row);
    }
}
