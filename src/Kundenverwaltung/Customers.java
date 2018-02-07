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

    public boolean removeCustomer(Customer customer){
        Iterator iterator = customers.iterator();
        while (iterator.hasNext()){
            Customer newCustomer = (Customer) iterator.next();
            if(newCustomer.equals(customer)){
                iterator.remove();
                return true;
            }
        }
        return false;
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
