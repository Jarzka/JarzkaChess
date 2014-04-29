package org.voimala.jarzkaengine.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/** This singleton is the main WindowListener of the Program. */
public class MainWindowListener implements WindowListener {
    private static MainWindowListener instanceOfThis = null;
    
    public static MainWindowListener getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new MainWindowListener();
        }
        
        return instanceOfThis;
    }
    @Override
    public void windowActivated(final WindowEvent e) {
    }

    @Override
    public void windowClosed(final WindowEvent e) {
    }

    @Override
    public void windowDeactivated(final WindowEvent e) {
    }

    @Override
    public void windowDeiconified(final WindowEvent e) {
    }

    @Override
    public void windowIconified(final WindowEvent e) {
    }

    @Override
    public void windowOpened(final WindowEvent e) {
    }

    @Override
    public final void windowClosing(final WindowEvent e) {
        System.exit(0);
    }
}
