package org.voimala.jarzkachess.inputdevices;

import org.voimala.jarzkachess.exceptions.ChessException;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.Tile;
import org.voimala.jarzkachess.programbody.ChessProgram;
import org.voimala.jarzkaengine.inputdevices.Mouse;

/**
 * This class contains methods that are related to the Chess game.
 */

public class ChessMouse extends Mouse {
    protected static ChessMouse instanceOfThis;
    
    public static final ChessMouse getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new ChessMouse();
        }
        
        return instanceOfThis;
    }
    
    public final boolean isOnTile(final Tile tile) {
        if (tile == null) {
            throw new ChessException("Tile can not be null!");
        }
        
        final int TILE_SIZE_PIXELS = ChessProgram.getInstance().getTileSize();
        final Gameboard gameboard = tile.getOwnerGameboard();
        if (getX() >= gameboard.getPositionX() + (tile.getColumn() - 1) * TILE_SIZE_PIXELS
            && getX() <= gameboard.getPositionX() + ((tile.getColumn() - 1) * TILE_SIZE_PIXELS) + TILE_SIZE_PIXELS
            && getY() >= gameboard.getPositionY() + (tile.getRow() - 1) * TILE_SIZE_PIXELS
            && getY() <= gameboard.getPositionY() + ((tile.getRow() - 1) * TILE_SIZE_PIXELS) + TILE_SIZE_PIXELS) {
            return true;
        }
        
        return false;
    }
}
