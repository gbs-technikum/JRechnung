package Rechnung.control;

import Rechnung.Publisher;
import Rechnung.model.db.BillEntryService;
import Rechnung.model.db.CustomerService;
import Rechnung.model.objects.*;
import Rechnung.view.BillConfigDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

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
    private JFrame parentWindow;

    private static OleFrame oleFrame1;
    private static OleClientSite clientSite;


    public BillConfigDialogController(JFrame window,Bill bill) {
        ImageIcon imageIcon = Publisher.getModel().getImageIconFromResources("delete.png");
        this.billConfigDialog = new BillConfigDialog(window,imageIcon);
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

        final Display display = new Display();

        this.billConfigDialog.setBillOverviewButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
/*                controllerReturnStatus = ControllerReturnStatus.OK;

                Controller controller = new BillOverviewConfigDialogController(parentWindow);
                controller.run();*/

                billConfigDialog.dispose();

                JFrame childFrame = new JFrame("Word Viewer Window");
                childFrame.setSize(850, 690);

                JPanel childPanel = new JPanel();
                childPanel.setSize(850, 40);
                final JButton selectAllButton = new JButton("Select All");
                final JButton copyButton = new JButton("Copy");
                final JButton copyPasteButton = new JButton("Copy & Paste");
                childPanel.add(selectAllButton);
                childPanel.add(copyButton);
                childPanel.add(copyPasteButton);

                final Canvas canvas = new Canvas();
                canvas.setSize(850, 650);

                childFrame.getContentPane().add(childPanel, BorderLayout.NORTH);
                childFrame.getContentPane().add(canvas,BorderLayout.SOUTH);
                childFrame.pack();
                childFrame.setVisible(true);

                System.out.println("init fertig");

                display.asyncExec(new Runnable() {

                    public void run() {
                        System.out.println("RUN");
                        FillLayout thisLayout = new FillLayout(
                                org.eclipse.swt.SWT.HORIZONTAL);
                        Shell shell = SWT_AWT.new_Shell(display, canvas);
                        shell.setLayout(thisLayout);
                        shell.setSize(800, 600);

                        try {
                            oleFrame1 = new OleFrame(shell, SWT.NONE);
                            clientSite = new OleClientSite(oleFrame1, SWT.NULL, new File("C:\\Users\\Mirko\\Documents\\test128.docx"));
                            clientSite.setBounds(0, 0, 104, 54);
                            clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
                            System.out.println("Complete process OLE Client Site");

                        } catch (Exception e2) {
                            String str = "Create OleClientSite Error:" + e2.toString();
                            System.out.println(str);
                            return;
                        }
                        shell.layout();
                        shell.open();

                        selectAllButton.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent e) {
                                display.asyncExec(new Runnable() {
                                    public void run() {
                                        try {
                                            doPrint();
                                        } catch (Exception e2) {
                                            String str = "OleAutomation Error:" + e2.toString();
                                            System.out.println(str);
                                            return;
                                        }
                                    }
                                });
                            }
                        });

                        copyButton.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent e) {
                                display.asyncExec(new Runnable() {

                                    public void run() {
                                        try {
                                            copySelection();
                                        } catch (Exception e2) {
                                            String str = "OleAutomation Error:" + e2.toString();
                                            System.out.println(str);
                                            return;
                                        }
                                    }
                                });
                            }
                        });
                    }

                    OleAutomation document;
                    int[] ids;
                    Variant result;

                    private void doPrint() {
                        if(clientSite == null)
                            return;


                        int result = clientSite.exec(OLE.OLECMDID_PRINT,
                                OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
                        if(result != OLE.S_OK)
                            System.out.println("Cann't print!!!" + result);


                    }

                    private void selectAll() {
                        document = new OleAutomation(clientSite);
                        System.out.println("Instantiated document");
                        ids = document.getIDsOfNames(new String[] {"wdDialogFilePrint"});
                        result = document.invoke(ids[0]);

                        document.dispose();

                        System.out.println("Invoked select");
                    }

                    private void copySelection() {
                        document = new OleAutomation(clientSite);
                        ids = document.getIDsOfNames(new String[] {"Application"});
                        result = document.getProperty(ids[0]);
                        document.dispose();

                        System.out.println("Got application");

                        OleAutomation application = result.getAutomation();

                        result.dispose();
                        ids = application.getIDsOfNames(new String[] {"Selection"});
                        result = application.getProperty(ids[0]);
                        application.dispose();

                        System.out.println("Got selection");

                        OleAutomation selection = result.getAutomation();
                        result.dispose();
                        ids = selection.getIDsOfNames(new String[] {"Copy"});
                        result = selection.invoke(ids[0]);
                        result.dispose();

                        System.out.println("Invoked Copy");

                        result.dispose();
                        ids = selection.getIDsOfNames(new String[] {"Move"});
                        result = selection.invoke(ids[0]);
                        result.dispose();

                        System.out.println("Invoked Move to deselect");
                    }
                });


            }
        });

        display.addListener(SWT.CLOSE, new Listener() {

            public void handleEvent(org.eclipse.swt.widgets.Event event) {
                EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        Frame[] frames = JFrame.getFrames();
                        for (int i = 0; i < frames.length; i++) {
                            frames[i].dispose();
                        }
                    }
                });
            }
        });

        this.billConfigDialog.setBillOverviewButtonEnabled(true);
    }

    private void saveComponentData() {
        createBillFromWindowData();
        Publisher.getModel().saveBill(this.bill);
    }
    private List<BillEntry> createBillEntriesFromWindowData(){
        List<BillEntry> billEntries = new ArrayList<>();

        int rowCount = this.billConfigDialog.getEntryTableRowCount();

        for(int i=0; i < rowCount;i++){
            String id = Publisher.getModel().getNewObjectId();
            String entryText = this.billConfigDialog.getCellValue(i,0);
            double unitPrice =  Double.parseDouble(this.billConfigDialog.getCellValue(i,3).replace(",","."));
            int amount = Integer.parseInt(this.billConfigDialog.getCellValue(i,2));

            BillEntry billEntry = new BillEntry(id,19,unitPrice,amount,entryText);
            billEntries.add(billEntry);
        }

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

        System.out.println(bill.toString());

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


