package Diensteverwaltung;

import java.util.ArrayList;
import java.util.Iterator;

/*
    @author  Richter Nadine 2018-02-10
    Class cointains a List of Products
 */


public class Products {

    private ArrayList<Product> products;

    public Products() {
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProducts(Product product){
        products.add(product);
    }

    public void removeProdcuts (Product product){
        products.remove(product);
    }

    public Product findProductByPosition(int position){
        Iterator iterator = products.iterator();
        while (iterator.hasNext()){
            Product product = (Product) iterator.next();
            if(position == product.getPosition()){
                return product;
            }
        }
        return null;
    }

    public void updateProduct (Product product, int position, String name, String description, int price){
        product.setPosition(position);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
    }


}
