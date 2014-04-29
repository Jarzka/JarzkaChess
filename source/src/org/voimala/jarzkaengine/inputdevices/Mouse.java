package org.voimala.jarzkaengine.inputdevices;

/**
 * This class represents the mouse input device. The game developer should inherit this class and possibly
 * make it a singleton class.
 * 
 * The reason why we are using a separate Mouse class rather than using Java's own classes
 * is that it makes the implementation not so depended on the Java context.
 * 
 * Only Java's real mouse observer/listener should use the set methods to update the mouse's state to this class.
 */

public abstract class Mouse {
    private MouseButtonState leftButtonState = MouseButtonState.RELEASED;
    private MouseButtonState rightButtonState = MouseButtonState.RELEASED;
    private double timeStampLeftButtonPressedInMs = 0;
    private double timeStampRightButtonPressedInMs = 0;
    private int x = 0;
    private int y = 0;

    public final MouseButtonState getLeftButtonState() {
        return leftButtonState;
    }

    /** Only Java's real mouse observer/listener should call this method. */
    public final void setLeftButtonState(final MouseButtonState leftButtonState) {
        this.leftButtonState = leftButtonState;
        
        if (leftButtonState == MouseButtonState.PRESSED) {
            timeStampLeftButtonPressedInMs = System.currentTimeMillis();
        }
    }

    public final MouseButtonState getRightButtonState() {
        return rightButtonState;
    }
    
    /** Returns true if less than 0.05 seconds have passed since the user
     * pressed the button. */
    public boolean clickedLeftButton() {
        return timeStampLeftButtonPressedInMs + 50 < System.currentTimeMillis();
    }
    
    /** Returns true if less than 0.05 seconds have passed since the user
     * pressed the button. */
    public boolean clickedRightButton() {
        return timeStampRightButtonPressedInMs + 50 < System.currentTimeMillis();
    }

    /** Only Java's real mouse observer/listener should call this method. */
    public final void setRightButtonState(final MouseButtonState rightButtonState) {
        this.rightButtonState = rightButtonState;
        
        if (rightButtonState == MouseButtonState.PRESSED) {
            timeStampRightButtonPressedInMs = System.currentTimeMillis();
        }
    }
    
    /** Returns the pointer's current position inside the game window. */
    public final int getX() {
        return x;
    }
    
    /** Returns the pointer's current position inside the game window. */
    public final int getY() {
        return y;
    }

    /** Only Java's real mouse observer/listener should call this method. */
    public final void setX(final int x) {
        this.x = x;
    }
    
    /** Only Java's real mouse observer/listener should call this method. */
    public final void setY(final int y) {
        this.y = y;
    }
}
