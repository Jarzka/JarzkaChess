package org.voimala.jarzkachess.tests.moves;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.pieces.Bishop;
import org.voimala.jarzkachess.gamelogic.pieces.Piece;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BishopTest {

    @Test
    /* 00000000
     * 0B000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteBishopMovement() {
        Gameboard gameboard = new Gameboard();
        Piece bishop = new Bishop(1);
        gameboard.insertPieceToTile(bishop, 2, 2);

        assertEquals(bishop.findPossibleMoves(false).size(), 9);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 0000000B
     */
    public final void testWhiteBishopMovement2() {
        Gameboard gameboard = new Gameboard();
        Piece bishop = new Bishop(1);
        gameboard.insertPieceToTile(bishop, 8, 8);

        assertEquals(bishop.findPossibleMoves(false).size(), 7);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * B0000000
     */
    public final void testWhiteBishopMovement3() {
        Gameboard gameboard = new Gameboard();
        Piece bishop = new Bishop(1);
        gameboard.insertPieceToTile(bishop, 8, 1);

        assertEquals(bishop.findPossibleMoves(false).size(), 7);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 000000B0
     * 0000000B
     */
    public final void testWhiteBishopMovementBlockedByWhiteBishop() {
        Gameboard gameboard = new Gameboard();
        Piece bishop = new Bishop(1);
        gameboard.insertPieceToTile(bishop, 8, 8);
        Piece bishop2 = new Bishop(1);
        gameboard.insertPieceToTile(bishop2, 7, 7);

        assertEquals(bishop.findPossibleMoves(false).size(), 0);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000B00
     * 00000000
     * 0000000B
     */
    public final void testWhiteBishopMovementBlockedByBlackBishop() {
        Gameboard gameboard = new Gameboard();
        Piece bishop = new Bishop(1);
        gameboard.insertPieceToTile(bishop, 8, 8);
        Piece bishop2 = new Bishop(2);
        gameboard.insertPieceToTile(bishop2, 6, 6);

        assertEquals(bishop.findPossibleMoves(false).size(), 2);
    }
}
