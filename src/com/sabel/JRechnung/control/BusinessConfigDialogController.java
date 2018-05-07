package com.sabel.JRechnung.control;

import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.db.BusinessService;
import com.sabel.JRechnung.model.db.LegalFormService;
import com.sabel.JRechnung.model.objects.Business;
import com.sabel.JRechnung.model.objects.LegalForm;
import com.sabel.JRechnung.view.BusinessConfigDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessConfigDialogController implements Controller {

    private BusinessConfigDialog businessConfigDialog;
    private ControllerReturnStatus controllerReturnStatus;
    private Business business;

    public BusinessConfigDialogController(JFrame window) {
        this.businessConfigDialog = new BusinessConfigDialog(window);
        this.fillWindowComponents();
        this.initEvents();
        this.business = null;
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
                createBussinesFromWindowData();
                saveComponentData();
                businessConfigDialog.setVisible(false);
                businessConfigDialog.dispose();
            }
        });
        this.businessConfigDialog.setOkayButtonEnabled(true);

        this.businessConfigDialog.setApplyButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBussinesFromWindowData();
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
            this.businessConfigDialog.setTextLocation(business.getLocation());

            LegalForm legalForm = business.getLegalForm();

            if(legalForm != null){
                this.businessConfigDialog.selectLegalform(this.businessConfigDialog.getIndexByLegalFormText(legalForm.getName()));
            }

        }
    }

    private void createBussinesFromWindowData(){
        String id = null;

        if(this.business != null){
            id = this.business.getId();
        }else {
            id = Publisher.getModel().getNewObjectId();
        }


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

        Business business = new Business(id,this.businessConfigDialog.getTextName(),
                this.businessConfigDialog.getTextProprietor(),
                this.businessConfigDialog.getTextStreet(),
                this.businessConfigDialog.getTextStreetNumber(),
                this.businessConfigDialog.getTextPostcode(),
                this.businessConfigDialog.getTextLocation(),legalForm);

        this.business = business;

    }


    private void saveComponentData(){
        try {
            BusinessService.writeBusiness(this.business);
        } catch (SQLException e) {
            //TODO
            e.printStackTrace();
        }
    }

}
