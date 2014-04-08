package org.voimala.jarzkachess.gamelogic.pieces;

/** The piece has reached it's target tile. */
public class PieceStateFinal extends PieceState {
    public PieceStateFinal(final Piece owner) {
        super(owner);
    }

    @Override
    public final void updateState() {
        // Do nothing special here
    }

    @Override
    public final PieceStateName getStateName() {
        return PieceStateName.PIECE_STATE_FINAL;
    }
}
