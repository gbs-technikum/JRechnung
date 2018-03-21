package Rechnung.view;

import Rechnung.model.Customer;
import Rechnung.model.LegalForm;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class BillConfigDialog extends ConfigDialog {

    private static final String[] TABLE_COLUMN_NAMES = {"Produkt/Dienstleistung","Einheit","Anzahl","Einzelpreis","Gesamtpreis"};
    private static final int PREDEFIND_TABLE_ROWCOUNT = 30;
    private JPanel mainPanel;
    private JTextField jtfTitel;
    private JComboBox<Customer> jcbxDebtor;
    private JTextField jtfToPayDate, jtfPaidOn;
    private JCheckBox jchkbxPaid, jchkbxTaxFree, jchkbxTaxIncluded;
    private JTable jtblEntries;
    private JTextArea jtxtaComment;
    private JPanel jPanelMiddle, jPanelCheckboxes;
    private JScrollPane jspEntryTable, jspComment;
    private JButton btnAddEntry;
    private JButton btnGenerateBillFile;
    private JPanel leftButtunPanel;


    public BillConfigDialog(JFrame frame) {
        super(frame, "Rechnung");
        this.initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(700,580));
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));

     //   this.chkBxButtonGroup = new ButtonGroup();

        this.jtfTitel = new JTextField();
        this.jtfTitel.setBorder(new TitledBorder("Bezeichnung"));

        this.jPanelMiddle = new JPanel();
        this.jPanelMiddle.setLayout( new GridLayout(1,4));

        this.jcbxDebtor = new JComboBox();
        this.jcbxDebtor.setBorder(new TitledBorder("Schuldner"));

        this.jtfToPayDate = new JTextField();
        this.jtfToPayDate.setBorder(new TitledBorder("zu Begleichen bis"));

        this.jtfPaidOn = new JTextField();
        this.jtfPaidOn.setBorder(new TitledBorder("beglichen am"));

        this.jPanelCheckboxes = new JPanel();

        this.jchkbxPaid = new JCheckBox("Beglichen");
        this.jchkbxTaxFree = new JCheckBox("ohne Mehrwertsteuer");
        this.jchkbxTaxIncluded = new JCheckBox("Einzelpositionen beinhalten Mehrwertsteuer");

        this.btnAddEntry = new JButton("Eintrag hinzufügen");


        this.jtblEntries = new JTable(new DefaultTableModel(TABLE_COLUMN_NAMES, PREDEFIND_TABLE_ROWCOUNT));
        this.jtblEntries.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.jtblEntries.getTableHeader().setReorderingAllowed(true);
        this.jtblEntries.setRowHeight(20);
        Dimension tableDimension = new Dimension(0,350);
        this.jtblEntries.setPreferredSize(tableDimension);
        this.jspEntryTable = new JScrollPane(this.jtblEntries);
        this.jspEntryTable.setPreferredSize(tableDimension);


        this.jtxtaComment = new JTextArea();
        Dimension commentDimension = new Dimension(400,150);
        this.jtxtaComment.setPreferredSize(commentDimension);
        this.jspComment = new JScrollPane(this.jtxtaComment);
        this.jspComment.setBorder(new TitledBorder("Beschreibung / Kommentar"));
        this.jspComment.setPreferredSize(commentDimension);

        this.leftButtunPanel = new JPanel();
        this.leftButtunPanel.setLayout(new BorderLayout());

        this.btnGenerateBillFile = new JButton("Rechnung generieren");

        this.mainPanel.add(this.jtfTitel);


        this.jPanelMiddle.add(this.jcbxDebtor);
        this.jPanelMiddle.add(this.jtfToPayDate);
        this.jPanelMiddle.add(this.jtfPaidOn);

        this.mainPanel.add(this.jPanelMiddle);

        this.jPanelCheckboxes.add(this.btnAddEntry);
        this.jPanelCheckboxes.add(this.jchkbxPaid);

        this.jPanelCheckboxes.add(this.jchkbxTaxFree);
        this.jPanelCheckboxes.add(this.jchkbxTaxIncluded);

        this.mainPanel.add(this.jPanelCheckboxes);



        this.mainPanel.add(this.jspEntryTable);

        this.mainPanel.add(this.jspComment);

        this.mainPanel.add(leftButtunPanel);

        this.mainPanel.add(this.btnGenerateBillFile);

        this.leftButtunPanel.add(this.btnGenerateBillFile,BorderLayout.WEST);

        this.mainPanel.add(this.leftButtunPanel);

        this.addMainPanel(this.mainPanel);
        this.pack();
    }


}
