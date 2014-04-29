package org.voimala.jarzkachess.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import org.voimala.jarzkachess.inputdevices.ChessMouse;

/** The main purpose of this listener is to keep the Mouse singleton up to date. */
public class ChessMouseMotionListener implements MouseMotionListener {
    private static ChessMouseMotionListener instanceOfThis = null;
    private ChessMouse mouse = ChessMouse.getInstance();
    
    public static ChessMouseMotionListener getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new ChessMouseMotionListener();
        }
        
        return instanceOfThis;
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        mouse.setX((int) e.getPoint().getX());
        mouse.setY((int) e.getPoint().getY());
    }

    @Override
    public final void mouseMoved(final MouseEvent e) {
        mouse.setX((int) e.getPoint().getX());
        mouse.setY((int) e.getPoint().getY());
    }
}
