package org.voimala.jarzkaengine.graphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;

/** 
 * A container for all animations. The game programmer should inherit this class and possibly
 * make it a singleton class.
 * 
 * All animation files should be in the following format: name_frameNumber.png.
 * For example name_23.png
 */
public abstract class AnimationContainer {
    private HashMap<String, Animation> animations = new HashMap<>();
    
    /**
     * @param name The animation's first image file name excluding the ending _XXXXX.png.
     */
    public final void makeAnimationFromSprites(SpriteContainer spriteContainer,
                                               final String name,
                                               final AnimationType type,
                                               final double speed)
            throws SpriteNotFoundException {
        ArrayList<Sprite> sprites = new ArrayList<>();
        
        int i = 0;
        while (true) {
            try {
                sprites.add(spriteContainer.getSprite(name + "_" + String.valueOf(i)));
            } catch (SpriteNotFoundException e) {
                // Was this the first try?
                if (i == 0) {
                    throw e; // The first animation sprite could not be loaded
                } else {
                    break; // The animation does not contain more files.
                }
            }

            i++;
        }
        
        // Create the animation
        Animation animation = new Animation(name, sprites, type, speed);
        animations.put(name, animation);
    }

    /**
     * @param name The image file name excluding the extension
     */
    public final Animation getAnimation(final String name) {
        if (animations.get(name) == null) {
            String errorMessage = "Animation named" + " " + name + " " + "not found!\n";
            errorMessage += "The following animations are loaded" + ":\n";
            
            for (String key : animations.keySet()) {
                errorMessage += key + "\n";
            }
            
            throw new SpriteNotFoundException(errorMessage);
        }

        return animations.get(name);
    }

    public abstract void loadAllAnimations(SpriteContainer spriteContainer) throws IOException;
}
