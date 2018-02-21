package Diensteverwaltung;

import java.util.Objects;

public class Product {

    private int id;
    private String name;
    private String description;
    private int price;


    public Product (int id, String name, String description, int price) {
        this.id = id;
        this.name = name;
        this. description = description;
        this.price = price;
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

        if (id != product.id) return false;
        if (name != product.name) return false;
        if (description != product.description) return false;
        if (price != product.price) return false;
        return false;
    }

  


} // Ende Klasse
