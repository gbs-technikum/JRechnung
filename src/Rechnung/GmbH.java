package Rechnung;

public class GmbH implements LegalForm{

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return "Gesellschaft mit beschränkter Haftung";
    }

    @Override
    public String getShortName() {
        return "GmbH";
    }

    @Override
    public boolean mustToBeAPostfix() {
        return true;
    }

    @Override
    public boolean isTaxFree() {
        return false;
    }
}
