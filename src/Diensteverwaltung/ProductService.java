package Diensteverwaltung;

import java.sql.*;

/**
 * @author Richter Nadine
 *
 * Class which opens the Databaseconnection
 * **/


public class ProductService {

    private static final String URL = "jdbc:sqlite:\\D:\\Richter\\3. Semester\\Programmierung\\IdeaProjects\\JRechnung\\src\\Diensteverwaltung\\product.sqlite";

    private Connection connection;
    private Statement statement;

    public ProductService() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        this.statement = connection.createStatement();
    }

    public void close() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        statement = null;

        if (connection != null) {
            connection.close();
        }
        connection = null;
    }




} // Ende Klasse

