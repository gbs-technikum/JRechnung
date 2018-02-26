package Rechnung.model;

import Rechnung.Debug;
import Rechnung.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AccessibilityService {

    public static final String SQL_INSERT_MAIL = "INSERT INTO fax VALUES (?,?,?)";
    public static final String SQL_INSERT_FAX = "INSERT INTO email VALUES (?,?,?)";
    public static final String SQL_INSERT_PHONE = "INSERT INTO phine VALUES (?,?,?)";


    public void saveListOfAccessibilitys(List<Accessibility> list) throws SQLException {
        for (Accessibility accessibility : list) {
            this.saveAccessibility(accessibility);
        }
    }

    public void saveAccessibility(Accessibility accessibility) throws SQLException {
        Connection connection = Publisher.getDBConnection();

        PreparedStatement preparedStatement = null;
        if(accessibility instanceof EMailAccessibility){
            preparedStatement = connection.prepareStatement(SQL_INSERT_MAIL);
        } else if(accessibility instanceof FaxAccessibility){
            preparedStatement = connection.prepareStatement(SQL_INSERT_FAX);
        }else if(accessibility instanceof TelephoneAccessibility){
            preparedStatement = connection.prepareStatement(SQL_INSERT_PHONE);
        }


        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setBytes(2, securityProvider.encrypt(customer.getName()));
            preparedStatement.setBytes(3, securityProvider.encrypt(customer.getForename()));
            preparedStatement.setBytes(4, securityProvider.encrypt(customer.getStreet()));
            preparedStatement.setBytes(5, securityProvider.encrypt(customer.getHouseNumber()));
            preparedStatement.setBytes(6, securityProvider.encrypt(customer.getPostCode()));
            preparedStatement.setBytes(7, securityProvider.encrypt(customer.getVillage()));
            preparedStatement.setBytes(8, securityProvider.encrypt(customer.getLand()));

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
}
