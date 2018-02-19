package Kundenverwaltung;

import java.sql.*;
import java.util.ArrayList;

public class CustomerService {

    private static final String URL = "jdbc:sqlite:src\\Kundenverwaltung\\customers.sqlite";

    private Connection connection;
    private Statement statement;

    public CustomerService() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        this.statement = connection.createStatement();
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

    public Customers readAllCustomers() throws SQLException {
        Customers customers = new Customers();
        statement.executeQuery("SELECT id, surname, forename, street, housenumber, postcode, village, land from customer");
        return customers;
    }

    public ArrayList<String> readAllContactsFromCustomer(String tableName, int customer_id) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT address FROM " + tableName + ", WHERE customer_id=" + customer_id);
        return null;
    }

    public void saveCustomer(Customer customer) throws SQLException {
        statement.executeUpdate("INSERT INTO customer (id, surname, forename, street, housenumber, postcode, village, land) VALUES ("
        + customer.getId() + ", '" + customer.getName() + "', '" + customer.getForename() + "', '" + customer.getStreet() + "', '" + customer.getHouseNumber() + "', '" + customer.getPostCode() + "', '" + customer.getVillage() + "', '" + customer.getLand() + "');");
        saveAllContactsFromCustomer("email", customer.getId(), customer.getMailAddresses());
        saveAllContactsFromCustomer("phone", customer.getId(), customer.getPhoneNumbers());
        saveAllContactsFromCustomer("fax", customer.getId(), customer.getFaxNumbers());
    }

    public void saveAllCustomers(Customers customers){
        for (Customer customer:customers.getCustomers()) {
            try {
                saveCustomer(customer);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeCustomer(int old_customer_id, Customer customer) throws SQLException {
        statement.executeUpdate("UPDATE customer set ID=" + customer.getId()
                + ", surname=" + customer.getName() + ", "
                + ", street=" + customer.getStreet()
                + ", housenumber=" + customer.getHouseNumber()
                + ", postcode=" + customer.getPostCode()
                + ", village=" + customer.getVillage()
                + ", land=" + customer.getLand() + "WHERE ID=" + old_customer_id + ";");
        changeAllContactsFromCustomer("email", old_customer_id, customer.getId(), customer.getMailAddresses());
        changeAllContactsFromCustomer("phone", old_customer_id, customer.getId(), customer.getPhoneNumbers());
        changeAllContactsFromCustomer("fax", old_customer_id, customer.getId(), customer.getFaxNumbers());
    }

    public void removeCustomer(int customer_id) throws SQLException {
        statement.executeUpdate("DELETE FROM customer WHERE id=" + customer_id + ";");
        removeAllContactsFromCustomer("email", customer_id);
        removeAllContactsFromCustomer("phone", customer_id);
        removeAllContactsFromCustomer("fax", customer_id);
    }

    public void saveContactFromCustomer(String tableName, int customer_id, String address) throws SQLException {
        statement.executeUpdate("INSERT INTO " + tableName + " (address, customer_id) VALUES ('" + address + "', " + customer_id + ");");
    }

    public void saveAllContactsFromCustomer(String tableName, int customer_id, ArrayList<String> addresses) throws SQLException {
        for (String address:addresses) {
            saveContactFromCustomer(tableName, customer_id, address);
        }
    }

    public void changeAllContactsFromCustomer(String tableName, int old_customer_id, int new_customer_id, ArrayList<String> addresses) throws SQLException {
        removeAllContactsFromCustomer(tableName, old_customer_id);
        saveAllContactsFromCustomer(tableName, new_customer_id, addresses);
    }

    public void removeAllContactsFromCustomer(String tableName, int customer_id) throws SQLException {
        statement.executeUpdate("DELETE FROM " + tableName + " WHERE customer_id=" + customer_id + ";");
    }


}
