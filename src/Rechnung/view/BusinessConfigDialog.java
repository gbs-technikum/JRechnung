package Rechnung.view;

import Rechnung.view.ConfigDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BusinessConfigDialog extends ConfigDialog{

    private JPanel mainPanel;
    private JTextField jtfName, jtfProprietor, jtfStreet, jtfStreetNumber, jtfPostcode;
    private JComboBox<String> jcbxLegalform;

    public BusinessConfigDialog(JFrame frame) {
        super(frame, "Unternehmen");
        this.initComponents();
    }

    private void initComponents() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));

        this.jtfName = new JTextField();
        this.jtfName.setBorder(new TitledBorder("Name"));

        this.jtfProprietor = new JTextField();
        this.jtfProprietor.setBorder(new TitledBorder("Inhaber"));

        this.jtfStreet = new JTextField();
        this.jtfStreet.setBorder(new TitledBorder("Straße"));

        this.jtfStreetNumber = new JTextField();
        this.jtfStreetNumber.setBorder(new TitledBorder("Hausnummer"));

        this.jtfPostcode = new JTextField();
        this.jtfPostcode.setBorder(new TitledBorder("Postleitzahl"));

        this.jcbxLegalform = new JComboBox<String>();


        this.mainPanel.add(this.jtfName);
        this.mainPanel.add(this.jtfProprietor);
        this.mainPanel.add(this.jtfStreet);
        this.mainPanel.add(this.jtfStreetNumber);
        this.mainPanel.add(this.jtfPostcode);
        this.mainPanel.add(this.jcbxLegalform);

        this.addMainPanel(this.mainPanel);
    }

    public void setTextName(String name){
        this.jtfName.setText(name);
    }

    public void setTextProprietor(String proprietor){
        this.jtfProprietor.setText(proprietor);
    }

    public void setTextStreet(String street){
        this.jtfStreet.setText(street);
    }

    public void setTextStreetNumber(String streetNumber){
        this.jtfStreetNumber.setText(streetNumber);
    }

    public void setTextPostcode(String postcode){
        this.jtfPostcode.setText(postcode);
    }

    public void selectLegalform(int index){
        this.jcbxLegalform.setSelectedIndex(index);
    }

    public void setLegalform(List<String> legalform){
        for (String lf: legalform) {
            this.jcbxLegalform.addItem(lf);
        }
    }

    public int getIndexByLegalFormText(String legalFormText){

        for(int i=0;i<this.jcbxLegalform.getItemCount();i++){
            String item = this.jcbxLegalform.getItemAt(i);
            if(item.equals(legalFormText)){
                return i;
            }
        }

        return -1;
    }

    public String getTextName(){
        return this.jtfName.getText().toString();
    }

    public String getTextProprietor(){
        return this.jtfProprietor.getText().toString();
    }

    public String getTextStreet(){
        return this.jtfStreet.getText().toString();
    }

    public String getTextStreetNumber(){
        return this.jtfStreetNumber.getText().toString();
    }

    public String getTextPostcode(){
        return this.jtfPostcode.getText().toString();
    }

    public String getSelectedLegalFormText(){
        return this.jcbxLegalform.getItemAt(this.jcbxLegalform.getSelectedIndex());
    }

}
