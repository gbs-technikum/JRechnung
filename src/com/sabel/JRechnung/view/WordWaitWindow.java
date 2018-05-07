package com.sabel.JRechnung.view;

import com.sabel.JRechnung.control.WordStarter;
import com.sabel.JRechnung.model.objects.Bill;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class WordWaitWindow extends JDialog {

    private JLabel jlblWaitText;
    private JPanel panel;
    private Bill bill;
    private ImageIcon loader;

    public WordWaitWindow(JFrame window, Bill bill, ImageIcon loader) {
        super(window,"Rechnungsanzeige wird vorbereitet...");
        this.loader = loader;
        this.bill = bill;
        this.initComponents();
        this.setSize(500,200);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                WordStarter runnable = new WordStarter(bill.getBillFile());

                Thread wordStarterThread = new Thread(runnable);

                wordStarterThread.start();



                while (!runnable.isReadyToUse()) {
                }

                WordWaitWindow.this.close();
            }
        },200);
    }

    private void initComponents() {

        this.setLayout(new BorderLayout());
        this.panel = new JPanel();

        this.jlblWaitText = new JLabel("Rechnungsanzeige wird geladen... ");
        this.jlblWaitText.setIcon( this.loader);

        this.panel.add(this.jlblWaitText);

        this.add(this.panel,BorderLayout.CENTER);
    }

    private void close(){
        this.setVisible(false);
        this.dispose();
    }



}
