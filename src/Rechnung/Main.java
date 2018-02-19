package Rechnung;

import org.apache.poi.POIReadOnlyDocument;
import rtf.AdvancedRTFEditorKit;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Logger logger = Publisher.getLogger();

        logger.loginfo("Start...");

        SecurityProvider sp = Publisher.getSecurityProvider();

        MainWindow mainWindow = new MainWindow();

        ConfigDialog frame = new BusinessConfigDialog(mainWindow, "No min max buttons");
        frame.setVisible(true);
    }

}
