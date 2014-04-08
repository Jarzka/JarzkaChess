package org.voimala.jarzkachess.listeners.menulisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.voimala.jarzkachess.programbody.ChessProgram;

public class ChessMenuView75Listener implements ActionListener {
    private static ChessMenuView75Listener instanceOfThis = null;
    
    public static final ChessMenuView75Listener getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new ChessMenuView75Listener();
        }
        
        return instanceOfThis;
    }
    
    @Override
    public final void actionPerformed(ActionEvent e) {
         ChessProgram.getInstance().setGraphicsSize75();
    }
}
