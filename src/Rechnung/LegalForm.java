package Rechnung;

public interface LegalForm {

    int getId();
    String getName();
    String getShortName();
    boolean mustToBeAPostfix();
    boolean isTaxFree();

}
