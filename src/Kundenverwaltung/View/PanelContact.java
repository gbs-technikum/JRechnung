package Kundenverwaltung.View;

import Kundenverwaltung.Listener.ContactListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PanelContact extends JPanel{

    private JTextField tvContact;
    private JComboBox<String> cbContact;
    private JPanel panelUp, panelDown;
    private JButton buttonAdd, buttonDelete;

    private ContactListener contactListener;

    private CustomerWindow customerWindow;

    public PanelContact(String title, CustomerWindow customerWindow) {
        super();
        this.customerWindow = customerWindow;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new TitledBorder(title));
        tvContact = new JTextField();
        cbContact = new JComboBox<>();
        panelUp = new JPanel(new GridLayout(1, 1));
        panelDown = new JPanel();
        panelDown.setLayout(new GridLayout(2, 1));
        buttonAdd = new JButton("Hinzufügen");
        buttonDelete = new JButton("Löschen");

        panelUp.add(tvContact);
        panelUp.add(buttonAdd);
        panelDown.add(cbContact);
        panelDown.add(buttonDelete);

        this.add(panelUp);
        this.add(panelDown);

        contactListener = new ContactListener(this);

        buttonAdd.addActionListener(contactListener);
        buttonDelete.addActionListener(contactListener);
    }

    public void reset(){
        cbContact.removeAllItems();
        resetText();
    }

    public void resetText(){
        tvContact.setText("");
    }

    public JTextField getTvContact() {
        return tvContact;
    }

    public void setTvContact(JTextField tvContact) {
        this.tvContact = tvContact;
    }

    public JComboBox<String> getCbContact() {
        return cbContact;
    }

    public void setCbContact(JComboBox<String> cbContact) {
        this.cbContact = cbContact;
    }

    public JPanel getPanelUp() {
        return panelUp;
    }

    public void setPanelUp(JPanel panelUp) {
        this.panelUp = panelUp;
    }

    public JPanel getPanelDown() {
        return panelDown;
    }

    public void setPanelDown(JPanel panelDown) {
        this.panelDown = panelDown;
    }

    public JButton getButtonAdd() {
        return buttonAdd;
    }

    public void setButtonAdd(JButton buttonAdd) {
        this.buttonAdd = buttonAdd;
    }

    public JButton getButtonDelete() {
        return buttonDelete;
    }

    public void setButtonDelete(JButton buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

    public ContactListener getContactListener() {
        return contactListener;
    }

    public void setContactListener(ContactListener contactListener) {
        this.contactListener = contactListener;
    }

    public CustomerWindow getCustomerWindow() {
        return customerWindow;
    }
}
