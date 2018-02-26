package Rechnung.model;

public abstract class Accessibility {

    private String entry;

    public Accessibility(String entry) {
        this.setEntry(entry);
    }

    public String getEntry(){
        return this.entry;
    }

    public void setEntry(String entry){
        if(entry != null){
            this.entry = entry;
        }
    }


}
