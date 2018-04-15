package Diensteverwaltung;

import Diensteverwaltung.View.*;
import Diensteverwaltung.controler.ProductService;
import Diensteverwaltung.model.*;

import java.sql.SQLException;
/*
    @author  Richter Nadine 2018-02-13
    MainClass
 */


public class Main {

    private Products products;
    private ProductOverview productOverview;
    private ProductService productService;

    public Main() {
//        setProductOverview(new ProductOverview(this));
//        try {
//            setProductService(new ProductService());
//            setProductView(new ProductView(this));
//            } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) throws SQLException {
//        Main main = new Main();
//        System.out.println(main.getProducts());
//        verify db actions
        Products test = new Products();
        System.out.println(test.getProducts());
        System.out.println("_____________________");
        Product testprod = test.getProduct(3);
        System.out.println(testprod);
        System.out.println("_____________________");

        test.updateProduct(testprod, "dineart", "schau ma mal obs nen dine art gibt", 1337.1337);

        System.out.println("_____________________");
        testprod = test.getProduct(3);
        System.out.println(testprod);

        System.out.println("_____________________");
        System.out.print("deleted: ");
        System.out.println(test.deleteProduct(testprod));

        System.out.println("_____________________");
        testprod = test.getProduct(3);
        System.out.println(testprod);

    } // Ende Main

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public ProductOverview getProductOverview() {
        return productOverview;
    }

    public void setProductOverview(ProductOverview productView) {
        this.productOverview = productView;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


} // Ende Klasse
