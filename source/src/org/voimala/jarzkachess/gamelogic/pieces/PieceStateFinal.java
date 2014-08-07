package org.voimala.jarzkachess.gamelogic.pieces;

/** The piece has reached it's target tile. */
public class PieceStateFinal extends AbstractPieceState {
    public PieceStateFinal(final Piece owner) {
        super(owner);
    }

    @Override
    public final void updateState() {
        // Do nothing special here
    }

    @Override
    public final PieceStateName getStateName() {
        return PieceStateName.MOVED;
    }
}
