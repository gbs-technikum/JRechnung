package Rechnung.model.objects;

import java.io.File;
import java.util.*;

public class Bill {
    private String id;
    private String number;
    private Customer debtor;
    private String titel;
    private Date creationDate;
    private Date toPayToDate;
    private Date paidOnDate;
    private boolean paid;
    private String comment;
    private File billFile;
    private List<BillEntry> entries;
    private boolean taxFree;
    private boolean taxIncluded;

    public Bill(String id, String number, Customer debtor, String titel, Date creationDate, Date toPayToDate, Date paidOnDate, boolean paid, String comment, File billFile, boolean taxFree, boolean taxIncluded) {
        this.id = id;
        this.number = number;
        this.debtor = debtor;
        this.titel = titel;
        this.creationDate = creationDate;
        this.toPayToDate = toPayToDate;
        this.paidOnDate = paidOnDate;
        this.paid = paid;
        this.comment = comment;
        this.billFile = billFile;
        this.taxFree = taxFree;
        this.taxIncluded = taxIncluded;
        this.entries = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    private boolean isEntryIndexValid(int index){
        return !(index < 0 || index >= this.entries.size());
    }

    public boolean mustBeIncludedTaxes() {
        return this.taxIncluded;
    }

    public void setTaxesMustBeIncluded(boolean taxIncluded) {
        this.taxIncluded = taxIncluded;
    }

    public Customer getDebtor() {
        return debtor;
    }

    public void setDebtor(Customer debtor) {
        this.debtor = debtor;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getToPayToDate() {
        return toPayToDate;
    }

    public void setToPayToDate(Date toPayToDate) {
        this.toPayToDate = toPayToDate;
    }

    public Date getPaidOnDate() {
        return paidOnDate;
    }

    public void setPaidOnDate(Date paidOnDate) {
        this.paidOnDate = paidOnDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public File getBillFile() {
        return billFile;
    }

    public void setBillFile(File billFile) {
        this.billFile = billFile;
    }

    public boolean isBusinessTaxFree() {
        return this.taxFree;
    }

    public void setBusinessTaxFree(boolean taxFree) {
        this.taxFree = taxFree;
    }

    public boolean addEntry(BillEntry entry){
        if(entry == null){
            return false;
        }

        return this.entries.add(entry);
    }

    public BillEntry removeEntry(int index){
        if(!this.isEntryIndexValid(index)){
            return null;
        }

        return this.entries.remove(index);
    }

    public BillEntry updateEntry(BillEntry newEntry, int index){
        if(newEntry == null || !this.isEntryIndexValid(index)){
            return null;
        }

        return this.entries.set(index,newEntry);
    }

    public double getEntryUnitPrice(int index){
        if(this.isEntryIndexValid(index)) {

            BillEntry entry = this.entries.get(index);
            if (entry != null) {
                if (this.isBusinessTaxFree() || !this.mustBeIncludedTaxes()) {
                    return entry.getUnitPrice();
                }else{
                    return entry.getUnitPrice() + (entry.getUnitPrice() * (entry.getTaxRateInPercent() / 100));
                }
            }
        }

        return 0;
    }

    public double getEntryTotalPrice(int index){
        double result = this.getEntryUnitPrice(index);

        if(result > 0){
            BillEntry entry = this.entries.get(index);
            if (entry != null) {
                result *= entry.getAmount();
            }
        }

        return result;
    }

    public double getEntryTaxValue(int index){
        double result = 0;

        if(this.isEntryIndexValid(index)){
            BillEntry entry = this.entries.get(index);
            if (entry != null) {
                if (this.isBusinessTaxFree() || !this.mustBeIncludedTaxes()) {
                    result = this.getEntryTotalPrice(index) * (entry.getTaxRateInPercent() / 100);
                }else{
                    result = this.getEntryTotalPrice(index) - (this.getEntryTotalPrice(index) * ((entry.getTaxRateInPercent()+100) / 100));
                }
            }
        }

        return result;
    }

    public double getEntryTaxRateInPercent(int index){
        double result = 0;

        if(this.isEntryIndexValid(index)){
            BillEntry entry = this.entries.get(index);
            if (entry != null) {
                result = entry.getTaxRateInPercent();
            }
        }

        return result;

    }

    public double getTotalPrice(){
        double result = 0;


        for (int i=0;i<this.entries.size();i++){
            if (this.isBusinessTaxFree() || !this.mustBeIncludedTaxes()) {
                result += (this.getEntryTotalPrice(i) + this.getEntryTaxValue(i));
            }else{
                result += this.getEntryTotalPrice(i);
            }
        }

        return result;
    }

    public List<BillEntry> getBillEntries() {
        List<BillEntry> billEntries = new ArrayList<>();

        for (BillEntry billEntry: this.entries) {
            billEntries.add(billEntry);
        }

        return billEntries;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Bill{");
        sb.append("id='").append(id).append('\'');
        sb.append(", number='").append(number).append('\'');
        sb.append(", debtor=").append(debtor);
        sb.append(", titel='").append(titel).append('\'');
        sb.append(", creationDate=").append(creationDate);
        sb.append(", toPayToDate=").append(toPayToDate);
        sb.append(", paidOnDate=").append(paidOnDate);
        sb.append(", paid=").append(paid);
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", billFile=").append(billFile);
        sb.append(String.format("%n"));
        sb.append(", entries=");
        for (BillEntry entry : entries) {
            sb.append(entry);
            sb.append(String.format("%n"));
        }

        sb.append(", taxFree=").append(taxFree);
        sb.append(", taxIncluded=").append(taxIncluded);
        sb.append('}');
        return sb.toString();
    }


}
