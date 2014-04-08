package org.voimala.jarzkaengine.gamelogic;

import java.util.ArrayList;

import org.voimala.jarzkaengine.graphics.Animation;
import org.voimala.jarzkaengine.graphics.Sprite;
import org.voimala.jarzkaengine.scenes.Layer;
import org.voimala.jarzkaengine.scenes.Scene;
import org.voimala.jarzkaengine.utility.PositionPoint;

public abstract class GameplayObject implements Cloneable {
    private Sprite sprite = null;
    private ArrayList<Animation> animations = new ArrayList<>();
    private Animation animationCurrent = null;
    private Layer ownerLayer = null;
    private PositionPoint globalPosition = new PositionPoint(0, 0, 0);
    private PositionPoint anchorPosition = new PositionPoint(0, 0, 0);
    private double angle = 0;
    
    public GameplayObject(final Layer ownerLayer) {
        this.ownerLayer = ownerLayer;
    }
    
    public GameplayObject() {
    }
    
    /** The cloned object will have the same parent layer as the source. */
    public GameplayObject clone() {
        try {
            GameplayObject clone = (GameplayObject) super.clone();
            clone.setAnchorPosition(this.getAnchorPosition().clone());
            clone.setGlobalPosition(this.getGlobalPosition().clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * The render engine uses this method to get the object's current sprite.
     * The gameplay object always makes sure that the returned sprite corresponds
     * the object's current state.
     */
    public Sprite getSprite() {
        if (sprite == null) {
            throw new NullPointerException(this.getClass().getName() + " " + "does not have a sprite!");
        }
        
        return sprite;
    }
    
    public final void setSprite(final Sprite sprite) {
        if (sprite == null) {
            throw new NullPointerException("GameplayObject.setSprite: Sprite is null!");
        }
        
        this.sprite = sprite;
    }


    public final Layer getOwnerLayer() {
        return ownerLayer;
    }
    
    public final void setOwnerLayer(Layer owner) {
        ownerLayer = owner;
    }
    
    public abstract void loadSprite();

    public final PositionPoint getAnchorPosition() {
        return anchorPosition;
    }

    public final void setAnchorPosition(PositionPoint anchorPosition) {
        this.anchorPosition = anchorPosition;
    }

    public final PositionPoint getGlobalPosition() {
        return globalPosition;
    }

    public final void setGlobalPosition(PositionPoint globalPosition) {
        this.globalPosition = globalPosition;
    }

    public Scene getOwnerScene() {
        return ownerLayer.getOwnerScene();
    }

    public final double getAngle() {
        return angle;
    }

    /** @param angle Angle must be between 0 and 360. */
    public final void setAngle(double angle) {
        if (angle < 0) {
            this.angle = 0;
        }
        
        if (angle > 360) {
            this.angle = 360;
        }
        
        this.angle = angle;
    }

    public Animation getAnimationCurrent() {
        return animationCurrent;
    }
}
