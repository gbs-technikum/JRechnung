package Rechnung;

import Rechnung.model.*;

import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

public class TestKlasse {

    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException, InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException {
        //Customer customer = new Customer(UUIDStringGenerator.generate(), "YYYYYYYYYY","Mustermann", "Max", "Musterstraße", "23", "433564", "Musterhausen", "Deutschland");
        Customer customer = new Customer(UUIDStringGenerator.generate(),"AAAAAAAAAAA", "Musterfrau", "Michael", "Mustergasse", "53", "43253", "Musterdorf", "");
        Customer customer3 = new Customer(UUIDStringGenerator.generate(), "xxxx","Mustermensch", "Monika", "Musteranger", "43", "34234,", "Musterstedt", "England");
        customer.addEMail(new EMailAccessibility(UUIDStringGenerator.generate(),"test1.gmx.de"));
        customer.addEMail(new EMailAccessibility(UUIDStringGenerator.generate(),"test2.gmx.de"));
        customer.addEMail(new EMailAccessibility(UUIDStringGenerator.generate(),"test3.gmx.de"));

        customer.addFaxNumber(new FaxAccessibility(UUIDStringGenerator.generate(),"1111111"));
        customer.addFaxNumber(new FaxAccessibility(UUIDStringGenerator.generate(),"2222222"));
        customer.addFaxNumber(new FaxAccessibility(UUIDStringGenerator.generate(),"3333333"));

        customer.addPhoneNumber(new TelephoneAccessibility(UUIDStringGenerator.generate(),"4444444"));
        customer.addPhoneNumber(new TelephoneAccessibility(UUIDStringGenerator.generate(),"5555555"));
        String teluuid = UUIDStringGenerator.generate();
        customer.addPhoneNumber(new TelephoneAccessibility(teluuid,"6666666"));

        SecurityProvider sp = Publisher.getSecurityProvider();
        sp.unlock("password");

         CustomerService.saveCustomer(customer);
        // customer.removePhoneNumber(new TelephoneAccessibility(teluuid,"6666666"));
       // CustomerService.saveCustomer(customer);

        System.out.println("\n----------------------------------------------------------------------------------\n");

        List<Customer> customers = CustomerService.readAllCustomers();

        for (Customer customer1 : customers) {
            System.out.println(customer1);
        }

   //     System.out.println(customer);
    }

}
