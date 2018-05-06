package com.sabel.JRechnung;

public class ConsoleLogger implements Logger {

    @Override
    public void loginfo(String info) {
        System.out.println("[Info]:: " + info + String.format("%n"));
    }

    @Override
    public void logwarning(String warning) {
        System.out.println("[Warning]:: " + warning + String.format("%n"));
    }

    @Override
    public void logerror(String error) {
        System.out.println("[Error]:: " + error + String.format("%n"));
    }
}
