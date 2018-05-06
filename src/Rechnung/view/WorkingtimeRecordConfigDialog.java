package Rechnung.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class WorkingtimeRecordConfigDialog extends ConfigDialog {

    private JPanel mainPanel;
    private JTextField jtfPricePerHour;
    private JLabel lblTime;
    private JButton jbtnStart, jbtnStop;
    private JPanel jpNorth, jpCenter, jpSouth;

    public WorkingtimeRecordConfigDialog(JFrame frame) {
        super(frame, "Unternehmen");
        this.initComponents();
    }

    private void initComponents() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());

        this.jpNorth = new JPanel();
        this.jpNorth.setLayout(new GridLayout(1,1));

        this.jpCenter = new JPanel();
        this.jpCenter.setLayout(new GridLayout(1,1));

        this.jpSouth = new JPanel();
        this.jpSouth.setLayout(new GridLayout(1,2));

        this.jtfPricePerHour = new JTextField();
        this.jtfPricePerHour.setBorder(new TitledBorder("Stundenpreis"));

        this.lblTime = new JLabel();
        this.lblTime.setText("00:00:00");
        this.lblTime.setBorder(new TitledBorder("Zeit"));
        lblTime.setSize(200,70);

        this.jbtnStart = new JButton("Start");
        this.jbtnStop = new JButton("Stop");

        this.jpNorth.add(this.jtfPricePerHour);
        this.jpCenter.add(this.lblTime);
        this.jpSouth.add(this.jbtnStart);
        this.jpSouth.add(this.jbtnStop);

        this.mainPanel.add(this.jpNorth,BorderLayout.NORTH);
        this.mainPanel.add(this.jpCenter,BorderLayout.CENTER);
        this.mainPanel.add(this.jpSouth,BorderLayout.SOUTH);

        this.addMainPanel(this.mainPanel);
    }

}
