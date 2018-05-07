package com.sabel.JRechnung.control;

import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.SecurityProvider;
import com.sabel.JRechnung.view.EncryptionConfigDialog;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class EncryptionConfigDialogController implements Controller {

    private EncryptionConfigDialog encryptionConfigDialog ;
    private ControllerReturnStatus controllerReturnStatus;
    private boolean reinitMode;
    private boolean passwordCheckOnly;
    private String encryptionKey;

    public EncryptionConfigDialogController(JFrame window, String encryptionKey) {
        this(window,false);
        this.encryptionKey = encryptionKey;
    }

    public EncryptionConfigDialogController(JFrame window,boolean reinit) {
        this.reinitMode = reinit;
        this.encryptionKey = null;
        this.passwordCheckOnly = false;
        this.encryptionConfigDialog = new EncryptionConfigDialog(window, "Passwort setzen");
        this.encryptionConfigDialog.setSecondPasswordFieldVisible(true);
        this.encryptionConfigDialog.setPreviousPasswordFieldVisible(reinit);
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.ABORT;
    }

    public EncryptionConfigDialogController(JFrame window) {
        this.encryptionKey = null;
        this.reinitMode = false;
        this.passwordCheckOnly = true;
        this.encryptionConfigDialog = new EncryptionConfigDialog(window, "Passwort eingeben");
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.ABORT;
    }

    @Override
    public ControllerReturnStatus run() {
        this.encryptionConfigDialog.setVisible(true);
        this.waitForWindowToClose(this.encryptionConfigDialog);
        this.encryptionConfigDialog = null;
        return controllerReturnStatus;
    }

    private void initEvents() {

        ActionListener submitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = (validateComponentData()) ?  ControllerReturnStatus.OK : ControllerReturnStatus.ERROR;
                encryptionConfigDialog.setVisible(false);
                encryptionConfigDialog.dispose();
            }
        };

        this.encryptionConfigDialog.setOkayButtonListener(submitListener);
        this.encryptionConfigDialog.setOkayButtonEnabled(true);
        this.encryptionConfigDialog.setFirstPasswordFieldListener(submitListener);

        this.encryptionConfigDialog.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.ABORT;
                encryptionConfigDialog.setVisible(false);
                encryptionConfigDialog.dispose();
            }
        });
        this.encryptionConfigDialog.setCancelButtonEnabled(true);
    }

    private boolean validateComponentData(){
        String password = this.encryptionConfigDialog.getFirstPassword();

        SecurityProvider sp = Publisher.getSecurityProvider();

        if(this.passwordCheckOnly){
            try {
                return (sp.unlock(password));
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else
        {
            String password2 = this.encryptionConfigDialog.getSecondPassword();
            if(!this.reinitMode){
                if(Publisher.getModel().isPasswordValid(password) && Publisher.getModel().isPasswordEqualsPassword2(password, password2)){
                    try {
                        sp = Publisher.getNewSecurityProvider();
                        boolean initOkay = false;

                        if(this.encryptionKey != null){
                            try {
                                initOkay = sp.reset(password,this.encryptionKey);
                            } catch (NoSuchPaddingException e) {

                            }
                        }else{
                            try {
                                initOkay = sp.firstInit(password);
                            } catch (NoSuchPaddingException e) {
                                initOkay = false;
                            }
                        }

                        if(initOkay && this.encryptionKey == null){
                            FileFilter filter = new FileNameExtensionFilter("Schlüsseldatei", "key");
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setFileFilter(filter);
                            int chooserResult = -1;
                            File saveFile = null;
                            do {
                                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                                chooserResult = fileChooser.showSaveDialog(encryptionConfigDialog);
                                if(chooserResult == JFileChooser.APPROVE_OPTION){
                                    System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
                                    saveFile = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".key");
                                }
                            }while(!Publisher.getModel().writeSecretKeyFile(saveFile));
                        }

                        return initOkay;

                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                String oldPassword = this.encryptionConfigDialog.getPreviousPassword();
                SecurityProvider tmpSecurityProvider = new SecurityProvider();
                try {
                    boolean isLocked = !tmpSecurityProvider.unlock(oldPassword);
                    tmpSecurityProvider = null;
                    if(!isLocked){
                        if(Publisher.getModel().isPasswordValid(password) && Publisher.getModel().isPasswordEqualsPassword2(password, password2)){
                            sp.reInit(password);
                        }
                    }
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }



}
