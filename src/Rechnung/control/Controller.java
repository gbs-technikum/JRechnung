package Rechnung.control;

import javax.swing.*;
import java.awt.*;

public interface Controller {



    enum ControllerReturnStatus {
        OK,
        ABORT,
        CLOSED
    }


    ControllerReturnStatus run();

    default void waitForWindowToClose(Window window){
        while (window.isDisplayable()){
            //Nichts zu tun ausser zu warten
        }
        window.dispose();
    }

}
