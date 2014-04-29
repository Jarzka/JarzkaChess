package org.voimala.jarzkachess.tests.smoketests;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.exceptions.ChessException;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.Tile;
import org.voimala.jarzkachess.gamelogic.pieces.Pawn;
import org.voimala.jarzkachess.gamelogic.pieces.PieceStateName;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PawnTest {
    @Before
    public void setUp() throws IOException {
    }
    
    @Test (expected = ChessException.class)
    public final void testPawnConstructorOwnerPlayer() {
        new Pawn(9);
    }
    
    @Test (expected = ChessException.class)
    public final void testPawnConstructorOwnerPlayer2() {
        new Pawn(0);
    }
    
    @Test (expected = ChessException.class)
    public final void testPawnConstructorOwnerPlayer3() {
        new Pawn(-2);
    }
    
    @Test
    public final void testPawnClone() throws CloneNotSupportedException {
        Tile tileSource = new Tile();
        Pawn pawnSource = new Pawn(1);
        tileSource.setPiece(pawnSource);
        
        Pawn pawnClone = pawnSource.clone();
        
        // Check that the clone has the same attributes as the source
        assertEquals(pawnSource.getOwnerPlayerNumber(), pawnClone.getOwnerPlayerNumber());
        assertEquals(pawnSource.getOwnerLayer(), pawnClone.getOwnerLayer());
        
        // Check that the clone's owner tile is the same as the source's owner tile.
        assertEquals(pawnSource.getOwnerTile(), pawnClone.getOwnerTile());
    }
    
    @Test
    public final void testChangeStateToMove() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(2);
        gameboard.insertPieceToTile(pawn, 2, 1);
        pawn.moveAnimated(new Cell(3, 1));
        
        assertTrue(pawn.getStateName() == PieceStateName.PIECE_STATE_MOVE);
    }
}
