package com.sabel.JRechnung.model;

import com.sabel.JRechnung.Main;
import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.db.BillService;
import com.sabel.JRechnung.model.db.BusinessService;
import com.sabel.JRechnung.model.db.CustomerService;
import com.sabel.JRechnung.model.db.ProductOrServiceService;
import com.sabel.JRechnung.model.objects.Bill;
import com.sabel.JRechnung.model.objects.Business;
import com.sabel.JRechnung.model.objects.Customer;
import com.sabel.JRechnung.model.objects.ProductOrService;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.CodeSource;
import java.sql.Connection;
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
    private List<Customer> tmpCustomers;
    private List<Bill> tmpBills;

    public Model() {
        this.config = new Configuration();
        this.random = new Random();
        this.tmpBills = null;
        this.tmpCustomers = null;
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
                return false;
            }
        }

        return false;
    }

    public boolean businessExists(){
        return getBusiness() != null;
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

    public boolean hasCustomerBills(Customer customer){
        try {
            return BillService.hasCustomerBills(customer);
        } catch (Exception e) {
            return true;
        }
    }

    public boolean thereAreCustomers(){
        List<Customer> customers = readCustomers();

        if(customers != null){
            return customers.size() > 0;
        }

        return false;
    }

    public String getNewObjectId(){
        return UUIDStringGenerator.generate();
    }

    public boolean isBillTitleValid(String billTitle){

        if(billTitle != null && billTitle.length() > 2){
            return true;
        }

        return false;
    }

    public boolean isBillNumberValid(String billNumber){

        if(billNumber != null && billNumber.length() > 2){
            return true;
        }

        return false;
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

    public boolean removeBill(Bill bill,boolean deleteFile){
        try {
            if(bill == null){
                return false;
            }
            if(bill.getBillFile() != null && deleteFile){
                bill.getBillFile().delete();
            }

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
            result = BillService.readAllBills(year);
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

            DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

            return format.format(date);
        }

        return "";
    }

    public String dateToGermanDateTimeString(Date date){
        if(date != null){

            DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);

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
        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        return file;

    }

    public ImageIcon getImageIconFromResources(String iconFileName){

        java.net.URL imgURL = getClass().getResource("/" + iconFileName);

        ImageIcon icon = new ImageIcon(imgURL);

        return icon;

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

    public boolean copyPreDefinedDBToApplicationDir(boolean replace) {
        File out = null;
        try {
            out = new File(Publisher.getModel().getJarContainingFolder(Main.class),Publisher.getModel().getDataBaseFileName());
        } catch (Exception e) {
           return false;
        }

        if (out == null || (!replace && out.exists())){
            return false;
        }

        InputStream resource = this.getClass().getResourceAsStream("/jrechnung.sqlite");
        if (resource == null){
            return false;
        }

                InputStream in = resource;
        try (OutputStream writer = new BufferedOutputStream(new FileOutputStream(out))) {

            byte[] buffer = new byte[1024 * 4];
            int length;
            while ((length = in.read(buffer)) >= 0) {
                writer.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }


        return true;
    }

    public boolean reEncryptDataBasePhase1(){

        try {
            this.tmpCustomers = CustomerService.readAllCustomers();
            this.tmpBills = BillService.readAllBills(0);
        } catch (UnsupportedEncodingException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    public boolean reEncryptDataBasePhase2(){

        if(this.tmpCustomers != null && this.tmpBills != null) {
            try {
                for (Customer customer : this.tmpCustomers) {
                    removeCustomer(customer);
                }

                CustomerService.saveAllCustomers(this.tmpCustomers);

                for (Bill bill : this.tmpBills) {
                    BillService.remove(bill);

                    BillService.save(bill);
                }
            } catch (UnsupportedEncodingException e) {
                return false;
            } catch (SQLException e) {
                return false;
            }
            return true;
        }

        return false;
    }

    public boolean writeSecretKeyFile(File keyfile){
        if(keyfile == null){
            return false;
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(keyfile,false));
            writer.write(Publisher.getSecurityProvider().getSecretKeyAsBase64());
        } catch (Exception e) {
            return false;
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }

        return true;
    }

    public String readSecretKeyFile(File keyfile){
        Scanner scanner = null;

        try {
            scanner = new Scanner(keyfile);
            StringBuilder fileContents = new StringBuilder((int)keyfile.length());

            String lineSeparator = System.getProperty("line.separator");

            while(scanner.hasNextLine()) {
                fileContents.append(scanner.next());
            }
            return fileContents.toString();
        } catch (Exception e) {
            return null;
        } finally {
            scanner.close();
        }

    }

    public int getCurrentYear(){
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    public List<Integer> getYearList(int rangeLength){
        int cYear = this.getCurrentYear();
        List<Integer> list = new ArrayList<>();

        for (int i = cYear-2; i < ((cYear-2)+rangeLength); i++) {
            list.add(Integer.valueOf(i));
        }

        return list;
    }

    public double calcCompletePriceForYear(List<Bill> billsOfYear){
        if(billsOfYear == null){
            return 0.0;
        }

        double result = 0.0;

        for (Bill bill : billsOfYear) {
            result += bill.getTotalPrice();
        }

        return result;
    }

    public double calcCompleteTax(List<Bill> billsOfYear){
        if(billsOfYear == null){
            return 0.0;
        }

        double result = 0.0;

        for (Bill bill : billsOfYear) {
            if(!bill.mustBeIncludedTaxes()){
                result += (bill.getTotalPrice() - bill.getEntryPriceSum());
            }else {
                for (Integer taxPercentage : bill.getTaxPercentages()) {
                    result += bill.getTaxValueForPercentage(taxPercentage.intValue());
                }
            }

        }

        return result;
    }

    public double calcNotYetPaidOrPaidPrice(List<Bill> billsOfYear, boolean paid){
        if(billsOfYear == null){
            return 0.0;
        }

        double result = 0.0;

        for (Bill bill : billsOfYear) {
            if(bill.isPaid() == paid) {
                result += bill.getTotalPrice();
            }
        }

        return result;
    }

    public String getCurrentGermanDateTime(){

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);

        return format.format(new Date());
    }

    public static boolean isDateAfterGivenDate(Date pastDate, Date dateAfterPast){
        return pastDate.after(dateAfterPast);
    }

    public boolean saveXMLConfigFile(File saveFile, String wordFileExportPath, String wordTemplateFile) {

        if(saveFile != null){

            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = null;
                dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                Element rootElement = doc.createElement("config");
                doc.appendChild(rootElement);

                Element wordFileExportPathElement = doc.createElement("export_path");
                rootElement.appendChild(wordFileExportPathElement);
                Attr attr = doc.createAttribute("value");
                attr.setValue(wordFileExportPath);
                wordFileExportPathElement.setAttributeNode(attr);

                Element wordTemplate = doc.createElement("word_template");
                rootElement.appendChild(wordTemplate);
                Attr attr1 = doc.createAttribute("value");
                attr1.setValue(wordTemplateFile);
                wordTemplate.setAttributeNode(attr1);

                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(saveFile);
                transformer.transform(source, result);

            } catch (Exception e) {
                return false;
            }

        }else
        {
            return false;
        }

        return true;
    }
}
