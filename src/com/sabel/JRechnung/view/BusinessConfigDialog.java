package com.sabel.JRechnung.view;

import com.sabel.JRechnung.model.objects.Customer;
import com.sabel.JRechnung.model.objects.LegalForm;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.util.List;

public class BusinessConfigDialog extends ConfigDialog{

    private JPanel mainPanel;
    private JTextField jtfName, jtfProprietor, jtfStreet, jtfStreetNumber, jtfPostcode,jtfLocation, jtfTaxNumber, jtfJurisdiction;
    private JTextField jtfPhone, jtfFax, jtfEmail;
    private JComboBox<LegalForm> jcbxLegalform;

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

        this.jtfLocation = new JTextField();
        this.jtfLocation.setBorder(new TitledBorder("Ort"));

        this.jtfTaxNumber = new JTextField();
        this.jtfTaxNumber.setBorder(new TitledBorder("Steuernummer"));

        this.jtfJurisdiction = new JTextField();
        this.jtfJurisdiction.setBorder(new TitledBorder("Gerichtsstand"));

        this.jtfPhone = new JTextField();
        this.jtfPhone.setBorder(new TitledBorder("Telefon"));

        this.jtfFax = new JTextField();
        this.jtfFax.setBorder(new TitledBorder("Fax"));

        this.jtfEmail = new JTextField();
        this.jtfEmail.setBorder(new TitledBorder("EMail-Adresse"));

        this.jcbxLegalform = new JComboBox<LegalForm>();


        this.mainPanel.add(this.jtfName);
        this.mainPanel.add(this.jtfProprietor);
        this.mainPanel.add(this.jtfStreet);
        this.mainPanel.add(this.jtfStreetNumber);
        this.mainPanel.add(this.jtfPostcode);
        this.mainPanel.add(this.jtfLocation);
        this.mainPanel.add(this.jtfTaxNumber);
        this.mainPanel.add(this.jtfJurisdiction);
        this.mainPanel.add(this.jtfPhone);
        this.mainPanel.add(this.jtfFax);
        this.mainPanel.add(this.jtfEmail);
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

    public void setTextLocation(String location){
        this.jtfLocation.setText(location);
    }

    public void setTextTaxNumber(String location){
        this.jtfTaxNumber.setText(location);
    }

    public void setTextJurisdiction(String jurisdiction){
        this.jtfJurisdiction.setText(jurisdiction);
    }

    public void setTextPhone(String phone){
        this.jtfPhone.setText(phone);
    }
    public void setTextFax(String fax){
        this.jtfFax.setText(fax);
    }
    public void setTextEmail(String email){
        this.jtfEmail.setText(email);
    }

    public String getTextName(){
        return this.jtfName.getText();
    }

    public String getTextProprietor(){
        return this.jtfProprietor.getText();
    }

    public String getTextStreet(){
        return this.jtfStreet.getText();
    }

    public String getTextStreetNumber(){
        return this.jtfStreetNumber.getText();
    }

    public String getTextPostcode(){
        return this.jtfPostcode.getText();
    }

    public String getTextLocation(){
        return this.jtfLocation.getText();
    }

    public String getTextTaxNumber(){
        return this.jtfTaxNumber.getText();
    }

    public String getTextJurisdiction(){
        return this.jtfJurisdiction.getText();
    }

    public String getTextPhone(){
        return this.jtfPhone.getText();
    }
    public String getTextFax(){
        return this.jtfFax.getText();
    }
    public String getTextEmail(){
        return this.jtfEmail.getText();
    }

    public void addToLegalFormList(LegalForm legalForm){
        if(legalForm != null){
            this.jcbxLegalform.addItem(legalForm);
        }
    }

    public int getIndexOfSelectedLegalForm(){
        return this.jcbxLegalform.getSelectedIndex();
    }

    public LegalForm getLegalFormFromList(int index){
        if(index >= 0 && index < this.jcbxLegalform.getItemCount()){
            return this.jcbxLegalform.getItemAt(index);
        }

        return null;
    }

    public void setIndexOfSelectedLegalForm(int index){
        if(index >= 0 && index < this.jcbxLegalform.getItemCount()) {
            this.jcbxLegalform.setSelectedIndex(index);
        }
    }

}
