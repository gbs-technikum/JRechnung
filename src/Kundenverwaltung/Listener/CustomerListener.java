package Kundenverwaltung.Listener;

import Kundenverwaltung.Customer;
import Kundenverwaltung.CustomerHelper;
import Kundenverwaltung.CustomerMain;
import Kundenverwaltung.View.CustomerWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CustomerListener implements ActionListener{

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
                customer.setMailAddresses(CustomerHelper.getStringArrayListFromStringComboBox(customerWindow.getPanelEmail().getCbContact()));
                customer.setPhoneNumbers(CustomerHelper.getStringArrayListFromStringComboBox(customerWindow.getPanelPhone().getCbContact()));
                customer.setFaxNumbers(CustomerHelper.getStringArrayListFromStringComboBox(customerWindow.getPanelFax().getCbContact()));
                customerMain.getCustomers().addCustomer(customer);
                customerMain.getCustomerService().saveCustomer(customer);
                customerWindow.getCbKunden().addItem(CustomerHelper.getStringForCustomerComboBox(customer));
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
                    customerMain.getCustomers().changeCustomer(customer,
                            new Integer(customerWindow.getTvId().getText()),
                            customerWindow.getTvName().getText(),
                            customerWindow.getTvForename().getText(),
                            customerWindow.getTvStreet().getText(),
                            customerWindow.getTvHouseNumber().getText(),
                            customerWindow.getTvPostCode().getText(),
                            customerWindow.getTvVillage().getText(),
                            customerWindow.getTvLand().getText());
                    customer.setMailAddresses(CustomerHelper.getStringArrayListFromStringComboBox(customerWindow.getPanelEmail().getCbContact()));
                    customer.setPhoneNumbers(CustomerHelper.getStringArrayListFromStringComboBox(customerWindow.getPanelPhone().getCbContact()));
                    customer.setFaxNumbers(CustomerHelper.getStringArrayListFromStringComboBox(customerWindow.getPanelFax().getCbContact()));
                    customerMain.getCustomerService().changeCustomer(idFromSelectedCustomer, customer);
                    idFromSelectedCustomer = customer.getId();
                    System.out.println(CustomerHelper.getStringForCustomerComboBox(customer));
                    customerWindow.getCbKunden().setSelectedItem(CustomerHelper.getStringForCustomerComboBox(customer));
                }else{
                    JOptionPane.showMessageDialog(null, "Sie müssen einen Kunden auswählen!", "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Daten konnten nicht geändert werden!\nBitte überprüfen Sie, ob die evtl. neue Kundennummer vergeben ist!", "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "Sie müssen eine Kundennummer als Zahl zwischen -2.147.483.648 und 2.147.483.647 eingeben!", "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
            }
        }else if(e.getSource() == customerWindow.getButtonDelete()){
            try {
                if(idFromSelectedCustomer != null){
                    customerMain.getCustomerService().removeCustomer(idFromSelectedCustomer);
                    Customer customer = customerMain.getCustomers().getCustomerById(idFromSelectedCustomer);
                    customerMain.getCustomers().removeCustomer(customer);
                    customerWindow.getCbKunden().removeItem(customerWindow.getCbKunden().getSelectedItem());
                }else {
                    JOptionPane.showMessageDialog(null, "Sie müssen einen Kunden auswählen!", "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }else if(e.getSource() == customerWindow.getButtonNew()){
            reset();
        }else if(e.getSource() == customerWindow.getCbKunden()){
            reset();
            idFromSelectedCustomer = CustomerHelper.getCustomerIdFromSelectedItem(customerWindow.getCbKunden().getSelectedItem().toString());
            System.out.println(idFromSelectedCustomer);
            Customer customer = customerMain.getCustomers().getCustomerById(idFromSelectedCustomer);
            System.out.println(customer);
            customerWindow.getTvId().setText(String.valueOf(customer.getId()));
            customerWindow.getTvName().setText(customer.getName());
            customerWindow.getTvForename().setText(customer.getForename());
            customerWindow.getTvStreet().setText(customer.getStreet());
            customerWindow.getTvHouseNumber().setText(customer.getHouseNumber());
            customerWindow.getTvPostCode().setText(customer.getPostCode());
            customerWindow.getTvVillage().setText(customer.getVillage());
            customerWindow.getTvLand().setText(customer.getLand());
            CustomerHelper.addStringArrayListToStringComboBox(customerWindow.getPanelEmail().getCbContact(), customer.getMailAddresses());
            CustomerHelper.addStringArrayListToStringComboBox(customerWindow.getPanelPhone().getCbContact(), customer.getPhoneNumbers());
            CustomerHelper.addStringArrayListToStringComboBox(customerWindow.getPanelFax().getCbContact(), customer.getFaxNumbers());
        }
    }

    private void reset(){
        customerWindow.reset();
        idFromSelectedCustomer = null;
    }
}
