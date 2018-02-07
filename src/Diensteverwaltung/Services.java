package Diensteverwaltung;

/*
    @author Richter 2018-02-07
    Class which contents the servicedatabase
*/

import java.util.ArrayList;

public class Services {

    private ArrayList<String> services;

    public Services() {
        services = new ArrayList<>();
    }

    public void addService (String service) {
        services.add(service);
    }

    public void showService (int index) {

        System.out.println(services.get(index));

    }
}
