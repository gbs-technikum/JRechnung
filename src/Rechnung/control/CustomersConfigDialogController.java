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
    private Customer customer;

    public CustomersConfigDialogController(JFrame window) {
        this.customersConfigDialog = new CustomersConfigDialog(window);
        this.fillWindowComponents();
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.OK;
        this.customer = null;
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
                createCustomerFromWindowData();
                Publisher.getModel().removeCustomer(customer);
            }
        });
        this.customersConfigDialog.setDeleteButtonEnabled(true);

        this.customersConfigDialog.setFaxAddButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = customersConfigDialog.getFaxInputText();

                if(Publisher.getModel().isFaxOrPhoneNumberValid(number) &&
                   !Publisher.getModel().isStringInList(customersConfigDialog.getFaxAcessibilityStringList(),number.trim())){
                    customersConfigDialog.addToFaxList(number.trim());
                }

            }
        });

        this.customersConfigDialog.setFaxDeleteButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = customersConfigDialog.getIndexOfSelectedFax();
                customersConfigDialog.removeFaxFromList(index);
            }
        });

        this.customersConfigDialog.setFaxAddButtonEnabled(true);
        this.customersConfigDialog.setFaxDeleteButtonEnabled(true);

        this.customersConfigDialog.setPhoneAddButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = customersConfigDialog.getPhoneInputText();

                if(Publisher.getModel().isFaxOrPhoneNumberValid(number) &&
                        !Publisher.getModel().isStringInList(customersConfigDialog.getPhoneAcessibilityStringList(),number.trim())){
                    customersConfigDialog.addToPhoneList(number.trim());
                }

            }
        });

        this.customersConfigDialog.setPhoneDeleteButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = customersConfigDialog.getIndexOfSelectedPhone();
                customersConfigDialog.removePhoneFromList(index);
            }
        });

        this.customersConfigDialog.setPhoneAddButtonEnabled(true);
        this.customersConfigDialog.setPhoneDeleteButtonEnabled(true);

        this.customersConfigDialog.setEMailAddButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mail = customersConfigDialog.getEMailInputText();

                if(Publisher.getModel().isValidEmailAddress(mail) &&
                        !Publisher.getModel().isStringInList(customersConfigDialog.getEMailAcessibilityStringList(),mail.trim())){
                    customersConfigDialog.addToEMailList(mail.trim());
                }

            }
        });

        this.customersConfigDialog.setEMailDeleteButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = customersConfigDialog.getIndexOfSelectedEMail();
                customersConfigDialog.removeEMailFromList(index);
            }
        });

        this.customersConfigDialog.setEMailAddButtonEnabled(true);
        this.customersConfigDialog.setEMailDeleteButtonEnabled(true);

    }

    private void saveComponentData() {
        createCustomerFromWindowData();
        Publisher.getModel().saveCustomer(this.customer);
    }


    private void fillWindowComponents() {
        this.customersConfigDialog.setForenameTextFieldEnabled(true);
        this.customersConfigDialog.setNameTextFieldEnabled(true);
        this.customersConfigDialog.setStreetTextFieldEnabled(true);
        this.customersConfigDialog.setHouseNumberTextFieldEnabled(true);
        this.customersConfigDialog.setVillageTextFieldEnabled(true);
        this.customersConfigDialog.setLandTextFieldEnabled(true);
        this.customersConfigDialog.setForenameTextFieldEnabled(true);
        this.customersConfigDialog.setPostCodeetJTextFieldEnabled(true);

        List<Customer> customers = Publisher.getModel().readCustomers();

        if(customers.size() > 0){
            this.customer = customers.get(0);

            this.customersConfigDialog.setNumberTextField(customer.getNumber());
            this.customersConfigDialog.setNameTextField(customer.getName());
            this.customersConfigDialog.setForennameTextField(customer.getForename());
            this.customersConfigDialog.setStreetTextField(customer.getStreet());
            this.customersConfigDialog.setHouseNumberTextField(customer.getHouseNumber());
            this.customersConfigDialog.setPostCodeTextField(customer.getPostCode());
            this.customersConfigDialog.setVillageTextField(customer.getVillage());
            this.customersConfigDialog.setLandTextField(customer.getLand());

            for (Accessibility accessibility : customer.getMailAddresses()) {
                this.customersConfigDialog.addToEMailList(accessibility.getEntry());
            }

            for (Accessibility accessibility : customer.getFaxNumbers()) {
                this.customersConfigDialog.addToFaxList(accessibility.getEntry());
            }

            for (Accessibility accessibility : customer.getPhoneNumbers()) {
                this.customersConfigDialog.addToPhoneList(accessibility.getEntry());
            }
        }


    }

    private void createCustomerFromWindowData(){

        Customer customer = null;

        String id = null;
        if(this.customer != null){
            id = this.customer.getId();
        }else {
            id = Publisher.getModel().getNewObjectId();
        }
        String number = this.customersConfigDialog.getNumberTextField();
        String forenname = this.customersConfigDialog.getForennameTextField();
        String name = this.customersConfigDialog.getNameTextField();
        String street = this.customersConfigDialog.getStreetTextField();
        String houseNumber = this.customersConfigDialog.getHouseNumberTextField();
        String postCode = this.customersConfigDialog.getPostCodeTextField();
        String village = this.customersConfigDialog.getVillageTextField();
        String land = this.customersConfigDialog.getLandTextField();

        customer = new Customer(id,number,name,forenname,street,houseNumber,postCode,village,land);

        List<String> mailList = this.customersConfigDialog.getEMailAcessibilityStringList();

        for (String entry : mailList){
            EMailAccessibility accessibility = new EMailAccessibility(Publisher.getModel().getNewObjectId(),entry);
            customer.addEMail(accessibility);
        }

        List<String> phoneList = this.customersConfigDialog.getPhoneAcessibilityStringList();

        for (String entry : phoneList){
            TelephoneAccessibility accessibility = new TelephoneAccessibility(Publisher.getModel().getNewObjectId(),entry);
            customer.addPhoneNumber(accessibility);
        }

        List<String> faxList = this.customersConfigDialog.getFaxAcessibilityStringList();

        for (String entry : faxList){
            FaxAccessibility accessibility = new FaxAccessibility(Publisher.getModel().getNewObjectId(),entry);
            customer.addFaxNumber(accessibility);
        }

        this.customer = customer;
    }
}