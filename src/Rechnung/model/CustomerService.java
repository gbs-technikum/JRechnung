package Rechnung.model;

import Rechnung.Debug;
import Rechnung.Publisher;
import Rechnung.model.SecurityProvider;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private static final String SQL_QUERY = "SELECT id, number, surname, forename, street, housenumber, postcode, village, land FROM customer";
    private static final String SQL_QUERY_WITH_ID = "SELECT id, number, surname, forename, street, housenumber, postcode, village, land FROM customer WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO customer (id, number, surname, forename, street, housenumber, postcode, village, land) VALUES (?,?,?,?,?,?,?,?,?);";
    private static final String SQL_UPDATE = "UPDATE customer set number=?, surname=?, forename=?, street=?, housenumber=?, postcode=?, village=?, land=? WHERE ID=?";
    private static final String SQL_DELETE = "DELETE FROM customer WHERE id = ?";
    private static final String SQL_INSERT_CONTACTS = "INSERT INTO ? (address, customer_id) VALUES (?,?)";
    private static final String SQL_DELETE_CONTACTS =  "DELETE FROM ? WHERE customer_id = ?";

    public CustomerService() throws SQLException {

    }

    public static List<Customer> readAllCustomers() throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();
        Statement statement = connection.createStatement();
        List<Customer> customers = new ArrayList<>();
        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                System.out.println("id xxxxx :: " + id);
                String number = securityProvider.decryptAsString(resultSet.getBytes("number"));
                String surname = securityProvider.decryptAsString(resultSet.getBytes("surname"));
                String forename = securityProvider.decryptAsString(resultSet.getBytes("forename"));
                String street = securityProvider.decryptAsString(resultSet.getBytes("street"));
                String housenumber = securityProvider.decryptAsString(resultSet.getBytes("housenumber"));
                String postcode = securityProvider.decryptAsString(resultSet.getBytes("postcode"));
                String village = securityProvider.decryptAsString(resultSet.getBytes("village"));
                String land = securityProvider.decryptAsString(resultSet.getBytes("land"));


                Customer customer = new Customer(id,number,surname,forename,street,housenumber,postcode,village,land);
                customers.add(customer);
            }
            for (Customer customer : customers) {
                List<Accessibility> list = AccessibilityService.loadCustomersEMailAccessibilitys(customer.getId());

                for (Accessibility accessibility : list) {
                    customer.addEMail((EMailAccessibility)accessibility);
                }

                list = null;

                list = AccessibilityService.loadCustomersFaxAccessibilitys(customer.getId());

                for (Accessibility accessibility : list) {
                    customer.addFaxNumber((FaxAccessibility) accessibility);
                }

                list = null;

                list = AccessibilityService.loadCustomersTelephoneAccessibilitys(customer.getId());

                for (Accessibility accessibility : list) {
                    customer.addPhoneNumber((TelephoneAccessibility) accessibility);
                }

                list = null;
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

    public static Customer readCustomer(String id) throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = null;
        Customer customer = null;
        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement = connection.prepareStatement(SQL_QUERY_WITH_ID);
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet != null && !resultSet.isClosed() && resultSet.next()){
                String number = securityProvider.decryptAsString(resultSet.getBytes("number"));
                String surname = securityProvider.decryptAsString(resultSet.getBytes("surname"));
                String forename = securityProvider.decryptAsString(resultSet.getBytes("forename"));
                String street = securityProvider.decryptAsString(resultSet.getBytes("street"));
                String housenumber = securityProvider.decryptAsString(resultSet.getBytes("housenumber"));
                String postcode = securityProvider.decryptAsString(resultSet.getBytes("postcode"));
                String village = securityProvider.decryptAsString(resultSet.getBytes("village"));
                String land = securityProvider.decryptAsString(resultSet.getBytes("land"));


                customer = new Customer(id,number,surname,forename,street,housenumber,postcode,village,land);

                List<Accessibility> list = AccessibilityService.loadCustomersEMailAccessibilitys(customer.getId());

                for (Accessibility accessibility : list) {
                    customer.addEMail((EMailAccessibility)accessibility);
                }

                list = null;

                list = AccessibilityService.loadCustomersFaxAccessibilitys(customer.getId());

                for (Accessibility accessibility : list) {
                    customer.addFaxNumber((FaxAccessibility) accessibility);
                }

                list = null;

                list = AccessibilityService.loadCustomersTelephoneAccessibilitys(customer.getId());

                for (Accessibility accessibility : list) {
                    customer.addPhoneNumber((TelephoneAccessibility) accessibility);
                }

                list = null;
            }
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    if (Debug.ON) {
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }
            preparedStatement = null;
        }

        return customer;
    }

    private static boolean modifyCustomer(Customer customer) throws SQLException, UnsupportedEncodingException {
            Connection connection = Publisher.getDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            SecurityProvider securityProvider = Publisher.getSecurityProvider();
            try {
                preparedStatement.setBytes(1,securityProvider.encrypt(customer.getNumber()));
                preparedStatement.setBytes(2,securityProvider.encrypt(customer.getName()));
                preparedStatement.setBytes(3,securityProvider.encrypt(customer.getForename()));
                preparedStatement.setBytes(4,securityProvider.encrypt(customer.getStreet()));
                preparedStatement.setBytes(5,securityProvider.encrypt(customer.getHouseNumber()));
                preparedStatement.setBytes(6,securityProvider.encrypt(customer.getPostCode()));
                preparedStatement.setBytes(7,securityProvider.encrypt(customer.getVillage()));
                preparedStatement.setBytes(8,securityProvider.encrypt(customer.getVillage()));
                preparedStatement.setString(9,customer.getId());

                preparedStatement.execute();

                AccessibilityService.removeCustomerAccessibilities(customer.getId());

                AccessibilityService.saveListOfAccessibilitys(customer.getMailAddresses(),customer.getId());
                AccessibilityService.saveListOfAccessibilitys(customer.getFaxNumbers(),customer.getId());
                AccessibilityService.saveListOfAccessibilitys(customer.getPhoneNumbers(),customer.getId());

                return true;
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

    public static boolean saveCustomer(Customer customer) throws SQLException, UnsupportedEncodingException {

        System.out.println("id des Neuen Kunden: " + customer.getId());

        if(customer != null && CustomerService.readCustomer(customer.getId()) != null){
            return CustomerService.modifyCustomer(customer);
        }

        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement.setString(1, customer.getId());
            preparedStatement.setBytes(2,securityProvider.encrypt(customer.getNumber()));
            preparedStatement.setBytes(3,securityProvider.encrypt(customer.getName()));
            preparedStatement.setBytes(4,securityProvider.encrypt(customer.getForename()));
            preparedStatement.setBytes(5,securityProvider.encrypt(customer.getStreet()));
            preparedStatement.setBytes(6,securityProvider.encrypt(customer.getHouseNumber()));
            preparedStatement.setBytes(7,securityProvider.encrypt(customer.getPostCode()));
            preparedStatement.setBytes(8,securityProvider.encrypt(customer.getVillage()));
            preparedStatement.setBytes(9,securityProvider.encrypt(customer.getLand()));

            preparedStatement.execute();
            AccessibilityService.saveListOfAccessibilitys(customer.getMailAddresses(),customer.getId());
            AccessibilityService.saveListOfAccessibilitys(customer.getFaxNumbers(),customer.getId());
            AccessibilityService.saveListOfAccessibilitys(customer.getPhoneNumbers(),customer.getId());

            return true;
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

    public static void saveAllCustomers(List<Customer> customers) throws UnsupportedEncodingException, SQLException {
        for (Customer customer : customers) {
            saveCustomer(customer);
        }
    }

/*    public void changeCustomer(int old_customer_id, Customer customer) throws SQLException, UnsupportedEncodingException {
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
    }*/

  /* public void removeCustomer(int customer_id) throws SQLException {
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
    }*/




}
