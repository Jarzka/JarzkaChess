package org.voimala.jarzkachess.gamelogic.players;

import org.voimala.jarzkachess.gamelogic.pieces.Piece;
import org.voimala.jarzkachess.gamelogic.pieces.PieceStateIdle;
import org.voimala.jarzkachess.gamelogic.pieces.PieceStateName;

/** The player is moving piece. */
public class PlayerStateMoving extends AbstractPlayerState {
    private Piece pieceMoving = null;
    
    /**
     * @param pieceMoving The piece that the player is currently moving
     */
    public PlayerStateMoving(final Player owner, final Piece pieceMoving) {
        super(owner);
        this.pieceMoving = pieceMoving;
    }

    @Override
    public final void updateState() {
        // Change state to final if the move is completed
        if (pieceMoving.getStateName() == PieceStateName.PIECE_STATE_FINAL) {
            getOwnerPlayer().changeState(new PlayerStateFinal(getOwnerPlayer(), pieceMoving));
            pieceMoving.changeState(new PieceStateIdle(pieceMoving));
        }
    }

    @Override
    public final PlayerStateName getStateName() {
        return PlayerStateName.MOVING;
    }

}
