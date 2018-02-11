package Rechnung;

import Rechnung.components.BillList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainWindow extends JFrame {

    private JButton jbtnCreateBill;
    private JPanel jpCenter;
    private JPanel jpWest;
    private JTable jtblBillList;

    public MainWindow() throws HeadlessException {
        super("JRechnung");

     //   initEvents();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.initComponents();
        this.setSize(1280,768);
        this.setLocationRelativeTo(null);
      //  this.pack();
        this.setVisible(true);
    }

    private void initComponents() {

        this.jpWest = new JPanel();
        this.jbtnCreateBill = new JButton();
        this.jpCenter = new JPanel();
        this.jpCenter.setLayout(new BorderLayout());

        this.jtblBillList = new BillList();
        JScrollPane scrollPane = new JScrollPane(this.jtblBillList);
        this.jpCenter.add(scrollPane,BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        this.jpWest.setBackground(new java.awt.Color(207, 117, 117));
        this.jpWest.setPreferredSize(new java.awt.Dimension(150, 0));

        this.jbtnCreateBill.setText("Neue Rechnung");
        this.jpWest.add(jbtnCreateBill);

        this.add(jpWest, BorderLayout.WEST);

        this.jpCenter.setBackground(new java.awt.Color(60, 73, 14));
    //    jpCenter.setPreferredSize(new java.awt.Dimension(0, 500));
        this.add(this.jpCenter, BorderLayout.CENTER);

    }


}