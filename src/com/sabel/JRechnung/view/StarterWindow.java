package com.sabel.JRechnung.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
        this.setSize(400,500);
        this.setLocationRelativeTo(null);
        this.pack();
    }

    private void initComponents() {
        Insets insets = new Insets(20, 20, 20, 20);
        this.appPanel = new JPanel();
        this.appPanel.setBorder(new TitledBorder("App"));
        this.appPanel.setLayout(new GridLayout(2,1));
        this.dbPanel = new JPanel();
        this.dbPanel.setLayout(new GridLayout(4,1));
        this.dbPanel.setBorder(new TitledBorder("Datenbank"));

        this.btnApp = new JButton();
        this.btnApp.setText("Rechnung starten...");
        this.btnApp.setMargin(insets);
        this.btnCreateConfig = new JButton();
        this.btnCreateConfig.setText("Konfigurationsdatei erstellen...");
        this.btnCreateConfig.setMargin(insets);

        this.btnInitEncryption = new JButton();
        this.btnInitEncryption.setText("Verschlüsselungspasswort setzen...");
        this.btnInitEncryption.setMargin(insets);

        this.btnResetEncryptionPasswort = new JButton();
        this.btnResetEncryptionPasswort.setText("Verschlüsselungspasswort zurücksetzen...");
        this.btnResetEncryptionPasswort.setMargin(insets);

        this.btnChangeDBEncryptionKey = new JButton();
        this.btnChangeDBEncryptionKey.setText("Verschlüsselung erneuern...");
        this.btnChangeDBEncryptionKey.setMargin(insets);

        this.btnResetDB = new JButton();
        this.btnResetDB.setText("Datenbank zurücksetzen");
        this.btnResetDB.setMargin(insets);

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
