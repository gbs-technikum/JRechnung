package Rechnung.setup;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewBusiness extends WizardMain {
    private JPanel mainPanel;
    private WizardMain retrunWindow;

    public NewBusiness(WizardMain retrunWindow) throws HeadlessException {
        super("neue Unternehmung erstellen...");
        this.retrunWindow = retrunWindow;
        initComponents();
        initEvents();
        this.setBackButtonEnabled(true);
    }

    private void initEvents() {

        this.setBackButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrunWindow.setVisible(true);
                setVisible(false); //you can't see me!
                dispose(); //Destroy the JFrame object
            }
        });

        this.setNextButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new NewBusiness();
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
