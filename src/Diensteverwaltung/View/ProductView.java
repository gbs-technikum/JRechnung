package Diensteverwaltung.View;

import Diensteverwaltung.Main;

import javax.swing.*;
import java.awt.*;


/*
    @author  Richter Nadine 2018-02-13

 */


public class ProductView extends JFrame{

    private JButton jbRemove, jbAdd, jbUpdate, jbNew;
    private JPanel jpNorth, jpSouth, jpCenter, jpTable;
    private JTextField tvPosition, tvName, tvDescription, tvPrice;
    private JComboBox jComboBox;

    private Main main;

    private JTable jTable;
    private JScrollPane jScrollPane;
    private JFrame jFrame = new JFrame();


   public ProductView(Main main){
       this.main = main;
       initComponents();
       this.setSize(900, 400);
       this.setVisible(true);

   }



    private void initComponents() {
       jComboBox = new JComboBox();

       jbAdd = new JButton("Dienstleistung hinzufügen");
       jbNew= new JButton("Neue Dienstleistung");
       jbRemove= new JButton("Dienstleistung löschen");
       jbUpdate = new JButton("Dienstleistung verändern");


       tvPosition = new JTextField("Position");
       tvName = new JTextField("Name");
       tvDescription = new JTextField("Beschreibung");
       tvPrice = new JTextField("Preis");
       jpTable = new JPanel();



       jpCenter = new JPanel();
       jpCenter.setLayout(new BoxLayout(jpCenter, BoxLayout.X_AXIS));
       this.add(jpCenter);
       jpNorth = new JPanel();
       this.add(jpNorth, BorderLayout.NORTH);
       jpSouth = new JPanel();
       this.add(jpSouth,BorderLayout.SOUTH);

       jpCenter.add(new JPanel());
       jpCenter.add(tvPosition);
       jpCenter.add(tvName);
       jpCenter.add(tvDescription);
       jpCenter.add(tvPrice);


       jpNorth.add(jComboBox);
       jpNorth.add(jbNew);
       jpNorth.add(jbRemove);

       jpSouth.add(jbAdd);
       jpSouth.add(jbUpdate);





    }


}
