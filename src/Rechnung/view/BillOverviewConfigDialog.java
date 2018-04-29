package Rechnung.view;

import org.eclipse.swt.widgets.Display;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class BillOverviewConfigDialog extends ConfigDialog{

    private JPanel mainPanel, wordEmbeddingPanel, buttonPanel;
    private JButton btnPrintDialog;
    private final Canvas canvas = new Canvas();

    public BillOverviewConfigDialog(JFrame frame)  {
        super(frame, "Rechnung");
        this.initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(1024,768));
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));

        this.buttonPanel = new JPanel();

        this.wordEmbeddingPanel = new JPanel();
        this.wordEmbeddingPanel.setLayout(new BorderLayout());

        this.canvas.setSize(850, 650);

        this.btnPrintDialog = new JButton("Durcken...");

        this.buttonPanel.add(this.btnPrintDialog);

        this.mainPanel.add(this.buttonPanel,BorderLayout.NORTH);
        this.mainPanel.add(this.canvas,BorderLayout.CENTER);

        this.addMainPanel(this.mainPanel);
    }

    public void setPrintDialogButtonListener(ActionListener listener){
        this.btnPrintDialog.addActionListener(listener);
        this.btnPrintDialog.setVisible(true);
        this.pack();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
