package org.voimala.jarzkachess.tests.moves;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.gamelogic.pieces.King;
import org.voimala.jarzkachess.gamelogic.pieces.Pawn;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PawnTest {
    @Before
    public final void setUp() throws IOException {
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * P0000000
     * 00000000
     */
    public final void testWhitePawnMovementStart() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 7, 1);

        assertEquals(pawn.findPossibleMoves(false).size(), 2);
        
        for (HalfMove move : pawn.findPossibleMoves(false)) {
            assertTrue(move.getTargetRow() < pawn.getOwnerTile().getRow());
            assertTrue(move.getTargetColumn() == pawn.getOwnerTile().getColumn());
        }
    }
    
    @Test
    /* 00000000
     * P0000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testBlackPawnMovementStart() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(2);
        gameboard.insertPieceToTile(pawn, 2, 1);

        assertEquals(pawn.findPossibleMoves(false).size(), 2);
        
        for (HalfMove move : pawn.findPossibleMoves(false)) {
            assertTrue(move.getTargetRow() > pawn.getOwnerTile().getRow());
            assertTrue(move.getTargetColumn() == pawn.getOwnerTile().getColumn());
        }
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * P0000000
     * P0000000
     * 00000000
     */
    public final void testWhitePawnMovementStartBlockedByWhitePawn() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 7, 1);
        Pawn pawn2 = new Pawn(1);
        gameboard.insertPieceToTile(pawn2, 6, 1);

        assertEquals(pawn.findPossibleMoves(false).size(), 0);
    }
    
    @Test
    /* 00000000
     * P0000000
     * P0000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testBlackPawnMovementStartBlockedByBlackPawn() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(2);
        gameboard.insertPieceToTile(pawn, 2, 1);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 3, 1);

        assertEquals(pawn.findPossibleMoves(false).size(), 0);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * P0000000
     * P0000000
     * 00000000
     */
    public final void testWhitePawnMovementStartBlockedByBlackPawn() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 7, 1);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 6, 1);

        assertEquals(pawn.findPossibleMoves(false).size(), 0);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 0P0P0000
     * 00P00000
     * 00000000
     */
    public final void testWhitePawnMovementStartAttack() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 7, 3);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 6, 2);
        Pawn pawn3 = new Pawn(2);
        gameboard.insertPieceToTile(pawn3, 6, 4);

        assertEquals(pawn.findPossibleMoves(false).size(), 4);
    }
    
    @Test
    /* 00000000
     * 00P00000
     * 0P0P0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testBlackPawnMovementStartAttack() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(2);
        gameboard.insertPieceToTile(pawn, 2, 3);
        Pawn pawn2 = new Pawn(1);
        gameboard.insertPieceToTile(pawn2, 3, 2);
        Pawn pawn3 = new Pawn(1);
        gameboard.insertPieceToTile(pawn3, 3, 4);

        assertEquals(pawn.findPossibleMoves(false).size(), 4);
    }
    
    @Test
    /* 00000000
     * 00P00000
     * 0P000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testBlackPawnMovementStartAttack2() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(2);
        gameboard.insertPieceToTile(pawn, 2, 3);
        Pawn pawn2 = new Pawn(1);
        gameboard.insertPieceToTile(pawn2, 3, 2);

        assertEquals(pawn.findPossibleMoves(false).size(), 3);
    }
    
    @Test
    /* 00000000
     * 000P0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhitePawnMovementMiddle() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 2, 4);
        
        assertEquals(pawn.findPossibleMoves(false).size(), 1);
        
        for (HalfMove move : pawn.findPossibleMoves(false)) {
            assertTrue(move.getTargetRow() < pawn.getOwnerTile().getRow());
            assertTrue(move.getTargetColumn() == pawn.getOwnerTile().getColumn());
        }
    }
    
    @Test
    /* 00000000
     * 00000000
     * 000P0000
     * 000P0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhitePawnMovementMiddleBlockedByWhitePawn() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 4, 4);
        Pawn pawn2 = new Pawn(1);
        gameboard.insertPieceToTile(pawn2, 3, 4);
        
        assertEquals(pawn.findPossibleMoves(false).size(), 0);
    }
    
    @Test
    /* 00000000
     * 0P000000
     * 00K00000
     * 000P0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testBlackPawnMovementKillWhiteKing() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 4, 4);
        King king = new King(1);
        gameboard.insertPieceToTile(king, 3, 3);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 2, 2);

        
        assertEquals(pawn2.findPossibleMoves(false).size(), 3);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 000P0000
     * 000P0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhitePawnMovementMiddleBlockedByBlackPawn() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 4, 4);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 3, 4);
        
        assertEquals(pawn.findPossibleMoves(false).size(), 0);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 0P0P0000
     * 00P00000
     * 00000000
     */
    public final void testFindPiecesThatCanBeKilledByThisPiece() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 7, 3);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 6, 2);
        Pawn pawn3 = new Pawn(2);
        gameboard.insertPieceToTile(pawn3, 6, 4);
        
        assertEquals(pawn.findPiecesThatCanBeKilledByThisPiece(false).size(), 2);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 0P0P0000
     * 0P000000
     * 00000000
     */
    public final void testFindPiecesThatCanBeKilledByThisPiece2() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 7, 2);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 6, 2);
        Pawn pawn3 = new Pawn(2);
        gameboard.insertPieceToTile(pawn3, 6, 4);
        
        assertEquals(pawn2.findPiecesThatCanBeKilledByThisPiece(true).size(), 0);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 0P0P0000
     * 00P00000
     * 00000000
     */
    public final void testFindPiecesThatCanKillThisPiece() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 7, 3);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 6, 2);
        Pawn pawn3 = new Pawn(2);
        gameboard.insertPieceToTile(pawn3, 6, 4);
        
        assertEquals(pawn2.findPiecesThatCanKillThisPiece(false).size(), 1);
        assertEquals(pawn2.findPiecesThatCanKillThisPiece(false).get(0), pawn);
    }
    
    @Test
    /* 00000000
     * 0P000000
     * 00K00000
     * 000P0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    /** The pawn is not allowed to move to a tile that would left the own king in check. */
    public final void testWhitePawnMovementWhileWhiteKingIsInCheck() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 4, 4);
        King king = new King(1);
        gameboard.insertPieceToTile(king, 3, 3);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 2, 2);

        
        assertEquals(pawn.findPossibleMoves(true).size(), 0);
    }
    
    @Test
    /* 00000000
     * 000000P0
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testBlackPawnMovement() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 2, 7);

        assertEquals(pawn.findPossibleMoves(false).size(), 1);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 0P0P0000
     * 00P00000
     * 00000000
     */
    public final void testFindProtectors() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 7, 3);
        Pawn pawn2 = new Pawn(1);
        gameboard.insertPieceToTile(pawn2, 6, 2);
        Pawn pawn3 = new Pawn(1);
        gameboard.insertPieceToTile(pawn3, 6, 4);
        
        assertEquals(pawn.findProtectors().size(), 0);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 0P0P0000
     * 00P00000
     * 00000000
     */
    public final void testFindProtectors2() {
        Gameboard gameboard = new Gameboard();
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 7, 3);
        Pawn pawn2 = new Pawn(1);
        gameboard.insertPieceToTile(pawn2, 6, 2);
        Pawn pawn3 = new Pawn(1);
        gameboard.insertPieceToTile(pawn3, 6, 4);
        
        assertEquals(pawn2.findProtectors().size(), 1);
    }
}
