package Rechnung.control;

import Rechnung.Publisher;
import Rechnung.model.*;
import Rechnung.view.BusinessConfigDialog;
import Rechnung.view.ConfigDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessConfigDialogController implements Controller {

    private BusinessConfigDialog businessConfigDialog;
    private ControllerReturnStatus controllerReturnStatus;

    public BusinessConfigDialogController(JFrame window) {
        this.businessConfigDialog = new BusinessConfigDialog(window);
        this.fillWindowComponents();
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.OK;
    }

    @Override
    public ControllerReturnStatus run() {
        this.businessConfigDialog.setVisible(true);
        this.waitForWindowToClose(this.businessConfigDialog);
        this.businessConfigDialog = null;
        return controllerReturnStatus;
    }

    private void initEvents() {
        this.businessConfigDialog.setOkayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.OK;
                saveComponentData();
                businessConfigDialog.setVisible(false);
                businessConfigDialog.dispose();
            }
        });
        this.businessConfigDialog.setOkayButtonEnabled(true);

        this.businessConfigDialog.setApplyButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveComponentData();
            }
        });
        this.businessConfigDialog.setApplyButtonEnabled(true);

        this.businessConfigDialog.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.ABORT;
                businessConfigDialog.setVisible(false);
                businessConfigDialog.dispose();
            }
        });
        this.businessConfigDialog.setCancelButtonEnabled(true);
    }


    private void fillWindowComponents(){
        Business business = Publisher.getModel().getBusiness();

        List<String> legalFormNames = new ArrayList<>();

        try {
            for (LegalForm legalForm : LegalFormService.readAllLegalForms()) {
                legalFormNames.add(legalForm.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.businessConfigDialog.setLegalform(legalFormNames);

        if(business != null){
            this.businessConfigDialog.setTextName(business.getName());
            this.businessConfigDialog.setTextProprietor(business.getProprietor());
            this.businessConfigDialog.setTextStreet(business.getStreet());
            this.businessConfigDialog.setTextStreetNumber(business.getStreetNumber());
            this.businessConfigDialog.setTextPostcode(business.getPostcode());

            LegalForm legalForm = business.getLegalForm();

            if(legalForm != null){
                this.businessConfigDialog.selectLegalform(this.businessConfigDialog.getIndexByLegalFormText(legalForm.getName()));
            }

        }
    }

    private void saveComponentData(){

        String legalFormText =   this.businessConfigDialog.getSelectedLegalFormText();
        LegalForm legalForm = null;

        try {
            for (LegalForm tmpLegalForm : LegalFormService.readAllLegalForms()) {
                if(tmpLegalForm.getName().equals(legalFormText))
                {
                    legalForm = tmpLegalForm;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Business business = new Business("",this.businessConfigDialog.getTextName(),
                            this.businessConfigDialog.getTextProprietor(),
                            this.businessConfigDialog.getTextStreet(),
                            this.businessConfigDialog.getTextStreetNumber(),
                            this.businessConfigDialog.getTextPostcode(),legalForm);

        try {
            BusinessService.writeBusiness(business);
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }
    }

}
