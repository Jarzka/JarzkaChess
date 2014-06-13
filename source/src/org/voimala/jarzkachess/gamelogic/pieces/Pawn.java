package org.voimala.jarzkachess.gamelogic.pieces;

import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.Move;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece implements Cloneable {
    
    public Pawn(final int playerOwner) {
        super(playerOwner);
        try {
            loadSprite();
        } catch (SpriteNotFoundException e) {
            // Continue without the sprite.
        }
    }
    
    public Pawn clone() throws CloneNotSupportedException {
        return (Pawn) super.clone();
    }
    
    @Override
    protected final List<Move> findPossibleRegularMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        
        // Get possible moves for white/black
        if (getOwnerPlayerNumber() == 1) {
            moves.addAll(findPossibleRegularMovesForWhite());
        } else if (getOwnerPlayerNumber() == 2) {
            moves.addAll(findPossibleRegularMovesForBlack());
        }
        
        return moves;
    }
    
    @Override
    protected final List<Move> findPossibleAttackMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        
        // Get possible moves for white/black
        if (getOwnerPlayerNumber() == 1) {
            moves.addAll(findPossibleAttackMovesForWhite());
        } else if (getOwnerPlayerNumber() == 2) {
            moves.addAll(findPossibleAttackMovesForBlack());
        }
        
        return moves;
    }
    
    @Override
    protected final List<Move> findPossibleSpecialMoves() {
        // TODO Needs to be implemented
        return new ArrayList<>();
    }

    private List<Move> findPossibleRegularMovesForWhite() {
        ArrayList<Move> moves = new ArrayList<>();
        moves.addAll(findPossibleRegularMovesForWhiteStartingRow());
        moves.addAll(findPossibleRegularMovesForWhiteNormalMoveForward());
        return moves;
    }
    
    private List<Move> findPossibleRegularMovesForBlack() {
        ArrayList<Move> moves = new ArrayList<>();
        moves.addAll(findPossibleRegularMovesForBlackStartingRow());
        moves.addAll(findPossibleRegularMovesForBlackNormalMoveForward());
        return moves;
    }
    
    private List<Move> findPossibleRegularMovesForWhiteStartingRow() {
        /* If the pawn is on the starting row it can be moved two tiles forward
         * but only if the next tiles do not contain any pieces
         */
        ArrayList<Move> moves = new ArrayList<>();
        if (getOwnerTile().getRow() != 7) {
            return moves;
        }

        if (!getOwnerTile().getTileAbove().hasPiece() && !getOwnerTile().getTileAbove().getTileAbove().hasPiece()) {
            moves.add(new Move(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() - 2, getOwnerTile().getColumn())));
        }
        
        return moves;
    }
    
    private List<Move> findPossibleRegularMovesForBlackStartingRow() {
        /* If the pawn is on the starting row it can be moved two tiles forward
         * but only if the next tiles do not contain any pieces
         */
        ArrayList<Move> moves = new ArrayList<>();
        if (getOwnerTile().getRow() != 2) {
            return moves;
        }

        if (!getOwnerTile().getTileBelow().hasPiece() && !getOwnerTile().getTileBelow().getTileBelow().hasPiece()) {
            moves.add(new Move(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() + 2, getOwnerTile().getColumn())));
        }
        
        return moves;
    }
    
    private List<Move> findPossibleRegularMovesForWhiteNormalMoveForward() {
        /* If the pawn has left it's starting row, but has not reached the other side of the gameboard,
         * it is possible to move the pawn one tile forward. Of course this works only
         * if the next tile does not contain any pieces
         */
        ArrayList<Move> moves = new ArrayList<>();
        if (!getOwnerTile().getTileAbove().hasPiece()) {
                moves.add(new Move(new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                        new Cell(getOwnerTile().getRow() - 1, getOwnerTile().getColumn())));
        }
        
        return moves;
    }
    
    private List<Move> findPossibleRegularMovesForBlackNormalMoveForward() {
        /* If the pawn has left it's starting row, but has not reached the other side of the gameboard,
         * it is possible to move the pawn one tile forward. Of course this works only
         * if the next tile does not contain any pieces
         */
        ArrayList<Move> moves = new ArrayList<>();
        if (!getOwnerTile().getTileBelow().hasPiece()) {
                moves.add(new Move(new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                        new Cell(getOwnerTile().getRow() + 1, getOwnerTile().getColumn())));
        }
        
        return moves;
    }
    
    private List<Move> findPossibleAttackMovesForWhite() {
        ArrayList<Move> moves = new ArrayList<>();
        moves.addAll(findPossibleAttackMovesForWhiteLeft());
        moves.addAll(findPossibleAttackMovesForWhiteRight());
        return moves;
    }
    
    private List<Move> findPossibleAttackMovesForBlack() {
        ArrayList<Move> moves = new ArrayList<>();
        moves.addAll(findPossibleAttackMovesForBlackLeft());
        moves.addAll(findPossibleAttackMovesForBlackRight());
        return moves;
    }
    
    private List<Move> findPossibleAttackMovesForWhiteLeft() {
        ArrayList<Move> moves = new ArrayList<>();
        
        if (getOwnerTile().getAdjacentTile(-1, -1) == null) {
            return moves;
        }
        
        if (!getOwnerTile().getAdjacentTile(-1, -1).hasPiece()) {
            return moves;
        }
        
        if (getOwnerTile().getAdjacentTile(-1, -1).getPiece().getOwnerPlayerNumber() != getOwnerPlayerNumber()) {
            moves.add(new Move(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() - 1, getOwnerTile().getColumn() - 1)));
        }
        
        return moves;
    }
    
    private List<Move> findPossibleAttackMovesForWhiteRight() {
        ArrayList<Move> moves = new ArrayList<>();
        
        if (getOwnerTile().getAdjacentTile(-1, 1) == null) {
            return moves;
        }
        
        if (!getOwnerTile().getAdjacentTile(-1, 1).hasPiece()) {
            return moves;
        }
        
        if (getOwnerTile().getAdjacentTile(-1, 1).getPiece().getOwnerPlayerNumber() != getOwnerPlayerNumber()) {
            moves.add(new Move(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() - 1, getOwnerTile().getColumn() + 1)));
        }
        
        return moves;
    }
    
    private List<Move> findPossibleAttackMovesForBlackLeft() {
        ArrayList<Move> moves = new ArrayList<>();
        
        if (getOwnerTile().getAdjacentTile(1, -1) == null) {
            return moves;
        }
        
        if (!getOwnerTile().getAdjacentTile(1, -1).hasPiece()) {
            return moves;
        }
        
        if (getOwnerTile().getAdjacentTile(1, -1).getPiece().getOwnerPlayerNumber() != getOwnerPlayerNumber()) {
            moves.add(new Move(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() + 1, getOwnerTile().getColumn() - 1)));
        }
        
        return moves;
    }
    
    private List<Move> findPossibleAttackMovesForBlackRight() {
        ArrayList<Move> moves = new ArrayList<>();
        
        if (getOwnerTile().getAdjacentTile(1, 1) == null) {
            return moves;
        }
        
        if (!getOwnerTile().getAdjacentTile(1, 1).hasPiece()) {
            return moves;
        }
        
        if (getOwnerTile().getAdjacentTile(1, 1).getPiece().getOwnerPlayerNumber() != getOwnerPlayerNumber()) {
            moves.add(new Move(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() + 1, getOwnerTile().getColumn() + 1)));
        }
        
        return moves;
    }

    @Override
    public final void loadSprite() {
        if (getOwnerPlayerNumber() == 1) {
            setSprite(ChessSpriteContainer.getInstance().getSprite("pawn_white"));
        } else {
            setSprite(ChessSpriteContainer.getInstance().getSprite("pawn_black"));
        }
    }
    
    /** Promotes the piece to Queen. */
    public final void promote() {
        getOwnerTile().getOwnerGameboard().changePieceType(this, PieceName.QUEEN);
    }

    @Override
    public final PieceName getName() {
        return PieceName.PAWN;
    }

    @Override
    public int getFightingValue() {
        return 1;
    }
}
