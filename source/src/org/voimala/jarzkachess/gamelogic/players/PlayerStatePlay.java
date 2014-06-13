package org.voimala.jarzkachess.gamelogic.players;

public abstract class PlayerStatePlay extends AbstractPlayerState {

    public PlayerStatePlay(final AbstractPlayer owner) {
        super(owner);
    }

    @Override
    public final PlayerStateName getStateName() {
        return PlayerStateName.PLAY;
    }
}
