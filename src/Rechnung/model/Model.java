package Rechnung.model;

import Rechnung.model.db.BillService;
import Rechnung.model.db.BusinessService;
import Rechnung.model.db.CustomerService;
import Rechnung.model.db.ProductOrServiceService;
import Rechnung.model.objects.Bill;
import Rechnung.model.objects.Business;
import Rechnung.model.objects.Customer;
import Rechnung.model.objects.ProductOrService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Model {

    private static final String DB_NAME = "jrechnung.sqlite";

    private Random random;

    public Model() {
        random = new Random();
    }

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

    public boolean saveBill(Bill bill){
        try {
            return BillService.save(bill);
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeBill(Bill bill){
        try {
            return BillService.remove(bill);
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }

        return false;
    }

    public List<Bill> readBillsInYear(int year){
        List<Bill> result = new ArrayList<>();

        try {
            result = BillService.readAllBillsInYear(year);
        } catch (SQLException e) {
            e.printStackTrace(); //TODO
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //TODO
        }

        return result;
    }

    public Date convert(String germanDateText){
        DateFormat format = new SimpleDateFormat("dd.mm.yyyy", Locale.GERMANY);
        Date date = null;
        try {
            date = format.parse(germanDateText);
        } catch (ParseException e) {

        }

        return date;
    }

    public String generateBillDefaultName(){
        return "Rechnung " + Integer.valueOf(random.nextInt());
    }

    public String generateBillNumber(){
        return Long.toString(System.currentTimeMillis());
    }

    public boolean isFloatingPointNumber(String text){

        text = text.replace(',','.');

        try
        {
            Double.parseDouble(text);
        }
        catch(NumberFormatException e)
        {
            return false;
        }

        return true;
    }

    public boolean isIntNumber(String text){
        try
        {
            Integer.parseInt(text);
        }
        catch(NumberFormatException e)
        {
            return false;
        }

        return true;
    }

    public static String getJarContainingFolder(Class aclass) throws Exception {
        CodeSource codeSource = aclass.getProtectionDomain().getCodeSource();

        File jarFile;

        if (codeSource.getLocation() != null) {
            jarFile = new File(codeSource.getLocation().toURI());
        }
        else {
            String path = aclass.getResource(aclass.getSimpleName() + ".class").getPath();
            String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));
            jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
            jarFile = new File(jarFilePath);
        }
        return jarFile.getParentFile().getAbsolutePath();
    }

    public String getDataBaseFileName(){
        return Model.DB_NAME;
    }

    public static boolean isCompletePriceDataValid(String[] completePriceData){
        int x = 0;

        for (String completePrice : completePriceData) {
            if(completePrice != null && completePrice.length() > 0){
                x ++;
            }
        }

        return x == completePriceData.length;
    }
}
