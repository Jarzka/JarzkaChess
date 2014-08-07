package org.voimala.jarzkachess.gamelogic;

public class GameSessionStateGameOver extends GameSessionState {

    public GameSessionStateGameOver(final GameSession ownerGameSession) {
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
