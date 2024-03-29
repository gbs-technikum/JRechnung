package com.sabel.JRechnung.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class BillList extends JTable {

    private static final String[] TABLE_COLUMN_NAMES = {"Rechnung","Rechnungsempfänger","Datum","zu begleichen bis","beglichen","beglichen am","Betrag",""};
    private static final Color ALTERNATE_ROW_COLOR = new Color(255,255,200);
    private static final Color ROW_COLOR = Color.WHITE;
    private static final Color GRID_COLOR = Color.BLACK;
    private static final int ROW_HEIGHT = 30;
    private static final int PREDEFIND_TABLE_ROWCOUNT = 0;


    public BillList() {
        super(new DefaultTableModel(TABLE_COLUMN_NAMES, BillList.PREDEFIND_TABLE_ROWCOUNT){

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

            @Override
            public Class getColumnClass(int column)
            {
                return (column == 7) ? ImageIcon.class : Object.class;
            }
        });
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TableColumnModel columnModel = this.getColumnModel();
        columnModel.getColumn(7).setMaxWidth(20);
        getTableHeader().setReorderingAllowed(true);
        setShowGrid(false);
        setRowHeight(BillList.ROW_HEIGHT);
        setVisible(true);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer tableCellRenderer, int row, int column) {
        JComponent jc = (JComponent)super.prepareRenderer(tableCellRenderer, row, column);
        Border border = BorderFactory.createMatteBorder(0,1,3,0,BillList.GRID_COLOR);
        jc.setBorder(border);
        jc.setBackground(row%2!=0 ? BillList.ROW_COLOR : BillList.ALTERNATE_ROW_COLOR);
        return jc;
    }





/*    public int getColumnCount(){
        return TABLE_COLUMN_NAMES.length;
    }*/
}

