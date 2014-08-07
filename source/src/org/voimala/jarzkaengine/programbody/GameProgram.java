package org.voimala.jarzkaengine.programbody;

import java.io.IOException;

import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;
import org.voimala.jarzkaengine.windows.mainwindow.ExtendedCanvas;
import org.voimala.jarzkaengine.windows.mainwindow.MainWindow;

public abstract class GameProgram extends Program {
    
    private boolean isRunning = true;
    private MainWindow mainWindow = null;

    public GameProgram(final String appName) {
        super(appName);
    }
    
    public final MainWindow getMainWindow() {
        return mainWindow;
    }
    
    public final void run() throws IOException {
        initialize();
        runMainLoop();
    }

    protected final void initialize() throws IOException {
        initializeMainWindow();
        initializeResources();
        initializeMenuBar();
        initializeScenes();
    }
    
    protected void initializeMainWindow() {
        mainWindow = new MainWindow(this);
    }

    /** Loads the external files that the game uses. */
    private void initializeResources() throws IOException {
        loadGraphicFiles();
        loadAnimations();
    }
    
    /* Inherited classes can implement these methods. */
    protected void loadGraphicFiles() throws IOException {}
    protected void loadAnimations() throws SpriteNotFoundException {}
    protected void initializeMenuBar() {}
    protected void initializeScenes() {}
    
    protected final void runMainLoop() {
        while (isRunning) {
            mainWindow.updateState();
        }
    }
    
    public final ExtendedCanvas getMainCanvas() {
        return mainWindow.getCanvas();
    }
    
    public final boolean isRunning() {
        return isRunning;
    }

}
