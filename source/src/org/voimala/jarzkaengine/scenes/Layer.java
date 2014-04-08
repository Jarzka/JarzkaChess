package org.voimala.jarzkaengine.scenes;

/**
 * Layer represents a layer in a scene. Typically a layer consists of gameplay objects.
 */
public abstract class Layer {
    Scene ownerScene = null;
    
    public Layer(Scene owner) {
        ownerScene = owner;
    }
    
    public Layer() {
        
    }
    
    public final Scene getOwnerScene() {
        return ownerScene;
    }
    
    public final void setOwnerScene(Scene owner) {
        ownerScene = owner;
    }
    
    public abstract String getName();
}
