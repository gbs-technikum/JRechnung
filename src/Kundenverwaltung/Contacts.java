package Kundenverwaltung;

/*
    @author Gollmer 2018-01-31
    Class which contents the Contact data
*/

import java.util.ArrayList;
import java.util.Iterator;

class Contacts {

    private ArrayList<String> contactDatas;

    public Contacts() {
        contactDatas = new ArrayList<>();
    }

    public ArrayList<String> getContactDatas() {
        return contactDatas;
    }

    public void addContact(String contact){
        contactDatas.add(contact);
    }

    public boolean removeContact(String contact){
        Iterator<String> iterator = contactDatas.iterator();
        while (iterator.hasNext()){
            String newContact = iterator.next();
            if(newContact.equals(contact)){
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
