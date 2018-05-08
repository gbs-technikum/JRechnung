package com.sabel.JRechnung.control;

import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.Configuration;
import com.sabel.JRechnung.model.Message;
import com.sabel.JRechnung.view.ConfigGeneratorDialog;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ConfigGeneratorDialogController implements Controller{
    private ConfigGeneratorDialog configGeneratorDialog;
    private Controller.ControllerReturnStatus controllerReturnStatus;

    public ConfigGeneratorDialogController(JFrame window) {
        this.configGeneratorDialog = new ConfigGeneratorDialog(window);
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.ERROR;
    }

    @Override
    public Controller.ControllerReturnStatus run() {
        this.configGeneratorDialog.setVisible(true);
        this.waitForWindowToClose(this.configGeneratorDialog);
        this.configGeneratorDialog = null;
        return controllerReturnStatus;
    }

    private void initEvents() {
        this.configGeneratorDialog.setOkayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(saveConfigFile()){
                    controllerReturnStatus = Controller.ControllerReturnStatus.OK;
                    configGeneratorDialog.setVisible(false);
                    configGeneratorDialog.dispose();
                }
            }
        });
        this.configGeneratorDialog.setOkayButtonEnabled(true);

        this.configGeneratorDialog.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = Controller.ControllerReturnStatus.ABORT;
                configGeneratorDialog.setVisible(false);
                configGeneratorDialog.dispose();
            }
        });
        this.configGeneratorDialog.setCancelButtonEnabled(true);

        this.configGeneratorDialog.setChooseExportDirectoryButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int chooserResult = fileChooser.showSaveDialog(configGeneratorDialog);
                if (chooserResult == JFileChooser.APPROVE_OPTION) {
                    configGeneratorDialog.setTextWordFileExportPath(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        this.configGeneratorDialog.setChooseWordTemplateButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileFilter filter = new FileNameExtensionFilter("Word-Vorlage-Datei", "dotx");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int chooserResult = fileChooser.showSaveDialog(configGeneratorDialog);
                if (chooserResult == JFileChooser.APPROVE_OPTION) {
                    configGeneratorDialog.setTextWordTemplateFile(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

    private boolean saveConfigFile(){
        if(configGeneratorDialog.getTextWordFileExportPath().length() > 0 && configGeneratorDialog.getTextWordFileExportPath().length() > 0) {
/*            JFileChooser fileChooser = new JFileChooser();
            File saveFile = null;

            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int chooserResult = fileChooser.showSaveDialog(configGeneratorDialog);

            if (chooserResult == JFileChooser.APPROVE_OPTION) {*/
            File saveFile = null;
            try {
                saveFile = new File(Publisher.getModel().getJarContainingFolder(com.sabel.JRechnung.Main.class),Configuration.CONFIG_FILE_NAME);
            } catch (Exception e) {
                Message.showErrorMessage("Konfigurationsdatei konnte nicht gespeichert werden.");
                return false;
            }


            if(!Publisher.getModel().saveXMLConfigFile(saveFile,configGeneratorDialog.getTextWordFileExportPath(),configGeneratorDialog.getTextWordTemplateFile())){
                Message.showErrorMessage("Konfigurationsdatei konnte nicht gespeichert werden.");
                return false;
            }else {
                return true;
            }

/*            }else{
                return false;

            }*/
        }else{
            Message.showErrorMessage("Sie haben nicht alle Konfigurationen durchgeführt!");
            return false;
        }

    }
}
