package org.voimala.jarzkachess.gamelogic.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.voimala.jarzkachess.exceptions.TileNotFoundException;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.gamelogic.Tile;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;

public class Pawn extends Piece implements Cloneable {
    
    public Pawn(final int playerOwner) {
        super(playerOwner);
        try {
            loadSprite();
        } catch (SpriteNotFoundException e) {
            // Continue without the sprite.
        }
    }
    
    public Pawn clone() {
        return (Pawn) super.clone();
    }
    
    @Override
    protected final List<HalfMove> findPossibleRegularMoves() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        // Get possible moves for white/black
        if (getOwnerPlayer() == 1) {
            moves.addAll(findPossibleRegularMovesForWhite());
        } else if (getOwnerPlayer() == 2) {
            moves.addAll(findPossibleRegularMovesForBlack());
        }
        
        return moves;
    }
    
    @Override
    protected final List<HalfMove> findPossibleAttackMoves() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        // Get possible moves for white/black
        if (getOwnerPlayer() == 1) {
            moves.addAll(findPossibleAttackMovesForWhite());
        } else if (getOwnerPlayer() == 2) {
            moves.addAll(findPossibleAttackMovesForBlack());
        }
        
        return moves;
    }
    
    @Override
    protected final List<HalfMove> findPossibleSpecialMoves() {
        // TODO Needs to be implemented
        return new ArrayList<HalfMove>();
    }

    private List<HalfMove> findPossibleRegularMovesForWhite() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        moves.addAll(findPossibleRegularMovesForWhiteStartingRow());
        moves.addAll(findPossibleRegularMovesForWhiteNormalMoveForward());
        return moves;
    }
    
    private List<HalfMove> findPossibleRegularMovesForBlack() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        moves.addAll(findPossibleRegularMovesForBlackStartingRow());
        moves.addAll(findPossibleRegularMovesForBlackNormalMoveForward());
        return moves;
    }
    
    private List<HalfMove> findPossibleRegularMovesForWhiteStartingRow() {
        /* If the pawn is on the starting row it can be moved two tiles forward
         * but only if the next tiles do not contain any pieces
         */
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        if (getOwnerTile().getRow() != 7) {
            return moves;
        }

        if (!getOwnerTile().getTileAbove().hasPiece() && !getOwnerTile().getTileAbove().getTileAbove().hasPiece()) {
            moves.add(new HalfMove(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() - 2, getOwnerTile().getColumn())));
        }
        
        return moves;
    }
    
    private List<HalfMove> findPossibleRegularMovesForBlackStartingRow() {
        /* If the pawn is on the starting row it can be moved two tiles forward
         * but only if the next tiles do not contain any pieces
         */
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        if (getOwnerTile().getRow() != 2) {
            return moves;
        }

        if (!getOwnerTile().getTileBelow().hasPiece() && !getOwnerTile().getTileBelow().getTileBelow().hasPiece()) {
            moves.add(new HalfMove(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() + 2, getOwnerTile().getColumn())));
        }
        
        return moves;
    }
    
    private List<HalfMove> findPossibleRegularMovesForWhiteNormalMoveForward() {
        /* If the pawn has left it's starting row, but has not reached the other side of the gameboard,
         * it is possible to move the pawn one tile forward. Of course this works only
         * if the next tile does not contain any pieces
         */
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        if (!getOwnerTile().getTileAbove().hasPiece()) {
                moves.add(new HalfMove(new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                        new Cell(getOwnerTile().getRow() - 1, getOwnerTile().getColumn())));
        }
        
        return moves;
    }
    
    private List<HalfMove> findPossibleRegularMovesForBlackNormalMoveForward() {
        /* If the pawn has left it's starting row, but has not reached the other side of the gameboard,
         * it is possible to move the pawn one tile forward. Of course this works only
         * if the next tile does not contain any pieces
         */
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        if (!getOwnerTile().getTileBelow().hasPiece()) {
                moves.add(new HalfMove(new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                        new Cell(getOwnerTile().getRow() + 1, getOwnerTile().getColumn())));
        }
        
        return moves;
    }
    
    private List<HalfMove> findPossibleAttackMovesForWhite() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        moves.addAll(findPossibleAttackMovesForWhiteLeft());
        moves.addAll(findPossibleAttackMovesForWhiteRight());
        return moves;
    }
    
    private List<HalfMove> findPossibleAttackMovesForBlack() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        moves.addAll(findPossibleAttackMovesForBlackLeft());
        moves.addAll(findPossibleAttackMovesForBlackRight());
        return moves;
    }
    
    private List<HalfMove> findPossibleAttackMovesForWhiteLeft() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        if (getOwnerTile().getAdjacentTile(-1, -1) == null) {
            return moves;
        }
        
        if (!getOwnerTile().getAdjacentTile(-1, -1).hasPiece()) {
            return moves;
        }
        
        if (getOwnerTile().getAdjacentTile(-1, -1).getPiece().getOwnerPlayer() != getOwnerPlayer()) {
            moves.add(new HalfMove(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() - 1, getOwnerTile().getColumn() - 1)));
        }
        
        return moves;
    }
    
    private List<HalfMove> findPossibleAttackMovesForWhiteRight() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        if (getOwnerTile().getAdjacentTile(-1, 1) == null) {
            return moves;
        }
        
        if (!getOwnerTile().getAdjacentTile(-1, 1).hasPiece()) {
            return moves;
        }
        
        if (getOwnerTile().getAdjacentTile(-1, 1).getPiece().getOwnerPlayer() != getOwnerPlayer()) {
            moves.add(new HalfMove(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() - 1, getOwnerTile().getColumn() + 1)));
        }
        
        return moves;
    }
    
    private List<HalfMove> findPossibleAttackMovesForBlackLeft() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        if (getOwnerTile().getAdjacentTile(1, -1) == null) {
            return moves;
        }
        
        if (!getOwnerTile().getAdjacentTile(1, -1).hasPiece()) {
            return moves;
        }
        
        if (getOwnerTile().getAdjacentTile(1, -1).getPiece().getOwnerPlayer() != getOwnerPlayer()) {
            moves.add(new HalfMove(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() + 1, getOwnerTile().getColumn() - 1)));
        }
        
        return moves;
    }
    
    private List<HalfMove> findPossibleAttackMovesForBlackRight() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        if (getOwnerTile().getAdjacentTile(1, 1) == null) {
            return moves;
        }
        
        if (!getOwnerTile().getAdjacentTile(1, 1).hasPiece()) {
            return moves;
        }
        
        if (getOwnerTile().getAdjacentTile(1, 1).getPiece().getOwnerPlayer() != getOwnerPlayer()) {
            moves.add(new HalfMove(
                    new Cell(getOwnerTile().getRow(), getOwnerTile().getColumn()),
                    new Cell(getOwnerTile().getRow() + 1, getOwnerTile().getColumn() + 1)));
        }
        
        return moves;
    }

    @Override
    public final void loadSprite() {
        if (getOwnerPlayer() == 1) {
            setSprite(ChessSpriteContainer.getInstance().getSprite("pawn_white"));
        } else {
            setSprite(ChessSpriteContainer.getInstance().getSprite("pawn_black"));
        }
    }
    
    /** Promotes the piece to Queen. */
    public final void promote() {
        getOwnerTile().getOwnerGameboard().changePieceType(this, PieceName.PIECE_NAME_QUEEN);
    }

    @Override
    public final PieceName getName() {
        return PieceName.PIECE_NAME_PAWN;
    }

    @Override
    public int getFightingValue() {
        return 1;
    }
}
