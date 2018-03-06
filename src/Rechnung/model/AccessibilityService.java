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

    public static final String SQL_INSERT_MAIL = "INSERT INTO email VALUES (?,?,?)";
    public static final String SQL_INSERT_FAX = "INSERT INTO  fax VALUES (?,?,?)";
    public static final String SQL_INSERT_PHONE = "INSERT INTO phone VALUES (?,?,?)";
    public static final String SQL_QUERY_MAIL = "SELECT * FROM email WHERE customer_id = ?";
    public static final String SQL_QUERY_FAX = "SELECT * FROM fax WHERE customer_id = ?";
    public static final String SQL_QUERY_PHONE = "SELECT * FROM phone WHERE customer_id = ?";
    public static final String SQL_DELETE_MAIL = "DELETE FROM email WHERE id = ?";
    public static final String SQL_DELETE_FAX = "DELETE FROM fax WHERE id = ?";
    public static final String SQL_DELETE_PHONE = "DELETE FROM phone WHERE id = ?";

    public static final String SQL_DELETE_MAIL_BY_CUSTOMER = "DELETE FROM email WHERE customer_id = ?";
    public static final String SQL_DELETE_FAX_BY_CUSTOMER = "DELETE FROM fax WHERE customer_id = ?";
    public static final String SQL_DELETE_PHONE_BY_CUSTOMER = "DELETE FROM phone WHERE customer_id = ?";



    public static void saveListOfAccessibilitys(List<Accessibility> list, String customer_id) throws SQLException, UnsupportedEncodingException {
        for (Accessibility accessibility : list) {
            AccessibilityService.saveAccessibility(accessibility,customer_id);
        }
    }

    public static void saveAccessibility(Accessibility accessibility,String customer_id) throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();

        PreparedStatement preparedStatement = null;

        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {

            if(accessibility instanceof EMailAccessibility){
                System.out.println("EMailAccessibility : " + accessibility.getEntry() );
                preparedStatement = connection.prepareStatement(SQL_INSERT_MAIL);
            } else if(accessibility instanceof FaxAccessibility){
                System.out.println("FaxAccessibility : " + accessibility.getEntry() );
                preparedStatement = connection.prepareStatement(SQL_INSERT_FAX);
            }else if(accessibility instanceof TelephoneAccessibility){
                System.out.println("TelephoneAccessibility : " + accessibility.getEntry() );
                preparedStatement = connection.prepareStatement(SQL_INSERT_PHONE);
            }

            preparedStatement.setString(1,accessibility.getId());
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

    public static void removeCustomerAccessibilities(String customer_id) throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatementMail = null;
        PreparedStatement preparedStatementFax = null;
        PreparedStatement preparedStatementPhone = null;
        try {

                preparedStatementMail = connection.prepareStatement(SQL_DELETE_MAIL_BY_CUSTOMER);
                preparedStatementFax = connection.prepareStatement(SQL_DELETE_FAX_BY_CUSTOMER);
                preparedStatementPhone = connection.prepareStatement(SQL_DELETE_PHONE_BY_CUSTOMER);

                preparedStatementMail.setString(1,customer_id);
                preparedStatementFax.setString(1,customer_id);
                preparedStatementPhone.setString(1,customer_id);

                preparedStatementMail.execute();
                preparedStatementFax.execute();
                preparedStatementPhone.execute();

        } finally {
            if (preparedStatementMail != null) {
                try {
                    preparedStatementMail.close();
                } catch (SQLException e) {
                    if (Debug.ON) {
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }

            if (preparedStatementFax != null) {
                try {
                    preparedStatementFax.close();
                } catch (SQLException e) {
                    if (Debug.ON) {
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }

            if (preparedStatementPhone != null) {
                try {
                    preparedStatementPhone.close();
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
            if (resultSet != null && !resultSet.isClosed()){

                while(resultSet.next()) {
                    String id = resultSet.getString("id");
                    String address = securityProvider.decryptAsString(resultSet.getBytes("address"));

                    System.out.println("EMIAL ID " +  id + "EMIAL : " + address);
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
            if (resultSet != null && !resultSet.isClosed()){

                while(resultSet.next()) {
                    String id = resultSet.getString("id");
                    String number = securityProvider.decryptAsString(resultSet.getBytes("number"));

                    Accessibility faxAccessibility = new FaxAccessibility(id,number);
                    accessibilityList.add(faxAccessibility);
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
            if (resultSet != null && !resultSet.isClosed()){

                while(resultSet.next()) {
                    String id = resultSet.getString("id");
                    String number = securityProvider.decryptAsString(resultSet.getBytes("number"));

                    Accessibility telephoneAccessibility = new TelephoneAccessibility(id,number);
                    accessibilityList.add(telephoneAccessibility);
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
