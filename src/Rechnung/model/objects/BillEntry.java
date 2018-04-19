package Rechnung.model.objects;

import org.apache.poi.hsmf.datatypes.StringChunk;

public class BillEntry {

    private String id;
    private double taxRateInPercent;
    private double unitPrice;
    private int amount;
    private String entryText;


    public BillEntry(String id, double taxRateInPercent, double unitPrice, int amount, String entryText) {
        this.taxRateInPercent = taxRateInPercent;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.entryText = entryText;
    }

    public String getId() {
        return id;
    }

    public double getTaxRateInPercent() {
        return taxRateInPercent;
    }

    public void setTaxRateInPercent(double taxRateInPercent) {
        this.taxRateInPercent = taxRateInPercent;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }
}
