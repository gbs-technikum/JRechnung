package com.sabel.JRechnung.view;

import com.sabel.JRechnung.model.objects.ProductOrService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProductOrServiceConfigDialog extends ConfigDialog {

    private JComboBox<ProductOrService> jcbxProductOrService;
    private JTextField jtfTitel, jtfPrice, jtfDescription;
    private JPanel mainPanel;
    private JPanel jPanelNorth, jPanelCenter;
    private JButton jbtnDeleteProductOrService, jbtnNewProductOrService;


    public ProductOrServiceConfigDialog(JFrame frame) {
        super(frame, "Produkt oder Dienstleistung");
        this.setPreferredSize(new Dimension(700,300));
        this.initComponents();
        this.pack();
    }

    private void initComponents() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));


        this.jcbxProductOrService = new JComboBox<>();
        this.jbtnDeleteProductOrService = new JButton("Löschen");
        this.jbtnNewProductOrService = new JButton("Neu");

        this.jtfTitel = new JTextField();
        this.jtfTitel.setBorder(new TitledBorder("Bezeichnung"));
        this.jtfPrice = new JTextField();
        this.jtfPrice.setBorder(new TitledBorder("Preis"));
        this.jtfDescription = new JTextField();
        this.jtfDescription.setBorder(new TitledBorder("Beschreibung"));

        this.jPanelNorth = new JPanel();
        this.jPanelCenter = new JPanel();
        this.jPanelCenter.setLayout(new BoxLayout(this.jPanelCenter, BoxLayout.Y_AXIS));

        this.jPanelNorth.add(this.jcbxProductOrService);
        this.jPanelNorth.add(this.jbtnDeleteProductOrService);
        this.jPanelNorth.add(this.jbtnNewProductOrService);

        this.jPanelCenter.add(this.jtfTitel);
        this.jPanelCenter.add(this.jtfPrice);
        this.jPanelCenter.add(this.jtfDescription);

        this.mainPanel.add(jPanelNorth, BorderLayout.NORTH);
        this.mainPanel.add(jPanelCenter);


        this.addMainPanel(this.mainPanel);


    }

    public String getTitleTextField() {
        return this.jtfTitel.getText().toString();
    }

    public String getPriceTextField() {
        return this.jtfPrice.getText().toString();
    }

    public String getDescriptionTextField() {
        return this.jtfDescription.getText().toString();
    }

    public void setTitleTextField(String text) {
        this.jtfTitel.setText(text);
    }

    public void setPriceTextField(String text) {
        this.jtfPrice.setText(text);
    }

    public void setDescriptionTextField(String text) {
        this.jtfDescription.setText(text);
    }

    public void setNewButtonListener(ActionListener listener){
        this.jbtnNewProductOrService.addActionListener(listener);
        this.jbtnNewProductOrService.setVisible(true);
        this.pack();
    }

    public void setNewButtonEnabled(boolean enabled){
        this.jbtnNewProductOrService.setEnabled(enabled);
    }

    public void setDeleteButtonEnabled(boolean enabled){
        this.jbtnDeleteProductOrService.setEnabled(enabled);
    }


    public void setDeleteButtonListener(ActionListener listener){
        this.jbtnDeleteProductOrService.addActionListener(listener);
        this.jbtnDeleteProductOrService.setVisible(true);
        this.pack();
    }

    public void addToProductOrServiceList(ProductOrService productOrService){
        if(productOrService != null){
            this.jcbxProductOrService.addItem(productOrService);
        }
    }

    public void removeProductOrServiceFromList(int index){
        if(index >= 0 && index < this.jcbxProductOrService.getItemCount()){
            this.jcbxProductOrService.removeItemAt(index);
        }
    }

    public void setProductOrServiceComboBoxListener(ActionListener listener){
        this.jcbxProductOrService.addActionListener(listener);
    }


    public void removeProductOrServiceComboBoxListener(){

        for(int i=0;i< this.jcbxProductOrService.getActionListeners().length;i++){
            this.jcbxProductOrService.removeActionListener(this.jcbxProductOrService.getActionListeners()[i]);
        }

    }

    public ActionListener getProductOrServiceComboBoxListener(){
        if(this.jcbxProductOrService.getActionListeners().length > 0) {
            return this.jcbxProductOrService.getActionListeners()[0];
        }

        return null;
    }


    public int getIndexOfSelectedProductOrService(){
        return this.jcbxProductOrService.getSelectedIndex();
    }


    public void setIndexOfSelectedProductOrService(int index){
        if(index >= 0 && index < this.jcbxProductOrService.getItemCount()) {
            this.jcbxProductOrService.setSelectedIndex(index);
        }
    }

    public ProductOrService getProductOrServiceFromList(int index){
        if(index >= 0 && index < this.jcbxProductOrService.getItemCount()){
            return this.jcbxProductOrService.getItemAt(index);
        }

        return null;
    }

    public void clearComponentData(){
        this.jcbxProductOrService.removeAllItems();

        this.jtfTitel.setText("");
        this.jtfPrice.setText("");
        this.jtfDescription.setText("");

    }



}
