package Kundenverwaltung;

public class CustomerHelper {

    public static String getStringForCustomerComboBox(Customer customer){
        return customer.getId() + " : " + customer.getName() + " " + customer.getForename();
    }
}
