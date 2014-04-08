package org.voimala.jarzkachess.gamelogic.players;

/** The opponent his playing and this player waits. */
public class PlayerStateIdle extends PlayerState {

    public PlayerStateIdle(final Player owner) {
        super(owner);
    }

    @Override
    public void updateState() {
        // No need to do anything
    }

    @Override
    public final PlayerStateName getStateName() {
        return PlayerStateName.PLAYER_STATE_IDLE;
    }

}
