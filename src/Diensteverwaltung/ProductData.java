package Diensteverwaltung;

/*
    @author Richter 2018-02-07
    Class which contents the servicedatabase
*/

import java.util.ArrayList;

public class ProductData {

    private String name;
    private String description;
    private int price;

    private ArrayList<String> productData;

    public ProductData() {
        productData = new ArrayList<>();
    }

    /** @return a new ArrayList of ProductData **/

    public ArrayList<String> getProductData() {
        return productData;
    }

    public void addProduct(String Product){
        productData.add(Product);
    }

    public void setProductData(ArrayList<String> productData) {
        this.productData = productData;
    }

    public boolean removeProductData (String product) {
        return productData.remove(product);
    }


    }

