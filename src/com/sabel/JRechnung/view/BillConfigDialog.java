package com.sabel.JRechnung.view;

import com.sabel.JRechnung.model.objects.Customer;
import com.sabel.JRechnung.model.objects.ProductOrService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class BillConfigDialog extends ConfigDialog {

    private static final String[] TABLE_COLUMN_NAMES = {"Produkt/Dienstleistung","MwSt(%)","Anzahl","Einzelpreis(€)","Gesamtpreis(€)",""};
    private static final int PREDEFIND_TABLE_ROWCOUNT = 0;
    private JPanel mainPanel;
    private JTextField jtfTitel, jtfBillNumber;
    private JComboBox<Customer> jcbxDebtor;
    private JComboBox<ProductOrService> jcbxProductOrService;
    private JTextField jtfToPayDate, jtfPaidOn;
    private JCheckBox jchkbxPaid, jchkbxTaxFree, jchkbxTaxIncluded;
    private JTable jtblEntries;
    private JTextArea jtxtaComment;
    private JPanel jPanelMiddle, jPanelCheckboxes;
    private JScrollPane jspEntryTable, jspComment;
    private JButton btnAddEntry;
    private JButton btnGenerateBillFile;
    private JButton btnBillOverview;
    private JPanel leftButtunPanel, leftPanel2;
    private JPanel entryPanel, jPanelTop;
    private JLabel jlblFile;
    private TableModelListener tableModelListener;
    private ImageIcon deleImage;
    private JRadioButton jrdbtnTax19, jrdbtnTax7, jrdbtnTax0;
    private ButtonGroup btnTaxGroup;
    private JPanel taxButtonPanel;
    private JPanel jpWorkingTimeRecord;
    private JLabel lblTime;
    private JButton jbtnStart, jbtnStop;


    public BillConfigDialog(JFrame frame, ImageIcon deleImage) {
        super(frame, "Rechnung");
        this.deleImage = deleImage;
        this.tableModelListener = null;
        this.initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(1024,768));
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));

        this.btnTaxGroup = new ButtonGroup();
        this.taxButtonPanel = new JPanel();
        this.taxButtonPanel.setBorder(new TitledBorder("MwSt."));
        this.jrdbtnTax19 = new JRadioButton("19%");
        this.jrdbtnTax19.setSelected(true);
        this.jrdbtnTax7 = new JRadioButton("7%");
        this.jrdbtnTax0 = new JRadioButton("0%");

        this.btnTaxGroup.add(this.jrdbtnTax19);
        this.btnTaxGroup.add(this.jrdbtnTax7);
        this.btnTaxGroup.add(this.jrdbtnTax0);

        this.taxButtonPanel.add(this.jrdbtnTax19);
        this.taxButtonPanel.add(this.jrdbtnTax7);
        this.taxButtonPanel.add(this.jrdbtnTax0);

        this.jpWorkingTimeRecord = new JPanel();

        this.lblTime = new JLabel();
        this.lblTime.setText("00:00:00");
        this.lblTime.setBorder(new TitledBorder("Zeit"));
        lblTime.setSize(200,70);

        this.jbtnStart = new JButton("Start");
        this.jbtnStop = new JButton("Stop");

        this.jpWorkingTimeRecord.setBorder(new TitledBorder("Arbeitszeiterfassung"));
        this.jpWorkingTimeRecord.add(this.lblTime);
        this.jpWorkingTimeRecord.add(this.jbtnStart);
        this.jpWorkingTimeRecord.add(this.jbtnStop);

        this.jtfTitel = new JTextField();
        this.jtfTitel.setBorder(new TitledBorder("Bezeichnung"));

        this.jtfBillNumber = new JTextField();
        this.jtfBillNumber.setBorder(new TitledBorder("Rechnungsnummer"));

        this.jPanelTop = new JPanel();
        this.jPanelTop.setLayout( new GridLayout(1,2));

        this.jPanelMiddle = new JPanel();
        this.jPanelMiddle.setLayout( new GridLayout(1,4));

        this.entryPanel = new JPanel();
        this.entryPanel.setLayout(new BorderLayout());

        this.leftPanel2 = new JPanel();
        this.leftPanel2.setLayout(new GridLayout(1,2));


        this.jcbxDebtor = new JComboBox();
        this.jcbxDebtor.setBorder(new TitledBorder("Schuldner"));

        this.jtfToPayDate = new JTextField();
        this.jtfToPayDate.setBorder(new TitledBorder("zu Begleichen bis"));

        this.jtfPaidOn = new JTextField();
        this.jtfPaidOn.setBorder(new TitledBorder("beglichen am"));

        this.jPanelCheckboxes = new JPanel();

        this.jchkbxPaid = new JCheckBox("Beglichen");
        this.jchkbxTaxFree = new JCheckBox("ohne Mehrwertsteuer");
        this.jchkbxTaxIncluded = new JCheckBox("Einzelpositionen beinhalten Mehrwertsteuer");

        this.btnAddEntry = new JButton("Eintrag hinzufügen");

        DefaultTableModel tableModel = new DefaultTableModel(TABLE_COLUMN_NAMES,PREDEFIND_TABLE_ROWCOUNT)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return (column < TABLE_COLUMN_NAMES.length-2) ? true : false;
            }

            @Override
            public Class getColumnClass(int column)
            {
                return (column == 5) ? ImageIcon.class : Object.class;
            }
        };

        this.jtblEntries = new JTable(tableModel);
        TableColumnModel columnModel = this.jtblEntries.getColumnModel();
        columnModel.getColumn(5).setMaxWidth(20);
        this.jtblEntries.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.jtblEntries.getTableHeader().setReorderingAllowed(true);
        this.jtblEntries.setRowHeight(20);
        Dimension tableDimension = new Dimension(0,350);
        this.jtblEntries.setPreferredSize(tableDimension);
        this.jspEntryTable = new JScrollPane(this.jtblEntries);
        this.jspEntryTable.setPreferredSize(tableDimension);


        this.jtxtaComment = new JTextArea();
        Dimension commentDimension = new Dimension(400,150);
        this.jtxtaComment.setPreferredSize(commentDimension);
        this.jspComment = new JScrollPane(this.jtxtaComment);
        this.jspComment.setBorder(new TitledBorder("Beschreibung / Kommentar"));
        this.jspComment.setPreferredSize(commentDimension);

        this.leftButtunPanel = new JPanel();
        this.leftButtunPanel.setLayout(new BorderLayout());

        this.btnGenerateBillFile = new JButton("Rechnung generieren");
        this.btnGenerateBillFile.setEnabled(false);
        this.btnBillOverview = new JButton("Rechnung Anzeigen");
        this.btnBillOverview.setEnabled(false);


        this.jPanelTop.add(this.jtfTitel);
        this.jPanelTop.add(this.jtfBillNumber);

        this.jPanelMiddle.add(this.jcbxDebtor);
        this.jPanelMiddle.add(this.jtfToPayDate);
        this.jPanelMiddle.add(this.jtfPaidOn);

        this.mainPanel.add(this.jPanelTop);
        this.mainPanel.add(this.jPanelMiddle);


        this.jPanelCheckboxes.add(this.jchkbxPaid);

        this.jPanelCheckboxes.add(this.jchkbxTaxFree);
        this.jPanelCheckboxes.add(this.jchkbxTaxIncluded);

        this.jcbxProductOrService = new JComboBox();
        this.jcbxProductOrService.setBorder(new TitledBorder("Produkt/Dienstleistung"));

        this.jlblFile = new JLabel();
        this.jlblFile.setBorder(new TitledBorder("Dateipfad"));
        this.jlblFile.setPreferredSize(new Dimension(500,40));

        this.leftPanel2.setPreferredSize(new Dimension(450,50));
        this.leftPanel2.add(this.btnAddEntry);
        this.leftPanel2.add(this.jcbxProductOrService);
        this.entryPanel.setBorder(new TitledBorder("Neue Eintragung"));
        this.entryPanel.add(this.leftPanel2,BorderLayout.WEST);
        this.entryPanel.add(this.taxButtonPanel,BorderLayout.CENTER);
        this.entryPanel.add(this.jpWorkingTimeRecord,BorderLayout.EAST);



        this.mainPanel.add(this.jPanelCheckboxes);
        this.mainPanel.add(this.entryPanel);



        this.mainPanel.add(this.jspEntryTable);

        this.mainPanel.add(this.jspComment);

        this.mainPanel.add(leftButtunPanel);

/*        this.mainPanel.add(this.btnGenerateBillFile);
        this.mainPanel.add(this.btnBillOverview);*/


        this.leftButtunPanel.add(this.btnGenerateBillFile,BorderLayout.WEST);
        this.leftButtunPanel.add(this.jlblFile,BorderLayout.CENTER);
        this.leftButtunPanel.add(this.btnBillOverview,BorderLayout.EAST);

        this.mainPanel.add(this.leftButtunPanel);

        this.addMainPanel(this.mainPanel);
        this.pack();
    }

    private void addDeleteButtonToRow(int rowCount){
        this.jtblEntries.setValueAt(this.deleImage.getImage(), rowCount, 5);
    }

    public String getTitleTextField(){
        return this.jtfTitel.getText().toString();
    }

    public String getBillNumberTextField(){
        return this.jtfBillNumber.getText();
    }

    public void addToCustomerList(Customer customer){
        if(customer != null){
            this.jcbxDebtor.addItem(customer);
        }
    }

    public void removeCustomerFromList(int index){
        if(index >= 0 && index < this.jcbxDebtor.getItemCount()){
            this.jcbxDebtor.removeItemAt(index);
        }
    }

    public void setCustomerComboBoxListener(ActionListener listener){
        this.jcbxDebtor.addActionListener(listener);
    }

    public void removeCustomerComboBoxListener(){

        for(int i=0;i< this.jcbxDebtor.getActionListeners().length;i++){
            this.jcbxDebtor.removeActionListener(this.jcbxDebtor.getActionListeners()[i]);
        }

    }

    public ActionListener getCustomerComboBoxListener(){
        if(this.jcbxDebtor.getActionListeners().length > 0) {
            return this.jcbxDebtor.getActionListeners()[0];
        }

        return null;
    }


    public int getIndexOfSelectedCustomer(){
        return this.jcbxDebtor.getSelectedIndex();
    }


    public void setIndexOfSelectedCustomer(int index){
        if(index >= 0 && index < this.jcbxDebtor.getItemCount()) {
            this.jcbxDebtor.setSelectedIndex(index);
        }
    }

    public Customer getCustomerFromList(int index){
        if(index >= 0 && index < this.jcbxDebtor.getItemCount()){
            return this.jcbxDebtor.getItemAt(index);
        }

        return null;
    }

    public String getToPayToDateTextField(){
        return this.jtfToPayDate.getText();
    }

    public String getPaidOnDateTextField(){
        return this.jtfPaidOn.getText();
    }

    public boolean isPaidCheckbox(){
        return this.jchkbxPaid.isSelected();
    }

    public boolean isTaxFreeCheckbox(){
        return this.jchkbxTaxFree.isSelected();
    }

    public boolean isTaxIncludedCheckbox(){
        return this.jchkbxTaxIncluded.isSelected();
    }

    public String getCommentTextArea(){
        return this.jtxtaComment.getText();
    }

    public String getFileLabel(){
        return this.jlblFile.getText();
    }

    public void setFileLabel(String text){
        this.jlblFile.setText(text);
    }

    public void setTitleTextField(String text){
        this.jtfTitel.setText(text);
    }

    public void setBillNumberTextField(String text){
        this.jtfBillNumber.setText(text);
    }

    public void setToPayToDateTextField(String text){
        this.jtfToPayDate.setText(text);
    }

    public void setPaidOnDateTextField(String text){
        this.jtfPaidOn.setText(text);
    }

    public void setPaidCheckbox(boolean check){
        this.jchkbxPaid.setSelected(check);
    }

    public void setTaxFreeCheckbox(boolean check){
        this.jchkbxTaxFree.setSelected(check);
    }

    public void setTaxIncludedCheckbox(boolean check){
        this.jchkbxTaxIncluded.setSelected(check);
    }

    public void setCommentTextArea(String text){
        this.jtxtaComment.setText(text);
    }



    public void setBillOverviewButtonListener(ActionListener actionListener){
        this.btnBillOverview.addActionListener(actionListener);
    }

    public void setBillOverviewButtonEnabled(Boolean enabled){
        this.btnBillOverview.setEnabled(enabled);
    }


    public void setAddEntryButtonListener(ActionListener actionListener){
        this.btnAddEntry.addActionListener(actionListener);
    }


    public void addRowsToEntryTable(int rowCount){
        DefaultTableModel model = (DefaultTableModel) this.jtblEntries.getModel();
        for (int i = 0; i < rowCount; i++) {
            model.addRow(new String[TABLE_COLUMN_NAMES.length]);
            model.setValueAt(this.deleImage, model.getRowCount()-1, 5);
        }
    }

    public void addRowToEntryTable(String[] cellData){
        if(cellData.length == (TABLE_COLUMN_NAMES.length-1)){
            DefaultTableModel model = (DefaultTableModel) this.jtblEntries.getModel();
            model.addRow(cellData);
            model.setValueAt(this.deleImage, model.getRowCount()-1, 5);
        }
    }

    public void removeEntryRow(int rowIndex){
        if(rowIndex >= 0 && rowIndex < this.jtblEntries.getRowCount()){
            ((DefaultTableModel)this.jtblEntries.getModel()).removeRow(rowIndex);
        }
    }

    public void setEntryTitel(int rowIndex, String text){
        this.jtblEntries.setValueAt(text,rowIndex,0);
    }

    public void setTableModelListener(TableModelListener tableModelListener){
        this.jtblEntries.getModel().addTableModelListener(tableModelListener);
        this.tableModelListener = tableModelListener;
    }

    public void setTableModelListenerEnabled(boolean enabled){
        if(enabled){
            this.jtblEntries.getModel().addTableModelListener(this.tableModelListener);
        }else {
            this.jtblEntries.getModel().removeTableModelListener(this.tableModelListener);
        }
    }

    public void removeTableChangeListener(){
      //  this.jtblEntries.getModel().removeTableModelListener();
    }

    public String[] getTableColumnData(int columnIndex){
        String[] columnData = new String[this.jtblEntries.getRowCount()];

        if(columnIndex >= 0 && columnIndex < this.jtblEntries.getColumnCount()){
            for(int i = 0; i < this.jtblEntries.getRowCount();i++){
                Object cellObject = this.jtblEntries.getValueAt(i,columnIndex);
                if(cellObject != null){
                    columnData[i] = cellObject.toString();
                }
            }
        }

        return columnData;
    }

    public int getEntryTableRowCount(){
        return this.jtblEntries.getRowCount();
    }

    public int getEntryTableColumnCount(){
        return this.jtblEntries.getColumnCount();
    }

    public String getCellValue(int row, int column){
        int rowCount = this.getEntryTableRowCount();
        int columnCount = this.getEntryTableColumnCount();

        if(row >= 0 && row < rowCount && column >= 0 && column < columnCount){
            Object cellValue = this.jtblEntries.getValueAt(row,column);
            if(cellValue != null){
                return cellValue.toString();
            }else{
                return null;
            }
        }

        return null;
    }

    public void setCellValue(Object value, int row, int column){
        int rowCount = this.getEntryTableRowCount();
        int columnCount = this.getEntryTableColumnCount();

        if(row >= 0 && row < rowCount && column >= 0 && column < columnCount){
            this.jtblEntries.getModel().setValueAt(value,row,column);
        }
    }

    public void clearComponentData(){
        this.jcbxDebtor.removeAllItems();

        this.jtfTitel.setText("");
        this.jtfToPayDate.setText("");
        this.jtfPaidOn.setText("");
        this.jchkbxTaxIncluded.setSelected(false);
        this.jchkbxTaxFree.setSelected(false);
        this.jchkbxPaid.setSelected(false);
        this.jtxtaComment.setText("");
        this.jlblFile.setText("");
        this.jtblEntries.selectAll();
        this.jtblEntries.clearSelection();

    }

    public void addToProductOrServiceList(ProductOrService productOrService){
        this.jcbxProductOrService.addItem(productOrService);
    }

    public ProductOrService getProductOrServiceFromList(int index){
        if(index >= 0 && index < this.jcbxProductOrService.getItemCount()){
            return this.jcbxProductOrService.getItemAt(index);
        }

        return null;
    }

    public int getIndexOfSelectedProductOrService(){
        return this.jcbxProductOrService.getSelectedIndex();
    }

    public void setProductOrService(int index){
        if(index > 0 && index < this.jcbxProductOrService.getItemCount()) {
            this.jcbxProductOrService.setSelectedIndex(index);
        }
    }

    public void removeProductOrServiceFromList(int index){
        if(index >= 0 && index < this.jcbxProductOrService.getItemCount()){
            this.jcbxProductOrService.removeItemAt(index);
        }
    }

    public void removeProductOrServiceComboBoxListener(){

        for(int i=0;i< this.jcbxProductOrService.getActionListeners().length;i++){
            this.jcbxProductOrService.removeActionListener(this.jcbxProductOrService.getActionListeners()[i]);
        }

    }

    public void setTableMouseListener(MouseListener mouseListener){
        this.jtblEntries.addMouseListener(mouseListener);
    }

    public void setBillGenerationButtonListener(ActionListener actionListener){
        this.btnGenerateBillFile.addActionListener(actionListener);
    }

    public void setBillGenerationEnabled(Boolean enabled){
        this.btnGenerateBillFile.setEnabled(enabled);
    }

    public void setTaxIncludedCheckboxListener(ActionListener actionListener){
        this.jchkbxTaxIncluded.addActionListener(actionListener);
    }

    public void setTaxFreeCheckboxListener(ActionListener actionListener){
        this.jchkbxTaxFree.addActionListener(actionListener);
    }

    public boolean isTax19RadioButtonChecked() {
        return jrdbtnTax19.isSelected();
    }

    public boolean isTax7RadioButtonChecked() {
        return jrdbtnTax7.isSelected();
    }

    public boolean isTax0RadioButtonChecked() {
        return jrdbtnTax0.isSelected();
    }


    public String getTimeLabel(){
        return this.lblTime.getText();
    }

    public void setTimeLabel(String text){
        this.lblTime.setText(text);
    }

    public void setStopButtonListener(ActionListener actionListener){
        this.jbtnStop.addActionListener(actionListener);
    }

    public void setStopButtonEnabled(Boolean enabled){
        this.jbtnStop.setEnabled(enabled);
    }

    public void setStartButtonListener(ActionListener actionListener){
        this.jbtnStart.addActionListener(actionListener);
    }

    public void setStartButtonEnabled(Boolean enabled){
        this.jbtnStart.setEnabled(enabled);
    }

    public JLabel getTimeLabelObject() {
        return lblTime;
    }
}
