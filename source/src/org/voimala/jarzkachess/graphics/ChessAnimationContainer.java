package org.voimala.jarzkachess.graphics;

import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;
import org.voimala.jarzkaengine.graphics.AnimationContainer;
import org.voimala.jarzkaengine.graphics.AnimationType;
import org.voimala.jarzkaengine.graphics.SpriteContainer;


public class ChessAnimationContainer extends AnimationContainer {
    private static ChessAnimationContainer instanceOfThis = null;
    
    public static final ChessAnimationContainer getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new ChessAnimationContainer();
        }
        
        return instanceOfThis;
    }
    
    public final void loadAllAnimations(SpriteContainer spriteContainer) throws SpriteNotFoundException {
        makeAnimationFromSprites(spriteContainer, "loading_icon", AnimationType.ANIMATION_TYPE_LOOP, 0.05);
    }
}
