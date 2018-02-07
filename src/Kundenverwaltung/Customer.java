package Kundenverwaltung;

/*
    @author Gollmer 2018-01-31
    Class, which contents the strucutre for the custumer-data
 */

import java.util.ArrayList;
import java.util.Objects;

public class Customer {

    private int id;
    private String name;
    private String forename;
    private String street;
    private String houseNumber;
    private String postCode;
    private String village;
    private Contacts mailAddresses;
    private Contacts phoneNumbers;
    private Contacts faxNumbers;

    public Customer() {
        id = -1;
        mailAddresses = new Contacts();
        phoneNumbers = new Contacts();
        faxNumbers = new Contacts();
    }

    public Customer(String name, String forename, String street, String houseNumber, String postCode, String village) {
        this();
        this.name = name;
        this.forename = forename;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.village = village;
    }

    public Customer(int id, String name, String forename, String street, String houseNumber, String postCode, String village) {
        this(name, forename, street, houseNumber, postCode, village); // Must be the first statement in the constructor
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    /**
     *
     * @return Returns the ArrayList of the MailAddresses
     */
    public ArrayList<String> getArrayListMailAddresses() {
        return mailAddresses.getContactDatas();
    }

    public void addMailAddress(String mailAddress){
        mailAddresses.addContact(mailAddress);
    }

    public void setMailAddresses(ArrayList<String> mailAddresses) {
        this.mailAddresses.setContactDatas(mailAddresses);
    }

    public void removeMailAddress(String mailAddress){
        mailAddresses.removeContact(mailAddress);
    }

    /**
     *
     * @return Returns the ArrayList of the PhoneNumbers
     */
    public ArrayList<String> getArrayListPhoneNumbers() {
        return phoneNumbers.getContactDatas();
    }

    public void addPhoneNumber(String phoneNumber){
        phoneNumbers.addContact(phoneNumber);
    }

    public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
        this.phoneNumbers.setContactDatas(phoneNumbers);
    }

    public void removePhoneNumber(String phoneNumber){
        phoneNumbers.removeContact(phoneNumber);
    }

    /**
     *
     * @return Returns the ArrayList of the FaxNumbers
     */
    public ArrayList<String> getArrayListFaxNumbers() {
        return faxNumbers.getContactDatas();
    }

    public void addFaxNumber(String faxNumber){
        faxNumbers.addContact(faxNumber);
    }

    public void setFaxNumbers(ArrayList<String> faxNumbers) {
        this.faxNumbers.setContactDatas(faxNumbers);
    }

    public void removeFaxNumber(String faxNumber){
        faxNumbers.removeContact(faxNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id &&
                Objects.equals(name, customer.name) &&
                Objects.equals(forename, customer.forename) &&
                Objects.equals(street, customer.street) &&
                Objects.equals(houseNumber, customer.houseNumber) &&
                Objects.equals(postCode, customer.postCode) &&
                Objects.equals(village, customer.village) &&
                Objects.equals(mailAddresses, customer.mailAddresses) &&
                Objects.equals(phoneNumbers, customer.phoneNumbers) &&
                Objects.equals(faxNumbers, customer.faxNumbers);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, forename, street, houseNumber, postCode, village, mailAddresses, phoneNumbers, faxNumbers);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", forename='" + forename + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", postCode='" + postCode + '\'' +
                ", village='" + village + '\'' +
                ", mailAddresses=" + mailAddresses +
                ", phoneNumbers=" + phoneNumbers +
                ", faxNumbers=" + faxNumbers +
                '}';
    }
}
