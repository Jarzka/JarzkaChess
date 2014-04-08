package org.voimala.jarzkaengine.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.voimala.jarzkaengine.programbody.CanvasProgram;

/**
 * A scene is an independent piece of the app workflow. Scene can be for example intro, main menu, gameplay etc.
 * A game app can have many scenes, but only one of them is active at a given time.
 */
public abstract class Scene {
    protected ArrayList<Layer> layers = new ArrayList<>();
    private double timeStampRenderStarted = 0;
    private long timestampFrameCountStarted = 0;
    private int countFrames = 0;
    private CanvasProgram canvasProgram = null;
    
    public Scene(CanvasProgram canvasProgram) {
        this.canvasProgram = canvasProgram;
    }
    
    public void updateState() {
        handleLogic();
        handleGraphics();
    }
    
    
    
    protected abstract void handleLogic();
    
    /** Renders the graphics based on the fps limiter. */
    public final void handleGraphics() {
         if (canvasProgram.isLimitFpsTurnedOn()) {
             // Render graphics only if (1 / fpsLimiter) seconds have passed.
            double timeToBeSpendForThisFrameInMilliseconds = 
                    ((double) 1000 / canvasProgram.getFpsLimiter());
            if (System.currentTimeMillis() >= timeStampRenderStarted + timeToBeSpendForThisFrameInMilliseconds) {
                renderGraphics();
            }
        } else {
            renderGraphics();
        }
    }
    
    private void renderGraphics() {
        timeStampRenderStarted = System.currentTimeMillis();
        
        clearScreen();
        renderGameGraphics();
        flipBuffer();
        countFps();
    }
    
    protected abstract void renderGameGraphics();

    private final void clearScreen() {
        Graphics2D graphics = (Graphics2D) canvasProgram.getBufferStrategy().getDrawGraphics();
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, canvasProgram.getWidth(), canvasProgram.getHeight());
    }

    private final void flipBuffer() {
        canvasProgram.getGraphics().dispose();
        canvasProgram.getBufferStrategy().show();
    }
    
    /** Counts how many frames we render in one second. */
    private final void countFps() {
        // Set a timestamp for the moment we started waiting for one second
        if (timestampFrameCountStarted == 0) {
            timestampFrameCountStarted = System.currentTimeMillis();
        }
        
        // One second passed, check how many frames we rendered
        if (timestampFrameCountStarted + 1000 <= System.currentTimeMillis()) { 
            canvasProgram.getWindow().setTitle(canvasProgram
                    .getAppName() + " " + "(" + countFrames + "fps" + ")"); // For testing purposes
            
            countFrames = 0;
            timestampFrameCountStarted = 0;
        }
        
        countFrames++;

    }


    public abstract String getName();
}