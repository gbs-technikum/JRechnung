package Diensteverwaltung.model;

/**
 * @author Richter Nadine
 * <p>
 * Class with Prodcut information
 **/

public class Product {

    private int id;
    private String name;
    private String description;
    private double price;
    private String artnr;


    public Product(int id, String name, String description, double price, String artnr) {
        this.id = id;
        this.name = name.trim();
        this.description = description.trim();
        this.price = price;
        this.artnr = artnr.trim();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getArtnr() {
        return artnr;
    }

    public void setArtnr(String artnr) {
        this.artnr = artnr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;

        if (id != product.id) return false;
        if (name.trim().toLowerCase() != product.name.trim().toLowerCase()) return false;
        if (description.trim().toLowerCase() != product.description.toLowerCase()) return false;
        if (price != product.price) return false;
        if (artnr.trim().toLowerCase() != product.artnr.trim().toLowerCase()) return false;
        return false;
    }

    @Override
    public String toString() {
        return "\nProduct{"
                + "\n\tId: " + id
                + "\n\tName: " + name
                + "\n\tBeschreibung: " + description
                + "\n\tPreis: " + price
                + "\n\tArtikelnummer: " + artnr
                + "\n}";
    }
} // Ende Klasse
