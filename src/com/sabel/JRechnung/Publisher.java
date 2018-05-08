package com.sabel.JRechnung;

import com.sabel.JRechnung.model.Model;
import com.sabel.JRechnung.model.SecurityProvider;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Publisher {

    private static SecurityProvider securityProviderInstance;
    private static Connection dbConnectionInstance;
    private static Model modelInstance;

    public static SecurityProvider getSecurityProvider(){
        if(!(securityProviderInstance instanceof SecurityProvider)){
            securityProviderInstance = new SecurityProvider();
        }

        return securityProviderInstance;
    }

    public static SecurityProvider getNewSecurityProvider(){
        securityProviderInstance = new SecurityProvider();

        return securityProviderInstance;
    }

    public static Connection getDBConnection() throws SQLException {
        try {
            if(dbConnectionInstance.isClosed()) {
                dbConnectionInstance = null;
            }
        } catch (Exception e) {
            if(Debug.ON){
                System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getMessage());
            }
        }

        if(!(dbConnectionInstance instanceof Connection)){
            File f, dbFile = null;
            String DB_CONNECTION_STRING = "jdbc:sqlite:";
            try {
                f = new File(Publisher.getModel().getJarContainingFolder(Main.class));
                dbFile = new File(f.getPath() + "/" + Publisher.getModel().getDataBaseFileName());

                if(!dbFile.exists() || !dbFile.canWrite()){
                //    throw new SQLException("DB File Error");
                    return null;
                }
                DB_CONNECTION_STRING += dbFile.getAbsoluteFile();
                dbConnectionInstance = DriverManager.getConnection(DB_CONNECTION_STRING);
            } catch (Exception e) {
                return null;
            }
        }

        return dbConnectionInstance;
    }

    public static Model getModel(){
        if(!(modelInstance instanceof Model)){
            modelInstance = new Model();
        }

        return modelInstance;
    }

    public static void destroy(){
        try {
            Connection dbConnection = getDBConnection();
            if(dbConnection != null && dbConnection.isClosed()){
                dbConnection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
