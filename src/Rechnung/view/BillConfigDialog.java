package Rechnung.view;

import Rechnung.model.objects.Customer;
import Rechnung.model.objects.ProductOrService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class BillConfigDialog extends ConfigDialog {

    private static final String[] TABLE_COLUMN_NAMES = {"Produkt/Dienstleistung","Einheit","Anzahl","Einzelpreis(€)","Gesamtpreis(€)"};
    private static final int PREDEFIND_TABLE_ROWCOUNT = 1;
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
    private JPanel leftButtunPanel, leftPanel2;
    private JPanel entryPanel, jPanelTop;
    private JLabel jlblFile;
    private TableModelListener tableModelListener;


    public BillConfigDialog(JFrame frame) {
        super(frame, "Rechnung");
        this.tableModelListener = null;
        this.initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(1024,768));
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));

     //   this.chkBxButtonGroup = new ButtonGroup();

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


        DefaultTableModel tableModel = new DefaultTableModel(TABLE_COLUMN_NAMES, PREDEFIND_TABLE_ROWCOUNT)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return (column < TABLE_COLUMN_NAMES.length-1) ? true : false;
            }
        };


        this.jtblEntries = new JTable(tableModel);
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
        this.entryPanel.add(this.leftPanel2,BorderLayout.WEST);


        this.mainPanel.add(this.jPanelCheckboxes);
        this.mainPanel.add(this.entryPanel);



        this.mainPanel.add(this.jspEntryTable);

        this.mainPanel.add(this.jspComment);

        this.mainPanel.add(leftButtunPanel);

        this.mainPanel.add(this.btnGenerateBillFile);

        this.leftButtunPanel.add(this.btnGenerateBillFile,BorderLayout.WEST);
        this.leftButtunPanel.add(this.jlblFile,BorderLayout.CENTER);

        this.mainPanel.add(this.leftButtunPanel);

        this.addMainPanel(this.mainPanel);
        this.pack();
    }

    public String getTitleTextField(){
        return this.jtfTitel.getText().toString();
    }

    public String getBillNumberTextField(){
        return this.jtfBillNumber.getText().toString();
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

    public void setFileLabel(String text){
        this.jlblFile.setText(text);
    }

    public void setAddEntryButtonListener(ActionListener actionListener){
        this.btnAddEntry.addActionListener(actionListener);
    }

    public void addRowsToEntryTable(int rowCount){
        DefaultTableModel model = (DefaultTableModel) this.jtblEntries.getModel();
        for (int i = 0; i < rowCount; i++) {
            model.addRow(new String[TABLE_COLUMN_NAMES.length]);
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
}
