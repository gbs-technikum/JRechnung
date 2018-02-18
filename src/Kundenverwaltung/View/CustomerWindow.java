package Kundenverwaltung.View;

import Kundenverwaltung.CustomerMain;
import Kundenverwaltung.Listener.CustomerListener;

import javax.swing.*;
import java.awt.*;

public class CustomerWindow extends JFrame {

    private JComboBox<String> cbKunden;
    private JTextField tvId, tvName, tvForename, tvStreet, tvHouseNumber, tvPostCode, tvVillage, tvLand;
    private PanelContact panelEmail, panelPhone, panelFax;
    private JButton buttonAdd, buttonChange, buttonDelete, buttonNew;

    private JPanel jPanelNorth, jPanelCenter, jPanelSouth;
    private JPanel jPanelCenterCustomer, jPanelCenterContacts;

    private CustomerListener customerListener;

    private CustomerMain customerMain;

    public CustomerWindow(CustomerMain customerMain) throws HeadlessException {
        super("Kundenverwaltung");
        this.customerMain = customerMain;
        initComponents();
        this.pack();
        this.setVisible(true);
        initAction();
    }

    public void initComponents(){
        cbKunden = new JComboBox<>();

        tvId = new JTextField();
        tvName = new JTextField();
        tvForename = new JTextField();
        tvStreet = new JTextField();
        tvHouseNumber = new JTextField();
        tvPostCode = new JTextField();
        tvVillage = new JTextField();
        tvLand = new JTextField();

        panelEmail = new PanelContact("E-Mail", this);
        panelPhone = new PanelContact("Telefon", this);
        panelFax = new PanelContact("Fax", this);

        buttonAdd = new JButton("Hinzufügen");
        buttonChange = new JButton("Änderung übernehmen");
        buttonDelete = new JButton("Löschen");
        buttonNew = new JButton("Neu");

        jPanelNorth = new JPanel();
        jPanelCenter = new JPanel();
        jPanelCenter.setLayout(new BoxLayout(jPanelCenter, BoxLayout.Y_AXIS));
        jPanelSouth = new JPanel();
        jPanelCenterCustomer = new JPanel(new GridLayout(4, 2));
        jPanelCenterContacts = new JPanel(new GridLayout(1, 3));

        jPanelNorth.add(cbKunden);
        jPanelNorth.add(buttonDelete);
        jPanelNorth.add(buttonNew);

        jPanelCenterCustomer.add(tvId);
        jPanelCenterCustomer.add(tvName);
        jPanelCenterCustomer.add(tvForename);
        jPanelCenterCustomer.add(tvStreet);
        jPanelCenterCustomer.add(tvHouseNumber);
        jPanelCenterCustomer.add(tvPostCode);
        jPanelCenterCustomer.add(tvVillage);
        jPanelCenterCustomer.add(tvLand);

        jPanelCenterContacts.add(panelEmail);
        jPanelCenterContacts.add(panelPhone);
        jPanelCenterContacts.add(panelFax);

        jPanelCenter.add(jPanelCenterCustomer);
        jPanelCenter.add(jPanelCenterContacts);

        jPanelSouth.add(buttonAdd);
        jPanelSouth.add(buttonChange);

        this.add(jPanelNorth, BorderLayout.NORTH);
        this.add(jPanelCenter);
        this.add(jPanelSouth, BorderLayout.SOUTH);
    }

    private void initAction() {
        customerListener = new CustomerListener(this);
        buttonAdd.addActionListener(customerListener);
        buttonChange.addActionListener(customerListener);
        buttonDelete.addActionListener(customerListener);
        buttonNew.addActionListener(customerListener);
        cbKunden.addItemListener(customerListener);
    }

    public void reset(){
        tvId.setText("");
        tvName.setText("");
        tvForename.setText("");
        tvStreet.setText("");
        tvHouseNumber.setText("");
        tvPostCode.setText("");
        tvVillage.setText("");
        tvLand.setText("");

        panelEmail.reset();
        panelPhone.reset();
        panelFax.reset();
    }

    public JComboBox<String> getCbKunden() {
        return cbKunden;
    }

    public void setCbKunden(JComboBox<String> cbKunden) {
        this.cbKunden = cbKunden;
    }

    public JTextField getTvId() {
        return tvId;
    }

    public void setTvId(JTextField tvId) {
        this.tvId = tvId;
    }

    public JTextField getTvName() {
        return tvName;
    }

    public void setTvName(JTextField tvName) {
        this.tvName = tvName;
    }

    public JTextField getTvForename() {
        return tvForename;
    }

    public void setTvForename(JTextField tvForename) {
        this.tvForename = tvForename;
    }

    public JTextField getTvStreet() {
        return tvStreet;
    }

    public void setTvStreet(JTextField tvStreet) {
        this.tvStreet = tvStreet;
    }

    public JTextField getTvHouseNumber() {
        return tvHouseNumber;
    }

    public void setTvHouseNumber(JTextField tvHouseNumber) {
        this.tvHouseNumber = tvHouseNumber;
    }

    public JTextField getTvPostCode() {
        return tvPostCode;
    }

    public void setTvPostCode(JTextField tvPostCode) {
        this.tvPostCode = tvPostCode;
    }

    public JTextField getTvVillage() {
        return tvVillage;
    }

    public void setTvVillage(JTextField tvVillage) {
        this.tvVillage = tvVillage;
    }

    public JTextField getTvLand() {
        return tvLand;
    }

    public void setTvLand(JTextField tvLand) {
        this.tvLand = tvLand;
    }

    public PanelContact getPanelEmail() {
        return panelEmail;
    }

    public void setPanelEmail(PanelContact panelEmail) {
        this.panelEmail = panelEmail;
    }

    public PanelContact getPanelPhone() {
        return panelPhone;
    }

    public void setPanelPhone(PanelContact panelPhone) {
        this.panelPhone = panelPhone;
    }

    public PanelContact getPanelFax() {
        return panelFax;
    }

    public void setPanelFax(PanelContact panelFax) {
        this.panelFax = panelFax;
    }

    public JButton getButtonAdd() {
        return buttonAdd;
    }

    public void setButtonAdd(JButton buttonAdd) {
        this.buttonAdd = buttonAdd;
    }

    public JButton getButtonChange() {
        return buttonChange;
    }

    public void setButtonChange(JButton buttonChange) {
        this.buttonChange = buttonChange;
    }

    public JButton getButtonDelete() {
        return buttonDelete;
    }

    public void setButtonDelete(JButton buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

    public JButton getButtonNew() {
        return buttonNew;
    }

    public void setButtonNew(JButton buttonNew) {
        this.buttonNew = buttonNew;
    }

    public JPanel getjPanelNorth() {
        return jPanelNorth;
    }

    public void setjPanelNorth(JPanel jPanelNorth) {
        this.jPanelNorth = jPanelNorth;
    }

    public JPanel getjPanelCenter() {
        return jPanelCenter;
    }

    public void setjPanelCenter(JPanel jPanelCenter) {
        this.jPanelCenter = jPanelCenter;
    }

    public JPanel getjPanelSouth() {
        return jPanelSouth;
    }

    public void setjPanelSouth(JPanel jPanelSouth) {
        this.jPanelSouth = jPanelSouth;
    }

    public JPanel getjPanelCenterCustomer() {
        return jPanelCenterCustomer;
    }

    public void setjPanelCenterCustomer(JPanel jPanelCenterCustomer) {
        this.jPanelCenterCustomer = jPanelCenterCustomer;
    }

    public JPanel getjPanelCenterContacts() {
        return jPanelCenterContacts;
    }

    public void setjPanelCenterContacts(JPanel jPanelCenterContacts) {
        this.jPanelCenterContacts = jPanelCenterContacts;
    }

    public CustomerListener getCustomerListener() {
        return customerListener;
    }

    public void setCustomerListener(CustomerListener customerListener) {
        this.customerListener = customerListener;
    }

    public CustomerMain getCustomerMain() {
        return customerMain;
    }
}
