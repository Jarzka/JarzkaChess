package org.voimala.jarzkachess.gamelogic;

import java.util.HashMap;

public class HalfMove {
    private int playerNumber = 0; /** Player who makes this move. */
    private Cell source = null;
    private Cell target = null;
    private HalfMoveType type = HalfMoveType.HALF_MOVE_TYPE_REGULAR;
    /** The source or target has been set to the final values.
     * This is true if the caller creates the object using one of the parameter-enabled
     * constructors, otherwise this is false and the value has to be set manually. */
    private boolean isSet;
    
    public HalfMove(final Cell source, final Cell target) {
        this.source = source;
        this.target = target;
        this.isSet = true;
    }
    
    public HalfMove(final Cell source, final Cell target, final int playerNumber) {
        this.source = source;
        this.target = target;
        this.playerNumber = playerNumber;
        this.isSet = true;
    }
    
    public HalfMove(final Cell source, final Cell target, final int playerNumber, HalfMoveType type) {
        this.source = source;
        this.target = target;
        this.playerNumber = playerNumber;
        this.type = type;
        this.isSet = true;
    }
    
    public HalfMove() {
        this.source = new Cell(0, 0);
        this.target = new Cell(0, 0);
        this.playerNumber = 0;
        this.type = HalfMoveType.HALF_MOVE_TYPE_REGULAR;
        this.isSet = false;
    }

    public final HashMap<Cell, Cell> getMoveAsHashMap() {
        HashMap<Cell, Cell> move = new HashMap<Cell, Cell>();
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
    
    public final void setSourceRow(final int row) {
        source.setRow(row);
    }
    
    public final void setSourceColumn(final int column) {
        source.setColumn(column);
    }
    
    public final void setTargetRow(final int row) {
        target.setRow(row);
    }
    
    public final void setTargetColumn(final int column) {
        target.setColumn(column);
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
    
    /** Returns true if the source and target has been set to their final values. */
    public final boolean isSet() {
        return isSet;
    }
    
    /** Are the source and the target set to their final values? */
    public final void setSet(boolean i) {
        this.isSet = i;
    }
}
