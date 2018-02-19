package Kundenverwaltung.Listener;

import Kundenverwaltung.Customer;
import Kundenverwaltung.CustomerHelper;
import Kundenverwaltung.CustomerMain;
import Kundenverwaltung.View.CustomerWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

public class CustomerListener implements ActionListener, ItemListener{

    private CustomerWindow customerWindow;
    private Integer idFromSelectedCustomer;

    public CustomerListener(CustomerWindow customerWindow) {
        this.customerWindow = customerWindow;
        idFromSelectedCustomer = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CustomerMain customerMain = customerWindow.getCustomerMain();

        if(e.getSource() == customerWindow.getButtonAdd()){
            try {
                Customer customer = new Customer(new Integer(customerWindow.getTvId().getText()), customerWindow.getTvName().getText(), customerWindow.getTvForename().getText(), customerWindow.getTvStreet().getText(), customerWindow.getTvHouseNumber().getText(), customerWindow.getTvPostCode().getText(), customerWindow.getTvVillage().getText(), customerWindow.getTvLand().getText());
                customerMain.getCustomers().addCustomer(customer);
                customerMain.getCustomerService().saveCustomer(customer);
                customerWindow.getCbKunden().addItem(CustomerHelper.getStringForCustomerComboBox(customer));
                reset();
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Daten konnten nicht gespeichert werden!\nBitte überprüfen Sie, ob die Kundennummer vergeben ist!", "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Sie müssen eine Kundennummer als Zahl zwischen -2.147.483.648 und 2.147.483.647 eingeben!", "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
            }
        }else if(e.getSource() == customerWindow.getButtonChange()){
            try {
                if(idFromSelectedCustomer != null){
                    Customer customer = customerMain.getCustomers().getCustomerById(idFromSelectedCustomer);
                    customerMain.getCustomerService().changeCustomer(idFromSelectedCustomer, customer);
                    customerWindow.getCbKunden().setSelectedItem(CustomerHelper.getStringForCustomerComboBox(customer));
                }else{
                    JOptionPane.showMessageDialog(null, "Sie müssen einen Kunden auswählen!", "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Daten konnten nicht geändert werden!\nBitte überprüfen Sie, ob die evtl. neue Kundennummer vergeben ist!", "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e1){
                JOptionPane.showMessageDialog(null, "Sie müssen eine Kundennummer als Zahl zwischen -2.147.483.648 und 2.147.483.647 eingeben!", "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
            }
        }else if(e.getSource() == customerWindow.getButtonDelete()){
            try {
                if(idFromSelectedCustomer != null){
                    customerWindow.getCbKunden().removeItem(customerWindow.getCbKunden().getSelectedItem());
                    customerMain.getCustomerService().removeCustomer(idFromSelectedCustomer);
                    reset();
                }else {
                    JOptionPane.showMessageDialog(null, "Sie müssen einen Kunden auswählen!", "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }else if(e.getSource() == customerWindow.getButtonNew()){
            reset();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    private void reset(){
        customerWindow.reset();
        idFromSelectedCustomer = null;
    }
}
