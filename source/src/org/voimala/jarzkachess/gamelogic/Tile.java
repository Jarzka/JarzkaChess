package org.voimala.jarzkachess.gamelogic;

import org.voimala.jarzkachess.exceptions.TilePositionNotDefinedException;
import org.voimala.jarzkachess.gamelogic.pieces.Piece;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;
import org.voimala.jarzkaengine.gamelogic.GameplayObject;

/**
 * A Tile represents a tile in the gameboard. 
 * One tile can have one or zero pieces.
 */
public class Tile extends GameplayObject implements Cloneable {
    private Gameboard ownerGameboard = null;
    private Piece piece = null;
    private Cell position = null; /** This tile's current position (row and column) in the gameboard. */
    private TileColor color = TileColor.TILE_COLOR_BLACK;
    
    public Tile() {
        try {
            loadSprite();
        } catch (SpriteNotFoundException e) {
            // Continue without the sprite.
        }
    }
    
    public Tile(final Gameboard owner, final Cell position, final TileColor color, final Piece piece) {
        this.ownerGameboard = owner;
        this.position = position;
        this.piece = piece;
        this.color = color;
        
        try {
            loadSprite();
        } catch (SpriteNotFoundException e) {
            // Continue without the sprite.
        }
    }
    
    public Tile(final Gameboard gameboard, final Cell position, final TileColor color) {
        this.ownerGameboard = gameboard;
        this.position = position;
        this.color = color;
        
        try {
            loadSprite();
        } catch (SpriteNotFoundException e) {
            // Continue without the sprite.
        }
    }
    
    /**
     * The cloned tile has the same owner gameboard as the source
     * The piece (if any) is also cloned with the tile.
     */
    public final Tile clone() {
        Tile clone = null;
        try {
            clone = (Tile) super.clone();

            // Deep copy
            if (getPiece() != null) { clone.setPiece(this.getPiece().clone()); }
            if (getPosition() != null) { clone.setPosition(this.getPosition().clone()); }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        
        return clone;
    }
    
    /** Asks the SpriteContainer to deliver the corresponding sprite. */
    public final void loadSprite() {
        if (color == TileColor.TILE_COLOR_WHITE) {
            setSprite(ChessSpriteContainer.getInstance().getSprite("tile_white"));
        } else {
            setSprite(ChessSpriteContainer.getInstance().getSprite("tile_black"));
        }
    }
    
    public final Piece getPiece() {
        return piece;
    }
    
    /** Tells also the piece that this tile is now the piece's owner. */
    public final void setPiece(final Piece piece) {
        this.piece = piece;
        piece.setOwnerTile(this);
    }

    /** @return The Tile's current location in the gameboard */
    public final Cell getPosition() {
        if (position == null) {
            throw new TilePositionNotDefinedException("The tile's position is not defined.");
        }
        
        return position;
    }

    public final void setPosition(final Cell position) {
        this.position = position;
    }
    
    public final int getRow() {
        try {
            return position.getRow();
        } catch (NullPointerException e) {
            throw new TilePositionNotDefinedException("The tile's position is not defined.");
        }
    }
    
    public final int getColumn() {
        try {
            return position.getColumn();
        } catch (NullPointerException e) {
            throw new TilePositionNotDefinedException("The tile's position is not defined.");
        }
    }
    /** Adds the given row and column to this tiles position and tries try
     * find the new tile. */
    public final Tile getAdjacentTile(final int row, final int column) {
        return ownerGameboard.getTileAtPosition(new Cell(position.getRow() + row, position.getColumn() + column));
    }
    
    public final Tile getTileAbove() {
        return ownerGameboard.getTileAtPosition(new Cell(position.getRow() - 1, position.getColumn()));
    }
    
    public final Tile getTileBelow() {
        return ownerGameboard.getTileAtPosition(new Cell(position.getRow() + 1, position.getColumn()));
    }
    
    public final Tile getTileOnRight() {
        return ownerGameboard.getTileAtPosition(new Cell(position.getRow(), position.getColumn() + 1));
    }
    
    public final Tile getTileOnLeft() {
        return ownerGameboard.getTileAtPosition(new Cell(position.getRow(), position.getColumn() - 1));
    }
    
    public final boolean hasPiece() {
        return piece != null;

    }

    public final Gameboard getOwnerGameboard() {
        return ownerGameboard;
    }

    public final TileColor getColor() {
        return color;
    }

    public final void setColor(final TileColor color) {
        this.color = color;
    }

    public final void removePiece() {
        this.piece = null;
    }
}
