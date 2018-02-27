package Rechnung.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomersConfigDialog extends ConfigDialog {

    private JComboBox<String> jcbxCustomers;
    private JTextField jtfId, jtfName, jtfForename, jtfStreet, jtfHouseNumber, jtfPostCode, jtfVillage, jtfLand;
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

        jcbxCustomers = new JComboBox<>();

        jtfId = new JTextField();
        jtfId.setBorder(new TitledBorder("Kundennummer"));
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

        jPanelNorth.add(jcbxCustomers);
        jPanelNorth.add(buttonDelete);
        jPanelNorth.add(buttonNew);

        jPanelCenterCustomer.add(jtfId);
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

        this.jtfId.setEnabled(false);
        this.jtfForename.setEnabled(false);

        this.jtfStreet.setEnabled(false);

        this.jtfName.setEnabled(false);

        this.jtfHouseNumber.setEnabled(false);

        this.jtfPostCode.setEnabled(false);

        this.jtfVillage.setEnabled(false);

        this.jtfLand.setEnabled(false);

        this.buttonDelete.setEnabled(false);
        this.buttonNew.setEnabled(false);
        this.buttonDelete.setEnabled(false);


    }

    void setIdTextFieldEnabled(boolean enabled){
        this.jtfId.setEnabled(enabled);
    }

    void setForenameTextFieldEnabled(boolean enabled){
        this.jtfForename.setEnabled(enabled);
    }

    void setStreetTextFieldEnabled(boolean enabled){
        this.jtfStreet.setEnabled(enabled);
    }

    void setNameTextFieldEnabled(boolean enabled){
        this.jtfName.setEnabled(enabled);
    }

    void setHouseNumberTextFieldEnabled(boolean enabled){
        this.jtfHouseNumber.setEnabled(enabled);
    }

    void setPostCodeetJTextFieldEnabled(boolean enabled){
        this.jtfPostCode.setEnabled(enabled);
    }

    void setVillageTextFieldEnabled(boolean enabled){
        this.jtfVillage.setEnabled(enabled);
    }

    void setLandTextFieldEnabled(boolean enabled){
        this.jtfLand.setEnabled(enabled);
    }


    void setDeleteButtonEnabled(boolean enabled){
        this.buttonDelete.setEnabled(enabled);
    }

    public void setDeleteButtonListener(ActionListener listener){
        this.buttonDelete.addActionListener(listener);
        this.buttonDelete.setVisible(true);
        this.pack();
    }

}
