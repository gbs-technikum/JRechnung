package Diensteverwaltung;

import Diensteverwaltung.View.ProductView;

import java.sql.SQLException;


/*
    @author  Richter Nadine 2018-02-13
    MainClass
 */


public class Main {

    private Products products;
    private ProductView productView;
    private ProductService productService;

    public Main() {
        try {
            setProductService(new ProductService());
            setProductView(new ProductView(this));
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.getProducts());
    } // Ende Main

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public ProductView getProductView() {
        return productView;
    }

    public void setProductView(ProductView productView) {
        this.productView = productView;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


} // Ende Klasse
