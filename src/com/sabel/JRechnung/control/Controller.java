package com.sabel.JRechnung.control;

import java.awt.*;

public interface Controller {



    enum ControllerReturnStatus {
        OK,
        ABORT,
        CLOSED,
        ERROR
    }


    ControllerReturnStatus run();

    default void waitForWindowToClose(Window window){
        while (window.isDisplayable()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        window.dispose();
    }

}
