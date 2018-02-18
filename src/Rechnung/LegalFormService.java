package Rechnung;

import java.sql.*;

public class LegalFormService {

    private static final String SQL_QUERY = "SELECT * FROM legalform WHERE id  = ?";

    public static LegalForm readLegalForm(int id) throws SQLException {
        LegalForm result = null;

        if(id > 0) {
            Connection connection = Publisher.getDBConnection();
            PreparedStatement preparedStatement = null;
            try {

                preparedStatement = connection.prepareStatement(LegalFormService.SQL_QUERY);

                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet != null && resultSet.next()) {
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


}
