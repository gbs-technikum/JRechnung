package Rechnung.control;

import Rechnung.view.BillConfigDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        this.billConfigDialog.setOkayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.OK;
             //   saveComponentData();
                billConfigDialog.setVisible(false);
                billConfigDialog.dispose();
            }
        });
        this.billConfigDialog.setOkayButtonEnabled(true);

        this.billConfigDialog.setApplyButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //    saveComponentData();
            }
        });
        this.billConfigDialog.setApplyButtonEnabled(true);

        this.billConfigDialog.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.ABORT;
                billConfigDialog.setVisible(false);
                billConfigDialog.dispose();
            }
        });
        this.billConfigDialog.setCancelButtonEnabled(true);
    }

}
