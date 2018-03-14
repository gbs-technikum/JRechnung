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

    public static void main(String[] args) {
        showErrorMessageNoCustomerSelected();
        showErrorMessageCustomerCouldNotAccepted();
        showErrorMessageNoGuiltyCustomerId();
    }
}
