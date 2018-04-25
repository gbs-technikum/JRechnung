package Rechnung.model.db;

import Rechnung.*;
import Rechnung.model.objects.Business;
import Rechnung.model.objects.LegalForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusinessService {

    private static final String SQL_QUERY_WITH_ID = "SELECT * FROM business WHERE id  = ?";
    private static final String SQL_QUERY = "SELECT * FROM business";
    private static final String SQL_INSERT = "INSERT INTO business VALUES (?,?,?,?,?,?,?)";

    public static boolean writeBusiness(Business business) throws SQLException{

        if(business != null){

            Connection connection = Publisher.getDBConnection();

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(BusinessService.SQL_INSERT);

                preparedStatement.setString(1, business.getId());
                preparedStatement.setString(2, business.getName());
                preparedStatement.setString(3, business.getProprietor());
                preparedStatement.setString(4, business.getStreet());
                preparedStatement.setString(5, business.getStreetNumber());
                preparedStatement.setString(6, business.getPostcode());

                LegalForm legalForm = business.getLegalForm();

                if (legalForm != null) {
                    preparedStatement.setInt(7, legalForm.getId());
                } else {
                    preparedStatement.setNull(7, java.sql.Types.INTEGER);
                }


                preparedStatement.execute();
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

        }

        return false;
    }

    public static Business readBusiness() throws SQLException {
        return BusinessService.readBusiness(null);
    }

    public static Business readBusiness(String id) throws SQLException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = null;
        Business result = null;

        try {
            if (id != null) {
                preparedStatement = connection.prepareStatement(BusinessService.SQL_QUERY_WITH_ID);

                preparedStatement.setString(1, id);

            } else {
                preparedStatement = connection.prepareStatement(BusinessService.SQL_QUERY);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null && !resultSet.isClosed() && resultSet.next()) {
                String dataset_id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String proprietor = resultSet.getString("proprietor");
                String street = resultSet.getString("street");
                String streetNumber = resultSet.getString("streetnumber");
                String postcode = resultSet.getString("postcode");
                int legalformId = resultSet.getInt("id_legalform");

                LegalForm legalForm = LegalFormService.readLegalForm(legalformId);

                result = new Business(dataset_id, name, proprietor, street, streetNumber, postcode, legalForm);
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

        return result;
    }

}
