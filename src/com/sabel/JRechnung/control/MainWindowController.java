package com.sabel.JRechnung.control;

import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.Message;
import com.sabel.JRechnung.model.objects.Bill;
import com.sabel.JRechnung.view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainWindowController implements Controller {

    private MainWindow mainWindow;
    private ControllerReturnStatus controllerReturnStatus;
    private List<Bill> billsOfYear;
    private ImageIcon deleteIcon, billIcon, businessIcon, customerIcon, passwordIcon, productOrServiceIcon;

    public MainWindowController() {
        this.loadIcons();
        this.mainWindow = new MainWindow();
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.OK;
        billsOfYear = new ArrayList<>();
        this.setIcons();
    }

    private void loadIcons() {
        this.deleteIcon = Publisher.getModel().getImageIconFromResources("delete.png");
        this.billIcon = Publisher.getModel().getImageIconFromResources("bill.png");
        this.businessIcon = Publisher.getModel().getImageIconFromResources("company.png");
        this.customerIcon = Publisher.getModel().getImageIconFromResources("customer.png");
        this.passwordIcon = Publisher.getModel().getImageIconFromResources("password.png");
        this.productOrServiceIcon = Publisher.getModel().getImageIconFromResources("product_service.png");
    }

    private void setIcons() {
        this.mainWindow.setCreateBillButtonIcon(this.billIcon);
        this.mainWindow.setDeleteIcon(this.deleteIcon);
        this.mainWindow.setManageBusinessButtonIcon(this.businessIcon);
        this.mainWindow.setManageCustomersButtonIcon(this.customerIcon);
        this.mainWindow.setEncPasswortResetButtonIcon(this.passwordIcon);
        this.mainWindow.setManageProductOrServiceButtonIcon(this.productOrServiceIcon);
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
        this.mainWindow.setYearComboBoxListenerEnabled(false);
        if(!refill) {
            List<Integer> yearList = Publisher.getModel().getYearList(5);

            for (Integer year : yearList) {
                this.mainWindow.addToYearList(year.intValue());
            }

            this.mainWindow.setIndexOfSelectedYear(2);

            this.billsOfYear = Publisher.getModel().readBillsInYear(Publisher.getModel().getCurrentYear());
        }else{
            int yearFromList = this.mainWindow.getYearFromList(this.mainWindow.getIndexOfSelectedYear());
            billsOfYear = Publisher.getModel().readBillsInYear(yearFromList);
            this.mainWindow.removeAllTableEntries();
        }



        for (int i = 0; i < billsOfYear.size(); i++) {
            Bill bill = billsOfYear.get(i);
            fillRowWithBillData(i,bill);
        }

        this.mainWindow.setCompletePriceLabelText(String.format(Locale.GERMANY,"%.2f €",Publisher.getModel().calcCompletePriceForYear(billsOfYear)));
        this.mainWindow.setPaidLabelText(String.format(Locale.GERMANY,"%.2f €",Publisher.getModel().calcNotYetPaidOrPaidPrice(billsOfYear,true)));
        this.mainWindow.setNotYetPaidLabelText(String.format(Locale.GERMANY,"%.2f €",Publisher.getModel().calcNotYetPaidOrPaidPrice(billsOfYear,false)));
        this.mainWindow.setTaxLabelText(String.format(Locale.GERMANY,"%.2f €",Publisher.getModel().calcCompleteTax(billsOfYear)));

        this.mainWindow.setYearComboBoxListenerEnabled(true);
    }

    private void fillRowWithBillData(int rowIndex, Bill bill){

        String[] cellData = new String[]{bill.getTitel(),bill.getDebtor().getName(),Publisher.getModel().dateToGermanDateString(bill.getCreationDate()),
                Publisher.getModel().dateToGermanDateString(bill.getToPayToDate()),bill.isPaid() ? "Ja" : "Nein",
                Publisher.getModel().dateToGermanDateString(bill.getPaidOnDate()),String.format(Locale.GERMANY,"%.2f",bill.getTotalPrice()),""};

     //   this.billConfigDialog.setTableModelListenerEnabled(false);

        this.mainWindow.addRowToBillTable(cellData);

       // this.billConfigDialog.setTableModelListenerEnabled(true);

    }

    private void initEvents() {
        this.mainWindow.setCreateBillButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Publisher.getModel().businessExists() &&Publisher.getModel().thereAreCustomers()) {

                    controllerReturnStatus = ControllerReturnStatus.OK;

                    Controller controller = new BillConfigDialogController(mainWindow, null);
                    controller.run();
                    fillWindowComponents(true);
                }else{
                    Message.showErrorMessage("Sie müssen ein Unternehmen anlegen und Kunden einpflegen bevor Sie Rechnungen anlegen können.");
                }
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
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1 && table.getSelectedColumn() < 7) {
                    if(row < billsOfYear.size()){
                        controllerReturnStatus = ControllerReturnStatus.OK;

                        Controller controller = new BillConfigDialogController(mainWindow, billsOfYear.get(row));
                        controller.run();
                        fillWindowComponents(true);
                    }
                }


            }

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                JTable table =(JTable) mouseEvent.getSource();
                table.removeMouseListener(this);
                if (table.getSelectedColumn() == 7) {
                    Bill billToRemove = billsOfYear.get(table.getSelectedRow());
                    if(Message.showYesNoConfirmDialog(mainWindow,"Möchten Sie diese Rechnung wirklich löschen?","Rechnung löschen")){
                        Publisher.getModel().removeBill(billToRemove,Message.showYesNoConfirmDialog(mainWindow,"Soll die zur Rechnung gehörige Word-Datei auch gelöscht werden?","Rechnung löschen"));
                        fillWindowComponents(true);
                    }
                }
                table.addMouseListener(this);
            }
        });

        this.mainWindow.setYearComboBoxListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillWindowComponents(true);
            }
        });
    }


}
