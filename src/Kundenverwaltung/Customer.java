package Kundenverwaltung;

/*
    @author Gollmer 2018-01-31
    Class, which contents the strucutre for the custumer-data
 */

import java.util.ArrayList;

public class Customer {

    private int id;
    private String name;
    private String forename;
    private String street;
    private String houseNumber;
    private String postCode;
    private String village;
    private String land;

    private Contacts mailAddresses;
    private Contacts phoneNumbers;
    private Contacts faxNumbers;

    public Customer(int id, String name, String forename, String street, String houseNumber, String postCode, String village, String land) {
        this.id = id;
        this.name = name;
        this.forename = forename;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.village = village;
        this.land = land;

        mailAddresses = new Contacts();
        phoneNumbers = new Contacts();
        faxNumbers = new Contacts();
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

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    /**
     *
     * @return Returns the ArrayList of the MailAddresses
     */
    public ArrayList<String> getMailAddresses() {
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
    public ArrayList<String> getPhoneNumbers() {
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
    public ArrayList<String> getFaxNumbers() {
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

        if (id != customer.id) return false;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (forename != null ? !forename.equals(customer.forename) : customer.forename != null) return false;
        if (street != null ? !street.equals(customer.street) : customer.street != null) return false;
        if (houseNumber != null ? !houseNumber.equals(customer.houseNumber) : customer.houseNumber != null)
            return false;
        if (postCode != null ? !postCode.equals(customer.postCode) : customer.postCode != null) return false;
        if (village != null ? !village.equals(customer.village) : customer.village != null) return false;
        if (land != null ? !land.equals(customer.land) : customer.land != null) return false;
        if (mailAddresses != null ? !mailAddresses.equals(customer.mailAddresses) : customer.mailAddresses != null)
            return false;
        if (phoneNumbers != null ? !phoneNumbers.equals(customer.phoneNumbers) : customer.phoneNumbers != null)
            return false;
        return faxNumbers != null ? faxNumbers.equals(customer.faxNumbers) : customer.faxNumbers == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (forename != null ? forename.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
        result = 31 * result + (village != null ? village.hashCode() : 0);
        result = 31 * result + (land != null ? land.hashCode() : 0);
        result = 31 * result + (mailAddresses != null ? mailAddresses.hashCode() : 0);
        result = 31 * result + (phoneNumbers != null ? phoneNumbers.hashCode() : 0);
        result = 31 * result + (faxNumbers != null ? faxNumbers.hashCode() : 0);
        return result;
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
                ", land='" + land + '\'' +
                ", mailAddresses=" + mailAddresses +
                ", phoneNumbers=" + phoneNumbers +
                ", faxNumbers=" + faxNumbers +
                '}';
    }
}
