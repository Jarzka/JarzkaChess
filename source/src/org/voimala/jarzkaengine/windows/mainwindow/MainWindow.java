package org.voimala.jarzkaengine.windows.mainwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.voimala.jarzkaengine.listeners.MainWindowListener;
import org.voimala.jarzkaengine.programbody.Program;

public class MainWindow extends JFrame {
    protected Program ownerProgram = null;
    protected ExtendedCanvas mainCanvas = null;
    
    public MainWindow(final Program ownerProgram) {
        this.ownerProgram = ownerProgram;
        initializeEverything();
    }

    private void initializeEverything() {
        initializeWindow();
        initializeCanvas();
        loadListeners();
    }
    
    protected final void initializeWindow() {
        setTitle(ownerProgram.getAppName());
        this.setLayout(new BorderLayout());
        this.pack();
        this.setResizable(false);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }

    private void initializeCanvas() {
        mainCanvas = new MainCanvas(this, getOwnerProgram());
        this.add(mainCanvas, BorderLayout.CENTER);
        mainCanvas.initialize();
    }
    
    protected final void loadListeners() {
        this.addWindowListener(MainWindowListener.getInstance());
    }
    
    public final void updateState() {
        mainCanvas.updateState();
    }
    
    public final ExtendedCanvas getCanvas() {
        return mainCanvas;
    }
    
    public final Program getOwnerProgram() {
        return ownerProgram;
    }

}
