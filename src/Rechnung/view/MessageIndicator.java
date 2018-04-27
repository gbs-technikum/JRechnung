package Rechnung.view;

import javax.swing.*;

public class MessageIndicator {
    public static void showErrorMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
    }
}
