package org.voimala.jarzkachess.gamelogic.players;

public abstract class AbstractPlayerState {
    private Player ownerPlayer;
    
    public AbstractPlayerState(final Player owner) {
        this.ownerPlayer = owner;
    }
    
    public abstract void updateState();
    public abstract PlayerStateName getStateName();

    public final Player getOwnerPlayer() {
        return ownerPlayer;
    }
}
