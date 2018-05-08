package com.sabel.JRechnung.model;

import com.sabel.JRechnung.Debug;
import com.sabel.JRechnung.Publisher;
import com.sabel.JRechnung.model.objects.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WordFileGenerator {

    public static boolean generate(File template, File destination, Bill bill, Business business){

        if((template != null && !template.exists()) || (destination == null) ||
                bill == null || business == null){
            return false;
        }

        if(destination.exists()){
            destination.delete();
        }

        OPCPackage pkg = null;
        try {

            pkg = OPCPackage.open(template.getAbsolutePath());
            pkg.replaceContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml");
            pkg.save(destination);
        } catch (Exception e) {
            if(Debug.ON){
                System.out.println(e.getMessage());
            }
            return false;
        }

        try {
            XWPFDocument doc = new XWPFDocument(new FileInputStream(destination));

            Customer customer = bill.getDebtor();

            replacePOI(doc,"$$CUSTOMERNAME$$",customer.getForename() + " " + customer.getName());
            replacePOI(doc,"$$CUSTOMERSTREET$$",customer.getStreet() + " " + customer.getHouseNumber());
            replacePOI(doc,"$$CUSTOMERLOCATION$$",customer.getVillage());
            replacePOI(doc,"$$CUSTOMERPOSTCODE$$",customer.getPostCode());


            LegalForm legalForm = business.getLegalForm();

            if(legalForm != null && legalForm.mustToBeAPostfix()){
                replacePOI(doc,"$$COMPANYNAME$$",business.getName() + " " + legalForm.getShortName());
            }else{
                replacePOI(doc,"$$COMPANYNAME$$",business.getName());
            }



            replacePOI(doc,"$$STREET$$",business.getStreet() + " " + business.getStreetNumber());
            replacePOI(doc,"$$LOCATION$$",business.getLocation());
            replacePOI(doc,"$$POSTCODE$$",business.getPostcode());

            replacePOI(doc,"$$TOPAYDATE$$",Publisher.getModel().dateToGermanDateString(bill.getToPayToDate()));

            replacePOI(doc,"$$PROPRIETOR$$",business.getProprietor());

            replacePOI(doc,"$$TAXNUMBER$$",business.getTaxNumber());

            replacePOI(doc,"$$JURISDICTION$$",business.getJurisdiction());

            replacePOI(doc,"$$COMPANYPHONE$$",business.getPhone());
            replacePOI(doc,"$$COMPANYFAX$$",business.getFax());
            replacePOI(doc,"$$COMPANYEMAIL$$",business.getEmail());

            modifyTableBillData(doc,bill);
            modifyTableWithEntries(doc,bill);


            modifyPriceTable(doc,bill);

            doc.write(new FileOutputStream(destination));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    private static void modifyTableWithEntries(XWPFDocument doc,Bill bill){
        try {
            XWPFTable table = doc.getTableArray(1);

            if(bill.isBusinessTaxFree()) {
                XWPFTableRow headerRow = table.getRow(0);
                XWPFTableCell cell = headerRow.getTableCells().get(2);
                XWPFParagraph paragraph = cell.getParagraphs().get(0);
                for (int i = 0; i < paragraph.getRuns().size(); i++) {
                    paragraph.removeRun(i);
                }
            }

            for (int i = bill.getBillEntries().size()-1; i >= 0 ; i--) {
                BillEntry billEntry = bill.getBillEntries().get(i);

                XWPFTableRow oldRow = table.getRow(table.getNumberOfRows() - 1);
                CTRow ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
                XWPFTableRow newRow = new XWPFTableRow(ctrow, table);

                for (int j = 0; j < newRow.getTableCells().size(); j++) {
                    XWPFTableCell cell = newRow.getTableCells().get(j);

                    XWPFParagraph paragraph = cell.getParagraphs().get(0);

                    XWPFRun run = paragraph.createRun();

                    switch (j) {
                        case 0:
                            run.setText(String.valueOf(i));
                            break;
                        case 1:
                            run.setText(billEntry.getEntryText());
                            break;
                        case 2:
                            if(!bill.isBusinessTaxFree()) {
                                run.setText(String.valueOf(bill.getEntryTaxRateInPercent(i)) + "%");
                            }
                            break;
                        case 3:
                            run.setText(String.format(Locale.GERMANY, "%.2f €", bill.getEntryUnitPrice(i)));
                            break;
                        case 4:
                            run.setText(String.valueOf(billEntry.getAmount()));
                            break;
                        case 5:
                            run.setText(String.format(Locale.GERMANY, "%.2f €", bill.getEntryTotalPrice(i)));
                            break;
                    }
                }

                table.addRow(newRow, 1);
            }

        } catch (XmlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void modifyPriceTable(XWPFDocument doc,Bill bill){
        try {
            XWPFTable table = doc.getTableArray(2);

            XWPFTableRow oldRow = table.getRow(0);

            XWPFTableCell cell = oldRow.getTableCells().get(1);
            XWPFParagraph paragraph = cell.getParagraphs().get(0);
            XWPFRun run = paragraph.createRun();

            if(bill.isBusinessTaxFree()) {
                run.setText(String.format(Locale.GERMANY, "%.2f €", bill.getTotalPrice()));
                run.setBold(true);

                cell = oldRow.getTableCells().get(0);
                paragraph = cell.getParagraphs().get(0);
                run =  paragraph.getRuns().get(0);
                run.setText("Zu zahlender Betrag",0);
            }else {

                run.setText(String.format(Locale.GERMANY, "%.2f €", bill.getEntryPriceSum()));
                run.setBold(false);

                for (Integer taxPercentage : bill.getTaxPercentages()) {

                    oldRow = table.getRow(table.getNumberOfRows() - 1);
                    CTRow ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
                    XWPFTableRow newRow = new XWPFTableRow(ctrow, table);

                    double taxValueForPercentage = bill.getTaxValueForPercentage(taxPercentage.intValue());

                    cell = newRow.getTableCells().get(0);
                    paragraph = cell.getParagraphs().get(0);

                    for (int i = 0; i < paragraph.getRuns().size(); i++) {
                        paragraph.removeRun(i);
                    }
                    run = paragraph.createRun();


                    if (bill.mustBeIncludedTaxes()) {
                        run.setText("enthaltene Mehrwersteuer " + taxPercentage.toString() + "%");
                    } else {
                        run.setText("Mehrwersteuer " + taxPercentage.toString() + "%");
                    }


                    cell = newRow.getTableCells().get(1);
                    paragraph = cell.getParagraphs().get(0);

                    for (int i = 0; i < paragraph.getRuns().size(); i++) {
                        paragraph.removeRun(i);
                    }
                    run = paragraph.createRun();
                    run.setText(String.format(Locale.GERMANY, "%.2f €", taxValueForPercentage));

                    table.addRow(newRow, 1);
                }


                oldRow = table.getRow(0);
                CTRow ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
                XWPFTableRow newRow = new XWPFTableRow(ctrow, table);

                cell = newRow.getTableCells().get(0);
                paragraph = cell.getParagraphs().get(0);
                run = paragraph.getRuns().get(0);
                run.setText("Zu zahlender Betrag", 0);

                cell = newRow.getTableCells().get(1);
                paragraph = cell.getParagraphs().get(0);
                for (int i = 0; i < paragraph.getRuns().size(); i++) {
                    paragraph.removeRun(i);
                }
                run = paragraph.createRun();
                run.setText(String.format(Locale.GERMANY, "%.2f €", bill.getTotalPrice()), 0);
                run.setBold(true);

                cell = oldRow.getTableCells().get(0);
                paragraph = cell.getParagraphs().get(0);
                run = paragraph.getRuns().get(0);
                run.setBold(false);

                table.addRow(newRow, table.getNumberOfRows());
            }

        } catch (XmlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void modifyTableBillData(XWPFDocument doc,Bill bill){
        XWPFTable table = doc.getTableArray(0);

        XWPFTableRow oldRow = table.getRow(1);

        XWPFTableCell cell = oldRow.getTableCells().get(0);
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        XWPFRun run = paragraph.createRun();
        run.setText(bill.getNumber());

        oldRow = table.getRow(3);
        cell = oldRow.getTableCells().get(0);
        paragraph = cell.getParagraphs().get(0);
        run = paragraph.createRun();

        run.setText(Publisher.getModel().dateToGermanDateString(bill.getCreationDate()));
    }


    public static XWPFDocument replacePOI(XWPFDocument doc, String placeHolder, String replaceText){
        // REPLACE ALL HEADERS
        for (XWPFHeader header : doc.getHeaderList())
            replaceAllBodyElements(header.getBodyElements(), placeHolder, replaceText);
        // REPLACE BODY
        replaceAllBodyElements(doc.getBodyElements(), placeHolder, replaceText);

        for (XWPFFooter footer : doc.getFooterList())
            replaceAllBodyElements(footer.getBodyElements(), placeHolder, replaceText);
        return doc;
    }

    private static void replaceAllBodyElements(List<IBodyElement> bodyElements, String placeHolder, String replaceText){
        for (IBodyElement bodyElement : bodyElements) {
            if (bodyElement.getElementType().compareTo(BodyElementType.PARAGRAPH) == 0)
                replaceParagraph((XWPFParagraph) bodyElement, placeHolder, replaceText);
            if (bodyElement.getElementType().compareTo(BodyElementType.TABLE) == 0)
                replaceTable((XWPFTable) bodyElement, placeHolder, replaceText);
        }
    }

    private static void replaceTable(XWPFTable table, String placeHolder, String replaceText) {
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                for (IBodyElement bodyElement : cell.getBodyElements()) {
                    if (bodyElement.getElementType().compareTo(BodyElementType.PARAGRAPH) == 0) {
                        replaceParagraph((XWPFParagraph) bodyElement, placeHolder, replaceText);
                    }
                    if (bodyElement.getElementType().compareTo(BodyElementType.TABLE) == 0) {
                        replaceTable((XWPFTable) bodyElement, placeHolder, replaceText);
                    }
                }
            }
        }
    }

    private static void replaceParagraph(XWPFParagraph paragraph, String placeHolder, String replaceText) {
        for (XWPFRun r : paragraph.getRuns()) {
            String text = r.getText(r.getTextPosition());
            if (text != null && text.contains(placeHolder)) {
                text = text.replace(placeHolder, replaceText);
                r.setText(text, 0);
            }
        }
    }
}
