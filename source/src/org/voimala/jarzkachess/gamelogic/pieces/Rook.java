package org.voimala.jarzkachess.gamelogic.pieces;

import org.voimala.jarzkachess.exceptions.ChessException;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;

import java.util.ArrayList;
import java.util.List;

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

        moves.addAll(findPossibleRegularMoves(Direction.UP));
        moves.addAll(findPossibleRegularMoves(Direction.RIGHT));
        moves.addAll(findPossibleRegularMoves(Direction.DOWN));
        moves.addAll(findPossibleRegularMoves(Direction.LEFT));

        return moves;
    }
    
    protected final List<HalfMove> findPossibleAttackMoves() {
        ArrayList<HalfMove> moves = new ArrayList<>();
        
        moves.addAll(findPossibleAttackMoves(Direction.UP));
        moves.addAll(findPossibleAttackMoves(Direction.RIGHT));
        moves.addAll(findPossibleAttackMoves(Direction.DOWN));
        moves.addAll(findPossibleAttackMoves(Direction.LEFT));

        return moves;
    }
    
    private List<HalfMove> findPossibleRegularMoves(final Direction direction) {
        if (direction != Direction.DOWN
                && direction != Direction.UP
                && direction != Direction.LEFT
                && direction != Direction.RIGHT) {
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
        if (direction != Direction.DOWN
                && direction != Direction.UP
                && direction != Direction.LEFT
                && direction != Direction.RIGHT) {
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
        return PieceName.ROOK;
    }

    @Override
    public int getFightingValue() {
        return 5;
    }
}
