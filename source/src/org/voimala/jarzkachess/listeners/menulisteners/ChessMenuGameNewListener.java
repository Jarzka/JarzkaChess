package org.voimala.jarzkachess.listeners.menulisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.voimala.jarzkachess.programbody.ChessProgram;
import org.voimala.jarzkachess.scenes.SceneGameplay;

public class ChessMenuGameNewListener implements ActionListener {
    private static ChessMenuGameNewListener instanceOfThis = null;
    
    public static final ChessMenuGameNewListener getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new ChessMenuGameNewListener();
        }
        
        return instanceOfThis;
    }
    
    @Override
    public final void actionPerformed(ActionEvent e) {
        if (ChessProgram.getInstance().getMainCanvas().getScene().getName() == "GAMEPLAY") {
            SceneGameplay scene = (SceneGameplay)ChessProgram.getInstance().getMainCanvas().getScene();
            scene.getGameSession().reset();
        }
    }
}
