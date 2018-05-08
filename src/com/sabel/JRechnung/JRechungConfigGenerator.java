package com.sabel.JRechnung;

import com.sabel.JRechnung.model.Configuration;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JRechungConfigGenerator extends JFrame {

    private JPanel mainPanel;
    private JTextField jtfWordFileExportPath, jtfWordTemplateFile;
    private JButton btnSaveConfig, btnChooseExportDirectory, btnChooseWordTemplate;

    public JRechungConfigGenerator() throws HeadlessException {
        super("Config");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.initComponents();
        this.pack();
        this.setVisible(true);
    }

    private void initComponents() {

        this.setLayout(new BorderLayout());
        this.jtfWordFileExportPath = new JTextField();
        this.jtfWordFileExportPath.setBorder(new TitledBorder("Exportpfad"));
        this.jtfWordFileExportPath.setEditable(false);
        this.btnSaveConfig = new JButton("Speichern...");
        this.btnChooseExportDirectory = new JButton("Export Verzeichnis wählen...");
        this.btnChooseWordTemplate = new JButton("Word-Vorlage wählen...");

        this.jtfWordTemplateFile = new JTextField();
        this.jtfWordTemplateFile.setBorder(new TitledBorder("Word-Vorlage-Datei"));
        this.jtfWordTemplateFile.setEditable(false);

        this.btnSaveConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                File saveFile = null;
                // Demonstrate "Save" dialog:
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int chooserResult = fileChooser.showSaveDialog(JRechungConfigGenerator.this);

                if (chooserResult == JFileChooser.APPROVE_OPTION) {
                    saveFile = new File(fileChooser.getSelectedFile().getAbsolutePath() + "\\" + Configuration.CONFIG_FILE_NAME);

                    if(jtfWordFileExportPath.getText().length() > 0) {
                     //   Publisher.getModel().saveXMLConfigFile(saveFile,);
                    }
                }
            }
        });

        this.btnChooseExportDirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int chooserResult = fileChooser.showSaveDialog(JRechungConfigGenerator.this);
                if (chooserResult == JFileChooser.APPROVE_OPTION) {
                    jtfWordFileExportPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        this.btnChooseWordTemplate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileFilter filter = new FileNameExtensionFilter("Word-Vorlage-Datei", "dotx");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int chooserResult = fileChooser.showSaveDialog(JRechungConfigGenerator.this);
                if (chooserResult == JFileChooser.APPROVE_OPTION) {
                    jtfWordTemplateFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel,BoxLayout.Y_AXIS));

        this.mainPanel.add(this.jtfWordFileExportPath);
        this.mainPanel.add(this.btnChooseExportDirectory);
        this.mainPanel.add(this.jtfWordTemplateFile);
        this.mainPanel.add(this.btnChooseWordTemplate);
        this.mainPanel.add(this.btnSaveConfig);

        this.add(this.mainPanel,BorderLayout.CENTER);


    }



}
