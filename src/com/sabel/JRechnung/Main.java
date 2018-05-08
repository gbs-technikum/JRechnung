package com.sabel.JRechnung;

import com.sabel.JRechnung.control.Controller;
import com.sabel.JRechnung.control.EncryptionConfigDialogController;
import com.sabel.JRechnung.control.StarterWindowController;
import com.sabel.JRechnung.model.Message;
import com.sabel.JRechnung.model.SecurityProvider;
import com.sabel.JRechnung.control.MainWindowController;

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


                boolean configExists = Publisher.getModel().loadConfigFile();
                boolean dbExist = !(Publisher.getDBConnection() == null);
                boolean encryptionIsInitialized = false;

                if(!dbExist){
                    if(Publisher.getModel().copyPreDefinedDBToApplicationDir(false)) {
                        dbExist = true;
                    }else{
                        Message.showErrorMessage("Datenbank kann nicht erstellt werden");
                        System.exit(0);
                    }
                }

                if(dbExist){
                    SecurityProvider sp = Publisher.getSecurityProvider();
                    encryptionIsInitialized = sp.isInitialized();
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


                Controller starter = new StarterWindowController(dbExist,encryptionIsInitialized,configExists);

                if(starter.run() != Controller.ControllerReturnStatus.ABORT) {

                    if(!configExists){
                        if(!Publisher.getModel().loadConfigFile()){
                            Message.showErrorMessage("Konfigurationsdatei nicht gefunden.");
                            System.exit(0);
                        }
                    }

                    if(!dbExist){
                        if(Publisher.getDBConnection() == null){
                            Message.showErrorMessage("Konfigurationsdatei nicht gefunden.");
                            System.exit(0);
                        }
                    }

                    SecurityProvider sp = Publisher.getSecurityProvider();

                    Controller.ControllerReturnStatus returnStatus = Controller.ControllerReturnStatus.ABORT;
                    JFrame tmpFrame = new JFrame();

                    if (!sp.isInitialized()) {
                        do {
                            Controller controller = new EncryptionConfigDialogController(tmpFrame, false);
                            returnStatus = controller.run();
                        }
                        while (returnStatus != Controller.ControllerReturnStatus.OK && returnStatus != Controller.ControllerReturnStatus.ABORT);
                        if (returnStatus == Controller.ControllerReturnStatus.ABORT) {
                            System.exit(0);
                        }
                    }

                    do {
                        Controller controller = new EncryptionConfigDialogController(tmpFrame);
                        returnStatus = controller.run();
                    }
                    while (returnStatus != Controller.ControllerReturnStatus.OK && returnStatus != Controller.ControllerReturnStatus.ABORT);

                    tmpFrame.dispose();
                    tmpFrame = null;

                    if (returnStatus == Controller.ControllerReturnStatus.ABORT) {
                        System.exit(0);
                    }

                    mainWindowController.run();

                    Publisher.destroy();

                }

    }

}
