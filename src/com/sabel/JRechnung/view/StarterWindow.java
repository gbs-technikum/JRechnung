package com.sabel.JRechnung.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class StarterWindow extends JFrame {

    private JPanel appPanel;
    private JPanel dbPanel;
    private JButton btnApp, btnCreateConfig, btnResetDB, btnResetEncryptionPasswort, btnInitEncryption, btnChangeDBEncryptionKey;

    public StarterWindow() throws HeadlessException {
        super("JRechnung Starter");

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.initComponents();
        this.setSize(300,500);
        this.setLocationRelativeTo(null);
        this.pack();
    }

    private void initComponents() {
        this.appPanel = new JPanel();
        this.appPanel.setBorder(new TitledBorder("App"));
        this.dbPanel = new JPanel();
        this.appPanel.setBorder(new TitledBorder("Datenbank"));

        this.btnApp = new JButton();
        this.btnApp.setText("Rechnung starten...");
        this.btnCreateConfig = new JButton();
        this.btnCreateConfig.setText("Konfigurationsdatei erstellen...");

        this.btnInitEncryption = new JButton();
        this.btnInitEncryption.setText("Verschlüsselungspasswort setzen...");

        this.btnResetEncryptionPasswort = new JButton();
        this.btnResetEncryptionPasswort.setText("Verschlüsselungspasswort zurücksetzen...");

        this.btnChangeDBEncryptionKey = new JButton();
        this.btnChangeDBEncryptionKey.setText("Verschlüsselung erneuern...");

        this.btnResetDB = new JButton();
        this.btnResetDB.setText("Datenbank zurücksetzen");

        this.btnApp.setEnabled(false);
        this.btnCreateConfig.setEnabled(false);
        this.btnChangeDBEncryptionKey.setEnabled(false);
        this.btnInitEncryption.setEnabled(false);
        this.btnResetEncryptionPasswort.setEnabled(false);
        this.btnResetDB.setEnabled(false);

        this.appPanel.add(this.btnApp);
        this.appPanel.add(this.btnCreateConfig);

        this.dbPanel.add(this.btnInitEncryption);
        this.dbPanel.add(this.btnResetEncryptionPasswort);
        this.dbPanel.add(this.btnChangeDBEncryptionKey);
        this.dbPanel.add(this.btnResetDB);


        this.add(this.appPanel,BorderLayout.NORTH);
        this.add(this.dbPanel,BorderLayout.SOUTH);
    }

    public void setAppButtonListener(ActionListener actionListener){
        this.btnApp.addActionListener(actionListener);
    }

    public void setAppButtonEnabled(Boolean enabled){
        this.btnApp.setEnabled(enabled);
    }

    public void setCreateConfigButtonListener(ActionListener actionListener){
        this.btnCreateConfig.addActionListener(actionListener);
    }

    public void setCreateConfigButtonEnabled(Boolean enabled){
        this.btnCreateConfig.setEnabled(enabled);
    }

    public void setResetDBButtonListener(ActionListener actionListener){
        this.btnResetDB.addActionListener(actionListener);
    }

    public void setResetDBButtonEnabled(Boolean enabled){
        this.btnResetDB.setEnabled(enabled);
    }

    public void setInitEncryptionButtonListener(ActionListener actionListener){
        this.btnInitEncryption.addActionListener(actionListener);
    }

    public void setInitEncryptionButtonEnabled(Boolean enabled){
        this.btnInitEncryption.setEnabled(enabled);
    }

    public void setChangeDBEncryptionKeyButtonListener(ActionListener actionListener){
        this.btnChangeDBEncryptionKey.addActionListener(actionListener);
    }

    public void setChangeDBEncryptionKeyButtonEnabled(Boolean enabled){
        this.btnChangeDBEncryptionKey.setEnabled(enabled);
    }

    public void setResetEncryptionPasswortButtonListener(ActionListener actionListener){
        this.btnResetEncryptionPasswort.addActionListener(actionListener);
    }

    public void setResetEncryptionPasswortButtonEnabled(Boolean enabled){
        this.btnResetEncryptionPasswort.setEnabled(enabled);
    }


}
