package Rechnung.control;

import Rechnung.view.BillOverviewConfigDialog;
import javafx.scene.Camera;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BillOverviewConfigDialogController implements Controller {
    private BillOverviewConfigDialog billOverviewConfigDialog;
    private ControllerReturnStatus controllerReturnStatus;

    private static OleFrame oleFrame;
    private static OleClientSite clientSite;


    public BillOverviewConfigDialogController(JFrame window) {
     //   this.billOverviewConfigDialog = new BillOverviewConfigDialog(window);
      //  this.initEvents();
        this.controllerReturnStatus = ControllerReturnStatus.OK;
    }

    @Override
    public ControllerReturnStatus run() {



        final Display display = new Display();

/*        EventQueue.invokeLater(new Runnable() {
            public void run() {*/
                JFrame mainFrame = new JFrame("Main Window");
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setSize(800, 600);

                JMenuBar menuBar = new JMenuBar();

                JMenu menuTool = new JMenu("Tool");

                menuBar.add(menuTool);

                JMenuItem openBrowserMenuItem = new JMenuItem("Open Word Viewer");

                menuTool.add(openBrowserMenuItem);
                mainFrame.setJMenuBar(menuBar);

                mainFrame.pack();
                mainFrame.setVisible(true);

                openBrowserMenuItem.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        JFrame childFrame = new JFrame("Word Viewer Window");
                        childFrame.setSize(850, 690);

                        JPanel childPanel = new JPanel();
                        childPanel.setSize(850, 40);
                        final JButton selectAllButton = new JButton("Select All");
                        final JButton copyButton = new JButton("Copy");
                        final JButton copyPasteButton = new JButton("Copy & Paste");
                        childPanel.add(selectAllButton);
                        childPanel.add(copyButton);
                        childPanel.add(copyPasteButton);

                        final Canvas canvas = new Canvas();
                        canvas.setSize(850, 650);

                        childFrame.getContentPane().add(childPanel, BorderLayout.NORTH);
                        childFrame.getContentPane().add(canvas, BorderLayout.SOUTH);
                        childFrame.pack();
                        childFrame.setVisible(true);

                        System.out.println("Init fertig");

                        display.asyncExec(new Runnable() {

                            public void run() {
                                FillLayout thisLayout = new FillLayout(
                                        org.eclipse.swt.SWT.HORIZONTAL);
                                Shell shell = SWT_AWT.new_Shell(display, canvas);
                                shell.setLayout(thisLayout);
                                shell.setSize(800, 600);

                                try {
                                    oleFrame = new OleFrame(shell, SWT.NONE);
                                    clientSite = new OleClientSite(oleFrame, SWT.NULL, new File("C:\\Users\\Mirko\\Documents\\test128.docx"));
                                    clientSite.setBounds(0, 0, 104, 54);
                                    clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
                                    System.out.println("Complete process OLE Client Site");

                                } catch (Exception e2) {
                                    String str = "Create OleClientSite Error:" + e2.toString();
                                    System.out.println(str);
                                    return;
                                }
                                shell.layout();
                                shell.open();

                                selectAllButton.addActionListener(new ActionListener() {

                                    public void actionPerformed(ActionEvent e) {
                                        display.asyncExec(new Runnable() {
                                            public void run() {
                                                try {
                                                    doPrintWithDialog();
                                                } catch (Exception e2) {
                                                    String str = "OleAutomation Error:" + e2.toString();
                                                    System.out.println(str);
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                });
                            }

                            void doPrintWithDialog(){
                                if(clientSite == null)
                                    return;

                                int result = clientSite.exec(OLE.OLECMDID_PRINT,
                                        OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
                                if(result != OLE.S_OK)
                                    System.out.println(result);

                            }
                        });

                    }
                });
           // }
    //    });

        display.addListener(SWT.CLOSE, new Listener() {

            public void handleEvent(org.eclipse.swt.widgets.Event event) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        Frame[] frames = JFrame.getFrames();
                        for (int i = 0; i < frames.length; i++) {
                            frames[i].dispose();
                        }
                    }
                });
            }
        });

/*        while (!display.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();

        } */

        return controllerReturnStatus;
    }

    private void initEvents() {
        this.billOverviewConfigDialog.setOkayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.OK;
                billOverviewConfigDialog.setVisible(false);
                billOverviewConfigDialog.dispose();
            }
        });
        this.billOverviewConfigDialog.setOkayButtonEnabled(true);

        this.billOverviewConfigDialog.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerReturnStatus = ControllerReturnStatus.ABORT;
                billOverviewConfigDialog.setVisible(false);
                billOverviewConfigDialog.dispose();
            }
        });
        this.billOverviewConfigDialog.setCancelButtonEnabled(true);


    }




}
