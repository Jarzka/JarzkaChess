package org.voimala.jarzkachess.gamelogic;

public class GameSessionStateEnd extends GameSessionState {

    public GameSessionStateEnd(final GameSession ownerGameSession) {
        super(ownerGameSession);
    }

    @Override
    public void updateState() {
        // Do nothing.
    }

    @Override
    public final GameSessionStateName getStateName() {
        return GameSessionStateName.GAME_OVER;
    }

}
