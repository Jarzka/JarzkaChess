package org.voimala.jarzkaengine.graphics;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;

/** 
 * A container for all sprites.
 * All sprite files used in the game should have an unique name.
 */
public abstract class SpriteContainer {
    private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    
    /**
     * @param name The image file name excluding the extension
     */
    public final Sprite getSprite(final String name) {
        if (sprites.get(name) == null) {
            String errorMessage = "Sprite named" + " " + name + " " + "not found!\n";
            errorMessage += "The following sprites are loaded" + ":\n";
            
            for (String key : sprites.keySet()) {
                errorMessage += key + "\n";
            }
            
            throw new SpriteNotFoundException(errorMessage);
        }

        return sprites.get(name);
    }

    public final void loadAllSprites() throws IOException {
        loadAllSingleSprites();
        loadAllAnimationSprites();
    }
    
    public abstract void loadAllSingleSprites() throws IOException;
    
    /**
     * Loads image file from the hard disk.
     * @param name Should be the same as the file name excluding the extension
     * @param path The relative path to the file including extension.
     * For example "Graphics\Gameboard\pawn_white.png"
     */
    public final Sprite loadSpriteFromFile(final String name, final String path) throws IOException {
        BufferedImage sourceImage = null;
        String pathFull = path;
        try {
            sourceImage = ImageIO.read(new File(pathFull));
        } catch (IOException e) {
            throw new IOException("Can not read input file" + ": " + pathFull);
        }
        
        // Create an accelerated image of the right size to store our sprite in
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage image = gc.createCompatibleImage(sourceImage.getWidth(),
                sourceImage.getHeight(),
                Transparency.TRANSLUCENT);
        
        // Draw our source image into the accelerated image
        image.getGraphics().drawImage(sourceImage, 0, 0, null);
        
        Sprite sprite = new Sprite(image, this);
        sprites.put(name, sprite);
        
        return sprite;
    }
    
    
    public abstract void loadAllAnimationSprites() throws IOException;
    
    /**
     * @param name The image file name excluding the ending _XXXXX.png.
     * @param path The path to the animations folder
     * For example "Graphics\Animations\Icons\loading_icon\"
     * @throws IOException 
     */
    public final void loadAnimationSprites(final String name, final String path) throws IOException {
            // Load all files starting from name_0.png.
            int i = 0;
            while (true) {
                try {
                    loadSpriteFromFile(name + "_" + String.valueOf(i), path + name + "_" + String.valueOf(i) + ".png");
                } catch (IOException e) {
                    // Was this the first try?
                    if (i == 0) {
                        throw e; // The first animation file could not be loaded
                    } else {
                        break; // The animation does not contain more files.
                    }
                }

                i++;
            }
        }
}
