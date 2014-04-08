package org.voimala.jarzkachess.tests.moves;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.pieces.Knight;
import org.voimala.jarzkachess.gamelogic.pieces.Pawn;
import org.voimala.jarzkachess.gamelogic.pieces.Queen;
import org.voimala.jarzkachess.gamelogic.pieces.Rook;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;

public class KnightTest {

    @Before
    public final void setUp() throws IOException {
        ChessSpriteContainer.getInstance().loadAllSprites();
    }
    
    @Test
    /* 00000000
     * 0N000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKnightMovement() {
        Gameboard gameboard = new Gameboard();
        Knight knight = new Knight(1);
        gameboard.insertPieceToTile(knight, 2, 2);

        assertEquals(knight.findPossibleMoves(false).size(), 4);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 000N0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKnightMovement2() {
        Gameboard gameboard = new Gameboard();
        Knight knight = new Knight(1);
        gameboard.insertPieceToTile(knight, 4, 4);

        assertEquals(knight.findPossibleMoves(false).size(), 8);
    }
    
    @Test
    /* N0000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKnightMovement3() {
        Gameboard gameboard = new Gameboard();
        Knight knight = new Knight(1);
        gameboard.insertPieceToTile(knight, 1, 1);

        assertEquals(knight.findPossibleMoves(false).size(), 2);
    }
    
    @Test
    /* 00000000
     * 00P0P000
     * 0P000P00
     * 000N0000
     * 0P000P00
     * 00P0P000
     * 00000000
     * 00000000
     */
    public final void testWhiteKnightMovementAttack() {
        Gameboard gameboard = new Gameboard();
        Knight knight = new Knight(1);
        gameboard.insertPieceToTile(knight, 4, 4);
        Pawn pawn1 = new Pawn(2);
        gameboard.insertPieceToTile(pawn1, 2, 3);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 3, 2);
        Pawn pawn3 = new Pawn(2);
        gameboard.insertPieceToTile(pawn3, 2, 5);
        Pawn pawn4 = new Pawn(2);
        gameboard.insertPieceToTile(pawn4, 3, 6);
        Pawn pawn5 = new Pawn(2);
        gameboard.insertPieceToTile(pawn5, 5, 6);
        Pawn pawn6 = new Pawn(2);
        gameboard.insertPieceToTile(pawn6, 6, 5);
        Pawn pawn7 = new Pawn(2);
        gameboard.insertPieceToTile(pawn7, 5, 2);
        Pawn pawn8 = new Pawn(2);
        gameboard.insertPieceToTile(pawn8, 6, 3);

        assertEquals(knight.findPossibleMoves(false).size(), 8);
    }
    
    @Test
    /* N0000000
     * 0P000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testfindProtectors() {
        Gameboard gameboard = new Gameboard();
        Knight knight = new Knight(1);
        gameboard.insertPieceToTile(knight, 1, 1);
        gameboard.insertPieceToTile(new Pawn(1), 2, 2);

        assertEquals(knight.findProtectors().size(), 1);
    }
    
    @Test
    /* N000000Q
     * 0P000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * R0000000
     */
    public final void testfindProtectors2() {
        Gameboard gameboard = new Gameboard();
        Knight knight = new Knight(1);
        gameboard.insertPieceToTile(knight, 1, 1);
        gameboard.insertPieceToTile(new Pawn(1), 2, 2);
        gameboard.insertPieceToTile(new Queen(1), 1, 8);
        gameboard.insertPieceToTile(new Rook(1), 8, 1);

        assertEquals(knight.findProtectors().size(), 3);
    }
    
    @Test
    /* N000000Q
     * 0P000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * R0000000
     */
    public final void testfindProtectors3() {
        Gameboard gameboard = new Gameboard();
        Knight knight = new Knight(1);
        gameboard.insertPieceToTile(knight, 1, 1);
        gameboard.insertPieceToTile(new Pawn(1), 2, 2);
        gameboard.insertPieceToTile(new Queen(2), 1, 8);
        gameboard.insertPieceToTile(new Rook(1), 8, 1);

        assertEquals(knight.findProtectors().size(), 2);
    }
    
    @Test
    /* N000000Q
     * 0P000000
     * 00000000
     * 00000000
     * P0000000
     * 00000000
     * 00000000
     * R0000000
     */
    public final void testfindProtectors4() {
        Gameboard gameboard = new Gameboard();
        Knight knight = new Knight(1);
        gameboard.insertPieceToTile(knight, 1, 1);
        gameboard.insertPieceToTile(new Pawn(1), 2, 2);
        gameboard.insertPieceToTile(new Queen(1), 1, 8);
        gameboard.insertPieceToTile(new Rook(1), 8, 1);
        gameboard.insertPieceToTile(new Pawn(2), 5, 1);

        assertEquals(knight.findProtectors().size(), 2);
    }
}
