package Kundenverwaltung;

public class CustomerHelper {

    public static String getStringForCustomerComboBox(Customer customer){
        return customer.getId() + " : " + customer.getName() + " " + customer.getForename();
    }

    public static String getCustomerIdFromSelectedItem(String stringFromSelectedItem){
        String[] parts = stringFromSelectedItem.split(" ");
        return parts[0];
    }
}
