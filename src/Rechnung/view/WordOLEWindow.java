package Rechnung.view;

import java.io.File;

import Rechnung.Debug;
import Rechnung.model.Message;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


public class WordOLEWindow extends ApplicationWindow {
    private OleFrame oleFrame;
    private OleClientSite clientSite;
    private File wordFile;
    private boolean ready;

    public WordOLEWindow(Shell parentShell, File wordFile) {
        super(parentShell);
        this.ready = false;
        this.wordFile = wordFile;

        addMenuBar();
        addToolBar(SWT.FLAT);
    }

    @Override
    public void create() {
        setShellStyle(SWT.DIALOG_TRIM);
        super.create();
    }

    @Override
    protected Control createContents(Composite parent) {

        Shell shell = getShell();
        shell.setSize(1024,768);

        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new FillLayout());

        oleFrame = new OleFrame(composite, SWT.NULL);

        clientSite = new OleClientSite(oleFrame, SWT.NULL, wordFile);
        System.out.println(clientSite.getProgramID() + ", " + clientSite);


        clientSite.doVerb(OLE.OLEIVERB_SHOW);

        this.ready = true;
        return composite;
    }


    @Override
    protected void constrainShellSize() {
        super.constrainShellSize();
        getShell().setMaximized( true );

    }

    @Override
    protected ToolBarManager createToolBarManager(int style) {
        ToolBarManager manager = new ToolBarManager(style);

        Action actionSaveAs = new Action("Speichern unter...") {
            public void run() {


                if ((clientSite.queryStatus(OLE.OLECMDID_SAVEAS)
                        & OLE.OLECMDF_ENABLED)
                        != 0) {
                    int result = clientSite.exec(
                            OLE.OLECMDID_SAVEAS,
                            OLE.OLECMDEXECOPT_PROMPTUSER,
                            null,
                            null);
                    if(result != OLE.S_OK){
                        Message.showErrorMessageCantSaveFile(wordFile.getAbsolutePath());
                    }
                }
            }
        };

        Action actionSave = new Action("Speichern") {
            public void run() {
                if (clientSite.save(wordFile, false)) {
                    if(Debug.ON){
                        System.out.println("Datei " + wordFile.getAbsolutePath() + " erfolgreich gespeichert!");
                    }
                } else {
                    Message.showErrorMessageCantSaveFile(wordFile.getAbsolutePath());
                }
            }
        };


        Action actionSpellCheck = new Action("Rechtschreibprüfung") {
            public void run() {
                if ((clientSite.queryStatus(OLE.OLECMDID_SPELL)
                        & OLE.OLECMDF_ENABLED)
                        != 0) {
                    clientSite.exec(
                            OLE.OLECMDID_SPELL,
                            OLE.OLECMDEXECOPT_PROMPTUSER,
                            null,
                            null);
                }
            }
        };

        Action actionPrintWithDialog = new Action("Drucken...") {
            public void run() {
                if ((clientSite.queryStatus(OLE.OLECMDID_PRINT)
                        & OLE.OLECMDF_ENABLED)
                        != 0) {
                    int result = clientSite.exec(
                            OLE.OLECMDID_PRINT,
                            OLE.OLECMDEXECOPT_PROMPTUSER,
                            null,
                            null);
                    if(result != OLE.S_OK){
                        Message.showErrorMessagePrinting();
                    }
                }
            }
        };

        manager.add(actionSaveAs);
        manager.add(actionSave);
        manager.add(actionSpellCheck);
        manager.add(actionPrintWithDialog);

        return manager;
    }

    public boolean isReady() {
        return ready;
    }
}
