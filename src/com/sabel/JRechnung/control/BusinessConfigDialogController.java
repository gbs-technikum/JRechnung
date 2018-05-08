package com.sabel.JRechnung.control;

import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.db.BusinessService;
import com.sabel.JRechnung.model.db.LegalFormService;
import com.sabel.JRechnung.model.objects.Business;
import com.sabel.JRechnung.model.objects.Customer;
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

        if(business != null){
            this.businessConfigDialog.setTextName(business.getName());
            this.businessConfigDialog.setTextProprietor(business.getProprietor());
            this.businessConfigDialog.setTextStreet(business.getStreet());
            this.businessConfigDialog.setTextStreetNumber(business.getStreetNumber());
            this.businessConfigDialog.setTextPostcode(business.getPostcode());
            this.businessConfigDialog.setTextLocation(business.getLocation());
            this.businessConfigDialog.setTextTaxNumber(business.getTaxNumber());
            this.businessConfigDialog.setTextJurisdiction(business.getJurisdiction());
            this.businessConfigDialog.setTextPhone(business.getPhone());
            this.businessConfigDialog.setTextFax(business.getFax());
            this.businessConfigDialog.setTextEmail(business.getEmail());

            int index = fillLegalFormComboBox(null,business.getLegalForm());
            this.businessConfigDialog.setIndexOfSelectedLegalForm(index);
        }
    }

    private int fillLegalFormComboBox(List<LegalForm> legalForms, LegalForm selectedLegalform){
        int selectedIndex = 0;

        if(legalForms == null){
            try {
                legalForms = LegalFormService.readAllLegalForms();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < legalForms.size(); i++) {
            LegalForm legalForm = legalForms.get(i);
            this.businessConfigDialog.addToLegalFormList(legalForm);
            if (selectedLegalform != null && selectedLegalform.equals(legalForm)) {
                selectedIndex = i;
            }
        }

        return selectedIndex;
    }


    private void createBussinesFromWindowData(){
        String id = null;

        if(this.business != null){
            id = this.business.getId();
        }else {
            id = Publisher.getModel().getNewObjectId();
        }


        LegalForm legalForm = this.businessConfigDialog.getLegalFormFromList(this.businessConfigDialog.getIndexOfSelectedLegalForm());

        Business business = new Business(id,this.businessConfigDialog.getTextName(),
                this.businessConfigDialog.getTextProprietor(),
                this.businessConfigDialog.getTextStreet(),
                this.businessConfigDialog.getTextStreetNumber(),
                this.businessConfigDialog.getTextPostcode(),
                this.businessConfigDialog.getTextLocation(),
                this.businessConfigDialog.getTextTaxNumber(),
                this.businessConfigDialog.getTextJurisdiction(),
                this.businessConfigDialog.getTextPhone(),
                this.businessConfigDialog.getTextFax(),
                this.businessConfigDialog.getTextEmail(),
                legalForm);

        System.out.println(legalForm);

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
