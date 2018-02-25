package Rechnung.Kundenverwaltung;

import Rechnung.Kundenverwaltung.View.CustomerWindow;

import java.sql.SQLException;

public class CustomerMain {

    private Customers customers;
    private CustomerService customerService;
    private CustomerWindow customerWindow;

    public CustomerMain() {
        try {
            setCustomerService(new CustomerService());
            importCustomerData();
            setCustomerWindow(new CustomerWindow(this));
            for (Customer customer:customers.getCustomers()) {
                customerWindow.getCbKunden().addItem(CustomerHelper.getStringForCustomerComboBox(customer));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CustomerMain customerMain = new CustomerMain();
        System.out.println(customerMain.getCustomers());
    }

    public void importCustomerData(){
        try {
            customers = customerService.readAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerWindow getCustomerWindow() {
        return customerWindow;
    }

    public void setCustomerWindow(CustomerWindow customerWindow) {
        this.customerWindow = customerWindow;
    }
}
