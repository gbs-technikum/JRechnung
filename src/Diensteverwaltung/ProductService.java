package Diensteverwaltung;

import java.sql.*;

/**
 * @author Richter Nadine
 *
 * Class which opens the Databaseconnection
 * **/


public class ProductService {

    private static final String URL = "jdbc:sqlite:src\\Diensteverwaltung\\product.sqlite";

    private Connection connection;
    private Statement statment;

    public ProductService() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        this.statment = connection.createStatement();
    }

    public void close() throws SQLException {
        if (statment != null) {
            statment.close();
        }
        statment = null;

        if (connection != null) {
            connection.close();
        }
        connection = null;
    }





} // Ende Klasse

