package org.voimala.jarzkachess.gamelogic.pieces;


public abstract class AbstractPieceState {
    private Piece ownerPiece;
    
    public AbstractPieceState(final Piece owner) {
        this.ownerPiece = owner;
    }
    
    public abstract void updateState();
    public abstract PieceStateName getStateName();

    public final Piece getOwnerPiece() {
        return ownerPiece;
    }

    public double getMovementProgress() {
        return 0;
    }
}
