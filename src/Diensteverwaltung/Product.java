package Diensteverwaltung;

/*
    @author Richter 2018-02-07
    Class which contents the servicedatabase
*/

import java.util.ArrayList;

public class Product {

    private String name;
    private String description;
    private int price;




    public Product() {
    }

    public Product(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
