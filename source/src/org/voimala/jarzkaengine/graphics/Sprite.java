package org.voimala.jarzkaengine.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.voimala.jarzkaengine.utility.PositionPoint;

public class Sprite {
    private BufferedImage image = null;
    private SpriteContainer ownerContainer = null;
    
    public Sprite(final BufferedImage image, final SpriteContainer ownerContainer) {
        this.image = image;
        this.ownerContainer = ownerContainer;
    }
    
    public final int getWidth() {
        return image.getWidth(null);
    }
    
    public final int getHeight() {
        return image.getHeight(null);
    }
    
    public final void draw(final Graphics graphics, final PositionPoint position2d) {
        graphics.drawImage(image, position2d.getX(), position2d.getY(), null);
    }
    
    public final void draw(final Graphics graphics, final PositionPoint position2d, final int width, final int height) {
        graphics.drawImage(image, position2d.getX(), position2d.getY(), width, height, null);
    }
    
    public final SpriteContainer getOwnerContainer() {
        return ownerContainer;
    }
    
    public final BufferedImage getSourceImage() {
        return image;
    }
}
