package Kundenverwaltung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerService {

    private static String URL = "jdbc:sqlite:src\\Kundenverwaltung\\customers.sqlite";

    private Connection connection;
    private Statement statement;

    public CustomerService() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
    }

    public void close() throws SQLException {
        if(statement != null){
            statement.close();
        }
        statement = null;

        if(connection != null){
            connection.close();
        }
        connection = null;
    }

    public void save(Customer customer){
        //String sql = String.valueOf(statement.executeUpdate("INSERT INTO customer (name, asdf, )"))
    }
}
