package Rechnung.view;

import javax.swing.*;
import java.awt.*;

public class WaitWindow extends JDialog {

    private JLabel jlblWaitText;
    private JPanel panel;

    public WaitWindow(JFrame window, String text) {
        super(window,text);
        this.initComponents();
    }

    private void initComponents() {
        this.setSize(500,200);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.panel = new JPanel();

        this.jlblWaitText = new JLabel("Bitte haben Sie einen Moment Gedult...");

        this.panel.add(this.jlblWaitText);
        this.add(this.panel,BorderLayout.CENTER);

        this.pack();

        this.setVisible(true);
    }

    public void close(){
        this.setVisible(false);
        this.dispose();
    }

}
