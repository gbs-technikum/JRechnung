package Rechnung.control;

import Rechnung.Publisher;
import Rechnung.model.db.BusinessService;
import Rechnung.model.db.LegalFormService;
import Rechnung.model.objects.Business;
import Rechnung.model.objects.LegalForm;
import Rechnung.model.objects.ProductOrService;
import Rechnung.view.BusinessConfigDialog;
import Rechnung.view.WorkingtimeRecordConfigDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkingtimeRecordConfigDialogController implements Controller {

    private WorkingtimeRecordConfigDialog workingtimeRecordConfigDialog;
    private ControllerReturnStatus controllerReturnStatus;
    private ProductOrService productOrService;

    public WorkingtimeRecordConfigDialogController(JFrame window) {
        this.workingtimeRecordConfigDialog = new WorkingtimeRecordConfigDialog(window);
        this.productOrService = null;
        this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.OK;
    }

    @Override
    public ControllerReturnStatus run() {
        this.workingtimeRecordConfigDialog.setVisible(true);
        this.waitForWindowToClose(this.workingtimeRecordConfigDialog);
        this.workingtimeRecordConfigDialog = null;
        return controllerReturnStatus;
    }

    private void initEvents() {
        this.workingtimeRecordConfigDialog.setOkayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.OK;
                createProductOrServiceFromWindowData();
                saveComponentData();
                workingtimeRecordConfigDialog.setVisible(false);
                workingtimeRecordConfigDialog.dispose();
            }
        });
        this.workingtimeRecordConfigDialog.setOkayButtonEnabled(true);

        this.workingtimeRecordConfigDialog.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.ABORT;
                workingtimeRecordConfigDialog.setVisible(false);
                workingtimeRecordConfigDialog.dispose();
            }
        });
        this.workingtimeRecordConfigDialog.setCancelButtonEnabled(true);
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

        String title = "erfasste Arbeitszeit am";
        String priceText = "";
        priceText = priceText.replace(',','.');
        double price = Double.parseDouble(priceText);
        String description = "";

        productOrService = new ProductOrService(id,title,price,description);

        this.productOrService = productOrService;
    }


    private void saveComponentData(){
        Publisher.getModel().saveProductOrService(productOrService);
    }

}
