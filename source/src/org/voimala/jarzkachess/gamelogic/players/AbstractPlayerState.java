package org.voimala.jarzkachess.gamelogic.players;

public abstract class AbstractPlayerState {
    private AbstractPlayer ownerPlayer;
    
    public AbstractPlayerState(final AbstractPlayer owner) {
        this.ownerPlayer = owner;
    }
    
    public abstract void updateState();
    public abstract PlayerStateName getStateName();

    public final AbstractPlayer getOwnerPlayer() {
        return ownerPlayer;
    }
}
