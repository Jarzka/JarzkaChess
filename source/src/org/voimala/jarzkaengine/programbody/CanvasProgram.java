package org.voimala.jarzkaengine.programbody;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;
import org.voimala.jarzkaengine.listeners.EngineWindowListener;
import org.voimala.jarzkaengine.scenes.Scene;

public abstract class CanvasProgram extends Canvas {
    protected JFrame jFrameMain = null;
    protected boolean isRunning = true;
    
    protected long timeDeltaTimestampInMilliseconds = 0;
    protected long timeDeltaInMilliseconds = 0;
    protected int fpsLimiter = 80;
    protected boolean limitFps = true;
    
    protected String appName = "";
    protected static Logger logger = Logger.getLogger("CanvasProgram");
    
    protected Scene sceneCurrent = null;
    
    protected CanvasProgram(String appName) {
        this.appName = appName;
        setupLogger();
    }
    
    private final static void setupLogger() {
        CanvasProgram.logger.setLevel(Level.OFF);
    }
    
    public final JFrame getWindow() {
        return jFrameMain;
    }
    
    public final void run() throws IOException {
        initialize();
        runMainLoop();
    }
    
    protected void initialize() throws IOException {
        initializeWindow();
        loadListeners();
        loadResources();
    }
    
    protected void loadListeners() {
        jFrameMain.addWindowListener(EngineWindowListener.getInstance());
    }
    
    /** Loads the external files that the game uses. */
    private void loadResources() throws IOException {
        loadGraphicFiles();
        loadAnimations();
    }
    
    protected final void runMainLoop() {
        while (isRunning) {
            countTimeDelta();
            sceneCurrent.updateState();
        }
    }
    
    public final Scene getScene() {
        return sceneCurrent;
    }
    
    protected void loadGraphicFiles() throws IOException {}
    protected void loadAnimations() throws SpriteNotFoundException {}
    
    private void countTimeDelta() {
        // If timestampTimeDelta is zero, this is the first time we call this method (first frame)
        // Set timedelta to the current time for the first frame
        if (timeDeltaTimestampInMilliseconds == 0) { 
            timeDeltaTimestampInMilliseconds = System.currentTimeMillis();
        }
        
        // Calculate the difference between current time and the last time we called this method (looped)
        timeDeltaInMilliseconds = System.currentTimeMillis() - timeDeltaTimestampInMilliseconds;
        timeDeltaTimestampInMilliseconds = System.currentTimeMillis();
    }
    
    protected void initializeWindow() {
        jFrameMain = new JFrame(appName);
        jFrameMain.setLayout(new BorderLayout());
        jFrameMain.add(this, BorderLayout.CENTER);

        // request the focus so that key events come to us
        requestFocus();
        
        // Tell AWT not to bother repainting our canvas since we're going to do that our self in accelerated mode
        setIgnoreRepaint(true);
                
        jFrameMain.pack();
        jFrameMain.setResizable(false);
       
        // Windows Size and location
        jFrameMain.setSize(800, 600);
        jFrameMain.setLocationRelativeTo(null);
        
        createBufferStrategy(2); // Use 2 buffers

        initializeFonts();
    }

    private final void initializeFonts() {
        setDefaultFont();
    }
    
    public final void setDefaultFont() {
        setForeground(Color.BLACK);
        setFont(new Font("Serif", Font.PLAIN, 36));
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
    
    public final String getAppName() {
        return appName;
    }
    
    public final Graphics getDrawGraphics() {
        return getBufferStrategy().getDrawGraphics();
    }
}
