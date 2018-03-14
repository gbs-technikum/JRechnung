package Diensteverwaltung.View;

import Diensteverwaltung.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.util.ArrayList;



public class ProductList extends JTable {

    private static final String[] column = { "Position", "Name", "Beschreibung", "Preis"};


    public ProductList() {
        super(new DefaultTableModel(column, 30));
        setVisible(true);
    }

    public void fillProductList (ArrayList<Product> products){

    }
}
