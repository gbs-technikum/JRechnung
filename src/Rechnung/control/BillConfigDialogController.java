package Rechnung.control;

import Rechnung.Publisher;
import Rechnung.model.db.BillEntryService;
import Rechnung.model.db.CustomerService;
import Rechnung.model.objects.*;
import Rechnung.view.BillConfigDialog;

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

public class BillConfigDialogController implements Controller {

    private BillConfigDialog billConfigDialog ;
    private Controller.ControllerReturnStatus controllerReturnStatus;
    private Bill bill;


    public BillConfigDialogController(JFrame window,Bill bill) {
        ImageIcon imageIcon = Publisher.getModel().getImageIconFromResources("delete.png");
        this.billConfigDialog = new BillConfigDialog(window,imageIcon);
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
                    } else {
                        billConfigDialog.addRowsToEntryTable(1);
                    }
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

                if(e.getLastRow() == e.getFirstRow()){
                    switch (e.getColumn()){
                        case 2:
                            if(!Publisher.getModel().isIntNumber(cellValue)){
                                tableModel.setValueAt("",e.getFirstRow(), e.getColumn());
                                tableModel.setValueAt("",e.getFirstRow(),4);
                            }else{
                                Object cellPriceObject = tableModel.getValueAt(e.getFirstRow(),3);
                                if(cellPriceObject != null){
                                    if(Publisher.getModel().isFloatingPointNumber(cellPriceObject.toString())){
                                        tableModel.setValueAt(String.format(Locale.GERMANY,"%.2f",Double.parseDouble(cellPriceObject.toString().replace(",",".")) * Double.parseDouble(cellValue.replace(",","."))),e.getFirstRow(),4);
                                    }
                                }
                            }
                            break;
                        case 3:
                            if(!Publisher.getModel().isFloatingPointNumber(cellValue)){
                                tableModel.setValueAt("",e.getFirstRow(), e.getColumn());
                                tableModel.setValueAt("",e.getFirstRow(),4);
                            }else{
                                Object cellAmountObject = tableModel.getValueAt(e.getFirstRow(),2);
                                if(cellAmountObject != null){
                                    if(Publisher.getModel().isIntNumber(cellAmountObject.toString())){
                                        tableModel.setValueAt(String.format(Locale.GERMANY,"%.2f",Double.parseDouble(cellAmountObject.toString()) * Double.parseDouble(cellValue.replace(",","."))),e.getFirstRow(),4);
                                    }
                                }
                            }
                            break;
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
    }

    private void saveComponentData() {
        createBillFromWindowData();
        Publisher.getModel().saveBill(this.bill);
    }
    private List<BillEntry> createBillEntriesFromWindowData(){
        List<BillEntry> billEntries = new ArrayList<>();



        return billEntries;
    }

    private void fillWindowComponents(Bill bill) {
        this.billConfigDialog.clearComponentData();

        if(bill != null) {
            fillDebtorComboBox(null,null);

            this.billConfigDialog.setTitleTextField(bill.getTitel());

            List<BillEntry> billEntries = bill.getBillEntries();
            for (int i = 0; i < billEntries.size(); i++) {
                fillRowWithBillEntry(i, billEntries.get(i));
            }

            if (this.bill == null) {
                this.bill = bill;
            }
        }else
        {
            this.billConfigDialog.setBillNumberTextField(Publisher.getModel().generateBillNumber());
            this.billConfigDialog.setTitleTextField(Publisher.getModel().generateBillDefaultName());
            fillDebtorComboBox(null,null);
            fillProductOrServiceComboBox();
        }
    }

    private void fillRowWithBillEntry(int rowIndex, BillEntry billEntry){
        this.billConfigDialog.setEntryTitel(rowIndex, billEntry.getEntryText());

    }

    private void addRowWithProductOrServiceData(ProductOrService productOrService){
        String[] cellData = new String[]{productOrService.getTitle(),"","",String.format(Locale.GERMANY,"%.2f",productOrService.getPrice()),""};

        this.billConfigDialog.addRowToEntryTable(cellData);
    }

    private void createBillFromWindowData(){

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
        String billNumber = this.billConfigDialog.getBillNumberTextField();

        Date creationDate = new Date();
        Date toPayToDate = Publisher.getModel().convert(this.billConfigDialog.getToPayToDateTextField());
        Date paidOnDate =  Publisher.getModel().convert(this.billConfigDialog.getPaidOnDateTextField());
        boolean paid = this.billConfigDialog.isPaidCheckbox();
        String comment = this.billConfigDialog.getCommentTextArea();
        File billFile = new File(this.billConfigDialog.getFileLabel());

        boolean taxFree = this.billConfigDialog.isTaxFreeCheckbox();
        boolean taxIncluded = this.billConfigDialog.isTaxIncludedCheckbox();

        bill = new Bill(id,billNumber,debtor,titel,creationDate,toPayToDate,paidOnDate,paid,comment,billFile,taxFree,taxIncluded);
        List<BillEntry> list = this.createBillEntriesFromWindowData();

        for (BillEntry entry : list){
            bill.addEntry(entry);
        }

        this.bill = bill;
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



}


