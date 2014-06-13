package org.voimala.jarzkachess.tests.smoketests;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.GamePhase;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.Tile;
import org.voimala.jarzkachess.gamelogic.pieces.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameboardTest {
    
    @Before
    public final void setUp() throws IOException {
    }

    @Test
    public final void testClone() {
        Gameboard source = new Gameboard();
        source.resetGameboard();
        
        Gameboard clone = source.clone();
        
        // Check that the clone has the same amount of tiles and pieces
        assertEquals(clone.getTiles().size(), source.getTiles().size());
        assertEquals(clone.getPieces().size(), source.getPieces().size());
        
        // DEEP COPY TEST
        
        // Check that not a single tile or piece in the clone is the same as in the source
        
        for (Tile tileClone : clone.getTiles()) {
            for (Tile tileSource : source.getTiles()) {
                assertTrue(tileClone != tileSource);
            }
        }
        
        for (Piece pieceClone : clone.getPieces()) {
            for (Piece pieceSource : source.getPieces()) {
                assertTrue(pieceClone != pieceSource);
            }
        }
        
        // Check that not a single peace has a reference to any tile in the source
        for (Piece pieceClone : clone.getPieces()) {
            for (Tile tileSource : source.getTiles()) {
                assertTrue(pieceClone.getOwnerTile() != tileSource);
            }
        }
        
        // Check other attributes
        assertTrue(source.getCurrentGamePhase() == clone.getCurrentGamePhase());
        assertTrue(source.getNumberOfPerformedMoves() == clone.getNumberOfPerformedMoves());
    }
    
    @Test
    public final void testReturnPiecesByPlayerNumber1() {
        Gameboard source = new Gameboard();
        Pawn pawn1 = new Pawn(1);
        source.insertPieceToTile(pawn1, new Cell(1, 1));
        Pawn pawn2 = new Pawn(1);
        source.insertPieceToTile(pawn2, new Cell(2, 2));
        Pawn pawn3 = new Pawn(2);
        source.insertPieceToTile(pawn3, new Cell(3, 3));
        
        assertEquals(source.findPiecesOwnedByPlayer(1).size(), 2);
    }
    
    @Test
    /*
    * RNBQKB0R
    * PP00PPPP
    * 0000P00N
    * 00P00000
    * 000P0000
    * 00000000
    * PPP00PPP
    * RNBQKBNR
    */
    public final void testGamePhase() {
        Gameboard gameboard = new Gameboard();
        gameboard.insertPieceToTile(new Rook(2), 1, 1);
        gameboard.insertPieceToTile(new Knight(2), 1, 2);
        gameboard.insertPieceToTile(new Bishop(2), 1, 3);
        gameboard.insertPieceToTile(new Queen(2), 1, 4);
        gameboard.insertPieceToTile(new King(2), 1, 5);
        gameboard.insertPieceToTile(new Bishop(2), 1, 6);
        gameboard.insertPieceToTile(new Rook(2), 1, 8);
        
        gameboard.insertPieceToTile(new Pawn(2), 2, 1);
        gameboard.insertPieceToTile(new Pawn(2), 2, 2);
        gameboard.insertPieceToTile(new Pawn(2), 2, 5);
        gameboard.insertPieceToTile(new Pawn(2), 2, 6);
        gameboard.insertPieceToTile(new Pawn(2), 2, 7);
        gameboard.insertPieceToTile(new Pawn(2), 2, 8);
        
        gameboard.insertPieceToTile(new Pawn(2), 3, 5);
        gameboard.insertPieceToTile(new Knight(2), 3, 8);
        
        gameboard.insertPieceToTile(new Pawn(2), 4, 3);
        
        gameboard.insertPieceToTile(new Pawn(1), 5, 4);
        
        gameboard.insertPieceToTile(new Pawn(1), 7, 1);
        gameboard.insertPieceToTile(new Pawn(1), 7, 2);
        gameboard.insertPieceToTile(new Pawn(1), 7, 3);
        gameboard.insertPieceToTile(new Pawn(1), 7, 6);
        gameboard.insertPieceToTile(new Pawn(1), 7, 7);
        gameboard.insertPieceToTile(new Pawn(1), 7, 8);
        
        gameboard.insertPieceToTile(new Rook(1), 8, 1);
        gameboard.insertPieceToTile(new Knight(1), 8, 2);
        gameboard.insertPieceToTile(new Bishop(1), 8, 3);
        gameboard.insertPieceToTile(new Queen(1), 8, 4);
        gameboard.insertPieceToTile(new King(1), 8, 5);
        gameboard.insertPieceToTile(new Bishop(1), 8, 6);
        gameboard.insertPieceToTile(new Knight(1), 8, 7);
        gameboard.insertPieceToTile(new Rook(1), 8, 8);

        assertEquals(gameboard.getCurrentGamePhase(), GamePhase.OPENING);
    }
    
    @Test
    public final void tesGetTileAtPosition() {
        Gameboard gameboard = new Gameboard();
        gameboard.resetGameboard();
        
        Tile tile = gameboard.getTileAtPosition(1, 2);
        assertEquals(tile.getRow(), 1);
        assertEquals(tile.getColumn(), 2);
    }
    
    @Test
    public final void tesGetTileAtPosition2() {
        Gameboard gameboard = new Gameboard();
        gameboard.resetGameboard();
        
        Tile tile = gameboard.getTileAtPosition(3, 5);
        assertEquals(tile.getRow(), 3);
        assertEquals(tile.getColumn(), 5);
    }
    
    @Test
    public final void tesGetTileAtPosition3() {
        Gameboard gameboard = new Gameboard();
        gameboard.resetGameboard();
        
        Tile tile = gameboard.getTileAtPosition(8, 8);
        assertEquals(tile.getRow(), 8);
        assertEquals(tile.getColumn(), 8);
    }
    
    @Test
    public final void tesGetTileAtPosition4() {
        Gameboard gameboard = new Gameboard();
        gameboard.resetGameboard();
        
        Tile tile = gameboard.getTileAtPosition(2, 4);
        assertEquals(tile.getRow(), 2);
        assertEquals(tile.getColumn(), 4);
    }
    
    @Test
    public final void tesGetTileAtPosition5() {
        Gameboard gameboard = new Gameboard();
        gameboard.resetGameboard();
        
        Tile tile = gameboard.getTileAtPosition(7, 3);
        assertEquals(tile.getRow(), 7);
        assertEquals(tile.getColumn(), 3);
    }
    
    @Test
    public final void tesGetTileAtPosition6() {
        Gameboard gameboard = new Gameboard();
        gameboard.resetGameboard();
        
        Tile tile = gameboard.getTileAtPosition(1, 3);
        assertEquals(tile.getRow(), 1);
        assertEquals(tile.getColumn(), 3);
    }
    
    @Test
    public final void tesGetTileAtPosition7() {
        Gameboard gameboard = new Gameboard();
        gameboard.resetGameboard();
        
        Tile tile = gameboard.getTileAtPosition(3, 8);
        assertEquals(tile.getRow(), 3);
        assertEquals(tile.getColumn(), 8);
    }
    
    @Test
    public final void tesGetTileAtPosition8() {
        Gameboard gameboard = new Gameboard();
        gameboard.resetGameboard();
        
        Tile tile = gameboard.getTileAtPosition(4, 1);
        assertEquals(tile.getRow(), 4);
        assertEquals(tile.getColumn(), 1);
    }
    
    @Test
    public final void tesGetTileAtPosition9() {
        Gameboard gameboard = new Gameboard();
        gameboard.resetGameboard();
        
        Tile tile = gameboard.getTileAtPosition(1, 1);
        assertEquals(tile.getRow(), 1);
        assertEquals(tile.getColumn(), 1);
    }
}
