package Rechnung;



import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class ConfigDialog extends JDialog {
    public ConfigDialog(JFrame frame, String str) {
        super(frame, str);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setModal(true);

        JPanel panel = new JPanel();
        panel.setSize(200, 200);
        JLabel lbl = new JLabel("blah blah");
        panel.add(lbl);
        this.add(panel);
        this.setSize(400, 400);

    }

}

