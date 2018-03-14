package Diensteverwaltung.View;

import Diensteverwaltung.Main;
import Diensteverwaltung.Product;
import Diensteverwaltung.Products;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;


/*
    @author  Richter Nadine 2018-02-13

 */


public class ProductView extends JFrame{

    private JButton jbRemove, jbAdd, jbUpdate, jbNew;
    private JPanel jpNorth, jpSouth, jpCenter, jpTable;
    private JTextField tvPosition, tvName, tvDescription, tvPrice;
    private JComboBox jComboBox;

    private Main main;

    private JTable jTableProductlist;
    private JScrollPane jScrollPane;
    private JFrame jFrame = new JFrame();





    public ProductView(Main main){
       this.main = main;
       initComponents();
       this.setSize(900, 400);
       this.setVisible(true);

   }


    private void initComponents() {
       this.jComboBox = new JComboBox();



        this.jbAdd = new JButton("Dienstleistung hinzufügen");
        this.jbNew= new JButton("Neue Dienstleistung");
        this.jbRemove= new JButton("Dienstleistung löschen");
        this.jbUpdate = new JButton("Dienstleistung verändern");


        this.tvPosition = new JTextField("Position");
        this.tvName = new JTextField("Name");
        this.tvDescription = new JTextField("Beschreibung");
        this.tvPrice = new JTextField("Preis");
        this.jpTable = new JPanel();




        this.jpNorth = new JPanel();
       this.add(jpNorth, BorderLayout.NORTH);
        this.jpSouth = new JPanel();
       this.add(jpSouth,BorderLayout.SOUTH);

       this.jpCenter = new JPanel();
       this.jpCenter.setLayout(new BorderLayout());
       this.jTableProductlist = new ProductList();
       this.jScrollPane = new JScrollPane(this.jTableProductlist);
       this.jpCenter.add(jScrollPane, BorderLayout.CENTER);
        this.add(jpCenter);



        this.jpNorth.add(jComboBox);
        this.jpNorth.add(jbNew);
        this.jpNorth.add(jbRemove);

        this.jpSouth.add(jbAdd);
        this.jpSouth.add(jbUpdate);

        this.tvPosition.setEnabled(false);
        this.tvName.setEnabled(false);
        this.tvDescription.setEnabled(false);
        this.tvPrice.setEnabled(false);

        this.jbAdd.setEnabled(false);
        this.jbNew.setEnabled(false);
        this.jbRemove.setEnabled(false);
        this.jbUpdate.setEnabled(false);
    }



        public void setTextFieldPositionEnabled (boolean enabled){
            this.tvPosition.setEnabled(enabled);
        }

        public void setTextFieldNameEnabled(boolean enabled){
            this.tvName.setEnabled(enabled);
        }

        public void setTextFieldDescriptionEnabled(boolean enabled){
       this.tvDescription.setEnabled(enabled);
        }

        public void setTextFieldPriceEnabled(boolean enabled){
       this.tvPrice.setEnabled(enabled);
        }

        public void setTextFieldName(String name){
       this.tvName.setText(name);
        }

        public void getTextFieldName (){
       this.tvName.getText().toString();
        }

    public void setTextFieldDescription(String description){
        this.tvDescription.setText(description);
    }

    public void getTextFieldDescription (){
        this.tvDescription.getText().toString();
    }
    public void setTextFieldPrice(String price){
        this.tvPrice.setText(price);
    }

    public void getTextFieldPrice (){
        this.tvPrice.getText().toString();
    }

    public void setNewButtonListen(ActionListener listener){
       this.jbNew.addActionListener(listener);
       this.jbNew.setEnabled(true);
       this.pack();
    }

    public void setNewButtonEnabled (boolean enabled){
       this.jbNew.setEnabled(enabled);
    }

    public void setRemoveButtonListen(ActionListener listener){
        this.jbRemove.addActionListener(listener);
        this.jbRemove.setEnabled(true);
        this.pack();
    }

    public void setRemoveButtonEnabled (boolean enabled){
        this.jbRemove.setEnabled(enabled);
    }

    public void addToProductList (Product product){
       if (product != null) {
           this.jComboBox.addItem(product);
       }
    }

    public void removeProductFromListbyName (String name){
       this.jComboBox.removeItem(name);
    }

    public void removeProductFromListbyPosition (int position){
       if (position >= 0 && position < this.jComboBox.getItemCount()){
           this.jComboBox.removeItemAt(position);
       }
    }

    public Product getProductfromList(int position){
       if (position >= 0 && position < this.jComboBox.getItemCount()){
           return (Product) this.jComboBox.getItemAt(position);
       }
       return null;
    }

    public void setComboBoxListener (ActionListener listener){
       this.jComboBox.addActionListener(listener);
    }









}
