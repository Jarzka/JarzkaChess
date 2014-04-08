package org.voimala.jarzkachess.tests.moves;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.pieces.Bishop;
import org.voimala.jarzkachess.gamelogic.pieces.King;
import org.voimala.jarzkachess.gamelogic.pieces.Knight;
import org.voimala.jarzkachess.gamelogic.pieces.Pawn;
import org.voimala.jarzkachess.gamelogic.pieces.Piece;
import org.voimala.jarzkachess.gamelogic.pieces.Queen;
import org.voimala.jarzkachess.gamelogic.pieces.Rook;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;

public class QueenTest {

    @Before
    public final void setUp() throws IOException {
    }
    
    @Test
    /* 00000000
     * 0Q000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteQueenMovement() {
        Gameboard gameboard = new Gameboard();
        Piece queen = new Queen(1);
        gameboard.insertPieceToTile(queen, 2, 2);

        assertEquals(queen.findPossibleMoves(false).size(), 23);
    }
    
    @Test
    /* 0P000000
     * 0Q000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteQueenMovement2() {
        Gameboard gameboard = new Gameboard();
        Piece queen = new Queen(1);
        gameboard.insertPieceToTile(queen, 2, 2);
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 1, 2);

        assertEquals(queen.findPossibleMoves(false).size(), 22);
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
    public final void testWhiteQueenMovement3() {
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
        Piece queen = new Queen(1);
        gameboard.insertPieceToTile(queen, 8, 4);
        gameboard.insertPieceToTile(new King(1), 8, 5);
        gameboard.insertPieceToTile(new Bishop(1), 8, 6);
        gameboard.insertPieceToTile(new Knight(1), 8, 7);
        gameboard.insertPieceToTile(new Rook(1), 8, 8);

        assertEquals(queen.findPossibleMoves(false).size(), 6);
    }
}
