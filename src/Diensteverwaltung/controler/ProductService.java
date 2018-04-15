package Diensteverwaltung.controler;

import java.sql.*;

/**
 * @author Richter Nadine
 * <p>
 * Class which opens the Databaseconnection
 **/


public class ProductService {
    private Connection connection;
    private Statement statement;
    private String connectionString = "jdbc:sqlite:src//Diensteverwaltung//product.sqlite";

    private Connection getConnection() {
        return connection;
    }

    private String getConnectionString() {
        return connectionString;
    }

    private void setConnection(Connection con) {
        this.connection = con;
    }

    public ProductService() throws SQLException {
        //connect to database
        connect();
    }

    public ResultSet getAllProducts() {
        String queryString = "select * from products";
        ResultSet res = null;

        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            res = stmt.executeQuery(queryString);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }

    public ResultSet getProductById(int productId) {
        String queryString = "select * from products where id = ?";
        ResultSet res = null;

        try {
            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(queryString);
            pstmt.setInt(1, productId);
            res = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }

    public void updateProduct(int productId, String name, String description, double price, String artnr) {
        String queryString = "update products set name = ?, description = ?, price = ?, artnr = ? where id = ?";
        ResultSet res = null;

        try {
            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(queryString);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.setString(4, artnr);
            pstmt.setInt(5, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean deleteProduct(int productId) {
        boolean ret = false;

        try {
            ResultSet prod = getProductById(productId);
            prod.next();

            if (prod.getRow() >= 0) {
                String queryString = "delete from products where id = ?";
                ResultSet res = null;

                Connection con = getConnection();
                PreparedStatement pstmt = con.prepareStatement(queryString);
                pstmt.setInt(1, productId);
                pstmt.executeUpdate();

                prod = getProductById(productId);
                prod.next();

                if (prod.getRow() <= 0) {
                    ret = true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ret;
    }

    public ResultSet addProduct(String name, String description, double price, String artnr) {
        String queryString = "insert into products (name, description, price, artnr) value (?,?,?,?)";
        ResultSet res = null;

        try {
            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(queryString);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.setString(4, artnr);
            res = pstmt.executeQuery();

            // check if insert is ok...
            // check value of generated keys
            System.out.println(pstmt.getGeneratedKeys());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }

    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(getConnectionString());
            setConnection(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void close() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
} // Ende Klasse

