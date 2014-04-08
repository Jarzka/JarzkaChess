package org.voimala.jarzkaengine.graphics;

import java.util.ArrayList;

import org.voimala.jarzkachess.programbody.ChessProgram;
import org.voimala.jarzkaengine.programbody.GameProgram;
import org.voimala.jarzkaengine.windows.mainwindow.ExtendedCanvas;

public class Animation {
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    private String name;
    private double speed = 0.2;
    private double frameCurrent = 1; /** 1-index. */
    private AnimationType type = AnimationType.ANIMATION_TYPE_RUN_ONCE;
    private AnimationDirection direction = AnimationDirection.ANIMATION_DIRECTION_FORWARD;
    
    public Animation(final String name, final ArrayList<Sprite> sprites, final AnimationType type, final double speed) {
        this.name = name;
        this.sprites = sprites;
        this.type = type;
        this.speed = speed;
    }

    public final ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public final Sprite getCurrentSprite() {
        return sprites.get((int) frameCurrent);
    }
    
    public final int getCurrentFrame() {
        return (int) frameCurrent;
    }

    public final void animate(final double timeDelta) {
        nextFrame(timeDelta);
        checkLastFrame();
    }
    
    private void nextFrame(final double timeDelta) {
        if (direction == AnimationDirection.ANIMATION_DIRECTION_FORWARD) {
            frameCurrent += speed * timeDelta;
            
        } else {
            frameCurrent -= speed * timeDelta;
        }
    }

    private void checkLastFrame() {
        // Check if the current frame is the last frame in the animation
        if (type == AnimationType.ANIMATION_TYPE_LOOP && (int) frameCurrent >= sprites.size()) {
            frameCurrent = 1;
        }
        
        if (type == AnimationType.ANIMATION_TYPE_BOUNCE) {
            if (direction == AnimationDirection.ANIMATION_DIRECTION_FORWARD
                    && (int) frameCurrent >= sprites.size()) {
                direction = AnimationDirection.ANIMATION_DIRECTION_BACKWARD;
            } else if (direction == AnimationDirection.ANIMATION_DIRECTION_BACKWARD
                    && (int) frameCurrent <= 1) {
                direction = AnimationDirection.ANIMATION_DIRECTION_FORWARD;
            }
        }
    }

    public final String getName() {
        return name;
    }
}