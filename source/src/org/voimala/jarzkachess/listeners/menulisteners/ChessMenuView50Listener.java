package org.voimala.jarzkachess.listeners.menulisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.voimala.jarzkachess.programbody.ChessProgram;

public class ChessMenuView50Listener implements ActionListener {
    private static ChessMenuView50Listener instanceOfThis = null;
    
    public static ChessMenuView50Listener getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new ChessMenuView50Listener();
        }
        
        return instanceOfThis;
    }
    
    @Override
    public final void actionPerformed(ActionEvent e) {
        ChessProgram.getInstance().setGraphicsSize50();
    }
}
