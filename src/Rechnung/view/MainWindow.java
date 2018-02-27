package Rechnung.view;

import Rechnung.view.BillList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private JButton jbtnCreateBill, jbtnManageBusiness, jbtnManageCustomers;
    private JPanel jpCenter;
    private JPanel jpWest;
    private JTable jtblBillList;

    public MainWindow() throws HeadlessException {
        super("JRechnung");

     //   initEvents();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.initComponents();
        this.setSize(1280,768);
        this.setLocationRelativeTo(null);
      //  this.pack();
    }

    private void initComponents() {

        this.jpWest = new JPanel();
        this.jbtnCreateBill = new JButton();
        this.jbtnManageBusiness = new JButton();
        this.jbtnCreateBill.setText("Neue Rechnung");
        this.jbtnManageBusiness.setText("Unternehmmen verwalten");
        this.jbtnManageCustomers = new JButton();
        this.jbtnManageCustomers.setText("Kundenverwaltung");

        this.jbtnManageBusiness.setEnabled(false);
        this.jbtnManageBusiness.setVisible(false);
        this.jbtnManageCustomers.setEnabled(false);
        this.jbtnManageCustomers.setVisible(false);

        this.jpCenter = new JPanel();
        this.jpCenter.setLayout(new BorderLayout());

        this.jtblBillList = new BillList();
        JScrollPane scrollPane = new JScrollPane(this.jtblBillList);
        this.jpCenter.add(scrollPane,BorderLayout.CENTER);

        this.jpWest.setBackground(new java.awt.Color(207, 117, 117));
        this.jpWest.setPreferredSize(new java.awt.Dimension(150, 0));


        this.jpWest.add(this.jbtnCreateBill);
        this.jpWest.add(this.jbtnManageBusiness);
        this.jpWest.add(this.jbtnManageCustomers);

        this.add(jpWest, BorderLayout.WEST);

        this.jpCenter.setBackground(new java.awt.Color(60, 73, 14));
    //    jpCenter.setPreferredSize(new java.awt.Dimension(0, 500));
        this.add(this.jpCenter, BorderLayout.CENTER);

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


}