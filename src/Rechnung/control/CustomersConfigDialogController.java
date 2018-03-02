package Rechnung.control;

import Rechnung.Publisher;
import Rechnung.model.*;
import Rechnung.view.BusinessConfigDialog;
import Rechnung.view.CustomersConfigDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomersConfigDialogController implements Controller {
    private CustomersConfigDialog customersConfigDialog;
    private ControllerReturnStatus controllerReturnStatus;

    public CustomersConfigDialogController(JFrame window) {
        this.customersConfigDialog = new CustomersConfigDialog(window);
        this.fillWindowComponents();
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.OK;
    }

    @Override
    public ControllerReturnStatus run() {
        this.customersConfigDialog.setVisible(true);
        this.waitForWindowToClose(this.customersConfigDialog);
        this.customersConfigDialog = null;
        return controllerReturnStatus;
    }

    private void initEvents() {
        this.customersConfigDialog.setOkayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.OK;
                saveComponentData();
                customersConfigDialog.setVisible(false);
                customersConfigDialog.dispose();
            }
        });
        this.customersConfigDialog.setOkayButtonEnabled(true);

        this.customersConfigDialog.setApplyButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveComponentData();
            }
        });
        this.customersConfigDialog.setApplyButtonEnabled(true);

        this.customersConfigDialog.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.ABORT;
                customersConfigDialog.setVisible(false);
                customersConfigDialog.dispose();
            }
        });
        this.customersConfigDialog.setCancelButtonEnabled(true);



        this.customersConfigDialog.setDeleteButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer customer = createCustomerFromWindowData();
                Publisher.getModel().removeCustomer(customer);
            }
        });
        this.customersConfigDialog.setDeleteButtonEnabled(true);



    }


    private void fillWindowComponents() {

    }

    private void saveComponentData(){
    }

    private Customer createCustomerFromWindowData(){
        Customer customer = null;

        String number = this.customersConfigDialog.getNumberTextField();
        String forenname = this.customersConfigDialog.getForennameTextField();
        String name = this.customersConfigDialog.getNameTextField();
        String street = this.customersConfigDialog.getStreetTextField();
        String houseNumber = this.customersConfigDialog.getHouseNumberTextField();
        String postCode = this.customersConfigDialog.getPostCodeTextField();
        String village = this.customersConfigDialog.getVillageTextField();
        String land = this.customersConfigDialog.getLandTextField();

        customer = new Customer("",number,name,forenname,street,houseNumber,postCode,village,land);

        List<String> mailList = this.customersConfigDialog.getEMailAcessibilityStringList();

        for (String entry : mailList){
            EMailAccessibility accessibility = new EMailAccessibility("",entry);
            customer.addEMail(accessibility);
        }

        List<String> phoneList = this.customersConfigDialog.getPhoneAcessibilityStringList();

        for (String entry : phoneList){
            TelephoneAccessibility accessibility = new TelephoneAccessibility("",entry);
            customer.addPhoneNumber(accessibility);
        }

        List<String> faxList = this.customersConfigDialog.getFaxAcessibilityStringList();

        for (String entry : faxList){
            FaxAccessibility accessibility = new FaxAccessibility("",entry);
            customer.addFaxNumber(accessibility);
        }

        return customer;
    }
}