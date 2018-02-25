package Rechnung.Kundenverwaltung;

import java.sql.SQLException;

public class Customer_Test {

    public static void main(String[] args) {

        Customer customer = new Customer(1, "Müller", "Max", "Musterstraße", "23", "433564", "Musterhausen", "Deutschland");
        Customer customer2 = new Customer(2, "Musterfrau", "Michael", "Mustergasse", "53", "43253", "Musterdorf", "");
        Customer customer3 = new Customer(6, "Mustermensch", "Monika", "Musteranger", "43", "34234,", "Musterstedt", "England");
        customer.addPhoneNumber("+064 4347 234");
        customer.addPhoneNumber("+23498235461234");
        customer.addMailAddress("max.mueller@musterhausen.de");
        customer.addMailAddress("max.mueller@arbeitsplatz.de");
        System.out.println(customer);
        System.out.println(customer2);
        System.out.println(customer3);

        Customers customers = new Customers();
        customers.addCustomer(customer);
        customers.addCustomer(customer2);
        customers.addCustomer(new Customer(3, "Mustermann", "Monika", "Musterweg", "43", "345254", "Musterstadt", ""));
        customers.addCustomer(customer3);
        System.out.println(customers);

        CustomerService customerService;
        try {
            customerService = new CustomerService();
            customerService.saveAllCustomers(customers);
            customerService.close();
            System.out.println(customers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}