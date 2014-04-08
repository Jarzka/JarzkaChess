package org.voimala.jarzkaengine.scenes;

/**
 * Layer represents a layer in a scene. Typically a layer consists of gameplay objects.
 */
public abstract class Layer {
    Scene ownerScene = null;
    
    public Layer(final Scene owner) {
        ownerScene = owner;
    }
    
    public final Scene getOwnerScene() {
        return ownerScene;
    }
    
    public final void setOwnerScene(final Scene owner) {
        ownerScene = owner;
    }
    
    public abstract String getName();
}
