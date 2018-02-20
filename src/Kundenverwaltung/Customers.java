package Kundenverwaltung;

import java.util.ArrayList;
import java.util.Iterator;

/*
    @author Gollmer 2018-01-31
    This Class contents a List of Customers
 */

public class Customers {

    private ArrayList<Customer> customers;

    public Customers() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public Customer getCustomerById(int id){
        Iterator iterator = customers.iterator();
        while (iterator.hasNext()){
            Customer customer = (Customer) iterator.next();
            if(id == customer.getId()){
                return customer;
            }
        }
        return null;
    }

    public boolean removeCustomer(Customer customer){
        return customers.remove(customer);
    }

    public void changeCustomer(Customer customer, int id, String name, String forename, String street, String houseNumber, String postCode, String village, String land){
        customer.setId(id);
        customer.setName(name);
        customer.setForename(forename);
        customer.setStreet(street);
        customer.setHouseNumber(houseNumber);
        customer.setPostCode(postCode);
        customer.setVillage(village);
        customer.setLand(land);
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        String string = "";
        for (Customer customer:customers) {
            string += "\n" + customer;
        }
        return string;
    }
}
