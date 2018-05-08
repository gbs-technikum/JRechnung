package com.sabel.JRechnung.model.objects;

import java.util.Objects;

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

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegalForm legalForm = (LegalForm) o;
        return id == legalForm.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, shortName, mustToBeAPostfix, isTaxFree);
    }
}
