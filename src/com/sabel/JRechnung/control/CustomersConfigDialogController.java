package com.sabel.JRechnung.control;

import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.Message;
import com.sabel.JRechnung.model.Model;
import com.sabel.JRechnung.model.objects.*;
import com.sabel.JRechnung.view.CustomersConfigDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomersConfigDialogController implements Controller {
    private CustomersConfigDialog customersConfigDialog;
    private ControllerReturnStatus controllerReturnStatus;
    private Customer customer;

    public CustomersConfigDialogController(JFrame window) {
        this.customersConfigDialog = new CustomersConfigDialog(window);
        this.customer = null;
        this.fillWindowComponents(false);
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
                customersConfigDialog.setVisible(false);
                customersConfigDialog.dispose();
            }
        });
        this.customersConfigDialog.setOkayButtonEnabled(true);

        this.customersConfigDialog.setApplyButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveComponentData();
                fillWindowComponents(false);
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
              //  createCustomerFromWindowData();
                if(!Publisher.getModel().hasCustomerBills(customer)) {
                    if (customersConfigDialog.getIndexOfSelectedCustomer() >= 0) {
                        Publisher.getModel().removeCustomer(customer);
                        fillWindowComponents(false);
                    }
                }else {
                    Message.showErrorMessage("Dieser Kunde ist noch an Rechnungen im Programm gebunden. Löschen Sie bitte erst die entsprechenden Rechnungen oder Ändern Sie deren Kunden.");
                }
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

        this.customersConfigDialog.setCustomerComboBoxListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               fillWindowComponents(true);
            }
        });

        this.customersConfigDialog.setNewButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customersConfigDialog.clearComponentData();
                customer = null;
                customersConfigDialog.setNumberTextField(Publisher.getModel().generateCustomerNumber());
                ActionListener actionListener = customersConfigDialog.getCustomerComboBoxListener();
                customersConfigDialog.removeCustomerComboBoxListener();
                fillComboBox(null,null);
                customersConfigDialog.setCustomerComboBoxListener(actionListener);
            }
        });
        this.customersConfigDialog.setNewButtonEnabled(true);
    }

    private void saveComponentData() {
        createCustomerFromWindowData();
        Publisher.getModel().saveCustomer(this.customer);
    }


    private void fillWindowComponents(boolean refill) {
        List<Customer> customers = Publisher.getModel().readCustomers();
        Customer selectedCustomer = null;

        if(refill){
            int index = this.customersConfigDialog.getIndexOfSelectedCustomer();
            selectedCustomer = this.customersConfigDialog.getCustomerFromList(index);
        }else{
            this.customersConfigDialog.setForenameTextFieldEnabled(true);
            this.customersConfigDialog.setNameTextFieldEnabled(true);
            this.customersConfigDialog.setStreetTextFieldEnabled(true);
            this.customersConfigDialog.setHouseNumberTextFieldEnabled(true);
            this.customersConfigDialog.setVillageTextFieldEnabled(true);
            this.customersConfigDialog.setLandTextFieldEnabled(true);
            this.customersConfigDialog.setForenameTextFieldEnabled(true);
            this.customersConfigDialog.setPostCodeetJTextFieldEnabled(true);
            this.customersConfigDialog.setNumberTextFieldEnabled(true);

            if(customers.size() > 0){
                selectedCustomer = customers.get(0);
            }
        }

        this.customersConfigDialog.clearComponentData();

        if(selectedCustomer != null) {
            this.customersConfigDialog.setNumberTextField(selectedCustomer.getNumber());
            this.customersConfigDialog.setNameTextField(selectedCustomer.getName());
            this.customersConfigDialog.setForennameTextField(selectedCustomer.getForename());
            this.customersConfigDialog.setStreetTextField(selectedCustomer.getStreet());
            this.customersConfigDialog.setHouseNumberTextField(selectedCustomer.getHouseNumber());
            this.customersConfigDialog.setPostCodeTextField(selectedCustomer.getPostCode());
            this.customersConfigDialog.setVillageTextField(selectedCustomer.getVillage());
            this.customersConfigDialog.setLandTextField(selectedCustomer.getLand());

            for (Accessibility accessibility : selectedCustomer.getMailAddresses()) {
                this.customersConfigDialog.addToEMailList(accessibility.getEntry());
            }

            for (Accessibility accessibility : selectedCustomer.getFaxNumbers()) {
                this.customersConfigDialog.addToFaxList(accessibility.getEntry());
            }

            for (Accessibility accessibility : selectedCustomer.getPhoneNumbers()) {
                this.customersConfigDialog.addToPhoneList(accessibility.getEntry());
            }

            if(this.customer == null){
                this.customer = selectedCustomer;
            }

            int selectedIndex = fillComboBox(customers,selectedCustomer);

            this.customersConfigDialog.setIndexOfSelectedCustomer(selectedIndex);
        }else {
            this.customersConfigDialog.setNumberTextField(Publisher.getModel().generateCustomerNumber());
        }
    }

    private int fillComboBox(List<Customer> customers, Customer selectedCustomer){
        int selectedIndex = 0;

        if(customers == null){
            customers = Publisher.getModel().readCustomers();
        }

        for (int i = 0; i < customers.size(); i++) {
            Customer currentCustomer = customers.get(i);
            this.customersConfigDialog.addToCustomerList(currentCustomer);
            if (selectedCustomer != null && selectedCustomer.equals(currentCustomer)) {
                selectedIndex = i;
                this.customer = currentCustomer;
            }
        }

        return selectedIndex;
    }

    private void createCustomerFromWindowData(){

        Model model = Publisher.getModel();
        boolean isNameValid = false;

        if(customersConfigDialog.getNameTextField().length() > 2){
            isNameValid = true;
        }else{
            Message.showErrorMessage("Der Name muss mindestens 3 Zeichen enthalten.");
        }

        boolean isForenameValid = true;
        if(customersConfigDialog.getForennameTextField().length() > 0){
            isForenameValid = model.isNameOrForenameOrStreetOrVillageValid("Vorname", customersConfigDialog.getForennameTextField());
        }
        boolean isStreetValid = model.isNameOrForenameOrStreetOrVillageValid("Straße", customersConfigDialog.getStreetTextField());
        boolean isHouseNumberValid = model.isHouseNumberValid(customersConfigDialog.getHouseNumberTextField());
        boolean isPostCodeValid = model.isPostCodeValid(customersConfigDialog.getPostCodeTextField());
        boolean isVillageValid = model.isNameOrForenameOrStreetOrVillageValid("Ort", customersConfigDialog.getVillageTextField());
        boolean isLandValid = model.isLandValid(customersConfigDialog.getLandTextField());

        if(isNameValid && isForenameValid && isStreetValid && isHouseNumberValid && isPostCodeValid && isVillageValid && isLandValid) {

            Customer customer = null;

            String id = null;

            if (this.customer != null) {
                id = this.customer.getId();
            } else {
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

            customer = new Customer(id, number, name, forenname, street, houseNumber, postCode, village, land);

            List<String> mailList = this.customersConfigDialog.getEMailAcessibilityStringList();

            for (String entry : mailList) {
                EMailAccessibility accessibility = new EMailAccessibility(Publisher.getModel().getNewObjectId(), entry);
                customer.addEMail(accessibility);
            }

            List<String> phoneList = this.customersConfigDialog.getPhoneAcessibilityStringList();

            for (String entry : phoneList) {
                TelephoneAccessibility accessibility = new TelephoneAccessibility(Publisher.getModel().getNewObjectId(), entry);
                customer.addPhoneNumber(accessibility);
            }

            List<String> faxList = this.customersConfigDialog.getFaxAcessibilityStringList();

            for (String entry : faxList) {
                FaxAccessibility accessibility = new FaxAccessibility(Publisher.getModel().getNewObjectId(), entry);
                customer.addFaxNumber(accessibility);
            }

            this.customer = customer;
        }
    }
}