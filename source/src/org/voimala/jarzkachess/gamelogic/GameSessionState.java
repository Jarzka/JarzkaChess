package org.voimala.jarzkachess.gamelogic;

public abstract class GameSessionState {
    private GameSession ownerGameSession = null;
    
    public GameSessionState(final GameSession ownerGameSession) {
        this.ownerGameSession = ownerGameSession;
    }
    
    public final GameSession getOwnerGameSession() {
        return ownerGameSession;
    }
    
    public abstract void updateState();
    public abstract GameSessionStateName getStateName();
}
