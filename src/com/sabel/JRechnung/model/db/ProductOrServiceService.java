package com.sabel.JRechnung.model.db;

import com.sabel.JRechnung.Debug;
import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.objects.ProductOrService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductOrServiceService {

    private static final String SQL_QUERY_WITH_ID = "SELECT * FROM product_srv WHERE id  = ?";
    private static final String SQL_QUERY = "SELECT * FROM product_srv";
    private static final String SQL_INSERT = "INSERT INTO product_srv VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE product_srv set title=?, price=?, description=? WHERE ID=?";
    private static final String SQL_DELETE = "DELETE FROM product_srv WHERE id = ?";

    public static boolean write(ProductOrService productOrService) throws SQLException{

        if(productOrService != null){

            if(ProductOrServiceService.read(productOrService.getId()) != null){
                return ProductOrServiceService.modify(productOrService);
            }

            Connection connection = Publisher.getDBConnection();

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(ProductOrServiceService.SQL_INSERT);

                preparedStatement.setString(1, productOrService.getId());
                preparedStatement.setString(2, productOrService.getTitle());
                preparedStatement.setBigDecimal(3, new BigDecimal(productOrService.getPrice()));
                preparedStatement.setString(4, productOrService.getDescription());

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

    public static boolean modify(ProductOrService productOrService) throws SQLException {
        if(productOrService != null){

            Connection connection = Publisher.getDBConnection();

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(ProductOrServiceService.SQL_UPDATE);


                preparedStatement.setString(1, productOrService.getTitle());
                preparedStatement.setBigDecimal(2, new BigDecimal(productOrService.getPrice()));
                preparedStatement.setString(3, productOrService.getDescription());

                preparedStatement.setString(4, productOrService.getId());

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

    public static List<ProductOrService> read() throws SQLException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = null;
        List<ProductOrService> result = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(ProductOrServiceService.SQL_QUERY);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet != null && !resultSet.isClosed() && resultSet.next()) {
                String dataset_id = resultSet.getString("id");
                String title = resultSet.getString("title");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");

                ProductOrService productOrService = new ProductOrService(dataset_id, title, price, description);

                result.add(productOrService);
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

    public static ProductOrService read(String id) throws SQLException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = null;
        ProductOrService result = null;

        try {
            if (id == null) {
                return result;
            }

            preparedStatement = connection.prepareStatement(ProductOrServiceService.SQL_QUERY_WITH_ID);

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null && !resultSet.isClosed() && resultSet.next()) {
                String dataset_id = resultSet.getString("id");
                String title = resultSet.getString("title");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");

                result = new ProductOrService(dataset_id, title, price, description);
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

    public static boolean remove(ProductOrService productOrService) throws SQLException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
        try {
            preparedStatement.setString(1,productOrService.getId());

            preparedStatement.execute();
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
        return true;
    }

}
