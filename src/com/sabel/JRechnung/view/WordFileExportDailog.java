package com.sabel.JRechnung.view;

import javax.swing.*;

public class WordFileExportDailog extends ConfigDialog {

    private JPanel mainPanel;

    public WordFileExportDailog(JFrame frame) {
        super(frame, "Word-Datei wird generiert...");
        this.initComponents();
    }

    private void initComponents() {
        this.mainPanel = new JPanel();

        this.addMainPanel(this.mainPanel);
    }

}
