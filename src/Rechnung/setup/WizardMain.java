package Rechnung.setup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class WizardMain extends JFrame {

    private JPanel jpWirzardControl;
    private  JPanel jpButtons;
    private JButton jbtnCancel;
    private JButton jbtnBack;
    private JButton jbtnNext;

    public WizardMain(String name) throws HeadlessException {
        super(name);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.initComponents();
        this.setSize(600,400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initComponents() {

        this.jpWirzardControl = new JPanel();
        this.jpButtons = new JPanel();
      //  this.jpButtons.setLayout(new FlowLayout(FlowLayout.RIGHT,100,200));
        this.jpWirzardControl.setBackground(Color.gray);

        Dimension size = new Dimension(0, 100);
        this.jpWirzardControl.setPreferredSize(size);

        this.jbtnCancel = new JButton("Abbrechen");
        this.jbtnBack= new JButton("Zurück");
        this.jbtnNext = new JButton("Weiter");

        this.jpButtons.add(this.jbtnCancel);
        this.jpButtons.add(this.jbtnBack);
        this.jpButtons.add(this.jbtnNext);

        this.jbtnCancel.setVisible(false);
        this.jbtnBack.setVisible(false);
        this.jbtnNext.setVisible(false);

        this.jbtnCancel.setEnabled(false);
        this.jbtnBack.setEnabled(false);
        this.jbtnNext.setEnabled(false);


        this.jpWirzardControl.add(jpButtons,BorderLayout.EAST);

        this.add(this.jpWirzardControl,BorderLayout.SOUTH);
    }

    public void setCancelButtonListener(ActionListener listener){
        this.jbtnCancel.addActionListener(listener);
        this.jbtnCancel.setVisible(true);
    }

    public void setBackButtonListener(ActionListener listener){
        this.jbtnBack.addActionListener(listener);
        this.jbtnBack.setVisible(true);
    }

    public void setNextButtonListener(ActionListener listener){
        this.jbtnNext.addActionListener(listener);
        this.jbtnNext.setVisible(true);
    }

    public void setCancelButtonEnabled(boolean enabled){
        this.jbtnCancel.setEnabled(enabled);
    }

    public void setBackButtonEnabled(boolean enabled){
        this.jbtnBack.setEnabled(enabled);
    }

    public void setNextButtonEnabled(boolean enabled){
        this.jbtnNext.setEnabled(enabled);
    }

    public void setNewMainPanel(JPanel panel){
        panel.setBackground(Color.magenta);
        this.add(panel,BorderLayout.CENTER);
    }

}
