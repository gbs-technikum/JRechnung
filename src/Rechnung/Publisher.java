package Rechnung;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Publisher {

    private static Logger loggerInstance;
    private static SecurityProvider securityProviderInstance;
    private static Connection dbConnectionInstance;


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
            f = new File(Publisher.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            dbFile = new File(f.getPath() + "/jrechnung.sqlite");
            if(!dbFile.exists() || !dbFile.canWrite()){
                throw new SQLException("DB File Error");
            }
            DB_CONNECTION_STRING += dbFile.getAbsoluteFile();
            dbConnectionInstance = DriverManager.getConnection(DB_CONNECTION_STRING);
        }

        return dbConnectionInstance;
    }

}
