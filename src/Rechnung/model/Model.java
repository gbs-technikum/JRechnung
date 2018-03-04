package Rechnung.model;

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
        return false;
    }

    public String getNewObjectId(){
        return UUIDStringGenerator.generate();
    }

    public boolean isPasswordValid(String password){

        return  (password != null && password.length() > 7);
    }


}
