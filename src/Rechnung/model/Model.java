package Rechnung.model;

import Rechnung.model.db.BusinessService;
import Rechnung.model.db.CustomerService;
import Rechnung.model.db.ProductOrServiceService;
import Rechnung.model.objects.Business;
import Rechnung.model.objects.Customer;
import Rechnung.model.objects.ProductOrService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        try {
            return CustomerService.removeCustomer(customer);
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }

        return false;
    }

    public List<ProductOrService> readProductsOrServices(){
        List<ProductOrService> result = new ArrayList<>();

        try {
            result = ProductOrServiceService.read();
        } catch (SQLException e) {
            e.printStackTrace(); //TODO
        }

        return result;
    }

    public boolean saveProductOrService(ProductOrService productOrService){
        try {
            return ProductOrServiceService.write(productOrService);
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO
        }

        return false;
    }

    public boolean removeProductOrService(ProductOrService productOrService){
        try {
            return ProductOrServiceService.remove(productOrService);
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }

        return false;
    }

    public String getNewObjectId(){
        return UUIDStringGenerator.generate();
    }

    public boolean isPasswordValid(String password){

        return  (password != null && password.length() > 7);
    }

    public boolean isFaxOrPhoneNumberValid(String number){
        return (number != null && number.length() > 5 && number.matches("[0-9]+"));
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
            result = false;
        }
        return result;
    }

    public String generateCustomerNumber(){
        return Long.toString(System.currentTimeMillis());
    }
}
