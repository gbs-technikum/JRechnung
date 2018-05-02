package Rechnung.control;

import Rechnung.Publisher;
import Rechnung.model.objects.Bill;
import Rechnung.view.WordFileExportDailog;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WordFileExportController implements Controller {

    private WordFileExportDailog wordFileExportDailog;
    private ControllerReturnStatus controllerReturnStatus;
    private Bill bill;

    public WordFileExportController(JFrame window, Bill bill) throws HeadlessException {
        this.bill = bill;
        this.wordFileExportDailog = new WordFileExportDailog(window);
        this.controllerReturnStatus = ControllerReturnStatus.OK;
    }

    @Override
    public ControllerReturnStatus run() {
        this.wordFileExportDailog.setVisible(true);
        this.generateWordFile();
        return controllerReturnStatus;
    }



    private void generateWordFile() {
        File wordFile = Publisher.getModel().changeFileName(new File(Publisher.getModel().getWordExportPath().getPath() + bill.getTitel()));

        //Blank Document
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(wordFile);
            document.write(out);
            out.close();
            System.out.println("createdocument.docx written successully");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
