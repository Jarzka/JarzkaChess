package org.voimala.jarzkachess.tests.moves;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.pieces.King;
import org.voimala.jarzkachess.gamelogic.pieces.Pawn;
import org.voimala.jarzkachess.gamelogic.pieces.Rook;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;

public class RookTest {

    @Before
    public final void setUp() throws IOException {
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00R00000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteRookMovement() {
        Gameboard gameboard = new Gameboard();
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 3, 3);

        assertEquals(rook.findPossibleMoves(false).size(), 14);
    }
    
    @Test
    /* 00000000
     * 00P00000
     * 0PR00000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteRookMovementBlockedByWhitePawn() {
        Gameboard gameboard = new Gameboard();
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 3, 3);
        Pawn pawn1 = new Pawn(1);
        gameboard.insertPieceToTile(pawn1, 2, 3);
        Pawn pawn2 = new Pawn(1);
        gameboard.insertPieceToTile(pawn2, 3, 2);

        assertEquals(rook.findPossibleMoves(false).size(), 10);
    }
    
    @Test
    /* 00000000
     * 00P00000
     * 0KR00000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteRookMovementBlockedByWhitePawnAndWhiteKing() {
        Gameboard gameboard = new Gameboard();
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 3, 3);
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 2, 3);
        King king = new King(1);
        gameboard.insertPieceToTile(king, 3, 2);

        assertEquals(rook.findPossibleMoves(true).size(), 10);
    }
    
    @Test
    /* 00000000
     * 00P00000
     * 0KR00000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteRookMovementBlockedByWhitePawnAndBlackKing() {
        Gameboard gameboard = new Gameboard();
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 3, 3);
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 2, 3);
        King king = new King(2);
        gameboard.insertPieceToTile(king, 3, 2);

        assertEquals(rook.findPossibleMoves(false).size(), 11);
    }
    
    @Test
    /* 00000000
     * 00P00000
     * 0PR00000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteRookMovementBlockedByBlackPawn() {
        Gameboard gameboard = new Gameboard();
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 3, 3);
        Pawn pawn1 = new Pawn(2);
        gameboard.insertPieceToTile(pawn1, 2, 3);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 3, 2);

        assertEquals(rook.findPossibleMoves(false).size(), 12);
    }
    
    @Test
    /* RP00000P
     * P0000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testBlackRookMovementBlockedByBlackPawn() {
        Gameboard gameboard = new Gameboard();
        Rook rook = new Rook(2);
        gameboard.insertPieceToTile(rook, 1, 1);
        Pawn pawn1 = new Pawn(2);
        gameboard.insertPieceToTile(pawn1, 1, 2);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 2, 1);
        Pawn pawn3 = new Pawn(1);
        gameboard.insertPieceToTile(pawn3, 1, 8);

        assertEquals(rook.findPossibleMoves(false).size(), 0);
    }

}
