package Rechnung;

import Rechnung.components.BillList;

import java.io.File;
import java.sql.ResultSet;
import java.util.*;

public class Bill {
    private Kunde debtor;
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


    private boolean isEntryIndexValid(int index){
        return !(index < 0 || index >= this.entries.size());
    }

    public boolean mustBeIncludedTaxes() {
        return this.taxIncluded;
    }

    public void setTaxesMustBeIncluded(boolean taxIncluded) {
        this.taxIncluded = taxIncluded;
    }

    public Kunde getDebtor() {
        return debtor;
    }

    public void setDebtor(Kunde debtor) {
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




}
