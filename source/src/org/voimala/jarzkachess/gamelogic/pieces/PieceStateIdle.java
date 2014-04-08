package org.voimala.jarzkachess.gamelogic.pieces;


public class PieceStateIdle extends PieceState {
    public PieceStateIdle(final Piece owner) {
        super(owner);
    }

    @Override
    public void updateState() {
        // Do nothing
    }

    @Override
    public final PieceStateName getStateName() {
        return PieceStateName.PIECE_STATE_IDLE;
    }
}
