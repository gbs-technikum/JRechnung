package com.sabel.JRechnung.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PanelAccessibility extends JPanel{

    private JTextField jtfAcessibility;



    private JComboBox<String> jcbxAcessibility;
    private JPanel panelUp, panelDown;
    private JButton buttonAdd, buttonDelete;

    public PanelAccessibility(String title) {
        super();
        this.setLayout(new GridLayout(4, 1));
        this.setBorder(new TitledBorder(title));
        this.initComponents();
    }

    private void initComponents() {
        jtfAcessibility = new JTextField();
        jcbxAcessibility = new JComboBox<>();
        panelUp = new JPanel(new GridLayout(2, 1));
        panelDown = new JPanel();
        panelDown.setLayout(new GridLayout(2, 1));
        buttonAdd = new JButton("Hinzufügen");
        buttonDelete = new JButton("Löschen");

        buttonAdd.setEnabled(false);
        buttonAdd.setVisible(false);

        buttonDelete.setEnabled(false);
        buttonDelete.setVisible(false);

        this.add(this.jtfAcessibility);
        this.add(this.buttonAdd);
        this.add(this.jcbxAcessibility);
        this.add(this.buttonDelete);
    }

    public void clearComponentData(){
        jcbxAcessibility.removeAllItems();
        jtfAcessibility.setText("");
    }

    public void addToAcessibilityStringList(String entry){
        if(entry != null){
            this.jcbxAcessibility.addItem(entry);
        }
    }

    public int getIndexOfSelectedAcessibility(){
        return this.jcbxAcessibility.getSelectedIndex();
    }

    public void removeItemFromAcessibilityStringList(int index){
        if(index >= 0 && index < this.jcbxAcessibility.getItemCount()){
            this.jcbxAcessibility.removeItemAt(index);
        }
    }

    public List<String> getAcessibilityStringList(){
        List<String> list = new ArrayList<>();

        for(int i=0;i< jcbxAcessibility.getItemCount();i++){
            list.add(jcbxAcessibility.getItemAt(i));
        }

        return list;
    }

    public String getAcessibilityText() {
        return jtfAcessibility.getText().toString();
    }

    void setDeleteButtonEnabled(boolean enabled){
        this.buttonDelete.setEnabled(enabled);
    }

    public void setDeleteButtonListener(ActionListener listener){
        this.buttonDelete.addActionListener(listener);
        this.buttonDelete.setVisible(true);
    }

    public void setAddButtonEnabled(boolean enabled){
        this.buttonAdd.setEnabled(enabled);
    }

    public void setAddButtonListener(ActionListener listener){
        this.buttonAdd.addActionListener(listener);
        this.buttonAdd.setVisible(true);
    }

}
