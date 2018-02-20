package Kundenverwaltung;

/*
    @author Gollmer 2018-01-31
    Class which contents the Contact data
*/

import java.util.ArrayList;

class Contacts {

    private ArrayList<String> contactDatas;

    public Contacts() {
        contactDatas = new ArrayList<>();
    }

    /**
     *
     * @return Returns a new ArrayList of ContactDatas
     */
    public ArrayList<String> getContactDatas() {
        return contactDatas;
    }

    public void addContact(String contact){
        contactDatas.add(contact);
    }

    public void setContactDatas(ArrayList<String> contactDatas) {
        this.contactDatas = contactDatas;
    }

    public boolean removeContact(String contact){
        return contactDatas.remove(contact);
    }

    @Override
    public String toString() {
        String string = "{";
        int contactDatasSize = contactDatas.size();
        for(int i=0; i<contactDatasSize; i++){
            string += contactDatas.get(i);
            if(i<contactDatasSize-1){
                string += ", ";
            }
        }
        string += "}";
        return string;
    }
}
