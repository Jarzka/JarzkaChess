package org.voimala.jarzkaengine.windows.mainwindow;

import org.voimala.jarzkaengine.programbody.Program;
import org.voimala.jarzkaengine.scenes.Scene;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This is mostly the same as Java's Canvas class but it adds fps and timedelta calculation functionality.
 */
public class ExtendedCanvas extends Canvas {
    private JFrame ownerJFrame = null;
    private Program ownerProgram = null;
    
    private ArrayList<Scene> scenes = new ArrayList<>();
    private Scene currentScene = null;
    
    private long timeDeltaTimestampInMilliseconds = 0;
    private long timeDeltaInMilliseconds = 0;
    private int fpsLimiter = 80;
    private boolean limitFps = true;
    
    public ExtendedCanvas(final JFrame ownerJFrame, final Program ownerProgram) {
        this.ownerProgram = ownerProgram;
        this.ownerJFrame = ownerJFrame;
    }

    public final JFrame getOwnerJFrame() {
        return ownerJFrame;
    }
    
    public final Program getOwnerProgram() {
        return ownerProgram;
    }

    public final void initialize() {
        initializeCanvas();
        initializeFonts();
    }
    
    private void initializeCanvas() {
        this.requestFocus();
        this.setIgnoreRepaint(true);
        this.createBufferStrategy(2);
    }
    
    public final void updateState() {
        countTimeDelta();
        currentScene.updateState();
    }
    
    public final Scene getScene() {
        return currentScene;
    }
    
    public final void setScene(final Scene newScene) {
        this.currentScene = newScene;
    }
    
    public final double getTimeDelta() {
        return timeDeltaInMilliseconds;
    }

    public final boolean isLimitFpsTurnedOn() {
        return limitFps;
    }

    public final void setLimitFps(final boolean limitFps) {
        this.limitFps = limitFps;
    }

    public final int getFpsLimiter() {
        return fpsLimiter;
    }

    public final void setFpsLimiter(final int fpsLimiter) {
        this.fpsLimiter = fpsLimiter;
    }
    
    private void countTimeDelta() {
        // If timestampTimeDelta is zero, this is the first time we call this method (first frame)
        // Set timedelta to the current time for the first frame
        if (timeDeltaTimestampInMilliseconds == 0) { 
            timeDeltaTimestampInMilliseconds = System.currentTimeMillis();
        }
        
        // Calculate the difference between current time and the last time we called this method.
        timeDeltaInMilliseconds = System.currentTimeMillis() - timeDeltaTimestampInMilliseconds;
        timeDeltaTimestampInMilliseconds = System.currentTimeMillis();
    }
    
    private void initializeFonts() {
        setDefaultFont();
    }
    
    public final void setDefaultFont() {
        this.setForeground(Color.BLACK);
        this.setFont(new Font("Serif", Font.PLAIN, 36));
    }

    public final ArrayList<Scene> getScenes() {
        return scenes;
    }

    public final void addScene(final Scene scene) {
        scenes.add(scene);
    }

}
