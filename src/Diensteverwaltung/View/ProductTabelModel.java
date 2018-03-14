package Diensteverwaltung.View;

import javax.swing.table.DefaultTableModel;

public class ProductTabelModel {

    String [] titles = new String []{"Position", "Name", "Beschreibung", "Preis"};

    final DefaultTableModel model = new DefaultTableModel( titles, 0 );
}
