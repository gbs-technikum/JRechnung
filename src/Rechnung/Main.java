package Rechnung;

import Rechnung.model.SecurityProvider;
import Rechnung.control.MainWindowController;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import it.sauronsoftware.junique.MessageHandler;


import java.sql.Connection;

public class Main {

    private static final String MSG_OPEN = "r-e-o-p-e-n";
    private static final String UNIQUE_APP_ID = "51bbcded-b410-46cf-b081-007dbb13ee06";


    public static void main(String[] args) throws Exception {

        MainWindowController mainWindowController = new MainWindowController();

        try {
            JUnique.acquireLock(UNIQUE_APP_ID, new MessageHandler() {
                @Override
                public String handle(String message) {
                    if(message.equals(MSG_OPEN)){
                        mainWindowController.showWindow();
                    }
                    return null;
                }
            });
        }catch (AlreadyLockedException exc) {
            // one instance is already running, inform it to open but don't continue!
            JUnique.sendMessage(UNIQUE_APP_ID, MSG_OPEN);
            System.exit(0);
        }


        Logger logger = Publisher.getLogger();

       Connection connection = Publisher.getDBConnection();

        System.out.println("xxx" + connection);

        logger.loginfo("Start...");

        SecurityProvider sp = Publisher.getSecurityProvider();


        mainWindowController.run();





    }

}
