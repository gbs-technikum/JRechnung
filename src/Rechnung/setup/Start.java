package Rechnung.setup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start extends WizardMain {

    JPanel mainPanel;

    public Start() throws HeadlessException {
        super("Start");
        initComponents();
        initEvents();
        this.setNextButtonEnabled(true);
        this.setCancelButtonEnabled(true);

    }

    private void initEvents() {

        this.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.setNextButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewBusiness((WizardMain) SwingUtilities.getRoot((Component) e.getSource()));
            }
        });

    }

    private void initComponents() {
        this.mainPanel = new JPanel();
        this.setNewMainPanel(this.mainPanel);
    }


    public static void main(String[] args) {
        new Start();
    }


}
