package org.voimala.jarzkachess.gamelogic.players;

public abstract class PlayerStatePlay extends PlayerState {

    public PlayerStatePlay(final Player owner) {
        super(owner);
    }

    @Override
    public final PlayerStateName getStateName() {
        return PlayerStateName.PLAYER_STATE_PLAY;
    }
}
