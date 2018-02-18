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
        /*Iterator iterator = customers.iterator();
        while (iterator.hasNext()){
            Customer newCustomer = (Customer) iterator.next();
            if(newCustomer.equals(customer)){
                iterator.remove();
                return true;
            }
        }
        return false;*/
        return customers.remove(customer);
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
