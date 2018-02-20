package Kundenverwaltung;

import javax.swing.*;
import java.util.ArrayList;

public class CustomerHelper {

    public static String getStringForCustomerComboBox(Customer customer){
        return customer.getId() + " : " + customer.getName() + " " + customer.getForename();
    }

    public static int getCustomerIdFromSelectedItem(String stringFromSelectedItem){
        String[] parts = stringFromSelectedItem.split(" ");
        return Integer.parseInt(parts[0]);
    }

    public static ArrayList<String> getStringArrayListFromStringComboBox(JComboBox comboBox){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for(int i=0; i<comboBox.getItemCount(); i++){
            stringArrayList.add(comboBox.getItemAt(i).toString());
        }
        return stringArrayList;
    }

    public static void addStringArrayListToStringComboBox(JComboBox<String> stringJComboBox, ArrayList<String> stringArrayList){
        for (String item:stringArrayList) {
            stringJComboBox.addItem(item);
        }
    }
}
