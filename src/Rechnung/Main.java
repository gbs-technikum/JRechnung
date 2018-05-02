package Rechnung;

import Rechnung.control.Controller;
import Rechnung.control.EncryptionConfigDialogController;
import Rechnung.model.SecurityProvider;
import Rechnung.control.MainWindowController;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import it.sauronsoftware.junique.MessageHandler;


import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final String MSG_OPEN = "r-e-o-p-e-n";
    private static final String UNIQUE_APP_ID = "51bbcded-b410-46cf-b081-007dbb13ee06";


    public static void main(String[] args) throws Exception {


                if(!Publisher.getModel().loadConfigFile()){
                    System.exit(0);
                }

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

                SecurityProvider sp = Publisher.getSecurityProvider();

                Controller.ControllerReturnStatus returnStatus = Controller.ControllerReturnStatus.ABORT;
                JFrame tmpFrame = new JFrame();

                if(!sp.isInitialized()){
                    do{
                        Controller controller = new EncryptionConfigDialogController(tmpFrame,false);
                        returnStatus = controller.run();
                    }while (returnStatus != Controller.ControllerReturnStatus.OK && returnStatus != Controller.ControllerReturnStatus.ABORT);
                    if(returnStatus == Controller.ControllerReturnStatus.ABORT){
                        System.exit(0);
                    }
                }

                do{
                    Controller controller = new EncryptionConfigDialogController(tmpFrame);
                    returnStatus = controller.run();
                }while (returnStatus != Controller.ControllerReturnStatus.OK && returnStatus != Controller.ControllerReturnStatus.ABORT);

                tmpFrame.dispose();
                tmpFrame = null;

                if(returnStatus == Controller.ControllerReturnStatus.ABORT){
                    System.exit(0);
                }

                Logger logger = Publisher.getLogger();

                try {
                    Connection connection = Publisher.getDBConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                logger.loginfo("Start...");

                mainWindowController.run();



    }

}
