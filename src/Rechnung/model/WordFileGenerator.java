package Rechnung.model;

import Rechnung.Publisher;
import Rechnung.model.objects.Bill;
import Rechnung.model.objects.BillEntry;
import Rechnung.model.objects.Business;
import Rechnung.model.objects.Customer;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
            return false;
        }

        try {
            XWPFDocument doc = new XWPFDocument(new FileInputStream(destination));

            Customer customer = bill.getDebtor();

            System.out.println(business);

            replaceText(doc,"$$CUSTOMERNAME$$",customer.getForename() + " " + customer.getName());
            replaceText(doc,"$$CUSTOMERSTREET$$",customer.getStreet() + " " + customer.getHouseNumber());
            replaceText(doc,"$$CUSTOMERLOCATION$$",customer.getVillage());
            replaceText(doc,"$$CUSTOMERPOSTCODE$$",customer.getPostCode());

            System.out.println(business.getLocation());
            System.out.println(business.getPostcode());
            replaceText(doc,"$$COMPANYNAME$$",business.getName());
            replaceText(doc,"$$STREET$$",business.getStreet() + " " + business.getStreetNumber());
            replaceText(doc,"$$LOCATION$$",business.getLocation());
            replaceText(doc,"$$POSTCODE$$",business.getPostcode());

/*            replaceText(doc,"$$CUSTOMERNUMBER$$","Kundennummer: " + customer.getNumber());

            replaceText(doc,"$$BILLNUMBER$$","Rechnungsnummer: " + bill.getNumber());*/

            replaceText(doc,"$$TOPAYDATE$$",Publisher.getModel().dateToGermanDateString(bill.getToPayToDate()));



            modifyTableBillData(doc,bill);
            modifyTableWithEntries(doc,bill);


            modifyPriceTable(doc,bill);

/*            if(bill.mustBeIncludedTaxes()) {

                int position = doc.getPosOfTable( tableTaxes );
                doc.removeBodyElement( position );
                replaceText(doc,"$$TAXVALUE$$","0.0");

            }else {*/

/*                for (int i = 0; i < bill.getBillEntries().size(); i++) {
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
                                run.setText(billEntry.getEntryText());
                                break;
                            case 1:
                                run.setText(String.format(Locale.GERMANY, "%.2f", billEntry.getUnitPrice()));
                                break;
                            case 2:
                                run.setText(String.valueOf(billEntry.getAmount()));
                                break;
                            case 3:
                                run.setText(String.format(Locale.GERMANY, "%.2f", billEntry.getUnitPrice() * billEntry.getAmount()));
                                break;
                        }
                        System.out.println(String.valueOf(j));
                    }

                    table.addRow(newRow, 1);
                }*/

/*
                replaceText(doc,"$$TAXVALUE$$","0.0");
            }
*/


          // replaceText(doc, "$$COMPLETEPRICE$$", String.format(Locale.GERMANY, "%.2f", bill.getTotalPrice()));


            doc.write(new FileOutputStream(destination));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private static void replaceText(XWPFDocument doc,String needle,String replacement){

        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    //    System.out.println(text);
                    if (text != null && text.contains(needle)) {
                        text = text.replace(needle,replacement);
                        r.setText(text, 0);
                    }
                }
            }
        }
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
                            run.setText(String.format(Locale.GERMANY, "%.2f€", bill.getEntryUnitPrice(i)));
                            break;
                        case 4:
                            run.setText(String.valueOf(billEntry.getAmount()));
                            break;
                        case 5:
                            run.setText(String.format(Locale.GERMANY, "%.2f€", bill.getEntryTotalPrice(i)));
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

            run.setText(String.format(Locale.GERMANY, "%.2f€", bill.getEntryPriceSum()));
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


                if(bill.mustBeIncludedTaxes()){
                    run.setText("enthaltene Mehrwersteuer " + taxPercentage.toString() + "%");
                }else{
                    run.setText("Mehrwersteuer " + taxPercentage.toString() + "%");
                }


                cell = newRow.getTableCells().get(1);
                paragraph = cell.getParagraphs().get(0);

                for (int i = 0; i < paragraph.getRuns().size(); i++) {
                    paragraph.removeRun(i);
            }
                run = paragraph.createRun();
                run.setText(String.format(Locale.GERMANY, "%.2f€", taxValueForPercentage));

                table.addRow(newRow, 1);
            }

            oldRow = table.getRow(0);
            CTRow ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
            XWPFTableRow newRow = new XWPFTableRow(ctrow, table);

            cell = newRow.getTableCells().get(0);
            paragraph = cell.getParagraphs().get(0);
            run =  paragraph.getRuns().get(0);
            run.setText("Zu zahlender Betrag",0);

            cell = newRow.getTableCells().get(1);
            paragraph = cell.getParagraphs().get(0);
            for (int i = 0; i < paragraph.getRuns().size(); i++) {
                paragraph.removeRun(i);
            }
            run = paragraph.createRun();
            run.setText(String.format(Locale.GERMANY, "%.2f€", bill.getTotalPrice()),0);
            run.setBold(true);

            cell = oldRow.getTableCells().get(0);
            paragraph = cell.getParagraphs().get(0);
            run = paragraph.getRuns().get(0);
            run.setBold(false);

            table.addRow(newRow, table.getNumberOfRows());

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

}
