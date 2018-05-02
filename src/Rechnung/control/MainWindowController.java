package Rechnung.control;

import Rechnung.Publisher;
import Rechnung.model.objects.Bill;
import Rechnung.view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController implements Controller {

    private MainWindow mainWindow;
    private ControllerReturnStatus controllerReturnStatus;
    private List<Bill> billsOfYear;

    public MainWindowController() {
            this.mainWindow = new MainWindow();
            this.initEvents();
            this.controllerReturnStatus = ControllerReturnStatus.OK;
            billsOfYear = new ArrayList<>();
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

        billsOfYear = Publisher.getModel().readBillsInYear(2018);
        System.out.println("count of bills in year: " + billsOfYear.size());
        for (int i = 0; i < billsOfYear.size(); i++) {
            Bill bill = billsOfYear.get(i);
            this.mainWindow.setBillTitel(i,bill.getTitel());
        }

    }

    private void initEvents() {
        this.mainWindow.setCreateBillButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.OK;

                Controller controller = new BillConfigDialogController(mainWindow,null);
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

        this.mainWindow.setTableMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    if(row < billsOfYear.size()){
                        controllerReturnStatus = ControllerReturnStatus.OK;

                        Controller controller = new BillConfigDialogController(mainWindow, billsOfYear.get(row));
                        controller.run();
                        fillWindowComponents(true);
                    }
                }
            }
        });
    }
}
