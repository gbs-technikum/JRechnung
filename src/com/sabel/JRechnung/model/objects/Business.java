package com.sabel.JRechnung.model.objects;

public class Business {

    private String id;
    private String name;
    private String proprietor;
    private String street;
    private String streetNumber;
    private String location;
    private String postcode;
    private String taxNumber;
    private String jurisdiction;
    private String phone;
    private String fax;
    private String email;
    private LegalForm legalForm;


    public Business(String id, String name, String proprietor, String street, String streetNumber,
                    String postcode, String location, String taxNumber, String jurisdiction,
                    String phone, String fax, String email, LegalForm legalForm) {
        this.id = id;
        this.name = name;
        this.proprietor = proprietor;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postcode = postcode;
        this.legalForm = legalForm;
        this.taxNumber = taxNumber;
        this.jurisdiction = jurisdiction;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProprietor() {
        return proprietor;
    }

    public void setProprietor(String proprietor) {
        this.proprietor = proprietor;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public LegalForm getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(LegalForm legalForm) {
        this.legalForm = legalForm;
    }

    public String getTaxNumber() {
        return this.taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
