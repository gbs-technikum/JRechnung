package Rechnung.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

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

        this.add(this.jtfAcessibility);
        this.add(this.buttonAdd);
        this.add(this.jcbxAcessibility);
        this.add(this.buttonDelete);
    }

    public void reset(){
        jcbxAcessibility.removeAllItems();
        jtfAcessibility.removeAll();
    }

}
