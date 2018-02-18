package Rechnung;

public class Business {

    private String id;
    private String name;
    private String proprietor;
    private String street;
    private String streetNumber;
    private String postcode;
    private LegalForm legalForm;


    public Business(String id, String name, String proprietor, String street, String streetNumber, String postcode, LegalForm legalForm) {
        this.id = id;
        this.name = name;
        this.proprietor = proprietor;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postcode = postcode;
        this.legalForm = legalForm;
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

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public LegalForm getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(LegalForm legalForm) {
        this.legalForm = legalForm;
    }
}
