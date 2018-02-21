package Rechnung;

import org.apache.poi.POIReadOnlyDocument;
import rtf.AdvancedRTFEditorKit;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws Exception {

        Logger logger = Publisher.getLogger();

       Connection onnection = Publisher.getDBConnection();

        System.out.println("xxx" + onnection);

        logger.loginfo("Start...");

        SecurityProvider sp = Publisher.getSecurityProvider();

    //    MainWindow mainWindow = new MainWindow();

   //     ConfigDialog frame = new BusinessConfigDialog(mainWindow, "No min max buttons");
     //   frame.setVisible(true);





    }

}
