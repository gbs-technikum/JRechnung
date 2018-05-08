package com.sabel.JRechnung.control;

import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.Message;
import com.sabel.JRechnung.model.WorkingTimeRecorder;
import com.sabel.JRechnung.model.objects.*;
import com.sabel.JRechnung.view.BillConfigDialog;
import com.sabel.JRechnung.view.WordWaitWindow;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class BillConfigDialogController implements Controller {

    private BillConfigDialog billConfigDialog;
    private Controller.ControllerReturnStatus controllerReturnStatus;
    private Bill bill;
    private JFrame parentWindow;
    private java.util.Timer timer;
    private boolean timeIsRecognized;
    private Date workingTimeRecordStart;


    public BillConfigDialogController(JFrame window,Bill bill) {
        ImageIcon imageIcon = Publisher.getModel().getImageIconFromResources("delete.png");
        this.billConfigDialog = new BillConfigDialog(window,imageIcon);
        this.workingTimeRecordStart = null;
        this.timeIsRecognized = false;
        this.parentWindow = window;
        this.initEvents();
        this.controllerReturnStatus = Controller.ControllerReturnStatus.ABORT;
        this.bill = bill;
        fillWindowComponents(this.bill);
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
                saveComponentData();
                billConfigDialog.setVisible(false);
                billConfigDialog.dispose();
            }
        });
        this.billConfigDialog.setOkayButtonEnabled(true);

        this.billConfigDialog.setApplyButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveComponentData();
                billConfigDialog.setBillGenerationEnabled(true);
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

        this.billConfigDialog.setAddEntryButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                billConfigDialog.setTableModelListenerEnabled(false);

                if(Publisher.getModel().isCompletePriceDataValid(billConfigDialog.getTableColumnData(4))) {
                    ProductOrService selectedProductOrService = billConfigDialog.getProductOrServiceFromList(billConfigDialog.getIndexOfSelectedProductOrService());
                    if (selectedProductOrService != null) {
                        addRowWithProductOrServiceData(selectedProductOrService);
                    } else if(timeIsRecognized && timer == null) {
                        addRowWithWorkingTime();
                        billConfigDialog.setTimeLabel("00:00:00");
                        timeIsRecognized = false;
                    } else{
                        billConfigDialog.addRowsToEntryTable(1);
                    }
                    billConfigDialog.setCellValue(String.valueOf(getPredefinedTax()),billConfigDialog.getEntryTableRowCount()-1,1);
                }else{
                    Message.showErrorMessage("Sie können erst eine Neue Zeile hinzufügen, wenn die bestehenden Zeilen vollständig befüllt sind.");
                }


                billConfigDialog.setTableModelListenerEnabled(true);
            }
        });

        this.billConfigDialog.setTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                TableModel tableModel = (TableModel)e.getSource();
                tableModel.removeTableModelListener(this);
                String cellValue = tableModel.getValueAt(e.getFirstRow(), e.getColumn()).toString();

                int currentRow = e.getFirstRow();

                if(e.getLastRow() == e.getFirstRow()){
                    switch (e.getColumn()){
                        case 1:
                            if(!Publisher.getModel().isIntNumber(cellValue)){
                                tableModel.setValueAt("",currentRow, e.getColumn());
                                System.out.println("dyfassadasdsadjltfhlasiuiesjaIPJOAKL");
                                tableModel.setValueAt("",currentRow,4);
                            }
                            break;

                        case 2:
                            if(!Publisher.getModel().isIntNumber(cellValue)){
                                tableModel.setValueAt("",e.getFirstRow(), e.getColumn());
                                tableModel.setValueAt("",e.getFirstRow(),4);
                            }
                            break;
                        case 3:
                            if(!Publisher.getModel().isFloatingPointNumber(cellValue)){
                                tableModel.setValueAt("",e.getFirstRow(), e.getColumn());
                                tableModel.setValueAt("",e.getFirstRow(),4);
                            }
                            break;
                    }

                    double calcPrice = calculateRowCompletePrice(currentRow);
                    System.out.println("calcPrice" + calcPrice);
                    if(calcPrice > -0.5){
                        tableModel.setValueAt(String.format(Locale.GERMANY,"%.2f",calcPrice),currentRow,4);
                    }else{
                        tableModel.setValueAt("",currentRow,4);
                    }

                }

                tableModel.addTableModelListener(this);
            }
        });

        this.billConfigDialog.setTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                int column = table.columnAtPoint(point);
                if (column == 5) {
                    billConfigDialog.setTableModelListenerEnabled(false);
                    billConfigDialog.removeEntryRow(row);
                    billConfigDialog.setTableModelListenerEnabled(true);
                }
            }
        });

        this.billConfigDialog.setBillOverviewButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bill != null && bill.getBillFile() != null) {
                    ImageIcon loader = Publisher.getModel().getImageIconFromResources("loader.gif");
                    JDialog waitWindow = new WordWaitWindow(parentWindow,bill,loader);

/*                    WordStarter runnable = new WordStarter(bill.getBillFile());

                    Thread wordStarterThread = new Thread(runnable);

                    wordStarterThread.start();


                    while (!runnable.isReadyToUse()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e1) {
                        }
                    }*/

                   //  ((WordWaitWindow) waitWindow).close();
                }

            }});

     //   this.billConfigDialog.setBillOverviewButtonEnabled(true);

        this.billConfigDialog.setBillGenerationButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(createBillFromWindowData(true)) {
                    File file = Publisher.getModel().generateWordFile(bill);
                    if (file != null) {
                        billConfigDialog.setFileLabel(file.getAbsolutePath());
                        bill.setBillFile(file);
                        Publisher.getModel().saveBillWithNewFile(bill);
                        billConfigDialog.setBillOverviewButtonEnabled(true);
                    }
                }
            }
        });

        this.billConfigDialog.setTaxIncludedCheckboxListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTableCompletePrice();
            }
        });

        this.billConfigDialog.setTaxFreeCheckboxListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTableCompletePrice();
            }
        });

        this.billConfigDialog.setStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!timeIsRecognized){
                    ((JButton)e.getSource()).setEnabled(false);

                    workingTimeRecordStart = new Date();

                    timeIsRecognized = true;

                    timer = new Timer();
                    timer.schedule(new WorkingTimeRecorder(billConfigDialog.getTimeLabelObject()), 0, 1000);
                }else{
                    //TODO Fehler
                }
            }
        });

        this.billConfigDialog.setStopButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timer != null){
                    timer.cancel();;
                    timer = null;

                    billConfigDialog.setStartButtonEnabled(true);
                }
            }
        });
    }

    private void updateTableCompletePrice(){
        for (int i = 0; i < billConfigDialog.getEntryTableRowCount(); i++) {
            double calcPrice = calculateRowCompletePrice(i);
            System.out.println("calcPrice" + calcPrice);
            if(calcPrice > -1){
                billConfigDialog.setCellValue(String.format(Locale.GERMANY,"%.2f",calcPrice),i,4);
            }else{
                billConfigDialog.setCellValue("",i,4);
            }
        }
    }

    private void saveComponentData() {
        if(createBillFromWindowData(true))
        {
            Publisher.getModel().saveBill(this.bill);
        }
    }
    private List<BillEntry> createBillEntriesFromWindowData(boolean withMessage){
        List<BillEntry> billEntries = new ArrayList<>();

        int rowCount = this.billConfigDialog.getEntryTableRowCount();

        for(int i=0; i < rowCount;i++){
            String id = Publisher.getModel().getNewObjectId();
            String entryText = this.billConfigDialog.getCellValue(i,0);

            if(entryText == null || entryText.length() < 3){
                if(withMessage){
                    Message.showErrorMessage("Sie müssen mindestens 3 Zeichen für das Feld 'Produkt/Dienstleistung' angeben.");
                }
                return null;
            }

            double unitPrice =  -1.0;
            int amount = -1;
            int taxRate = -1;

            try {
                taxRate = Integer.parseInt(this.billConfigDialog.getCellValue(i,1));
            }catch (Exception e){
                if(withMessage){
                    Message.showErrorMessage("Sie müssen einen ganzzaligen Wert für den Mehrwertsteuersatz angeben.");
                }
                return null;
            }

            try {
                amount = Integer.parseInt(this.billConfigDialog.getCellValue(i,2));
            }catch (Exception e){
                if(withMessage){
                    Message.showErrorMessage("Sie müssen einen ganzzaligen Wert für die Anzahl angeben.");
                }
                return null;
            }

            try {
                unitPrice =  Double.parseDouble(this.billConfigDialog.getCellValue(i,3).replace(",","."));
            }catch (Exception e){
                if(withMessage){
                    Message.showErrorMessage("Sie müssen einen Wert für den Einzelpreis angeben.");
                }
                return null;
            }

            BillEntry billEntry = new BillEntry(id,taxRate,unitPrice,amount,entryText);
            billEntries.add(billEntry);
        }

        return billEntries;
    }

    private void fillWindowComponents(Bill bill) {
        this.billConfigDialog.clearComponentData();

        if(bill != null) {
            fillDebtorComboBox(null,bill.getDebtor());


            this.billConfigDialog.setTitleTextField(bill.getTitel());
            this.billConfigDialog.setToPayToDateTextField(Publisher.getModel().dateToGermanDateString(bill.getToPayToDate()));
            this.billConfigDialog.setPaidOnDateTextField(Publisher.getModel().dateToGermanDateString(bill.getPaidOnDate()));
            this.billConfigDialog.setBillNumberTextField(bill.getNumber());

            billConfigDialog.setTaxIncludedCheckbox(bill.mustBeIncludedTaxes());
            billConfigDialog.setTaxFreeCheckbox(bill.isBusinessTaxFree());
            billConfigDialog.setPaidCheckbox(bill.isPaid());

            billConfigDialog.setCommentTextArea(bill.getComment());

            List<BillEntry> billEntries = bill.getBillEntries();
            for (int i = 0; i < billEntries.size(); i++) {
                fillRowWithBillEntry(i, billEntries.get(i));
            }

            billConfigDialog.setBillGenerationEnabled(true);

            File billFile = bill.getBillFile();

            if(billFile != null){
                billConfigDialog.setFileLabel(billFile.getAbsolutePath());
                billConfigDialog.setBillOverviewButtonEnabled(true);
            }

            this.bill = bill;
        }else
        {
            this.billConfigDialog.setToPayToDateTextField(Publisher.getModel().dateToGermanDateString(Publisher.getModel().getTwoWeeksFromTodayDate()));
            this.billConfigDialog.setBillNumberTextField(Publisher.getModel().generateBillNumber());
            this.billConfigDialog.setTitleTextField(Publisher.getModel().generateBillDefaultName());
            fillDebtorComboBox(null,null);
        }

        fillProductOrServiceComboBox();
    }

    private void fillRowWithBillEntry(int rowIndex, BillEntry billEntry){

        String[] cellData = new String[]{billEntry.getEntryText(),String.valueOf(billEntry.getTaxRateInPercent()),
                String.valueOf(billEntry.getAmount()),String.format(Locale.GERMANY,"%.2f",billEntry.getUnitPrice()),""};

        this.billConfigDialog.setTableModelListenerEnabled(false);

        this.billConfigDialog.addRowToEntryTable(cellData);

        this.updateTableCompletePrice();

        this.billConfigDialog.setTableModelListenerEnabled(true);

    }

    private void addRowWithProductOrServiceData(ProductOrService productOrService){
        String[] cellData = new String[]{productOrService.getTitle(),"","",String.format(Locale.GERMANY,"%.2f",productOrService.getPrice()),""};

        //String.valueOf(getPredefinedTax())

        this.billConfigDialog.addRowToEntryTable(cellData);
    }

    private void addRowWithWorkingTime(){
        String[] cellData = new String[]{"erfasste Arbeitszeit am " + Publisher.getModel().dateToGermanDateTimeString(this.workingTimeRecordStart) + " ==> " +
                this.billConfigDialog.getTimeLabel(),String.valueOf(getPredefinedTax()),"1","",""};

        this.billConfigDialog.addRowToEntryTable(cellData);
    }

    private boolean createBillFromWindowData(boolean withMessage){

        Bill bill = null;

        String id = null;

        if(this.bill != null){
            id = this.bill.getId();
        }else {
            id = Publisher.getModel().getNewObjectId();
        }

        int customerIndex = this.billConfigDialog.getIndexOfSelectedCustomer();
        Customer debtor = null;

        if(customerIndex >= 0) {
            debtor = this.billConfigDialog.getCustomerFromList(customerIndex);
        }

        String titel = this.billConfigDialog.getTitleTextField();

        if(!Publisher.getModel().isBillTitleValid(titel)){

            if(withMessage){
                Message.showErrorMessage("Rechnungname muss mindestens 3 Zeichen beinhalten.");
            }
            return false;
        }

        String billNumber = this.billConfigDialog.getBillNumberTextField();

        if(!Publisher.getModel().isBillNumberValid(billNumber)){

            if(withMessage){
                Message.showErrorMessage("Rechnungsnummer muss mindestens 3 Zeichen beinhalten.");
            }
            return false;
        }

        Date creationDate = null;
        if(this.bill != null){
            creationDate = this.bill.getCreationDate();
        }else {
            creationDate = new Date();
        }

        Date toPayToDate = Publisher.getModel().germanDateStringToDate(this.billConfigDialog.getToPayToDateTextField());
        Date paidOnDate =  Publisher.getModel().germanDateStringToDate(this.billConfigDialog.getPaidOnDateTextField());

        if(Publisher.getModel().isDateAfterGivenDate(creationDate,toPayToDate)){
            if(withMessage){
                Message.showErrorMessage("Sie müssen ein gültiges Datum für den Zahlungsstermin eintragen. Dieses muss nach dem Erstellungsdatum liegen!");
            }
            return false;
        }

        if(this.billConfigDialog.getPaidOnDateTextField().length() > 0){
            if(Publisher.getModel().isDateAfterGivenDate(creationDate,paidOnDate)){
                if(withMessage){
                    Message.showErrorMessage("Wenn Sie ein Datum für den Tag, an die die Rechnung beglichen wurde, angeben. Muss dieses Datum nach dem Erstellungsdatum liegen.");
                }
                return false;
            }else {
                this.billConfigDialog.setPaidCheckbox(true);
            }
        }



        boolean paid = this.billConfigDialog.isPaidCheckbox();

        if(paid && this.billConfigDialog.getPaidOnDateTextField().length() < 6){
            if(withMessage){
                Message.showErrorMessage("Wenn Sie die Rechnung als bezahlt markieren, müssen Sie ein Datum, an dem sie beglichen wurde, eingeben.");
            }
            return false;
        }

        String comment = this.billConfigDialog.getCommentTextArea();

        String filePath = this.billConfigDialog.getFileLabel();

        File billFile = null;
        if(filePath.length() > 0){
            billFile = new File(this.billConfigDialog.getFileLabel());
        }

        boolean taxFree = this.billConfigDialog.isTaxFreeCheckbox();
        boolean taxIncluded = this.billConfigDialog.isTaxIncludedCheckbox();

        bill = new Bill(id,billNumber,debtor,titel,creationDate,toPayToDate,paidOnDate,paid,comment,billFile,taxFree,taxIncluded);
        List<BillEntry> list = this.createBillEntriesFromWindowData(withMessage);

        if(list == null){
            return false;
        }

        for (BillEntry entry : list){
            bill.addEntry(entry);
        }

        this.bill = bill;

        return true;
    }

    private int fillDebtorComboBox(List<Customer> customers,  Customer selectedCustomer){
        int selectedIndex = 0;

        if(customers == null){
            customers = Publisher.getModel().readCustomers();
        }

        for (int i = 0; i < customers.size(); i++) {
            Customer currentCustomer = customers.get(i);
            this.billConfigDialog.addToCustomerList(currentCustomer);
            if (selectedCustomer != null && selectedCustomer.equals(currentCustomer)) {
                selectedIndex = i;
            }
        }

        return selectedIndex;
    }

    private void fillProductOrServiceComboBox(){

        List<ProductOrService> productsOrServices = Publisher.getModel().readProductsOrServices();

        this.billConfigDialog.addToProductOrServiceList(null);

        for (int i = 0; i < productsOrServices.size(); i++) {
            ProductOrService productOrService = productsOrServices.get(i);
            this.billConfigDialog.addToProductOrServiceList(productOrService);
        }
    }

    private double calculateRowCompletePrice(int row){

        if(createBillFromWindowData(false) && this.bill != null) {
            return this.bill.getEntryTotalPrice(row);
        }

        return -1;
    }

    public int getPredefinedTax(){

        if(this.billConfigDialog.isTax0RadioButtonChecked() || this.billConfigDialog.isTaxFreeCheckbox()) {
            return 0;
        }

        return (this.billConfigDialog.isTax19RadioButtonChecked()) ? 19 : 7;
    }


}


