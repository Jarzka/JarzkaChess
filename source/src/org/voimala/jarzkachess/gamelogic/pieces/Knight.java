package org.voimala.jarzkachess.gamelogic.pieces;


import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece implements Cloneable {
    
    public Knight(final int playerOwner) {
        super(playerOwner);
        try {
            loadSprite();
        } catch (SpriteNotFoundException e) {
            // Continue without the sprite.
        }
    }
    
    @Override
    public final Knight clone() throws CloneNotSupportedException {
        return (Knight) super.clone();
    }

    @Override
    public final void loadSprite() {
        if (getOwnerPlayerNumber() == 1) {
            setSprite(ChessSpriteContainer.getInstance().getSprite("knight_white"));
        } else {
            setSprite(ChessSpriteContainer.getInstance().getSprite("knight_black"));
        }
    }

    @Override
    public final PieceName getName() {
        return PieceName.KNIGHT;
    }

    @Override
    protected final List<HalfMove> findPossibleRegularMoves() {
        ArrayList<HalfMove> moves = new ArrayList<>();
        
        moves.addAll(findPossibleRegularMove(-2, -1));
        moves.addAll(findPossibleRegularMove(-1, -2));
        moves.addAll(findPossibleRegularMove(-1, 2));
        moves.addAll(findPossibleRegularMove(-2, 1));
        moves.addAll(findPossibleRegularMove(2, 1));
        moves.addAll(findPossibleRegularMove(1, 2));
        moves.addAll(findPossibleRegularMove(1, -2));
        moves.addAll(findPossibleRegularMove(2, -1));
        
        return moves;
    }

    private List<HalfMove> findPossibleRegularMove(final int rowFromSource, final int columnFromSource) {
        ArrayList<HalfMove> moves = new ArrayList<>();
        if (getOwnerTile().getAdjacentTile(rowFromSource, columnFromSource) == null) {
            return moves;
        }
        
        if (getOwnerTile().getAdjacentTile(rowFromSource, columnFromSource).hasPiece()) {
            return moves;
        }
        
        moves.add(new HalfMove(
                new Cell(getOwnerTile().getPosition()),
                new Cell(getOwnerTile().getRow() + rowFromSource, getOwnerTile().getColumn() + columnFromSource)));
        
        return moves;
    }

    @Override
    protected final List<HalfMove> findPossibleAttackMoves() {
        ArrayList<HalfMove> moves = new ArrayList<>();
        
        moves.addAll(findPossibleAttackMove(-2, -1));
        moves.addAll(findPossibleAttackMove(-1, -2));
        moves.addAll(findPossibleAttackMove(-1, 2));
        moves.addAll(findPossibleAttackMove(-2, 1));
        moves.addAll(findPossibleAttackMove(2, 1));
        moves.addAll(findPossibleAttackMove(1, 2));
        moves.addAll(findPossibleAttackMove(1, -2));
        moves.addAll(findPossibleAttackMove(2, -1));
        
        return moves;
    }
    
    private List<HalfMove> findPossibleAttackMove(final int rowFromSource, final int columnFromSource) {
        ArrayList<HalfMove> moves = new ArrayList<>();
        if (getOwnerTile().getAdjacentTile(rowFromSource, columnFromSource) == null) {
            return moves;
        }
        
        if (!getOwnerTile().getAdjacentTile(rowFromSource, columnFromSource).hasPiece()) {
            return moves;
        }
        
        Piece piece = getOwnerTile().getAdjacentTile(rowFromSource, columnFromSource).getPiece();
        if (piece.getOwnerPlayerNumber() == getOwnerPlayerNumber()) {
            return moves;
        }
        
        moves.add(new HalfMove(
                new Cell(getOwnerTile().getPosition()),
                new Cell(getOwnerTile().getRow() + rowFromSource, getOwnerTile().getColumn() + columnFromSource)));
        
        return moves;
    }

    @Override
    protected final List<HalfMove> findPossibleSpecialMoves() {
        // This piece does not have any special moves
        return new ArrayList<>();
    }

    @Override
    public int getFightingValue() {
        return 3;
    }
}
