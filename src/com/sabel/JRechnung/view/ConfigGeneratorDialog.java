package com.sabel.JRechnung.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfigGeneratorDialog extends ConfigDialog {

    private JPanel mainPanel;
    private JTextField jtfWordFileExportPath, jtfWordTemplateFile;
    private JButton btnChooseExportDirectory, btnChooseWordTemplate;

    public ConfigGeneratorDialog(JFrame window) {
        super(window, "Konfiguration erstellen");
        this.setSize(700,400);
        this.initComponents();
    }

    private void initComponents() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayout(4,1));

        this.jtfWordFileExportPath = new JTextField();
        this.jtfWordFileExportPath.setBorder(new TitledBorder("Exportpfad"));
        this.jtfWordFileExportPath.setEditable(false);
        this.btnChooseExportDirectory = new JButton("Export Verzeichnis wählen...");
        this.btnChooseWordTemplate = new JButton("Word-Vorlage wählen...");

        this.jtfWordTemplateFile = new JTextField();
        this.jtfWordTemplateFile.setBorder(new TitledBorder("Word-Vorlage-Datei"));
        this.jtfWordTemplateFile.setEditable(false);

        this.mainPanel.add(this.jtfWordFileExportPath);
        this.mainPanel.add(this.btnChooseExportDirectory);

        this.mainPanel.add(this.jtfWordTemplateFile);
        this.mainPanel.add(this.btnChooseWordTemplate);

        this.addMainPanel(this.mainPanel);
    }


    public void setTextWordFileExportPath(String wordFileExportPath){
        this.jtfWordFileExportPath.setText(wordFileExportPath);
    }

    public void setTextWordTemplateFile(String wordTemplateFile){
        this.jtfWordTemplateFile.setText(wordTemplateFile);
    }

    public String getTextWordFileExportPath(){
        return this.jtfWordFileExportPath.getText();
    }

    public String getTextWordTemplateFile(){
        return this.jtfWordTemplateFile.getText();
    }

    public void setChooseExportDirectoryButtonEnabled(boolean enabled){
        this.btnChooseExportDirectory.setEnabled(enabled);
    }


    public void setChooseExportDirectoryButtonListener(ActionListener listener){
        this.btnChooseExportDirectory.addActionListener(listener);
    }

    public void setChooseWordTemplateButtonEnabled(boolean enabled){
        this.btnChooseWordTemplate.setEnabled(enabled);
    }


    public void setChooseWordTemplateButtonListener(ActionListener listener){
        this.btnChooseWordTemplate.addActionListener(listener);
    }
}
