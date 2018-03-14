package Rechnung.view;

import Rechnung.model.Customer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;


public class CustomersConfigDialog extends ConfigDialog {

    private JComboBox<Customer> jcbxCustomer;
    private JTextField jtfNumber, jtfName, jtfForename, jtfStreet, jtfHouseNumber, jtfPostCode, jtfVillage, jtfLand;
    private PanelAccessibility panelEmail, panelPhone, panelFax;
    private JButton buttonDelete, buttonNew;
    private JPanel mainPanel;

    private JPanel jPanelNorth, jPanelCenter, jPanelSouth;
    private JPanel jPanelCenterCustomer, jPanelCenterContacts;


    public CustomersConfigDialog(JFrame frame) throws HeadlessException {
        super(frame,"Kundenverwaltung");
        this.initComponents();
        this.setPreferredSize(new Dimension(800,600));
        this.pack();
    }

    public void initComponents(){

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());

        jcbxCustomer = new JComboBox<>();

        jtfNumber = new JTextField();
        jtfNumber.setBorder(new TitledBorder("Kundennummer"));
        jtfName = new JTextField();
        jtfName.setBorder(new TitledBorder("Name"));
        jtfForename = new JTextField();
        jtfForename.setBorder(new TitledBorder("Vorname"));
        jtfStreet = new JTextField();
        jtfStreet.setBorder(new TitledBorder("Straße"));
        jtfHouseNumber = new JTextField();
        jtfHouseNumber.setBorder(new TitledBorder("Hausnummer"));
        jtfPostCode = new JTextField();
        jtfPostCode.setBorder(new TitledBorder("Postleitzahl"));
        jtfVillage = new JTextField();
        jtfVillage.setBorder(new TitledBorder("Ort"));
        jtfLand = new JTextField();
        jtfLand.setBorder(new TitledBorder("Land"));

        panelEmail = new PanelAccessibility("E-Mail");
        panelPhone = new PanelAccessibility("Telefon");
        panelFax = new PanelAccessibility("Fax");

        buttonDelete = new JButton("Löschen");
        buttonNew = new JButton("Neu");

        jPanelNorth = new JPanel();
        jPanelCenter = new JPanel();
        jPanelCenter.setLayout(new BoxLayout(jPanelCenter, BoxLayout.Y_AXIS));
        jPanelSouth = new JPanel();
        jPanelCenterCustomer = new JPanel(new GridLayout(4, 2));
        jPanelCenterContacts = new JPanel(new GridLayout(1, 3));

        jPanelNorth.add(jcbxCustomer);
        jPanelNorth.add(buttonDelete);
        jPanelNorth.add(buttonNew);

        jPanelCenterCustomer.add(jtfNumber);
        jPanelCenterCustomer.add(jtfName);
        jPanelCenterCustomer.add(jtfForename);
        jPanelCenterCustomer.add(jtfStreet);
        jPanelCenterCustomer.add(jtfHouseNumber);
        jPanelCenterCustomer.add(jtfPostCode);
        jPanelCenterCustomer.add(jtfVillage);
        jPanelCenterCustomer.add(jtfLand);

        jPanelCenterContacts.add(panelEmail);
        jPanelCenterContacts.add(panelPhone);
        jPanelCenterContacts.add(panelFax);

        jPanelCenter.add(jPanelCenterCustomer);
        jPanelCenter.add(jPanelCenterContacts);

        this.mainPanel.add(jPanelNorth, BorderLayout.NORTH);
        this.mainPanel.add(jPanelCenter);
        this.mainPanel.add(jPanelSouth, BorderLayout.SOUTH);

        this.addMainPanel(this.mainPanel);

        this.jtfNumber.setEnabled(false);
        this.jtfForename.setEnabled(false);
        this.jtfStreet.setEnabled(false);
        this.jtfName.setEnabled(false);
        this.jtfHouseNumber.setEnabled(false);
        this.jtfPostCode.setEnabled(false);
        this.jtfVillage.setEnabled(false);
        this.jtfLand.setEnabled(false);
        this.buttonDelete.setEnabled(false);
        this.buttonNew.setEnabled(false);
    }

    public void setNumberTextFieldEnabled(boolean enabled){
        this.jtfNumber.setEnabled(enabled);
    }

    public void setForenameTextFieldEnabled(boolean enabled){
        this.jtfForename.setEnabled(enabled);
    }

    public void setStreetTextFieldEnabled(boolean enabled){
        this.jtfStreet.setEnabled(enabled);
    }

    public void setNameTextFieldEnabled(boolean enabled){
        this.jtfName.setEnabled(enabled);
    }

    public void setHouseNumberTextFieldEnabled(boolean enabled){
        this.jtfHouseNumber.setEnabled(enabled);
    }

    public void setPostCodeetJTextFieldEnabled(boolean enabled){
        this.jtfPostCode.setEnabled(enabled);
    }

    public void setVillageTextFieldEnabled(boolean enabled){
        this.jtfVillage.setEnabled(enabled);
    }

    public void setLandTextFieldEnabled(boolean enabled){
        this.jtfLand.setEnabled(enabled);
    }

    public String getNumberTextField() {
        return this.jtfNumber.getText().toString();
    }

    public String getNameTextField() {
        return this.jtfName.getText().toString();
    }

    public String getForennameTextField() {
        return this.jtfForename.getText().toString();
    }

    public String getStreetTextField() {
        return this.jtfStreet.getText().toString();
    }

    public String getHouseNumberTextField() {
        return this.jtfHouseNumber.getText().toString();
    }

    public String getPostCodeTextField() {
        return this.jtfPostCode.getText().toString();
    }

    public String getVillageTextField() {
        return this.jtfVillage.getText().toString();
    }

    public String getLandTextField() {
        return this.jtfLand.getText().toString();
    }


    public List<String> getEMailAcessibilityStringList(){
        return this.panelEmail.getAcessibilityStringList();
    }

    public List<String> getPhoneAcessibilityStringList(){
        return this.panelPhone.getAcessibilityStringList();
    }

    public List<String> getFaxAcessibilityStringList(){
        return this.panelFax.getAcessibilityStringList();
    }


    public void setNumberTextField(String text) {
        this.jtfNumber.setText(text);
    }

    public void setNameTextField(String text) {
        this.jtfName.setText(text);
    }

    public void setForennameTextField(String text) {
        this.jtfForename.setText(text);
    }

    public void setStreetTextField(String text) {
        this.jtfStreet.setText(text);
    }

    public void setHouseNumberTextField(String text) {
        this.jtfHouseNumber.setText(text);
    }

    public void setPostCodeTextField(String text) {
        this.jtfPostCode.setText(text);
    }

    public void setVillageTextField(String text) {
        this.jtfVillage.setText(text);
    }

    public void setLandTextField(String text) {
        this.jtfLand.setText(text);
    }

    public void setNewButtonListener(ActionListener listener){
        this.buttonNew.addActionListener(listener);
        this.buttonNew.setVisible(true);
        this.pack();
    }

    public void setNewButtonEnabled(boolean enabled){
        this.buttonNew.setEnabled(enabled);
    }

    public void setDeleteButtonEnabled(boolean enabled){
        this.buttonDelete.setEnabled(enabled);
    }


    public void setDeleteButtonListener(ActionListener listener){
        this.buttonDelete.addActionListener(listener);
        this.buttonDelete.setVisible(true);
        this.pack();
    }

    public void setEMailDeleteButtonEnabled(boolean enabled){
        this.panelEmail.setDeleteButtonEnabled(enabled);
    }

    public void setEMailDeleteButtonListener(ActionListener listener){
        this.panelEmail.setDeleteButtonListener(listener);
    }

    public void setEMailAddButtonEnabled(boolean enabled){
        this.panelEmail.setAddButtonEnabled(enabled);
    }

    public void setEMailAddButtonListener(ActionListener listener){
        this.panelEmail.setAddButtonListener(listener);
    }

    public void setPhoneDeleteButtonEnabled(boolean enabled){
        this.panelPhone.setDeleteButtonEnabled(enabled);
    }

    public void setPhoneDeleteButtonListener(ActionListener listener){
        this.panelPhone.setDeleteButtonListener(listener);
    }

    public void setPhoneAddButtonEnabled(boolean enabled){
        this.panelPhone.setAddButtonEnabled(enabled);
    }

    public void setPhoneAddButtonListener(ActionListener listener){
        this.panelPhone.setAddButtonListener(listener);
    }

    public void setFaxDeleteButtonEnabled(boolean enabled){
        this.panelFax.setDeleteButtonEnabled(enabled);
    }

    public void setFaxDeleteButtonListener(ActionListener listener){
        this.panelFax.setDeleteButtonListener(listener);
    }

    public void setFaxAddButtonEnabled(boolean enabled){
        this.panelFax.setAddButtonEnabled(enabled);
    }

    public void setFaxAddButtonListener(ActionListener listener){
        this.panelFax.setAddButtonListener(listener);
    }

    public void addToFaxList(String entry){
        this.panelFax.addToAcessibilityStringList(entry);
    }

    public void addToPhoneList(String entry){
        this.panelPhone.addToAcessibilityStringList(entry);
    }

    public void addToEMailList(String entry){
        this.panelEmail.addToAcessibilityStringList(entry);
    }

    public void removeEMailFromList(int index){
        this.panelEmail.removeItemFromAcessibilityStringList(index);
    }

    public void removeFaxFromList(int index){
        this.panelFax.removeItemFromAcessibilityStringList(index);
    }

    public void removePhoneFromList(int index){
        this.panelPhone.removeItemFromAcessibilityStringList(index);
    }

    public String getEMailInputText(){
        return this.panelEmail.getAcessibilityText();
    }

    public String getFaxInputText(){
        return this.panelFax.getAcessibilityText();
    }

    public String getPhoneInputText(){
        return this.panelPhone.getAcessibilityText();
    }

    public int getIndexOfSelectedEMail(){
        return this.panelEmail.getIndexOfSelectedAcessibility();
    }

    public int getIndexOfSelectedFax(){
        return this.panelFax.getIndexOfSelectedAcessibility();
    }

    public int getIndexOfSelectedPhone(){
        return this.panelPhone.getIndexOfSelectedAcessibility();
    }

    public void addToCustomerList(Customer customer){
        if(customer != null){
            this.jcbxCustomer.addItem(customer);
        }
    }

    public void removeCustomerFromList(int index){
        if(index >= 0 && index < this.jcbxCustomer.getItemCount()){
            this.jcbxCustomer.removeItemAt(index);
        }
    }

    public void setCustomerComboBoxListener(ActionListener listener){
        this.jcbxCustomer.addActionListener(listener);
    }

    public int getIndexOfSelectedCustomer(){
        return this.jcbxCustomer.getSelectedIndex();
    }


    public void setIndexOfSelectedCustomer(int index){
        if(index >= 0 && index < this.jcbxCustomer.getItemCount()) {
            this.jcbxCustomer.setSelectedIndex(index);
        }
    }

    public Customer getCustomerFromList(int index){
        if(index >= 0 && index < this.jcbxCustomer.getItemCount()){
            return this.jcbxCustomer.getItemAt(index);
        }

        return null;
    }

/*
    public void clearCustomerList(){
        this.jcbxCustomer.removeAllItems();
    }
*/


    public void clearComponentData(){
        this.jcbxCustomer.removeAllItems();

        this.panelFax.clearComponentData();
        this.panelPhone.clearComponentData();
        this.panelEmail.clearComponentData();

        this.jtfNumber.setText("");
        this.jtfForename.setText("");
        this.jtfStreet.setText("");
        this.jtfName.setText("");
        this.jtfHouseNumber.setText("");
        this.jtfPostCode.setText("");
        this.jtfVillage.setText("");
        this.jtfLand.setText("");
    }
}
