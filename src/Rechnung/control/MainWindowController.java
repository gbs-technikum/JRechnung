package Rechnung.control;

import Rechnung.control.BusinessConfigDialogController;
import Rechnung.control.Controller;
import Rechnung.view.CustomersConfigDialog;
import Rechnung.view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindowController implements Controller {

    private MainWindow mainWindow;
    private ControllerReturnStatus controllerReturnStatus;

    public MainWindowController() {
            this.mainWindow = new MainWindow();
     //  this.fillWindowComponents();
            this.initEvents();
            this.controllerReturnStatus = ControllerReturnStatus.OK;
        }

    @Override
    public ControllerReturnStatus run() {
        this.mainWindow.setVisible(true);
        this.waitForWindowToClose(this.mainWindow);
        this.mainWindow = null;
        return controllerReturnStatus;
    }

    public void showWindow(){
        this.mainWindow.setVisible(true);
        this.mainWindow.toFront();
        this.mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.mainWindow.setExtendedState(JFrame.NORMAL);
    };

    private void initEvents() {
        this.mainWindow.setManageBusinessButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.OK;

                Controller controller = new BusinessConfigDialogController(mainWindow);
                controller.run();
            }
        });
        this.mainWindow.setManageBusinessButtonEnabled(true);
        this.mainWindow.setManageCustomersButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.OK;

                Controller controller = new CustomersConfigDialogController(mainWindow);
                controller.run();
            }
        });
        this.mainWindow.setManageCustomersButtonEnabled(true);

        this.mainWindow.setEncPasswortResetButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller controller = new EncryptionConfigDialogController(mainWindow,true);
                controller.run();
            }
        });

        this.mainWindow.setEncPasswortResetButtonEnabled(true);
    }
}
