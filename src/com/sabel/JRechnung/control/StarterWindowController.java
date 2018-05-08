package com.sabel.JRechnung.control;

import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.Message;
import com.sabel.JRechnung.model.SecurityProvider;
import com.sabel.JRechnung.view.StarterWindow;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.sql.SQLException;

public class StarterWindowController implements Controller {

    private StarterWindow starterWindow;
    private Controller.ControllerReturnStatus controllerReturnStatus;
    private boolean configExists;
    private boolean encryptionIsInitialized;
    private boolean dbExists;

    public StarterWindowController(boolean dbExists, boolean encryptionIsInitialized, boolean configExists) {
        this.starterWindow = new StarterWindow();
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.OK;

        this.dbExists = dbExists;
        this.encryptionIsInitialized = encryptionIsInitialized;
        this.configExists = configExists;

        setButtonStatus();

    }

    private void setButtonStatus(){

        this.starterWindow.setResetDBButtonEnabled(false);
        this.starterWindow.setCreateConfigButtonEnabled(false);

        this.starterWindow.setChangeDBEncryptionKeyButtonEnabled(false);
        this.starterWindow.setResetEncryptionPasswortButtonEnabled(false);
        this.starterWindow.setInitEncryptionButtonEnabled(false);
        this.starterWindow.setAppButtonEnabled(false);


        if(dbExists){
            this.starterWindow.setResetDBButtonEnabled(true);
        }

        this.starterWindow.setCreateConfigButtonEnabled(true);

        if(encryptionIsInitialized){
            this.starterWindow.setChangeDBEncryptionKeyButtonEnabled(true);
            this.starterWindow.setResetEncryptionPasswortButtonEnabled(true);
        }else if(dbExists){
            this.starterWindow.setInitEncryptionButtonEnabled(true);
        }

        if(configExists && dbExists && encryptionIsInitialized){
            this.starterWindow.setAppButtonEnabled(true);
        }
    }

    private void initEvents() {
        this.starterWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                controllerReturnStatus = ControllerReturnStatus.ABORT;
            }
        });

        this.starterWindow.setAppButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.OK;
                starterWindow.dispose();
            }
        });

        this.starterWindow.setInitEncryptionButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SecurityProvider sp = Publisher.getSecurityProvider();

                Controller.ControllerReturnStatus returnStatus = Controller.ControllerReturnStatus.ABORT;
                JFrame tmpFrame = new JFrame();

                if (!sp.isInitialized()) {
                    do {
                        Controller controller = new EncryptionConfigDialogController(tmpFrame, false);
                        returnStatus = controller.run();
                    }
                    while (returnStatus != Controller.ControllerReturnStatus.OK && returnStatus != Controller.ControllerReturnStatus.ABORT);
                    if(returnStatus == ControllerReturnStatus.OK){
                        dbExists = true;
                        encryptionIsInitialized = true;
                    }
                }

                setButtonStatus();
            }
        });

        this.starterWindow.setResetDBButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Publisher.getDBConnection().close();
                } catch (SQLException e1) {
                   // e1.printStackTrace();
                }

                if(Publisher.getModel().copyPreDefinedDBToApplicationDir(true)){
                    dbExists = true;
                }else {
                    dbExists = false;
                }
                encryptionIsInitialized = false;

                setButtonStatus();
            }
        });

        this.starterWindow.setCreateConfigButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Controller controller = new ConfigGeneratorDialogController(starterWindow);


                if(controller.run() == ControllerReturnStatus.OK){
                    configExists = true;
                    setButtonStatus();
                }
            }
        });

        this.starterWindow.setChangeDBEncryptionKeyButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerReturnStatus returnStatus = ControllerReturnStatus.ABORT;

                do{
                    Controller controller = new EncryptionConfigDialogController(starterWindow);
                    returnStatus = controller.run();
                }
                while (returnStatus != ControllerReturnStatus.OK && returnStatus != ControllerReturnStatus.ABORT);

                if(returnStatus == ControllerReturnStatus.OK){
                    if(Publisher.getModel().reEncryptDataBasePhase1()) {
                        do {
                            Controller controller = new EncryptionConfigDialogController(starterWindow, false);
                            returnStatus = controller.run();
                        }
                        while (returnStatus != Controller.ControllerReturnStatus.OK && returnStatus != Controller.ControllerReturnStatus.ABORT);
                        if (returnStatus == ControllerReturnStatus.OK) {

                            do{
                                Controller controller = new EncryptionConfigDialogController(starterWindow);
                                returnStatus = controller.run();
                            }
                            while (returnStatus != ControllerReturnStatus.OK && returnStatus != ControllerReturnStatus.ABORT);

                            if (returnStatus == ControllerReturnStatus.OK) {
                                if (!Publisher.getModel().reEncryptDataBasePhase2()) {
                                    Message.showErrorMessage("Umschlüsseln der Datenbank fehlgeschlagen.");
                                }
                                SecurityProvider sp = Publisher.getNewSecurityProvider();
                            }
                        }
                    }
                }
            }
        });

        this.starterWindow.setResetEncryptionPasswortButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileFilter filter = new FileNameExtensionFilter("Schlüsseldatei", "key");
                JFileChooser fileChooser = new JFileChooser();
                int chooserResult = -1;

                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setAcceptAllFileFilterUsed(false);
                chooserResult = fileChooser.showSaveDialog(starterWindow);
                if(chooserResult == JFileChooser.APPROVE_OPTION){
                    ControllerReturnStatus returnStatus = ControllerReturnStatus.ABORT;

                    String secretKeyBase64 = Publisher.getModel().readSecretKeyFile(fileChooser.getSelectedFile());

                    if(secretKeyBase64 != null) {
                        do {
                            Controller controller = new EncryptionConfigDialogController(starterWindow, secretKeyBase64);
                            returnStatus = controller.run();
                        }
                        while (returnStatus != ControllerReturnStatus.OK && returnStatus != ControllerReturnStatus.ABORT);
                    }
                }

            }
        });
    }

    @Override
    public ControllerReturnStatus run() {
        this.starterWindow.setVisible(true);
        this.waitForWindowToClose(this.starterWindow);
        this.starterWindow = null;
        return controllerReturnStatus;
    }
}
