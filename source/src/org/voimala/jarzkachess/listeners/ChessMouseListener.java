package org.voimala.jarzkachess.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.voimala.jarzkachess.inputdevices.ChessMouse;
import org.voimala.jarzkaengine.inputdevices.MouseButtonState;

/** The main purpose of this listener is to keep the Mouse singleton up to date. */
public class ChessMouseListener implements MouseListener {
    private static ChessMouseListener instanceOfThis = null;
    private ChessMouse mouse = ChessMouse.getInstance();
    
    public static final ChessMouseListener getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new ChessMouseListener();
        }
        
        return instanceOfThis;
    }

    @Override
    public final void mouseClicked(final MouseEvent e) { 
    }

    @Override
    public final void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public final void mousePressed(final MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouse.setLeftButtonState(MouseButtonState.PRESSED);
        }
        
        if (e.getButton() == MouseEvent.BUTTON3) {
            mouse.setRightButtonState(MouseButtonState.PRESSED);
        }
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouse.setLeftButtonState(MouseButtonState.RELEASED);
        }
        
        if (e.getButton() == MouseEvent.BUTTON3) {
            mouse.setRightButtonState(MouseButtonState.RELEASED);
        }
    }
}
