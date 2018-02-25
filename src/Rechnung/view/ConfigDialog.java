package Rechnung.view;



import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public abstract class ConfigDialog extends JDialog {

    private JPanel bottomPanel;
    private JButton btnOkay;
    private JButton btnCancel;
    private JButton btnApply;
    private JPanel jpButtons;

    public ConfigDialog(JFrame frame, String str){
        super(frame, str);
        this.initComponents();
    }

    public void addMainPanel(JPanel mainPanel){
        this.add(mainPanel,BorderLayout.CENTER);
        this.pack();
    }

    private void initComponents() {
        this.bottomPanel = new JPanel();
        this.jpButtons = new JPanel();

        this.btnOkay = new JButton("OK");
        this.btnCancel = new JButton("Abbrechen");
        this.btnApply = new JButton("Übernehmen");

        this.jpButtons.add(this.btnOkay);
        this.jpButtons.add(this.btnCancel);
        this.jpButtons.add(this.btnApply);

        this.btnCancel.setVisible(false);
        this.btnApply.setVisible(false);
        this.btnOkay.setVisible(false);

        this.btnCancel.setEnabled(false);
        this.btnApply.setEnabled(false);
        this.btnOkay.setEnabled(false);

        this.bottomPanel.setLayout(new BorderLayout());
        this.bottomPanel.add(this.jpButtons,BorderLayout.EAST);

        this.add(this.bottomPanel,BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setModal(true);

        this.pack();
    }

    public void setCancelButtonListener(ActionListener listener){
        this.btnCancel.addActionListener(listener);
        this.btnCancel.setVisible(true);
        this.pack();
    }

    public void setApplyButtonListener(ActionListener listener){
        this.btnApply.addActionListener(listener);
        this.btnApply.setVisible(true);
        this.pack();
    }

    public void setOkayButtonListener(ActionListener listener){
        this.btnOkay.addActionListener(listener);
        this.btnOkay.setVisible(true);
        this.pack();
    }

    public void setCancelButtonEnabled(boolean enabled){
        this.btnCancel.setEnabled(enabled);
    }

    public void setApplyButtonEnabled(boolean enabled){
        this.btnApply.setEnabled(enabled);
    }

    public void setOkayButtonEnabled(boolean enabled){
        this.btnOkay.setEnabled(enabled);
    }

    public void setNewMainPanel(JPanel panel){
        panel.setBackground(Color.magenta);
        this.add(panel,BorderLayout.CENTER);
    }

}

