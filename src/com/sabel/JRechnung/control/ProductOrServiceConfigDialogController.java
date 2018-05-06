package com.sabel.JRechnung.control;
import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.objects.ProductOrService;
import com.sabel.JRechnung.view.ProductOrServiceConfigDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ProductOrServiceConfigDialogController implements Controller {
    private ProductOrServiceConfigDialog productOrServiceConfigDialog;
    private ControllerReturnStatus controllerReturnStatus;
    private ProductOrService productOrService;

    public ProductOrServiceConfigDialogController(JFrame window) {
        this.productOrServiceConfigDialog = new ProductOrServiceConfigDialog(window);
        this.productOrService = null;
        this.fillWindowComponents(false);
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.OK;
    }

    private int fillComboBox(List<ProductOrService> productsOrServices, ProductOrService selectedProductOrService){
        int selectedIndex = 0;

        if(productsOrServices == null){
            productsOrServices = Publisher.getModel().readProductsOrServices();
        }

        for (int i = 0; i < productsOrServices.size(); i++) {
            ProductOrService currentProductOrService = productsOrServices.get(i);
            this.productOrServiceConfigDialog.addToProductOrServiceList(currentProductOrService);
            if (selectedProductOrService != null && selectedProductOrService.equals(currentProductOrService)) {
                selectedIndex = i;
                this.productOrService = currentProductOrService;
            }
        }

        return selectedIndex;
    }

    private void fillWindowComponents(boolean refill) {
        List<ProductOrService> productsOrServices = Publisher.getModel().readProductsOrServices();
        ProductOrService selectedProductOrService = null;

        if(refill){
            int index = this.productOrServiceConfigDialog.getIndexOfSelectedProductOrService();
            selectedProductOrService = this.productOrServiceConfigDialog.getProductOrServiceFromList(index);
        }else{

            if(productsOrServices.size() > 0){
                selectedProductOrService = productsOrServices.get(0);
            }
        }

        this.productOrServiceConfigDialog.clearComponentData();

        if(selectedProductOrService != null) {
            this.productOrServiceConfigDialog.setTitleTextField(selectedProductOrService.getTitle());
            this.productOrServiceConfigDialog.setPriceTextField(String.valueOf(selectedProductOrService.getPrice()));
            this.productOrServiceConfigDialog.setDescriptionTextField(selectedProductOrService.getDescription());

            if(this.productOrService == null){
                this.productOrService = selectedProductOrService;
            }

            int selectedIndex = 0;

            for (int i = 0; i < productsOrServices.size(); i++) {
                ProductOrService currentProductOrService = productsOrServices.get(i);
                this.productOrServiceConfigDialog.addToProductOrServiceList(currentProductOrService);
                if (selectedProductOrService.equals(currentProductOrService)) {
                    selectedIndex = i;
                    this.productOrService = currentProductOrService;
                }
            }

            this.productOrServiceConfigDialog.setIndexOfSelectedProductOrService(selectedIndex);
        }
    }

    @Override
    public ControllerReturnStatus run() {
        this.productOrServiceConfigDialog.setVisible(true);
        this.waitForWindowToClose(this.productOrServiceConfigDialog);
        this.productOrServiceConfigDialog = null;
        return controllerReturnStatus;
    }

    private void initEvents() {

        this.productOrServiceConfigDialog.setOkayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.OK;

                productOrServiceConfigDialog.setVisible(false);
                productOrServiceConfigDialog.dispose();
            }
        });
        this.productOrServiceConfigDialog.setOkayButtonEnabled(true);

        this.productOrServiceConfigDialog.setApplyButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveComponentData();
                fillWindowComponents(false);
            }
        });
        this.productOrServiceConfigDialog.setApplyButtonEnabled(true);

        this.productOrServiceConfigDialog.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.ABORT;
                productOrServiceConfigDialog.setVisible(false);
                productOrServiceConfigDialog.dispose();
            }
        });
        this.productOrServiceConfigDialog.setCancelButtonEnabled(true);

        this.productOrServiceConfigDialog.setNewButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productOrServiceConfigDialog.clearComponentData();
                productOrService = null;

                ActionListener actionListener = productOrServiceConfigDialog.getProductOrServiceComboBoxListener();
                productOrServiceConfigDialog.removeProductOrServiceComboBoxListener();
                fillComboBox(null,null);
                productOrServiceConfigDialog.setProductOrServiceComboBoxListener(actionListener);
            }
        });
        this.productOrServiceConfigDialog.setNewButtonEnabled(true);

        this.productOrServiceConfigDialog.setDeleteButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  createCustomerFromWindowData();
                if(productOrServiceConfigDialog.getIndexOfSelectedProductOrService() >= 0) {
                    Publisher.getModel().removeProductOrService(productOrService);
                    fillWindowComponents(false);
                }
            }
        });
        this.productOrServiceConfigDialog.setDeleteButtonEnabled(true);

        this.productOrServiceConfigDialog.setProductOrServiceComboBoxListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillWindowComponents(true);
            }
        });
    }

    private void saveComponentData() {
        createProductOrServiceFromWindowData();
        Publisher.getModel().saveProductOrService(this.productOrService);
    }

    private void createProductOrServiceFromWindowData(){

        ProductOrService productOrService = null;

        String id = null;
        System.out.println(this.productOrService);
        if(this.productOrService != null){
            id = this.productOrService.getId();
            System.out.println("alte id");
        }else {
            id = Publisher.getModel().getNewObjectId();
            System.out.println("neue id");
        }

        String title = this.productOrServiceConfigDialog.getTitleTextField();
        //TODO
        String priceText = this.productOrServiceConfigDialog.getPriceTextField();
        priceText = priceText.replace(',','.');
        double price = Double.parseDouble(priceText);
        String description = this.productOrServiceConfigDialog.getDescriptionTextField();

        productOrService = new ProductOrService(id,title,price,description);

        this.productOrService = productOrService;
    }
}