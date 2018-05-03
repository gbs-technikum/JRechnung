package Rechnung.control;

import Rechnung.JRechungConfigGenerator;
import Rechnung.Publisher;
import Rechnung.model.SecurityProvider;
import Rechnung.view.MainWindow;
import Rechnung.view.StarterWindow;

import javax.swing.*;
import java.awt.event.*;

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
                JFrame window = new JRechungConfigGenerator();

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
