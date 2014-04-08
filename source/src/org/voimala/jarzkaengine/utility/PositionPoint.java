package org.voimala.jarzkaengine.utility;

public class PositionPoint implements Cloneable {
    private int x = 0;
    private int y = 0;
    private int z = 0;
    
    public PositionPoint(final int x, final int y) {
        setX(x);
        setY(y);
    }
    
    public PositionPoint(final int x, final int y, final int z) {
        setX(x);
        setY(y);
        this.setZ(z);
    }
    
    public PositionPoint clone() throws CloneNotSupportedException {
        return (PositionPoint) super.clone();
    }
    
    public final int getX() {
        return x;
    }
    
    public final void setX(final int x) {
        this.x = x;
    }
    
    public final int getY() {
        return y;
    }
    
    public final void setY(final int y) {
        this.y = y;
    }

    public final int getZ() {
        return z;
    }

    public final void setZ(final int z) {
        this.z = z;
    }
}
