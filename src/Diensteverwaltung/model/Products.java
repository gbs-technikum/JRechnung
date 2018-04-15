package Diensteverwaltung.model;

import Diensteverwaltung.controler.ProductService;
import Diensteverwaltung.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/*
    @author  Richter Nadine 2018-02-10
    Class cointains a List of Products
 */


public class Products {

    private ArrayList<Product> products = new ArrayList<>();
    private ProductService service = null;

    public Products() throws SQLException {
        setService(new ProductService());
        loadProducts();
    }

    private void loadProducts() {
        ResultSet resProd = getService().getAllProducts();
        try {
            while (resProd.next()) {
                Product prod = new Product(resProd.getInt("id"), resProd.getString("name"), resProd.getString("description"), resProd.getDouble("price"), resProd.getString("artnr"));
                addProducts(prod);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProducts(Product product) {
        products.add(product);
    }

    public boolean deleteProduct(Product product) throws SQLException {
        // delete first from db, because we need product id...
        boolean ret = getService().deleteProduct(product.getId());
        if (ret) {
            products.remove(product);
        }
        return ret;
    }

    public Product findProductById(int id) {
        Iterator iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = (Product) iterator.next();
            if (id == product.getId()) {
                return product;
            }
        }
        return null;
    }

    public Product getProduct(int id) {
        for (int i = 0; i < getProducts().size(); i++) {
            if (getProducts().get(i).getId() == id) {
                return getProducts().get(i);
            }
        }

        return null;
    }

    public void updateProduct(Product product, String name, String description, double price) {
        System.out.println("--updateProduct------------------------------");
        System.out.println("--Daten alt");
        System.out.println("Produkt Id:" + product.getId());
        System.out.println("Produkt Name:" + product.getName());
        System.out.println("Produkt Beschreibung:" + product.getDescription());
        System.out.println("Produkt Preis:" + product.getPrice());
        System.out.println("Produkt ArtNr:" + product.getArtnr());

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        getService().updateProduct(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getArtnr());

        System.out.println("--Daten neu");
        System.out.println("Produkt Id:" + product.getId());
        System.out.println("Produkt Name:" + product.getName());
        System.out.println("Produkt Beschreibung:" + product.getDescription());
        System.out.println("Produkt Preis:" + product.getPrice());
        System.out.println("Produkt ArtNr:" + product.getArtnr());
        System.out.println("------------------------------updateProduct--");
    }

    private ProductService getService() {
        return service;
    }

    private void setService(ProductService service) {
        this.service = service;
    }
}
