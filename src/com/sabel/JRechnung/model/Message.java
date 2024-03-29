package com.sabel.JRechnung.model;

import javax.swing.*;
import java.awt.*;

public class Message {

    public static void showErrorMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Fehlermeldung",  JOptionPane.ERROR_MESSAGE);
    }

    public static void showErrorMessagePasswordFalse(){
        showErrorMessage("Passwort ist falsch!");
    }

    public static void showErrorMessagePasswordNotEqualPassword2(){
        showErrorMessage("Das Neue Passwort ist nicht identisch mit der Wiederholdung!");
    }

    public static void showErrorMessageNoValidPassword(){
        showErrorMessage("Passwort muss mindestens 8 Zeichen, Klein-, Großbuchstaben und Zahlen enthalten!");
    }

    public static void showErrorMessageNoCustomerSelected(){
        showErrorMessage("Sie müssen einen Kunden auswählen!");
    }

    public static void showErrorMessageCustomerCouldNotAccepted(){
        showErrorMessage("Daten konnten nicht übernommen werden!\nBitte überprüfen Sie, ob die Kundennummer schon vergeben ist!");
    }

    public static void showErrorMessageNoValidCustomerId(){
        showErrorMessage("Sie müssen eine Kundennummer als Zahl zwischen -2.147.483.648 und 2.147.483.647 eingeben!");
    }

    public static void showErrorMessageNoValidNameOrForenameOrStreetOrVillage(String fieldname){
        showErrorMessage(fieldname + " ist ein Pflichtfeld und darf keine Zahl enthalten!");
    }

    public static void showErrorMessageNoValidHouseNumber() {
        showErrorMessage("Hausnummer ist ein Pflichtfeld, muss mindestens eine Zahl enthalten und darf kein Leerzeichen enthalten!");
    }

    public static void showErrorMessageNoValidLand(){
        showErrorMessage("Land darf keine Zahlen enthalten!");
    }

    public static void showErrorMessageNoValidPostCode(){
        showErrorMessage("Postleitzahl ist ein Pflichtfeld und muss als Zahl eingegeben werden!");
    }

    public static void showErrorMessageNoValidMailAddress(){
        showErrorMessage("Mailadresse ist ungültig!");
    }

    public static void showErrorMessageNoValidPhoneOrFaxNumber(){
        showErrorMessage("Die Telefon oder Fax Nummer ist ungültig!");
    }

    public static void showErrorMessageCantSaveFile(String fileName){
        showErrorMessage("Die Datei " + fileName + " kann nicht gespeichert werden!");
    }

    public static void showErrorMessagePrinting(){
        showErrorMessage("Dokument konnte nicht gedruckt werden!");
    }

    public static boolean showYesNoConfirmDialog(Component parent,String message, String title){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(parent, message, title, dialogButton);
        return  dialogResult == 0;
    }
}
