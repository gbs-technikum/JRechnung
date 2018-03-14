package Rechnung.control;

import Rechnung.view.BillConfigDialog;

import javax.swing.*;

public class BillConfigDialogController implements Controller {

    private BillConfigDialog billConfigDialog ;
    private Controller.ControllerReturnStatus controllerReturnStatus;


    public BillConfigDialogController(JFrame window) {
        this.billConfigDialog = new BillConfigDialog(window);
        this.initEvents();
        this.controllerReturnStatus = Controller.ControllerReturnStatus.ABORT;
    }

    @Override
    public Controller.ControllerReturnStatus run() {
        this.billConfigDialog.setVisible(true);
        this.waitForWindowToClose(this.billConfigDialog);
        this.billConfigDialog = null;
        return controllerReturnStatus;
    }

    private void initEvents() {

    }

}
