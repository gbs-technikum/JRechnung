package Rechnung.model;

import Allgemein.Message;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Model {

    public Business getBusiness(){
        Business business = null;

        try {
            business = BusinessService.readBusiness();
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }

        return business;
    }

    public boolean setBusiness(Business business){
        if(business != null){
            try {
                return BusinessService.writeBusiness(business);
            } catch (SQLException e) {
                //TODO
                e.printStackTrace();
            }
        }

        return false;
    }

    public List<Customer> readCustomers(){
        List<Customer> result = new ArrayList<>();

        try {
            result = CustomerService.readAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace(); //TODO
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //TODO
        }

        return result;
    }

    public boolean saveCustomer(Customer customer){
        try {
            return CustomerService.saveCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //TODO
        }

        return false;
    }

    public boolean removeCustomer(Customer customer){
        return false;
    }

    public String getNewObjectId(){
        return UUIDStringGenerator.generate();
    }

    public boolean isPasswordValid(String password){

        return  (password != null && password.length() > 7 && charsCorrespondPasswordPolicy(password));
    }

    public boolean isNameOrForenameOrStreetOrVillageValid(String fieldname, String worth){
        if (Pattern.compile( "[0-9]" ).matcher(worth).find() || worth.isEmpty()){
            Message.showErrorMessageNoGuiltyNameOrForenameOrStreetOrVillage(fieldname);
            return false;
        }
        return true;
    }

    public boolean isHouseNumberValid(String houseNumber){
        if(!Pattern.compile( "[0-9]" ).matcher(houseNumber).find() || houseNumber.isEmpty() || houseNumber.contains(" ")){
            Message.showErrorMessageNoGuiltyHouseNumber();
            return false;
        }
        return true;
    }

    public boolean isPostCodeValid(String number){
        try{
            Integer.parseInt(number);
        }catch (NumberFormatException e){
            Message.showErrorMessageNoGuiltyPostCode();
            return false;
        }
        return true;
    }

    public boolean isLandValid(String land){
        if(Pattern.compile( "[0-9]" ).matcher(land).find()){
            Message.showErrorMessageNoGuiltyLand();
            return false;
        }
        return true;
    }

    public boolean isFaxOrPhoneNumberValid(String number){
        if(number != null && number.length() > 5 && number.matches("[0-9]+")){
            return true;
        }
        Message.showErrorMessageNoGuiltyPhoneOrFaxNumber();
        return false;
    }

    public boolean isStringInList(List<String> list, String needle){
        for (String s : list) {
            if(s.equals(needle)){
                return true;
            }
        }

        return false;
    }

    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            Message.showErrorMessageNoGuiltyMailAddress();
            result = false;
        }
        return result;
    }

    public boolean charsCorrespondPasswordPolicy(String password){
        boolean containUpperCase = false;
        boolean containLowerCase = false;
        boolean containNumber = false;
        for (int i=0; i<password.length(); i++) {
            if(containUpperCase && containLowerCase && containNumber){
                return true;
            }
            char c = password.charAt(i);
            if(Character.isUpperCase(c)){
                containUpperCase = true;
                continue;
            }
            if(Character.isLowerCase(c)){
                containLowerCase = true;
                continue;
            }
            if(Character.isDigit(c)){
                containNumber = true;
                continue;
            }
        }
        return (containUpperCase && containLowerCase && containNumber);
    }

    public String generateCustomerNumber(){
        return Long.toString(System.currentTimeMillis());
    }
}
