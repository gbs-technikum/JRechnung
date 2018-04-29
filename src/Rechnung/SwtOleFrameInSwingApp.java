package Rechnung;


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class SwtOleFrameInSwingApp {

    private static OleFrame oleFrame1;
    private static OleClientSite clientSite;

    /**
     * @param args
     */
    public static void main(String[] args) {
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
                        childFrame.getContentPane().add(canvas,BorderLayout.SOUTH);
                        childFrame.pack();
                        childFrame.setVisible(true);

                        display.asyncExec(new Runnable() {

                            public void run() {
                                FillLayout thisLayout = new FillLayout(
                                        org.eclipse.swt.SWT.HORIZONTAL);
                                Shell shell = SWT_AWT.new_Shell(display, canvas);
                                shell.setLayout(thisLayout);
                                shell.setSize(800, 600);

                                try {
                                    oleFrame1 = new OleFrame(shell, SWT.NONE);
                                    clientSite = new OleClientSite(oleFrame1, SWT.NULL, new File("C:\\Users\\Mirko\\Documents\\test128.docx"));
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
                                                    doPrint();
                                                } catch (Exception e2) {
                                                    String str = "OleAutomation Error:" + e2.toString();
                                                    System.out.println(str);
                                                    return;
                                                }
                                            }
                                        });
                                    }
                                });

                                copyButton.addActionListener(new ActionListener() {

                                    public void actionPerformed(ActionEvent e) {
                                        display.asyncExec(new Runnable() {

                                            public void run() {
                                                try {
                                                    copySelection();
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

                            OleAutomation document;
                            int[] ids;
                            Variant result;

                            private void doPrint() {
                                if(clientSite == null)
                                    return;


                                        int result = clientSite.exec(OLE.OLECMDID_PRINT,
                                                OLE.OLECMDEXECOPT_PROMPTUSER, null, null);
                                        if(result != OLE.S_OK)
                                            System.out.println("Cann't print!!!" + result);


                            }

                            private void selectAll() {
                                document = new OleAutomation(clientSite);
                                System.out.println("Instantiated document");
                                ids = document.getIDsOfNames(new String[] {"wdDialogFilePrint"});
                                result = document.invoke(ids[0]);

                                document.dispose();

                                System.out.println("Invoked select");
                            }

                            private void copySelection() {
                                document = new OleAutomation(clientSite);
                                ids = document.getIDsOfNames(new String[] {"Application"});
                                result = document.getProperty(ids[0]);
                                document.dispose();

                                System.out.println("Got application");

                                OleAutomation application = result.getAutomation();

                                result.dispose();
                                ids = application.getIDsOfNames(new String[] {"Selection"});
                                result = application.getProperty(ids[0]);
                                application.dispose();

                                System.out.println("Got selection");

                                OleAutomation selection = result.getAutomation();
                                result.dispose();
                                ids = selection.getIDsOfNames(new String[] {"Copy"});
                                result = selection.invoke(ids[0]);
                                result.dispose();

                                System.out.println("Invoked Copy");

                                result.dispose();
                                ids = selection.getIDsOfNames(new String[] {"Move"});
                                result = selection.invoke(ids[0]);
                                result.dispose();

                                System.out.println("Invoked Move to deselect");
                            }
                        });

                    }
                });
/*            }
        });*/
        display.addListener(SWT.CLOSE, new Listener() {

            public void handleEvent(Event event) {
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
        while (!display.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();

        }
    }

}
