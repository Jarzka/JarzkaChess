package org.voimala.jarzkachess.gamelogic;

import java.util.HashMap;

public class HalfMove {
    private int playerNumber = 0; /** Player who makes this move. */
    private Cell source = null;
    private Cell target = null;
    private HalfMoveType type = HalfMoveType.REGULAR;
    
    public HalfMove(final Cell source, final Cell target) {
        this.source = source;
        this.target = target;
    }
    
    public HalfMove(final Cell source, final Cell target, final int playerNumber) {
        this.source = source;
        this.target = target;
        this.playerNumber = playerNumber;
    }
    
    public HalfMove(final Cell source, final Cell target, final int playerNumber, HalfMoveType type) {
        this.source = source;
        this.target = target;
        this.playerNumber = playerNumber;
        this.type = type;
    }
    
    public HalfMove() {
        this.source = new Cell(0, 0);
        this.target = new Cell(0, 0);
        this.playerNumber = 0;
        this.type = HalfMoveType.REGULAR;
    }

    public final HashMap<Cell, Cell> getMoveAsHashMap() {
        HashMap<Cell, Cell> move = new HashMap<>();
        move.put(source, target);
        return move;
    }
    
    public final int getSourceRow() {
        return source.getRow();
    }
    
    public final int getSourceColumn() {
        return source.getColumn();
    }
    
    public final int getTargetRow() {
        return target.getRow();
    }
    
    public final int getTargetColumn() {
        return target.getColumn();
    }
    
    public final Cell getSource() {
        return source;
    }
    
    public final Cell getTarget() {
        return target;
    }
    
    public final void setSource(final Cell cell) {
        source = cell;
    }
    
    public final void setTarget(final Cell cell) {
        target = cell;
    }

	public final int getPlayerNumber() {
		return playerNumber;
	}

	public final void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

    public final HalfMoveType getType() {
        return type;
    }

    public final void setType(HalfMoveType type) {
        this.type = type;
    }

}
