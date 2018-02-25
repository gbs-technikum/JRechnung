package Rechnung.Kundenverwaltung.Listener;

import Rechnung.Kundenverwaltung.View.PanelContact;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactListener implements ActionListener{

    private PanelContact panelContact;

    public ContactListener(PanelContact panelContact) {
        this.panelContact = panelContact;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == panelContact.getButtonAdd()){
            panelContact.getCbContact().addItem(panelContact.getTvContact().getText());
            panelContact.resetText();
        }else if(e.getSource() == panelContact.getButtonDelete()){
            panelContact.getCbContact().removeItem(panelContact.getCbContact().getSelectedItem());
        }
    }
}
