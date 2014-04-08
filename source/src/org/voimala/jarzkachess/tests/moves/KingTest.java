package org.voimala.jarzkachess.tests.moves;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.gamelogic.HalfMoveType;
import org.voimala.jarzkachess.gamelogic.pieces.King;
import org.voimala.jarzkachess.gamelogic.pieces.Pawn;
import org.voimala.jarzkachess.gamelogic.pieces.Rook;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;

public class KingTest {
    @Before
    public final void setUp() throws IOException {
        ChessSpriteContainer.getInstance().loadAllSprites();
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 000K0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKingMovement() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 4, 4);

        assertEquals(king.findPossibleMoves(true).size(), 8);
    }

    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * P0000000
     * K0000000
     */
    public final void testWhiteKingMovement2() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 8, 1);
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 7, 1);

        assertEquals(king.findPossibleMoves(true).size(), 2);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00P00000
     * 000K0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKingMovementBlockedByWhitePawn() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 4, 4);
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 3, 3);

        assertEquals(king.findPossibleMoves(true).size(), 7);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00P00000
     * 000K0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKingCheckedByBlackPawn() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 4, 4);
        Pawn pawn = new Pawn(2);
        gameboard.insertPieceToTile(pawn, 3, 3);

        assertTrue(king.isInCheck());
    }
    
    @Test
    /* 00000000
     * 00000000
     * 0000P000
     * 000K0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKingCheckedByBlackPawn2() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 4, 4);
        Pawn pawn = new Pawn(2);
        gameboard.insertPieceToTile(pawn, 3, 5);

        assertTrue(king.isInCheck());
    }
    
    @Test
    /* 00000000
     * 00000000
     * 000P0000
     * 000K0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKingIsNotInCheck() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 4, 4);
        Pawn pawn = new Pawn(2);
        gameboard.insertPieceToTile(pawn, 3, 4);

        assertEquals(king.isInCheck(), false);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00P00000
     * 000K0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKingIsNotInCheck2() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 4, 4);
        Pawn pawn = new Pawn(1);
        gameboard.insertPieceToTile(pawn, 3, 3);

        assertEquals(king.isInCheck(), false);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 0P000000
     * 000K0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKingMovementBlockedByBlackPawn() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 4, 4);
        Pawn pawn = new Pawn(2);
        gameboard.insertPieceToTile(pawn, 3, 2);

        assertEquals(king.findPossibleMoves(true).size(), 7);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 000P0000
     * 000K0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKingMovementBlockedByBlackPawn2() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 4, 4);
        Pawn pawn = new Pawn(2);
        gameboard.insertPieceToTile(pawn, 3, 4);

        assertEquals(king.findPossibleMoves(true).size(), 6);
    }
    
    @Test
    /* 00000000
     * 000K0000
     * 00000000
     * 000K0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKingMovementBlockedByBlackKing() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 4, 4);
        King king2 = new King(2);
        gameboard.insertPieceToTile(king2, 2, 4);

        assertEquals(king.findPossibleMoves(true).size(), 5);
    }
    
    @Test
    /* 00000000
     * 000K0000
     * 00000000
     * 000K0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public final void testWhiteKingMovementBlockedByBlackKingExcludeCheck() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 4, 4);
        King king2 = new King(2);
        gameboard.insertPieceToTile(king2, 2, 4);

        assertEquals(king.findPossibleMoves(false).size(), 8);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000PP0
     * 00000PP0
     * 0000000K
     */
    public final void testWhiteKingIsInCheckMate() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 8, 8);
        Pawn pawn1 = new Pawn(2);
        gameboard.insertPieceToTile(pawn1, 7, 7);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 6, 7);
        Pawn pawn3 = new Pawn(2);
        gameboard.insertPieceToTile(pawn3, 7, 6);
        Pawn pawn4 = new Pawn(2);
        gameboard.insertPieceToTile(pawn4, 6, 6);
        Pawn pawn5 = new Pawn(1);
        gameboard.insertPieceToTile(pawn5, 2, 2);

        assertTrue(king.isInCheckMate());
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 000000P0
     * 00000PP0
     * 0000000K
     */
    public final void testWhiteKingIsNotInCheckMate() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 8, 8);
        Pawn pawn1 = new Pawn(2);
        gameboard.insertPieceToTile(pawn1, 7, 7);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 6, 7);
        Pawn pawn3 = new Pawn(2);
        gameboard.insertPieceToTile(pawn3, 7, 6);

        assertEquals(king.isInCheckMate(), false);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000P00
     * 000000P0
     * 00000P0K
     */
    public final void testWhiteKingIsNotInCheckMate2() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 8, 8);
        Pawn pawn1 = new Pawn(2);
        gameboard.insertPieceToTile(pawn1, 7, 7);
        Pawn pawn2 = new Pawn(2);
        gameboard.insertPieceToTile(pawn2, 6, 6);
        Pawn pawn3 = new Pawn(1);
        gameboard.insertPieceToTile(pawn3, 8, 6);

        assertEquals(king.isInCheckMate(), false);
    }
    
    //@Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 0000K00R
     */
    public final void testWhiteKingCastling() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 8, 5);
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 8, 8);

        assertEquals(countCastlingMoves(king), 1);
    }
    
    //@Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * R000K000
     */
    public final void testWhiteKingCastling2() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 8, 5);
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 8, 1);

        assertEquals(countCastlingMoves(king), 1);
    }
    
    //@Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * R000K00R
     */
    public final void testWhiteKingCastling3() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 8, 5);
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 8, 1);
        Rook rook2 = new Rook(1);
        gameboard.insertPieceToTile(rook2, 8, 1);

        assertEquals(countCastlingMoves(king), 2);
    }
    
    
    //@Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * R000K00R
     */
    public final void testWhiteKingCastling4() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 8, 5);
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 8, 1);
        Rook rook2 = new Rook(1);
        gameboard.insertPieceToTile(rook2, 8, 1);
        
        rook.setHasMoved(true);

        assertEquals(countCastlingMoves(king), 1);
    }
    
    //@Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * R0P0KP0R
     */
    public final void testWhiteKingCastlingNotPossible() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 8, 5);
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 8, 1);
        Rook rook2 = new Rook(1);
        gameboard.insertPieceToTile(rook2, 8, 1);
        Pawn pawn1 = new Pawn(1);
        gameboard.insertPieceToTile(pawn1 , 8, 3);
        Pawn pawn2 = new Pawn(1);
        gameboard.insertPieceToTile(pawn2, 8, 6);

        assertEquals(countCastlingMoves(king), 0);
    }
    
    //@Test
    /* 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * R000K00R
     */
    public final void testWhiteKingCastlingNotPossible2() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 8, 5);
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 8, 1);
        Rook rook2 = new Rook(1);
        gameboard.insertPieceToTile(rook2, 8, 1);
        
        king.setHasMoved(true);

        assertEquals(countCastlingMoves(king), 0);
    }
    
    //@Test
    /* 00000R00
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * R000K00R
     */
    
    public final void testWhiteKingCastlingNotPossible3() {
        Gameboard gameboard = new Gameboard();
        King king = new King(1);
        gameboard.insertPieceToTile(king, 8, 5);
        Rook rook = new Rook(1);
        gameboard.insertPieceToTile(rook, 8, 1);
        Rook rook2 = new Rook(2);
        gameboard.insertPieceToTile(rook2, 1, 6);

        assertEquals(countCastlingMoves(king), 0);
    }

    private int countCastlingMoves(King king) {
        int castlingMovesCount = 0;
        for (HalfMove move : king.findPossibleMoves(true)) {
            if (move.getType() == HalfMoveType.HALF_MOVE_TYPE_CASTLING) {
                castlingMovesCount++;
            }
        }
        
        return castlingMovesCount;
    }
}
