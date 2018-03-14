package Diensteverwaltung;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Richter Nadine
 *
 * Productlist
 * **/


public class Productlist {

    private ArrayList<Product> products;

    public Productlist() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }

    public void changeProduct ( Product product, int position, String name, String description, int price){
        product.setPosition(position);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Product findProductbyId (int id){
        Iterator iterator = products.iterator();
        while (iterator.hasNext()){
            Product product = (Product) iterator.next();
            if(id == product.getPosition()){
                return product;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String string = "";
        for (Product product:products) {
            string += "\n" + product;

        }
return string;
    }
}

