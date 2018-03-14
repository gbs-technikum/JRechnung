package Allgemein;

import javax.swing.*;

public class Message {

    public static void showErrorMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
    }

    public static void showErrorMessageNoCustomerSelected(){
        showErrorMessage("Sie müssen einen Kunden auswählen!");
    }

    public static void showErrorMessageCustomerCouldNotAccepted(){
        showErrorMessage("Daten konnten nicht übernommen werden!\nBitte überprüfen Sie, ob die Kundennummer schon vergeben ist!");
    }

    public static void showErrorMessageNoGuiltyCustomerId(){
        showErrorMessage("Sie müssen eine Kundennummer als Zahl zwischen -2.147.483.648 und 2.147.483.647 eingeben!");
    }

    public static void showErrorMessageNoGuiltyNameOrForenameOrStreetOrVillage(){
        showErrorMessage("Name, Vorname, Straße und Ort sind Pflichtfelder und dürfen keine Zahl enthalten!");
    }

    public static void showErrorMessageNoGuiltyPostCode(){
        showErrorMessage("Postleitzahl ist ein Pflichtfeld und muss als Zahl eingegeben werden!");
    }

    public static void main(String[] args) {
        showErrorMessageNoCustomerSelected();
        showErrorMessageCustomerCouldNotAccepted();
        showErrorMessageNoGuiltyCustomerId();
    }
}
