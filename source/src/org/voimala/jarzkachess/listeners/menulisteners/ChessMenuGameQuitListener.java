package org.voimala.jarzkachess.listeners.menulisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessMenuGameQuitListener implements ActionListener {
    private static ChessMenuGameQuitListener instanceOfThis = null;
    
    public static ChessMenuGameQuitListener getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new ChessMenuGameQuitListener();
        }
        
        return instanceOfThis;
    }
    
    @Override
    public final void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

}
