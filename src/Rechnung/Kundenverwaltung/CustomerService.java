package Rechnung.Kundenverwaltung;

import Rechnung.Debug;
import Rechnung.Publisher;
import Rechnung.model.SecurityProvider;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;

public class CustomerService {
    private static final String SQL_QUERY = "SELECT id, surname, forename, street, housenumber, postcode, village, land FROM customer";
    private static final String SQL_QUERY_WITH_ID = "SELECT address FROM ? WHERE customer_id = ?";
    private static final String SQL_INSERT = "INSERT INTO customer (id, surname, forename, street, housenumber, postcode, village, land) VALUES (?,?,?,?,?,?,?,?);";
    private static final String SQL_UPDATE = "UPDATE customer set ID=?, surname=?, forename=?, street=?, housenumber=?, postcode=?, village=?, land=? WHERE ID=?";
    private static final String SQL_DELETE = "DELETE FROM customer WHERE id = ?";

    public CustomerService() throws SQLException {

    }

    public Customers readAllCustomers() throws SQLException {
        Connection connection = Publisher.getDBConnection();
        Statement statement = connection.createStatement();
        Customers customers = new Customers();
        try {
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("forename"),
                        resultSet.getString("street"),
                        resultSet.getString("housenumber"),
                        resultSet.getString("postcode"),
                        resultSet.getString("village"),
                        resultSet.getString("land")
                );
                customers.addCustomer(customer);
            }
            for (Customer customer : customers.getCustomers()) {
                customer.setMailAddresses(readAllContactsFromCustomer("email", customer.getId()));
                customer.setPhoneNumbers(readAllContactsFromCustomer("phone", customer.getId()));
                customer.setFaxNumbers(readAllContactsFromCustomer("fax", customer.getId()));
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    if (Debug.ON) {
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }
            statement = null;
        }

        return customers;
    }

    public ArrayList<String> readAllContactsFromCustomer(String tableName, int customer_id) throws SQLException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_WITH_ID);
        ArrayList<String> contacts = new ArrayList<>();
        try{
            preparedStatement.setString(1, tableName);
            preparedStatement.setInt(2, customer_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contacts.add(resultSet.getString(1));
            }
        }finally {
            if (preparedStatement != null) {
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }
            preparedStatement = null;
            connection = null;
        }
        return contacts;
    }

    public void saveCustomer(Customer customer) throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setBytes(2,securityProvider.encrypt(customer.getName()));
            preparedStatement.setBytes(3,securityProvider.encrypt(customer.getForename()));
            preparedStatement.setBytes(4,securityProvider.encrypt(customer.getStreet()));
            preparedStatement.setBytes(5,securityProvider.encrypt(customer.getHouseNumber()));
            preparedStatement.setBytes(6,securityProvider.encrypt(customer.getPostCode()));
            preparedStatement.setBytes(7,securityProvider.encrypt(customer.getVillage()));
            preparedStatement.setBytes(8,securityProvider.encrypt(customer.getLand()));

            if(preparedStatement.execute()) {
                saveAllContactsFromCustomer("email", customer.getId(), customer.getMailAddresses());
                saveAllContactsFromCustomer("phone", customer.getId(), customer.getPhoneNumbers());
                saveAllContactsFromCustomer("fax", customer.getId(), customer.getFaxNumbers());
            }
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    if (Debug.ON) {
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }
        }
    }

    public void saveAllCustomers(Customers customers) throws UnsupportedEncodingException, SQLException {
        for (Customer customer : customers.getCustomers()) {
                saveCustomer(customer);

        }
    }

    public void changeCustomer(int old_customer_id, Customer customer) throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setBytes(2,securityProvider.encrypt(customer.getName()));
            preparedStatement.setBytes(3,securityProvider.encrypt(customer.getForename()));
            preparedStatement.setBytes(4,securityProvider.encrypt(customer.getStreet()));
            preparedStatement.setBytes(5,securityProvider.encrypt(customer.getHouseNumber()));
            preparedStatement.setBytes(6,securityProvider.encrypt(customer.getPostCode()));
            preparedStatement.setBytes(7,securityProvider.encrypt(customer.getVillage()));
            preparedStatement.setBytes(8,securityProvider.encrypt(customer.getLand()));
            preparedStatement.setInt(9,old_customer_id);

            if(preparedStatement.execute()) {
                changeAllContactsFromCustomer("email", old_customer_id, customer.getId(), customer.getMailAddresses());
                changeAllContactsFromCustomer("phone", old_customer_id, customer.getId(), customer.getPhoneNumbers());
                changeAllContactsFromCustomer("fax", old_customer_id, customer.getId(), customer.getFaxNumbers());
            }
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    if (Debug.ON) {
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }
        }
    }

    public void removeCustomer(int customer_id) throws SQLException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);

        try{
            preparedStatement.setInt(1, customer_id);
            if(preparedStatement.execute()){
                removeAllContactsFromCustomer("email", customer_id);
                removeAllContactsFromCustomer("phone", customer_id);
                removeAllContactsFromCustomer("fax", customer_id);
            }
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    if (Debug.ON) {
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }
        }

    }

    public void saveContactFromCustomer(String tableName, int customer_id, String address) throws SQLException {
   //     statement.executeUpdate("INSERT INTO " + tableName + " (address, customer_id) VALUES ('" + address + "', " + customer_id + ");");
    }

    public void saveAllContactsFromCustomer(String tableName, int customer_id, ArrayList<String> addresses) throws SQLException {
        for (String address : addresses) {
            saveContactFromCustomer(tableName, customer_id, address);
        }
    }

    public void changeAllContactsFromCustomer(String tableName, int old_customer_id, int new_customer_id, ArrayList<String> addresses) throws SQLException {
        removeAllContactsFromCustomer(tableName, old_customer_id);
        saveAllContactsFromCustomer(tableName, new_customer_id, addresses);
    }

    public void removeAllContactsFromCustomer(String tableName, int customer_id) throws SQLException {
    //    statement.executeUpdate("DELETE FROM " + tableName + " WHERE customer_id=" + customer_id + ";");
    }


}
