package Rechnung.model.objects;

/*
    @author Gollmer 2018-01-31
    Class, which contents the strucutre for the custumer-data
 */

import java.util.*;

public class Customer {

    private String id;
    private String number;
    private String name;
    private String forename;
    private String street;
    private String houseNumber;
    private String postCode;
    private String village;
    private String land;

    private List<Accessibility> accessibilities;

    public Customer(String id, String  number, String name, String forename, String street, String houseNumber, String postCode, String village, String land) {
        this.id = id;
        System.out.println("new Custimer id" + this.id);
        this.number = number;
        this.name = name;
        this.forename = forename;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.village = village;
        this.land = land;

        this.accessibilities = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String id) {
        this.number = id;
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
    public List<Accessibility> getMailAddresses() {
        List<Accessibility> addresses = new ArrayList<>();

        for (Accessibility accessibility: this.accessibilities) {
            if(accessibility instanceof EMailAccessibility){
                addresses.add(accessibility);
            }

        }

        return addresses;
    }

    public List<Accessibility> getPhoneNumbers() {
        List<Accessibility> numbers = new ArrayList<>();

        for (Accessibility accessibility: this.accessibilities) {
            if(accessibility instanceof TelephoneAccessibility){
                numbers.add(accessibility);
            }

        }

        return numbers;
    }

    public List<Accessibility> getFaxNumbers() {
        List<Accessibility> numbers = new ArrayList<>();

        for (Accessibility accessibility: this.accessibilities) {
            if(accessibility instanceof FaxAccessibility){
                numbers.add(accessibility);
            }

        }

        return numbers;
    }

    public void addPhoneNumber(TelephoneAccessibility telephoneAccessibility){
        if(telephoneAccessibility != null){
            this.accessibilities.add(telephoneAccessibility);
        }
    }


    public TelephoneAccessibility removePhoneNumber(TelephoneAccessibility telephoneAccessibility){
        Iterator<Accessibility> it = this.accessibilities.iterator();

        while (it.hasNext()){
            Accessibility accessibility = it.next();
            if(accessibility instanceof TelephoneAccessibility && accessibility.getId().equals(telephoneAccessibility.getId())){
                it.remove();
                return (TelephoneAccessibility)accessibility;
            }
        }

        return null;
    }

    public void addEMail(EMailAccessibility eMailAccessibility){
        if(eMailAccessibility != null){
            this.accessibilities.add(eMailAccessibility);
        }
    }


    public EMailAccessibility removeEMail(EMailAccessibility eMailAccessibility){
        Iterator<Accessibility> it = this.accessibilities.iterator();

        while (it.hasNext()){
            Accessibility accessibility = it.next();
            if(accessibility instanceof EMailAccessibility && accessibility.getId().equals(eMailAccessibility.getId())){
                it.remove();
                return (EMailAccessibility)accessibility;
            }
        }

        return null;
    }

    public void addFaxNumber(FaxAccessibility faxAccessibility){
        if(faxAccessibility != null){
            this.accessibilities.add(faxAccessibility);
        }
    }


    public FaxAccessibility removeFaxNumber(FaxAccessibility faxAccessibility){
        Iterator<Accessibility> it = this.accessibilities.iterator();

        while (it.hasNext()){
            Accessibility accessibility = it.next();
            if(accessibility instanceof FaxAccessibility && accessibility.getId().equals(faxAccessibility.getId())){
                it.remove();
                return (FaxAccessibility)accessibility;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.getName() + " " + this.getNumber());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return this.id.equals(customer.getId());
    }


}
