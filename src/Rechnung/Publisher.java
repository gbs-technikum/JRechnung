package Rechnung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Publisher {

    private static Logger loggerInstance;
    private static SecurityProvider securityProviderInstance;
    private static final String DB_CONNECTION_STRING = "jdbc:sqlite://home//mirko//jrechnung.sqlite";
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
                System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
            }
        }

        if(!(dbConnectionInstance instanceof Connection)){
            dbConnectionInstance = DriverManager.getConnection(Publisher.DB_CONNECTION_STRING);
        }

        return dbConnectionInstance;
    }

}
