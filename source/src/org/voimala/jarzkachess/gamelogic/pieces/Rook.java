package org.voimala.jarzkachess.gamelogic.pieces;

import java.util.ArrayList;
import java.util.List;

import org.voimala.jarzkachess.exceptions.ChessException;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;

public class Rook extends Piece implements Cloneable {
    
    public Rook(final int playerOwner) {
        super(playerOwner);
        try {
            loadSprite();
        } catch (SpriteNotFoundException e) {
            // Continue without the sprite.
        }
    }

    protected final List<HalfMove> findPossibleRegularMoves() {
        ArrayList<HalfMove> moves = new ArrayList<>();

        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_UP));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_RIGHT));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_DOWN));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_LEFT));

        return moves;
    }
    
    protected final List<HalfMove> findPossibleAttackMoves() {
        ArrayList<HalfMove> moves = new ArrayList<>();
        
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_UP));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_RIGHT));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_DOWN));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_LEFT));

        return moves;
    }
    
    private List<HalfMove> findPossibleRegularMoves(final Direction direction) {
        if (direction != Direction.DIRECTION_DOWN
                && direction != Direction.DIRECTION_UP
                && direction != Direction.DIRECTION_LEFT
                && direction != Direction.DIRECTION_RIGHT) {
            throw new ChessException("Direction should be down, up, left or right.");
        }
        
        // Loop until we get null or find a piece that is blocking our way
        ArrayList<HalfMove> moves = new ArrayList<>();
        
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
        if (direction != Direction.DIRECTION_DOWN
                && direction != Direction.DIRECTION_UP
                && direction != Direction.DIRECTION_LEFT
                && direction != Direction.DIRECTION_RIGHT) {
            throw new ChessException("Direction should be down, up, left or right.");
        }
        
        // Loop until we get null or find a piece
        ArrayList<HalfMove> moves = new ArrayList<>();
        
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
    protected final List<HalfMove> findPossibleSpecialMoves() {
        // This piece does not have any special moves
        return new ArrayList<>();
    }

    @Override
    public final Rook clone() throws CloneNotSupportedException {
        return (Rook) super.clone();
    }

    @Override
    public final void loadSprite() {
        if (getOwnerPlayerNumber() == 1) {
            setSprite(ChessSpriteContainer.getInstance().getSprite("rook_white"));
        } else {
            setSprite(ChessSpriteContainer.getInstance().getSprite("rook_black"));
        }
    }

    @Override
    public final PieceName getName() {
        return PieceName.PIECE_NAME_ROOK;
    }

    @Override
    public int getFightingValue() {
        return 5;
    }
}
