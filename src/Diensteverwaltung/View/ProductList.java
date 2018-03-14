package Diensteverwaltung.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class ProductList extends JTable {

    private static final String[] column = { "Position", "Name", "Beschreibung", "Preis"};


    public ProductList() {
        super(new DefaultTableModel(column, ));
    }
}
