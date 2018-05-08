package com.sabel.JRechnung.model.db;

import com.sabel.JRechnung.*;
import com.sabel.JRechnung.model.objects.Business;
import com.sabel.JRechnung.model.objects.LegalForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusinessService {

    private static final String SQL_QUERY_WITH_ID = "SELECT * FROM business WHERE id  = ?";
    private static final String SQL_QUERY = "SELECT * FROM business";
    private static final String SQL_INSERT = "INSERT INTO business VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE business SET name=?, proprietor=?, street=?, streetnumber=?, " +
                                                "postcode=?, location=?, tax_number=?, jurisdiction=?, " +
                                                "phone=?, fax=?, email=?, id_legalform=?";

    public static boolean writeBusiness(Business business) throws SQLException{

        if(business != null && BusinessService.readBusiness() != null)
        {
            return BusinessService.modifyBusiness(business);
        }

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
                preparedStatement.setString(7, business.getLocation());
                preparedStatement.setString(8, business.getTaxNumber());
                preparedStatement.setString(9, business.getJurisdiction());
                preparedStatement.setString(10, business.getPhone());
                preparedStatement.setString(11, business.getFax());
                preparedStatement.setString(12, business.getEmail());

                LegalForm legalForm = business.getLegalForm();



                if (legalForm != null) {
                    preparedStatement.setInt(13, legalForm.getId());
                    System.out.println(legalForm + " " +  legalForm.getId());
                } else {
                    preparedStatement.setNull(13, java.sql.Types.INTEGER);
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

    public static boolean modifyBusiness(Business business) throws SQLException{

        if(business != null){

            Connection connection = Publisher.getDBConnection();

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(BusinessService.SQL_UPDATE);

                preparedStatement.setString(1, business.getName());
                preparedStatement.setString(2, business.getProprietor());
                preparedStatement.setString(3, business.getStreet());
                preparedStatement.setString(4, business.getStreetNumber());
                preparedStatement.setString(5, business.getPostcode());
                preparedStatement.setString(6, business.getLocation());
                preparedStatement.setString(7, business.getTaxNumber());
                preparedStatement.setString(8, business.getJurisdiction());
                preparedStatement.setString(9, business.getPhone());
                preparedStatement.setString(10, business.getFax());
                preparedStatement.setString(11, business.getEmail());

                LegalForm legalForm = business.getLegalForm();

                if (legalForm != null) {
                    preparedStatement.setInt(12, legalForm.getId());
                } else {
                    preparedStatement.setNull(12, java.sql.Types.INTEGER);
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
                String location = resultSet.getString("location");
                String postcode = resultSet.getString("postcode");
                String taxNumber = resultSet.getString("tax_number");
                String jurisdiction = resultSet.getString("jurisdiction");
                String phone = resultSet.getString("phone");
                String fax = resultSet.getString("fax");
                String email = resultSet.getString("email");
                int legalformId = resultSet.getInt("id_legalform");

                LegalForm legalForm = LegalFormService.readLegalForm(legalformId);

                result = new Business(dataset_id, name, proprietor, street, streetNumber,
                        postcode, location, taxNumber, jurisdiction,phone,fax,email,legalForm);
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
