package Rechnung.control;

import Rechnung.JRechungConfigGenerator;
import Rechnung.Publisher;
import Rechnung.model.Configuration;
import Rechnung.model.SecurityProvider;
import Rechnung.view.EncryptionConfigDialog;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
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

    public EncryptionConfigDialogController(JFrame window,boolean reinit) {
        this.reinitMode = reinit;
        this.passwordCheckOnly = false;
        this.encryptionConfigDialog = new EncryptionConfigDialog(window, "Passwort setzen");
        this.encryptionConfigDialog.setSecondPasswordFieldVisible(true);
        this.encryptionConfigDialog.setPreviousPasswordFieldVisible(reinit);
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.ABORT;
    }

    public EncryptionConfigDialogController(JFrame window) {
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
                        boolean initOkay = sp.firstInit(password);

                        if(initOkay){
                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = -1;
                            File saveFile = null;
                            do {
                                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                                chooserResult = fileChooser.showSaveDialog(encryptionConfigDialog);
                                if(chooserResult == JFileChooser.APPROVE_OPTION){
                                    saveFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
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
