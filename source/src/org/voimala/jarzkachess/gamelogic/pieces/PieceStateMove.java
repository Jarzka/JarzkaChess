package org.voimala.jarzkachess.gamelogic.pieces;

import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.programbody.ChessProgram;

public class PieceStateMove extends PieceState {
    private double moveProgressPercentage = 0;
    private Cell source = null;
    private Cell target = null;
    private double speed = 0.2;
    
    public PieceStateMove(final Piece owner, final Cell source, final Cell target) {
        super(owner);
        
        this.source = source;
        this.target = target;
    }

    @Override
    public final void updateState() {
        move();
        checkTargetCellReached();
    }

    private void move() {
        double timeDelta = getOwnerPiece().getOwnerTile().getOwnerGameboard().getOwnerGameSession()
                .getOwnerScene().getOwnerCanvas().getTimeDelta();
        moveProgressPercentage += speed * timeDelta;
    }

    private void checkTargetCellReached() {
        if (moveProgressPercentage >= 100) {
           getOwnerPiece().changeState(new PieceStateFinal(getOwnerPiece()));
           getOwnerPiece().getOwnerTile().getOwnerGameboard().movePieceImmediately(getOwnerPiece(), target);
        }
    }

    @Override
    public final PieceStateName getStateName() {
        return PieceStateName.PIECE_STATE_MOVE;
    }
    
    @Override
    public final double getMovementProgress() {
        return moveProgressPercentage;
    }
    
    public final Cell getTarget() {
        return target;
    }

}
