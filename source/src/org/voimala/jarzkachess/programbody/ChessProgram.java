package org.voimala.jarzkachess.programbody;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.voimala.jarzkachess.graphics.ChessAnimationContainer;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkachess.listeners.ChessMouseListener;
import org.voimala.jarzkachess.listeners.ChessMouseMotionListener;
import org.voimala.jarzkachess.listeners.menulisteners.ChessMenuGameNewListener;
import org.voimala.jarzkachess.listeners.menulisteners.ChessMenuGameQuitListener;
import org.voimala.jarzkachess.listeners.menulisteners.ChessMenuView100Listener;
import org.voimala.jarzkachess.listeners.menulisteners.ChessMenuView50Listener;
import org.voimala.jarzkachess.listeners.menulisteners.ChessMenuView75Listener;
import org.voimala.jarzkachess.scenes.SceneGameplay;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;
import org.voimala.jarzkaengine.programbody.GameProgram;

/** This singleton represents the core body of the entire application. */
public class ChessProgram extends GameProgram {
    protected static ChessProgram instanceOfThis = null;
    
    // Top menus
    private JMenuBar menuBar = new JMenuBar();
    private JMenu jMenuGame = new JMenu("Game");
    private JMenu jMenuView = new JMenu("View");
    // Game
    private JMenuItem jMenuItemNew = new JMenuItem("New");
    private JMenuItem jMenuItemQuit = new JMenuItem("Quit");
    // View
    private JMenuItem jMenuItem100Percent = new JMenuItem("100%");
    private JMenuItem jMenuItem75Percent = new JMenuItem("75%");
    private JMenuItem jMenuItem50Percent = new JMenuItem("50%");
    
    // Global constants
    protected int tileSizeInPixels = 128;
    /** 
     * If set to 1, the program renders all tiles and objects in their actual size.
     * If set to 0.5, the program renders all tiles and objects in the half of the actual size.
     * */
    protected double gameplayGraphicsSize = 1;
    
    private ChessProgram() {
        super("JarzkaChess");
    }
    
    public static final ChessProgram getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new ChessProgram();
        }
        
        return instanceOfThis;
    }
    
    @Override
    protected void initializeMainWindow() {
        super.initializeMainWindow();
        setGraphicsSize75();
        getMainWindow().setSize(tileSizeInPixels * 8, tileSizeInPixels * 8);
    }
    
    public final void setGraphicsSize75() {
        setGameplayGraphicsSize(0.75);
        /* TODO For some reason tileSizeInPixels * 9 gives a wrong result and
         * thats why " - 45 " is a temporary fix for that. */
        getMainWindow().setSize(tileSizeInPixels * 8, tileSizeInPixels * 9 - 45);
    }
    
    public final void setGraphicsSize50() {
        setGameplayGraphicsSize(0.5);
        /* TODO For some reason tileSizeInPixels * 9 gives a wrong result and
         * thats why " - 14 " is a temporary fix for that. */
        getMainWindow().setSize(tileSizeInPixels * 8, tileSizeInPixels * 9 - 14);
    }
    
    public final void setGraphicsSize100() {
        setGameplayGraphicsSize(1);
        getMainWindow().setSize(tileSizeInPixels * 8, tileSizeInPixels * 8);
    }
    
    @Override
    protected void initializeListeners() {
        super.initializeListeners();
        getMainCanvas().addMouseListener(ChessMouseListener.getInstance());
        getMainCanvas().addMouseMotionListener(ChessMouseMotionListener.getInstance());
        
        jMenuItemNew.addActionListener(ChessMenuGameNewListener.getInstance());
        jMenuItemQuit.addActionListener(ChessMenuGameQuitListener.getInstance());
        jMenuItem100Percent.addActionListener(ChessMenuView100Listener.getInstance());
        jMenuItem75Percent.addActionListener(ChessMenuView75Listener.getInstance());
        jMenuItem50Percent.addActionListener(ChessMenuView50Listener.getInstance());
    }
    
    /** Adds all graphic files to the SpriteContainer as Sprite objects. */
    @Override
    protected void loadGraphicFiles() throws IOException {
        ChessSpriteContainer container = ChessSpriteContainer.getInstance();
        container.loadAllSprites();
    }
    
    /** Adds all animation files to the AnimationContainer as Animation objects. */
    @Override
    protected void loadAnimations() throws SpriteNotFoundException {
        ChessAnimationContainer container = ChessAnimationContainer.getInstance();
        container.loadAllAnimations(ChessSpriteContainer.getInstance());
    }
   
    @Override
    protected void initializeMenuBar() {
        super.initializeMenuBar();
        
        // Construct top menus
        menuBar.add(jMenuGame);
        menuBar.add(jMenuView);
        
        // Construct sub menus
        
        // Game
        jMenuGame.add(jMenuItemNew);
        jMenuGame.add(jMenuItemQuit);
        // View
        jMenuView.add(jMenuItem100Percent);
        jMenuView.add(jMenuItem75Percent);
        jMenuView.add(jMenuItem50Percent);
        
        getMainWindow().setJMenuBar(menuBar);
        /* Turns out that menu bar does not show up if the main window is set to
         * visible before the menu bar is added to it. */
        getMainWindow().setVisible(true);
    }
    
    @Override
    protected void initializeScenes() {
        super.initializeScenes();
        getMainCanvas().setScene(new SceneGameplay(getMainCanvas()));
    }

    public final double getGameplayGraphicsSize() {
        return gameplayGraphicsSize;
    }

    public final void setGameplayGraphicsSize(final double gameplayGraphicsSize) {
        this.gameplayGraphicsSize = gameplayGraphicsSize;
        this.tileSizeInPixels = (int) (128 * gameplayGraphicsSize);
    }
    
    public final int getTileSize() {
        return tileSizeInPixels;
    }
}
