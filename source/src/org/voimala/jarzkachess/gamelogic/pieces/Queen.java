package org.voimala.jarzkachess.gamelogic.pieces;


import java.util.ArrayList;
import java.util.List;

import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;

public class Queen extends Piece implements Cloneable {

    public Queen(final int playerOwner) {
        setOwnerPlayer(playerOwner);
        try {
            loadSprite();
        } catch (SpriteNotFoundException e) {
            // Continue without the sprite.
        }
    }

    @Override
    public final Queen clone() {
        return (Queen) super.clone();
    }

    @Override
    public final void loadSprite() {
        if (getOwnerPlayer() == 1) {
            setSprite(ChessSpriteContainer.getInstance().getSprite("queen_white"));
        } else {
            setSprite(ChessSpriteContainer.getInstance().getSprite("queen_black"));
        }
    }

    @Override
    public final PieceName getName() {
        return PieceName.PIECE_NAME_QUEEN;
    }

    protected final List<HalfMove> findPossibleRegularMoves() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_UP));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_RIGHT));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_DOWN));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_LEFT));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_DOWN_LEFT));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_DOWN_RIGHT));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_UP_RIGHT));
        moves.addAll(findPossibleRegularMoves(Direction.DIRECTION_UP_LEFT));

        return moves;
    }
    
    protected final List<HalfMove> findPossibleAttackMoves() {
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_UP));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_RIGHT));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_DOWN));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_LEFT));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_UP_LEFT));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_UP_RIGHT));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_DOWN_LEFT));
        moves.addAll(findPossibleAttackMoves(Direction.DIRECTION_DOWN_RIGHT));

        return moves;
    }
    
    private List<HalfMove> findPossibleRegularMoves(final Direction direction) {
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
        // Loop until we get null or find a piece
        ArrayList<HalfMove> moves = new ArrayList<HalfMove>();
        
        int i = 1;
        while (true) {
            Cell possibleTarget = nextCellFromSource(direction, i);
            if (getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget) == null) {
                break;
            }
            
            Piece foundPiece = getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget).getPiece();
            if (foundPiece != null) {
                if (foundPiece.getOwnerPlayer() != getOwnerPlayer()) {
                    moves.add(new HalfMove(
                            new Cell(getRow(), getColumn()),
                            possibleTarget));
                }
                
                break;
            }
            
            i++;
        }
        
        return moves;
    }
    
    @Override
    protected final List<HalfMove> findPossibleSpecialMoves() {
        // This piece does not have any special moves
        return new ArrayList<HalfMove>();
    }

    @Override
    public int getFightingValue() {
        return 9;
    }
}
