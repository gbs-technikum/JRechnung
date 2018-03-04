package Rechnung.model;

import Rechnung.Debug;
import Rechnung.Publisher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LegalFormService {

    private static final String SQL_QUERY = "SELECT * FROM legalform WHERE id  = ?";
    private static final String SQL_QUERY_ALL = "SELECT * FROM legalform";

    public static LegalForm readLegalForm(int id) throws SQLException {
        LegalForm result = null;

        if(id > 0) {
            Connection connection = Publisher.getDBConnection();
            PreparedStatement preparedStatement = null;
            try {

                preparedStatement = connection.prepareStatement(LegalFormService.SQL_QUERY);

                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet != null && !resultSet.isClosed() && resultSet.next()) {
                    String name = resultSet.getString("name");
                    String shortname = resultSet.getString("shortname");
                    boolean postfix = (resultSet.getInt("postfix") != 0);
                    boolean taxfree = (resultSet.getInt("taxfree") != 0);


                    result = new LegalForm(resultSet.getInt("id"), name, shortname, postfix, taxfree);
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
        }

        return result;
    }

    public static List<LegalForm> readAllLegalForms() throws SQLException {
        List<LegalForm> result = new ArrayList<>();


        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connection.prepareStatement(LegalFormService.SQL_QUERY_ALL);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null && !resultSet.isClosed()){

                while(resultSet.next()) {
                    String name = resultSet.getString("name");
                    String shortname = resultSet.getString("shortname");
                    boolean postfix = (resultSet.getInt("postfix") != 0);
                    boolean taxfree = (resultSet.getInt("taxfree") != 0);


                    LegalForm legalForm = new LegalForm(resultSet.getInt("id"), name, shortname, postfix, taxfree);

                    result.add(legalForm);
                }
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
