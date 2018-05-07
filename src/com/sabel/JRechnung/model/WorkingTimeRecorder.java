package com.sabel.JRechnung.model;

import javax.swing.*;
import java.time.Duration;
import java.util.TimerTask;

public class WorkingTimeRecorder extends TimerTask {

    private JLabel lblOutput;
    private long lastTickTime;

    public WorkingTimeRecorder(JLabel lblOutput) {
        this.lblOutput = lblOutput;
        this.lastTickTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        long runningTime = System.currentTimeMillis() - lastTickTime;
        Duration duration = Duration.ofMillis(runningTime);
        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long millis = duration.toMillis();
        long seconds = millis / 1000;
        this.lblOutput.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }
}
