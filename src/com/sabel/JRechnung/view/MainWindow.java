package com.sabel.JRechnung.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class MainWindow extends JFrame {

    private JButton jbtnCreateBill, jbtnManageBusiness, jbtnManageCustomers, jbtnEncPasswortReset, jbtnManageProductOrService, jbtnWorkingtime;
    private JPanel jpCenter;
    private JPanel jpWest, jpCenterWest, jpSouthWest;
    private JTable jtblBillList;
    private JComboBox<Integer> jcbxYear;
    private JLabel lblTax, lblCompletePrice, lblPaid, lblNotYetPaid;
    private ActionListener comboBoxListener;
    private ImageIcon deletImage;

    public MainWindow(ImageIcon deletImage) throws HeadlessException {
        super("Rechnung");
        this.deletImage = deletImage;
     //   initEvents();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.initComponents();
        this.setSize(1280,768);
        this.setLocationRelativeTo(null);
      //  this.pack();
    }

    private void initComponents() {

        this.jpWest = new JPanel();
        this.jpWest.setLayout(new BorderLayout());

        this.jpCenterWest = new JPanel();
        this.jbtnCreateBill = new JButton();
        this.jbtnManageBusiness = new JButton();
        this.jbtnCreateBill.setText("Neue Rechnung");
        this.jbtnManageBusiness.setText("Unternehmmen verwalten");
        this.jbtnManageCustomers = new JButton();
        this.jbtnManageCustomers.setText("Kundenverwaltung");
        this.jbtnEncPasswortReset = new JButton();
        this.jbtnEncPasswortReset.setText("Passwort ändern");
        this.jbtnManageProductOrService = new JButton("Produkt oder Dienstleistung");
        this.jbtnWorkingtime = new JButton("Arbeitszeit erfassen");


        this.jbtnManageBusiness.setEnabled(false);
        this.jbtnManageBusiness.setVisible(false);
        this.jbtnManageCustomers.setEnabled(false);
        this.jbtnManageCustomers.setVisible(false);
        this.jbtnEncPasswortReset.setEnabled(false);
        this.jbtnEncPasswortReset.setVisible(false);
        this.jbtnManageProductOrService.setEnabled(false);
        this.jbtnManageProductOrService.setVisible(false);
        this.jbtnWorkingtime.setEnabled(false);
        this.jbtnWorkingtime.setVisible(false);

        this.jpCenter = new JPanel();
        this.jpCenter.setLayout(new BorderLayout());

        this.jtblBillList = new BillList();
        JScrollPane scrollPane = new JScrollPane(this.jtblBillList);
        this.jpCenter.add(scrollPane,BorderLayout.CENTER);

        this.jpWest.setBackground(new java.awt.Color(207, 117, 117));
        this.jpWest.setPreferredSize(new java.awt.Dimension(200, 0));


        this.jpCenterWest.add(this.jbtnCreateBill);
        this.jpCenterWest.add(this.jbtnManageBusiness);
        this.jpCenterWest.add(this.jbtnManageCustomers);
        this.jpCenterWest.add(this.jbtnEncPasswortReset);
        this.jpCenterWest.add(this.jbtnManageProductOrService);
        this.jpCenterWest.add(this.jbtnWorkingtime);

        this.jpSouthWest = new JPanel();
        this.jpSouthWest.setSize(0,250);
        this.jpSouthWest.setLayout(new GridLayout(5,1));

        this.lblCompletePrice = new JLabel("xxx");
        this.lblCompletePrice.setBorder(new TitledBorder("Gesamtrechnungsbetrag"));

        this.lblPaid = new JLabel("xxx");
        this.lblPaid.setBorder(new TitledBorder("beglichener Gesamtbertag"));
        this.lblNotYetPaid = new JLabel("xxx");
        this.lblNotYetPaid.setBorder(new TitledBorder("offener Gesamtbetrag"));

        this.lblTax = new JLabel("xxx");
        this.lblTax.setBorder(new TitledBorder("Gesamte Umsatzsteuer"));

        this.jcbxYear = new JComboBox<>();
        this.jcbxYear.setBorder(new TitledBorder("Anzeigejahr"));

        this.jpSouthWest.add(this.jcbxYear);
        this.lblCompletePrice.setSize(200,70);
        this.lblNotYetPaid.setSize(200,70);
        this.lblPaid.setSize(200,70);
        this.lblTax.setSize(200,70);

        this.jpSouthWest.add(this.lblCompletePrice);
        this.jpSouthWest.add(this.lblPaid);
        this.jpSouthWest.add(this.lblNotYetPaid);
        this.jpSouthWest.add(this.lblTax);

        this.jpWest.add(this.jpCenterWest,BorderLayout.CENTER);
        this.jpWest.add(this.jpSouthWest,BorderLayout.SOUTH);
        this.add(jpWest, BorderLayout.WEST);

        this.jpCenter.setBackground(new java.awt.Color(60, 73, 14));
    //    jpCenter.setPreferredSize(new java.awt.Dimension(0, 500));
        this.add(this.jpCenter, BorderLayout.CENTER);

    }

    public void setCreateBillButtonListener(ActionListener listener){
        this.jbtnCreateBill.addActionListener(listener);
        this.jbtnCreateBill.setVisible(true);
    }

    public void setCreateBillButtonEnabled(boolean enabled){
        this.jbtnCreateBill.setEnabled(enabled);
    }

    public void setManageBusinessButtonListener(ActionListener listener){
        this.jbtnManageBusiness.addActionListener(listener);
        this.jbtnManageBusiness.setVisible(true);
    }

    public void setManageBusinessButtonEnabled(boolean enabled){
        this.jbtnManageBusiness.setEnabled(enabled);
    }

    public void setManageCustomersButtonListener(ActionListener listener){
        this.jbtnManageCustomers.addActionListener(listener);
        this.jbtnManageCustomers.setVisible(true);
    }

    public void setManageCustomersButtonEnabled(boolean enabled){
        this.jbtnManageCustomers.setEnabled(enabled);
    }

    public void setEncPasswortResetButtonListener(ActionListener listener){
        this.jbtnEncPasswortReset.addActionListener(listener);
        this.jbtnEncPasswortReset.setVisible(true);
    }

    public void setTableMouseListener(MouseListener mouseListener){
        this.jtblBillList.addMouseListener(mouseListener);
    }

    public void setEncPasswortResetButtonEnabled(boolean enabled){
        this.jbtnEncPasswortReset.setEnabled(enabled);
    }

    public void setManageProductOrServiceButtonListener(ActionListener listener){
        this.jbtnManageProductOrService.addActionListener(listener);
        this.jbtnManageProductOrService.setVisible(true);
    }

    public void setManageProductOrServiceButtonEnabled(boolean enabled){
        this.jbtnManageProductOrService.setEnabled(enabled);
    }

    public void setWorkingtimeButtonListener(ActionListener listener){
        this.jbtnWorkingtime.addActionListener(listener);
        this.jbtnWorkingtime.setVisible(true);
    }

    public void setWorkingtimeButtonButtonEnabled(boolean enabled){
        this.jbtnWorkingtime.setEnabled(enabled);
    }

    public void setBillTitel(int rowIndex, String text){
        this.jtblBillList.setValueAt(text,rowIndex,0);
    }

    public void addToYearList(int year){
        if(year > 2000){
            this.jcbxYear.addItem(Integer.valueOf(year));
        }
    }

    public void setYearComboBoxListener(ActionListener listener){
        this.jcbxYear.addActionListener(listener);
        this.comboBoxListener = listener;
    }

    public void removeCustomerComboBoxListener(){

        for(int i=0;i< this.jcbxYear.getActionListeners().length;i++){
            this.jcbxYear.removeActionListener(this.jcbxYear.getActionListeners()[i]);
        }

    }

    public ActionListener getYearComboBoxListener(){
        if(this.jcbxYear.getActionListeners().length > 0) {
            return this.jcbxYear.getActionListeners()[0];
        }

        return null;
    }


    public int getIndexOfSelectedYear(){
        return this.jcbxYear.getSelectedIndex();
    }


    public void setIndexOfSelectedYear(int index){
        if(index >= 0 && index < this.jcbxYear.getItemCount()) {
            this.jcbxYear.setSelectedIndex(index);
        }
    }

    public int getYearFromList(int index){
        if(index >= 0 && index < this.jcbxYear.getItemCount()){
            return this.jcbxYear.getItemAt(index).intValue();
        }

        return 0;
    }

    public void setCompletePriceLabelText(String text) {
        this.lblCompletePrice.setText(text);
    }

    public void setNotYetPaidLabelText(String text) {
        this.lblNotYetPaid.setText(text);
    }

    public void setTaxLabelText(String text) {
        this.lblTax.setText(text);
    }

    public void setPaidLabelText(String text) {
        this.lblPaid.setText(text);
    }


    public void removeAllTableEntries(){
        this.jtblBillList.removeAll();
        DefaultTableModel defaultTableModel = (DefaultTableModel) this.jtblBillList.getModel();
        defaultTableModel.setRowCount(0);
    }

    public void setYearComboBoxListenerEnabled(boolean enabled){
        if(enabled){
            this.jcbxYear.addActionListener(this.comboBoxListener);
        }else {
            this.jcbxYear.removeActionListener(this.comboBoxListener);
        }
    }

    public void addRowToBillTable(String[] cellData){
        if(cellData.length == this.jtblBillList.getColumnCount()){
            DefaultTableModel model = (DefaultTableModel) this.jtblBillList.getModel();
            model.addRow(cellData);
            model.setValueAt(this.deletImage, model.getRowCount()-1, 7);
        }
    }

    public void removeTableRow(int rowIndex){
        if(rowIndex >= 0 && rowIndex < this.jtblBillList.getRowCount()){
            ((DefaultTableModel)this.jtblBillList.getModel()).removeRow(rowIndex);
        }
    }
}