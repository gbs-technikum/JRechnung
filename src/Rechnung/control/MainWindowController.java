package Rechnung.control;

import Rechnung.Publisher;
import Rechnung.control.BusinessConfigDialogController;
import Rechnung.control.Controller;
import Rechnung.model.db.BillService;
import Rechnung.model.objects.Bill;
import Rechnung.model.objects.BillEntry;
import Rechnung.view.CustomersConfigDialog;
import Rechnung.view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

public class MainWindowController implements Controller {

    private MainWindow mainWindow;
    private ControllerReturnStatus controllerReturnStatus;

    public MainWindowController() {
            this.mainWindow = new MainWindow();
            this.initEvents();
            this.controllerReturnStatus = ControllerReturnStatus.OK;
        }

    @Override
    public ControllerReturnStatus run() {
        this.fillWindowComponents(false);
        this.mainWindow.setVisible(true);
        this.waitForWindowToClose(this.mainWindow);
        this.mainWindow = null;
        return controllerReturnStatus;
    }

    public void showWindow(){
        this.fillWindowComponents(false);
        this.mainWindow.setVisible(true);
        this.mainWindow.toFront();
        this.mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.mainWindow.setExtendedState(JFrame.NORMAL);
    };

    private void fillWindowComponents(boolean refill) {

        List<Bill> bills = Publisher.getModel().readBillsInYear(2018);
        System.out.println("count of bills in year: " + bills.size());
        for (int i = 0; i < bills.size(); i++) {
            Bill bill = bills.get(i);
            this.mainWindow.setBillTitel(i,bill.getTitel());
        }

    }

    private void initEvents() {
        this.mainWindow.setCreateBillButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.OK;

                Controller controller = new BillConfigDialogController(mainWindow);
                controller.run();
            }
        });
        this.mainWindow.setCreateBillButtonEnabled(true);

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

        this.mainWindow.setManageProductOrServiceButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller controller = new ProductOrServiceConfigDialogController(mainWindow);
                controller.run();
            }
        });

        this.mainWindow.setManageProductOrServiceButtonEnabled(true);
    }
}
