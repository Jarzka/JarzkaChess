package org.voimala.jarzkachess.tests.smoketests;

import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.Tile;
import org.voimala.jarzkachess.gamelogic.TileColor;
import org.voimala.jarzkachess.gamelogic.pieces.Pawn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TileTest {

    @Test
    public final void testClone() {
        Tile tileSource = new Tile(null, new Cell(1, 1), TileColor.BLACK);
        tileSource.setPiece(new Pawn(1));
        
        Tile tileClone = tileSource.clone();
        
        // Check that the clone's attributes are the same as the source's attributes
        assertEquals(tileSource.getColor(), tileClone.getColor());
        assertEquals(tileSource.getRow(), tileClone.getRow());
        assertEquals(tileSource.getColumn(), tileClone.getColumn());
        
        // DEEP COPY CHECK
        
        // Check that the clone's piece is NOT the same object than the source's piece
        assertTrue(tileSource.getPiece() != tileClone.getPiece());
        // Check that the clone's cell is NOT the same object than the source's cell
        assertTrue(tileSource.getPosition() != tileClone.getPosition());
    }

    @Test
    public final void testSetPiece() {
        Tile tile = new Tile(null, new Cell(1, 1), TileColor.BLACK);
        Pawn pawn = new Pawn(1);
        
        tile.setPiece(pawn);
        
        assertEquals(tile.getPiece(), pawn);
        assertEquals(pawn.getOwnerTile(), tile);
    }
}
