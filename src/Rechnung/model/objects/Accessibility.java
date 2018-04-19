package Rechnung.model.objects;

public abstract class Accessibility {

    private String entry;
    private String id;

    public Accessibility(String id, String entry) {
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

    @Override
    public String toString() {
        return "{" +
                "entry='" + entry + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
