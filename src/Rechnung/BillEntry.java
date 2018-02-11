package Rechnung;

public interface BillEntry {

    double getTaxRateInPercent();
    double getUnitPrice();
    double getPerEntryPrice(boolean taxIncluded);

    int getAmount();
    int setAmount();

    void setTaxRateInPercent(double taxRateInPercent);
    void setUnitPrice(double unitPrice);

    String getEntryText();
    String setEntryText();

}
