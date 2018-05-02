package Rechnung.model;

import Rechnung.model.db.BillService;
import Rechnung.model.db.BusinessService;
import Rechnung.model.db.CustomerService;
import Rechnung.model.db.ProductOrServiceService;
import Rechnung.model.objects.Bill;
import Rechnung.model.objects.Business;
import Rechnung.model.objects.Customer;
import Rechnung.model.objects.ProductOrService;
import Rechnung.view.WordFileExportDailog;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Model {

    private static final String DB_NAME = "jrechnung.sqlite";

    private Random random;
    private Configuration config;

    public Model() {
        this.config = new Configuration();
        this.random = new Random();
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

        if(password != null && password.length() > 7 && charsCorrespondPasswordPolicy(password)){
            return true;
        }
        Message.showErrorMessageNoValidPassword();
        return false;
    }

    public boolean isPasswordEqualsPassword2(String password, String password2){
        if(password.equals(password2)){
            return true;
        }
        Message.showErrorMessagePasswordNotEqualPassword2();
        return false;
    }

    public boolean isNameOrForenameOrStreetOrVillageValid(String fieldname, String worth){
        if (Pattern.compile( "[0-9]" ).matcher(worth).find() || worth.isEmpty()){
            Message.showErrorMessageNoValidNameOrForenameOrStreetOrVillage(fieldname);
            return false;
        }
        return true;
    }

    public boolean isHouseNumberValid(String houseNumber){
        if(!Pattern.compile( "[0-9]" ).matcher(houseNumber).find() || houseNumber.isEmpty() || houseNumber.contains(" ")){
            Message.showErrorMessageNoValidHouseNumber();
            return false;
        }
        return true;
    }

    public boolean isPostCodeValid(String number){
        try{
            Integer.parseInt(number);
        }catch (NumberFormatException e){
            Message.showErrorMessageNoValidPostCode();
            return false;
        }
        return true;
    }

    public boolean isLandValid(String land){
        if(Pattern.compile( "[0-9]" ).matcher(land).find()){
            Message.showErrorMessageNoValidLand();
            return false;
        }
        return true;
    }

    public boolean isFaxOrPhoneNumberValid(String number){
        if(number != null && number.length() > 5 && number.matches("[0-9]+")){
            return true;
        }
        Message.showErrorMessageNoValidPhoneOrFaxNumber();
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
            Message.showErrorMessageNoValidMailAddress();
            result = false;
        }
        return result;
    }

    public boolean charsCorrespondPasswordPolicy(String password){
        boolean containUpperCase = false;
        boolean containLowerCase = false;
        boolean containNumber = false;
        for (int i=0; i<=password.length(); i++) {
            if(containUpperCase && containLowerCase && containNumber){
                return true;
            }else if(i==password.length()){
                return false;
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
        return false;
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

    public boolean saveBillWithNewFile(Bill bill){
        try {
            return BillService.modifyBillFile(bill);
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

    public Bill readBill(String id){
        Bill bill = null;
        try {
            bill = BillService.read(id);
        } catch (SQLException e) {
            e.printStackTrace(); //TODO
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //TODO
        }

        return bill;
    }


    public Date germanDateStringToDate(String germanDateText){
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        Date date = null;
        try {
            date = format.parse(germanDateText);
        } catch (ParseException e) {

        }

        return date;
    }

    public String dateToGermanDateString(Date date){
        if(date != null){
            System.out.println(date.toString());

            DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

            return format.format(date);
        }

        return "";
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

    public File getFileFromResources(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        if(file.exists()){
            return file;
        }

        return null;
    }

    public ImageIcon getImageIconFromResources(String iconFileName){

        File f = this.getFileFromResources(iconFileName);

        if(f != null) {
            ImageIcon icon = new ImageIcon(f.getAbsolutePath());
            return icon;
        }

        return null;
    }

    public File getWordExportPath(){
        return new File(this.config.getWordFileExportPath());
    }

    public File getWordTemaple(){
        return new File(this.config.getWordTemplate());
    }

    public static File changeFileName(File file){
        if(!file.exists()){
            return file;
        }

        return changeFileName(new File(file.getPath() + file.getParentFile() + "0" + ".docx"));
    }

    public boolean loadConfigFile(){
        try {
            this.config.loadConfigFile();
        } catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }

    public File generateWordFile(Bill bill){
        if(bill != null) {
            File file = new File(this.getWordExportPath() + "/" + bill.getTitel() + ".docx");

            if (WordFileGenerator.generate(this.getWordTemaple(), file, bill, this.getBusiness())) {
                return file;
            }
        }

        return null;
    }

    public Date getTwoWeeksFromTodayDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 336);

        return cal.getTime();
    }
}
