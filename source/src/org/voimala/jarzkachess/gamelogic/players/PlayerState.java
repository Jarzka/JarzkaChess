package org.voimala.jarzkachess.gamelogic.players;

public abstract class PlayerState {
    private Player ownerPlayer;
    
    public PlayerState(final Player owner) {
        this.ownerPlayer = owner;
    }
    
    public abstract void updateState();
    public abstract PlayerStateName getStateName();

    public final Player getOwnerPlayer() {
        return ownerPlayer;
    }
}
