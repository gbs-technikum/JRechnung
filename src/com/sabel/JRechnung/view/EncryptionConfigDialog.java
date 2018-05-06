package com.sabel.JRechnung.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;

public class EncryptionConfigDialog extends ConfigDialog{

    private JPanel mainPanel;
    private JPasswordField jpswfFirst, jpswSecond;
    private JPasswordField jpswfPrevious;

    public EncryptionConfigDialog(JFrame frame, String title) {
        super(frame, title);
        this.initComponents();
    }

    private void initComponents() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));

        this.jpswfPrevious = new JPasswordField(25);
        this.jpswfPrevious.setBorder(new TitledBorder("aktuelles Passwort"));
        this.jpswfPrevious.setVisible(false);

        this.jpswfFirst = new JPasswordField(25);
        this.jpswfFirst.setBorder(new TitledBorder("Passwort"));

        this.jpswSecond = new JPasswordField(25);
        this.jpswSecond.setBorder(new TitledBorder("Passwort Wiederholung"));
        this.jpswSecond.setVisible(false);

        this.mainPanel.add(this.jpswfPrevious);
        this.mainPanel.add(this.jpswfFirst);
        this.mainPanel.add(this.jpswSecond);

        this.addMainPanel(this.mainPanel);
    }


    public String getPreviousPassword() {
        return new String(this.jpswfPrevious.getPassword());
    }

    public String getFirstPassword() {
        return new String(this.jpswfFirst.getPassword());
    }

    public String getSecondPassword() {
        return new String(this.jpswSecond.getPassword());
    }

    public void setSecondPasswordFieldVisible(boolean visible){
        this.jpswSecond.setVisible(visible);
    }

    public void setPreviousPasswordFieldVisible(boolean visible){
        this.jpswfPrevious.setVisible(visible);
    }

    public void setFirstPasswordFieldListener(ActionListener listener){
        this.jpswfFirst.addActionListener(listener);
    }

}
