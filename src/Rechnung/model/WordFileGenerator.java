package Rechnung.model;

import Rechnung.model.objects.Bill;
import Rechnung.model.objects.BillEntry;
import Rechnung.model.objects.Business;
import Rechnung.model.objects.Customer;
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
            return false;
        }

        try {
            XWPFDocument doc = new XWPFDocument(new FileInputStream(destination));

            Customer customer = bill.getDebtor();

            replaceText(doc,"$$CUSTOMERNAME$$",customer.getForename() + " " + customer.getName());
            replaceText(doc,"$$CUSTOMERSTREET",customer.getStreet() + " " + customer.getHouseNumber());
            replaceText(doc,"$$CUSTOMERLOCATION$$",customer.getVillage());
            replaceText(doc,"$$CUSTOMERPOSTCODE$",customer.getPostCode());

            replaceText(doc,"$$COMPANYNAME$$",business.getName());
            replaceText(doc,"$$STREET$$",business.getStreet() + " " + business.getStreetNumber());
            replaceText(doc,"$$LOCATION$$",business.getLocation());
            replaceText(doc,"$$POSTCODE$$",business.getPostcode());

            replaceText(doc,"$$CUSTOMERNUMBER$$","Kundennummer: " + customer.getNumber());

            replaceText(doc,"$$BILLNUMBER$$","Rechnungsnummer: " + bill.getNumber());

            XWPFTable table = doc.getTableArray(0);

            for (int i = 0; i < bill.getBillEntries().size(); i++) {
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
            }

            XWPFTable tableTaxes = doc.getTableArray(1);

            if(bill.mustBeIncludedTaxes()) {

                int position = doc.getPosOfTable( tableTaxes );
                doc.removeBodyElement( position );
                replaceText(doc,"$$TAXVALUE$$","0.0");

            }else {

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

                replaceText(doc,"$$TAXVALUE$$","0.0");
            }


            replaceText(doc, "$$COMPLETEPRICE$$", String.format(Locale.GERMANY, "%.2f", bill.getTotalPrice()));


            doc.write(new FileOutputStream(destination));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlException e) {
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

}
