package Rechnung.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class BillConfigDialog extends ConfigDialog {

    private JPanel mainPanel;
    private JTextField jtfTitel;
    private JComboBox<String> jcbxLegalform;

    public BillConfigDialog(JFrame frame) {
        super(frame, "Rechnung");
        this.initComponents();
    }

    private void initComponents() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));

        this.jtfTitel = new JTextField();
        this.jtfTitel.setBorder(new TitledBorder("Bezeichnung"));

        this.mainPanel.add(this.jtfTitel);

        this.addMainPanel(this.mainPanel);
    }


}
