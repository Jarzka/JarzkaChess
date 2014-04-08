package org.voimala.jarzkachess.gamelogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.voimala.jarzkachess.exceptions.ChessException;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;

public class Bishop extends Piece implements Cloneable {
    
    public Bishop(final int playerOwner) {
        super(playerOwner);
        try {
            loadSprite();
        } catch (SpriteNotFoundException e) {
            // Continue without the sprite.
        }
    }

    protected final List<HalfMove> findPossibleRegularMoves() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_DOWN_LEFT));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_DOWN_RIGHT));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_UP_RIGHT));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_UP_LEFT));

        return moves;
    }
    
    protected final List<HalfMove> findPossibleAttackMoves() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_UP_LEFT));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_UP_RIGHT));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_DOWN_LEFT));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_DOWN_RIGHT));

        return moves;
    }
    
    @Override
    protected final List<HalfMove> findPossibleSpecialMoves() {
        // This piece does not have any special moves
        return new ArrayList<HalfMove>();
    }

    private List<HalfMove> findPossibleRegularMoves(final Direction direction) {
        if (direction != Direction.DIRECTION_DOWN_LEFT
                && direction != Direction.DIRECTION_DOWN_RIGHT
                && direction != Direction.DIRECTION_UP_LEFT
                && direction != Direction.DIRECTION_UP_RIGHT) {
            throw new ChessException("Direction should be down left, down right, up left or up right.");
        }
        
        // Loop until we get null or find a piece that is blocking our way
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        for (int i = 1; i <= 7; i++) {
            Cell possibleTarget = nextCellFromSource(direction, i);
            if (getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget) == null) {
                break;
            }
            
            if (getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget).hasPiece()) {
                break;
            } else {
                moves.add(new HalfMove(
                        new Cell(getRow(), getColumn()),
                        possibleTarget));
            }
        }
        
        return moves;
    }
    
    private List<HalfMove> findPossibleAttackMoves(final Direction direction) {
        if (direction != Direction.DIRECTION_DOWN_LEFT
                && direction != Direction.DIRECTION_DOWN_RIGHT
                && direction != Direction.DIRECTION_UP_LEFT
                && direction != Direction.DIRECTION_UP_RIGHT) {
            throw new ChessException("Direction should be down left, down right, up left or up right.");
        }
        
        // Loop until we get null or find a piece
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        for (int i = 1; i <= 7; i++) {
            Cell possibleTarget = nextCellFromSource(direction, i);
            if (getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget) == null) {
                break;
            }
            
            Piece foundPiece = getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget).getPiece();
            if (foundPiece != null) {
                if (foundPiece.getOwnerPlayerNumber() != getOwnerPlayerNumber()) {
                    moves.add(new HalfMove(
                            new Cell(getRow(), getColumn()),
                            possibleTarget));
                }
                
                break;
            }
        }
        
        return moves;
    }
    
    @Override
    public final Bishop clone() {
        return (Bishop) super.clone();
    }

    @Override
    public final void loadSprite() {
        if (getOwnerPlayerNumber() == 1) {
            setSprite(ChessSpriteContainer.getInstance().getSprite("bishop_white"));
        } else {
            setSprite(ChessSpriteContainer.getInstance().getSprite("bishop_black"));
        }
    }

    @Override
    public final PieceName getName() {
        return PieceName.PIECE_NAME_BISHOP;
    }

    @Override
    public int getFightingValue() {
        return 3;
    }
}
