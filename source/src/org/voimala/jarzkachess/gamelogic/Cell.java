package org.voimala.jarzkachess.gamelogic;

/** 
 * Cell represents a position in the gameboard, containing row and column.
 * Cell is 1-index.
 */
public class Cell implements Cloneable {
    private int row = 0;
    private int column = 0;
    
    public Cell(final int row, final int column) {
        this.setRow(row);
        this.setColumn(column);
    }
    
    public Cell(final Cell source) {
        setRow(source.getRow());
        setColumn(source.getColumn());
    }
    
    @Override
    public final Cell clone() {
        try {
            return (Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public final boolean hasSameRowAndColumn(Cell cell) {
        return row == cell.getRow() && column == cell.getColumn();
    }
    
    public final int getRow() {
        return row;
    }

    public final void setRow(final int row) {
        this.row = row;
    }

    public final int getColumn() {
        return column;
    }

    public final void setColumn(final int column) {
        this.column = column;
    }

    /** Return true if the cell is located in the center of the board. */
    public final boolean isLocatedInCenter() {
        return getRow() >= 4 && getRow() <= 5 && getColumn() >= 4 && getRow() <= 5;
    }
}
