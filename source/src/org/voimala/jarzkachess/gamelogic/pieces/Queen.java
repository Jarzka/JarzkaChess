package org.voimala.jarzkachess.gamelogic.pieces;

import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.Move;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;

import java.util.ArrayList;
import java.util.List;

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
    public final Queen clone() throws CloneNotSupportedException {
        return (Queen) super.clone();
    }

    @Override
    public final void loadSprite() {
        if (getOwnerPlayerNumber() == 1) {
            setSprite(ChessSpriteContainer.getInstance().getSprite("queen_white"));
        } else {
            setSprite(ChessSpriteContainer.getInstance().getSprite("queen_black"));
        }
    }

    @Override
    public final PieceName getName() {
        return PieceName.QUEEN;
    }

    protected final List<Move> findPossibleRegularMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        
        moves.addAll(findPossibleRegularMoves(Direction.UP));
        moves.addAll(findPossibleRegularMoves(Direction.RIGHT));
        moves.addAll(findPossibleRegularMoves(Direction.DOWN));
        moves.addAll(findPossibleRegularMoves(Direction.LEFT));
        moves.addAll(findPossibleRegularMoves(Direction.DOWN_LEFT));
        moves.addAll(findPossibleRegularMoves(Direction.DOWN_RIGHT));
        moves.addAll(findPossibleRegularMoves(Direction.UP_RIGHT));
        moves.addAll(findPossibleRegularMoves(Direction.UP_LEFT));

        return moves;
    }
    
    protected final List<Move> findPossibleAttackMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        
        moves.addAll(findPossibleAttackMoves(Direction.UP));
        moves.addAll(findPossibleAttackMoves(Direction.RIGHT));
        moves.addAll(findPossibleAttackMoves(Direction.DOWN));
        moves.addAll(findPossibleAttackMoves(Direction.LEFT));
        moves.addAll(findPossibleAttackMoves(Direction.UP_LEFT));
        moves.addAll(findPossibleAttackMoves(Direction.UP_RIGHT));
        moves.addAll(findPossibleAttackMoves(Direction.DOWN_LEFT));
        moves.addAll(findPossibleAttackMoves(Direction.DOWN_RIGHT));

        return moves;
    }
    
    private List<Move> findPossibleRegularMoves(final Direction direction) {
        // Loop until we get null or find a piece that is blocking our way
        ArrayList<Move> moves = new ArrayList<>();
        
        for (int i = 1; i <= 7; i++) {
            Cell possibleTarget = nextCellFromSource(direction, i);
            if (getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget) == null) {
                break;
            }
            
            if (getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget).hasPiece()) {
                break;
            } else {
                moves.add(new Move(
                        new Cell(getRow(), getColumn()),
                        possibleTarget));
            }
        }
        
        return moves;
    }
    
    private List<Move> findPossibleAttackMoves(final Direction direction) {
        // Loop until we get null or find a piece
        ArrayList<Move> moves = new ArrayList<>();
        
        int i = 1;
        while (true) {
            Cell possibleTarget = nextCellFromSource(direction, i);
            if (getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget) == null) {
                break;
            }
            
            Piece foundPiece = getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget).getPiece();
            if (foundPiece != null) {
                if (foundPiece.getOwnerPlayerNumber() != getOwnerPlayerNumber()) {
                    moves.add(new Move(
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
    protected final List<Move> findPossibleSpecialMoves() {
        // This piece does not have any special moves
        return new ArrayList<>();
    }

    @Override
    public int getFightingValue() {
        return 9;
    }
}
