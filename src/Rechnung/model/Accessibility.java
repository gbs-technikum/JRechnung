package Rechnung.model;

public abstract class Accessibility {

    private String entry;
    private String id;

    public Accessibility(String Id, String entry) {
        this.setId(id);
        this.setEntry(entry);
    }

    public String getEntry(){
        return this.entry;
    }

    public String getId() {
        return id;
    }

    public void setEntry(String entry){
        if(entry != null){
            this.entry = entry;
        }
    }

    public void setId(String id){
        if(id != null){
            this.id = id;
        }
    }



}
