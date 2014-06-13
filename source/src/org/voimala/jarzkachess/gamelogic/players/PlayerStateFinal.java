package org.voimala.jarzkachess.gamelogic.players;

import org.voimala.jarzkachess.gamelogic.pieces.Piece;

public class PlayerStateFinal extends AbstractPlayerState {
    private Piece pieceMoved = null;
    
    /**
     * @param pieceMoved The piece that the player just moved
     */
    public PlayerStateFinal(final Player owner, final Piece pieceMoved) {
        super(owner);
        this.pieceMoved = pieceMoved;
    }

    @Override
    public void updateState() {
        // Do nothing special here.
    }

    @Override
    public final PlayerStateName getStateName() {
        return PlayerStateName.FINAL;
    }
}
