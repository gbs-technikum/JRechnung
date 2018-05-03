package Rechnung;

import Rechnung.model.Model;
import Rechnung.model.SecurityProvider;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Publisher {

    private static Logger loggerInstance;
    private static SecurityProvider securityProviderInstance;
    private static Connection dbConnectionInstance;
    private static Model modelInstance;


    public static Logger getLogger() {
        if(!(loggerInstance instanceof ConsoleLogger)){
            loggerInstance = new ConsoleLogger();
        }

        return loggerInstance;
    }


    public static SecurityProvider getSecurityProvider(){
        if(!(securityProviderInstance instanceof SecurityProvider)){
            securityProviderInstance = new SecurityProvider();
        }

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

}
