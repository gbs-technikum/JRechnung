package com.sabel.JRechnung.control;

import com.sabel.JRechnung.view.WordOLEWindow;
import org.eclipse.swt.widgets.Display;

import java.io.File;

public class WordStarter implements Runnable {

    private File wordFile;
    private WordOLEWindow wordOLE;

    public WordStarter(File wordFile) {
        this.wordFile = wordFile;
        this.wordOLE = null;
    }

    @Override
    public void run() {
        wordOLE = new WordOLEWindow(null,wordFile);
        wordOLE.setBlockOnOpen(true);
        wordOLE.open();

        Display.getCurrent().dispose();

        wordOLE.close();
    }

    public synchronized boolean isReadyToUse(){
        if(wordOLE == null){
            return false;
        }

        return wordOLE.isReady();
    }
}
