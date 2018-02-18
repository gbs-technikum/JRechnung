package Rechnung;

public class LegalForm {

    private int id;
    private String name;
    private String shortName;
    private boolean mustToBeAPostfix;
    private boolean isTaxFree;

    public LegalForm(int id, String name, String shortName, boolean mustToBeAPostfix, boolean isTaxFree) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.mustToBeAPostfix = mustToBeAPostfix;
        this.isTaxFree = isTaxFree;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public boolean mustToBeAPostfix() {
        return this.mustToBeAPostfix;
    }

    public boolean isTaxFree() {
        return this.isTaxFree;
    }

}
