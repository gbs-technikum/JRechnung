package Rechnung.model;

import Rechnung.Debug;
import Rechnung.Publisher;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccessibilityService {

    public static final String SQL_INSERT_MAIL = "INSERT INTO fax VALUES (?,?,?)";
    public static final String SQL_INSERT_FAX = "INSERT INTO email VALUES (?,?,?)";
    public static final String SQL_INSERT_PHONE = "INSERT INTO phone VALUES (?,?,?)";
    public static final String SQL_QUERY_MAIL = "SELECT * FROM email WHERE customers_id = ?";
    public static final String SQL_QUERY_FAX = "SELECT * FROM fax WHERE customers_id = ?";
    public static final String SQL_QUERY_PHONE = "SELECT * FROM phone WHERE customers_id = ?";
    public static final String SQL_DELETE_MAIL = "DELETE FROM email WHERE id = ?";
    public static final String SQL_DELETE_FAX = "DELETE FROM fax WHERE id = ?";
    public static final String SQL_DELETE_PHONE = "DELETE FROM phone WHERE id = ?";



    public static void saveListOfAccessibilitys(List<Accessibility> list, String customer_id) throws SQLException, UnsupportedEncodingException {
        for (Accessibility accessibility : list) {
            AccessibilityService.saveAccessibility(accessibility,customer_id);
        }
    }

    public static void saveAccessibility(Accessibility accessibility,String customer_id) throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();

        PreparedStatement preparedStatement = null;

        String id = UUIDStringGenerator.generate();

        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {

            if(accessibility instanceof EMailAccessibility){
                preparedStatement = connection.prepareStatement(SQL_INSERT_MAIL);
            } else if(accessibility instanceof FaxAccessibility){
                preparedStatement = connection.prepareStatement(SQL_INSERT_FAX);
            }else if(accessibility instanceof TelephoneAccessibility){
                preparedStatement = connection.prepareStatement(SQL_INSERT_PHONE);
            }

            preparedStatement.setString(1,id);
            preparedStatement.setString(2,customer_id);
            preparedStatement.setBytes(3,securityProvider.encrypt(accessibility.getEntry()));

            preparedStatement.execute();
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
        }
    }

    public static void removeAccessibility(Accessibility accessibility) throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = null;
        try {

            if(accessibility instanceof EMailAccessibility){
                preparedStatement = connection.prepareStatement(SQL_DELETE_MAIL);
            } else if(accessibility instanceof FaxAccessibility){
                preparedStatement = connection.prepareStatement(SQL_DELETE_FAX);
            }else if(accessibility instanceof TelephoneAccessibility){
                preparedStatement = connection.prepareStatement(SQL_DELETE_PHONE);
            }

            preparedStatement.setString(1,accessibility.getId());

            preparedStatement.execute();
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
        }
    }

    public static List<Accessibility> loadCustomersEMailAccessibilitys(String customer_id) throws SQLException, UnsupportedEncodingException {
        List<Accessibility> eMailAccessibilityList = new ArrayList<>();
        Connection connection = Publisher.getDBConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_MAIL);

        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement.setString(1,customer_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null){

                while(resultSet.next()) {
                    String id = resultSet.getString("id");
                    String address = securityProvider.decryptAsString(resultSet.getBytes("address"));

                    Accessibility eMailAccessibility = new EMailAccessibility(id,address);
                    eMailAccessibilityList.add(eMailAccessibility);
                }
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
        }

        return eMailAccessibilityList;
    }

    public static List<Accessibility> loadCustomersFaxAccessibilitys(String customer_id) throws SQLException, UnsupportedEncodingException {
        List<Accessibility> accessibilityList = new ArrayList<>();
        Connection connection = Publisher.getDBConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_FAX);

        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement.setString(1,customer_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null){

                while(resultSet.next()) {
                    String id = resultSet.getString("id");
                    String number = securityProvider.decryptAsString(resultSet.getBytes("number"));

                    Accessibility eMailAccessibility = new FaxAccessibility(id,number);
                    accessibilityList.add(eMailAccessibility);
                }
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
        }

        return accessibilityList;
    }

    public static List<Accessibility> loadCustomersTelephoneAccessibilitys(String customer_id) throws SQLException, UnsupportedEncodingException {
        List<Accessibility> accessibilityList = new ArrayList<>();
        Connection connection = Publisher.getDBConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_PHONE);

        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement.setString(1,customer_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null){

                while(resultSet.next()) {
                    String id = resultSet.getString("id");
                    String number = securityProvider.decryptAsString(resultSet.getBytes("number"));

                    Accessibility eMailAccessibility = new TelephoneAccessibility(id,number);
                    accessibilityList.add(eMailAccessibility);
                }
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
        }

        return accessibilityList;
    }

}
