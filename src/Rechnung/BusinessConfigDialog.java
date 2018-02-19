package Rechnung;

import Rechnung.ConfigDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BusinessConfigDialog extends ConfigDialog{

    private JPanel mainPanel;

    public BusinessConfigDialog(JFrame frame, String str) {
        super(frame, str);
        this.initComponents();
        this.initEvents();
    }

    private void initEvents() {
        this.setOkayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        this.setApplyButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        this.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void initComponents() {
        this.mainPanel = new JPanel();

        this.addMainPanel(this.mainPanel);
    }

}
