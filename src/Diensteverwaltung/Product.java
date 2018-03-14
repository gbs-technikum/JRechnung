package Diensteverwaltung;

import java.util.Objects;

/**
 * @author Richter Nadine
 *
 * Class with Prodcut information
  **/

public class Product {

    private int position;
    private String name;
    private String description;
    private int price;


    public Product (int position, String name, String description, int price) {
        this.position = position;
        this.name = name;
        this. description = description;
        this.price = price;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int id) {
        this.position = id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;

        if (position != product.position) return false;
        if (name != product.name) return false;
        if (description != product.description) return false;
        if (price != product.price) return false;
        return false;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Position: " + position +
                "Name: " + name +
                "Beschreibung: " + description +
                "Preis: " + price;
    }



} // Ende Klasse
