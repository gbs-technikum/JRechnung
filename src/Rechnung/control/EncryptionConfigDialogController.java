package Rechnung.control;

import Rechnung.Publisher;
import Rechnung.model.SecurityProvider;
import Rechnung.view.EncryptionConfigDialog;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.encryptionConfigDialog = new EncryptionConfigDialog(window);
        this.encryptionConfigDialog.setSecondPasswordFieldVisible(true);
        this.encryptionConfigDialog.setPreviousPasswordFieldVisible(reinit);
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.ABORT;
    }

    public EncryptionConfigDialogController(JFrame window) {
        this.reinitMode = false;
        this.passwordCheckOnly = true;
        this.encryptionConfigDialog = new EncryptionConfigDialog(window);
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
                return (Publisher.getModel().isPasswordValid(password) && sp.unlock(password));
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
                if(Publisher.getModel().isPasswordValid(password) && password.equals(password2)){
                    try {
                        return sp.firstInit(password);
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
                        if(Publisher.getModel().isPasswordValid(password) && password.equals(password2)){
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
