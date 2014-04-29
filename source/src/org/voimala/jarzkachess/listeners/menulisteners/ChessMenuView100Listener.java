package org.voimala.jarzkachess.listeners.menulisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.voimala.jarzkachess.programbody.ChessProgram;

public class ChessMenuView100Listener implements ActionListener {
    private static ChessMenuView100Listener instanceOfThis = null;
    
    public static ChessMenuView100Listener getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new ChessMenuView100Listener();
        }
        
        return instanceOfThis;
    }
    
    @Override
    public final void actionPerformed(ActionEvent e) {
        ChessProgram.getInstance().setGraphicsSize100();
    }
}
