package org.voimala.jarzkachess.tests.ai;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.Move;
import org.voimala.jarzkachess.gamelogic.pieces.*;
import org.voimala.jarzkachess.gamelogic.players.ai.AIPlayerLocal;
import org.voimala.jarzkachess.gamelogic.players.ai.AIThread;
import org.voimala.jarzkachess.gamelogic.players.ai.PlayerStatePlayAI;
import org.voimala.jarzkachess.programbody.ChessProgram;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AIMoveTest {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    @Before
    public final void setUp() throws IOException {
        logger.setLevel(ChessProgram.LOG_LEVEL);
    }
    
    public final void logAnswer(Move answer) {
        logger.info("Source: " + answer.getSourceRow() + ","
                + answer.getSourceColumn());
        logger.info("Target: " + answer.getTargetRow() + ","
                + answer.getTargetColumn());
    }

    @Test
    /* We assume that killing the pawn with the queen is the best move.
     * 00000000
     * 00000000
     * 00000000
     * 0000P000
     * 0000Q000
     * 00000000
     * 00000000
     * 00000000
     */
    public void testAIBestMove() throws InterruptedException {
        Gameboard gameboard = new Gameboard(null);
        gameboard.insertPieceToTile(new Queen(2), 5, 5);
        gameboard.insertPieceToTile(new Pawn(1), 4, 5);

        AIPlayerLocal playerAi = new AIPlayerLocal(2, gameboard);
        PlayerStatePlayAI playerStatePlayAI = new PlayerStatePlayAI(playerAi);
        AIThread ai = new AIThread(gameboard, 50, playerStatePlayAI);
        ai.start();
        
        /* Wait for the answer (unfortunately the loop does not break without doing anything in the loop
         * for some reason). */
        while (!playerStatePlayAI.isAIAnswerFound()) {
            logger.info("Waiting for the answer...");
            Thread.sleep(1000);
        }
        
        assertEquals(playerStatePlayAI.getMove().getSourceRow(), 5);
        assertEquals(playerStatePlayAI.getMove().getSourceColumn(), 5);
        assertEquals(playerStatePlayAI.getMove().getTargetRow(), 4);
        assertEquals(playerStatePlayAI.getMove().getTargetColumn(), 5);
    }
    
    @Test
    /* In this test we just assume that the AI finds some move
     * RNBQKB0R
     * PP00PPPP
     * 0000P00N
     * 00P00000
     * 000P0000
     * 00000000
     * PPP00PPP
     * RNBQKBNR
     */
    public void testAIFindsAnswer() throws InterruptedException {
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

        AIPlayerLocal playerAi = new AIPlayerLocal(2, gameboard);
        PlayerStatePlayAI playerStatePlayAI = new PlayerStatePlayAI(playerAi);
        AIThread ai = new AIThread(gameboard, 30, playerStatePlayAI);
        ai.start();
        
        /* Wait for the answer (unfortunately the loop does not break without doing anything in the loop
         * for some reason). */
        while (!playerStatePlayAI.isAIAnswerFound()) {
            logger.info("Waitign for the answer...");
            Thread.sleep(1000);
        }
        
        assertTrue(true); // Test passed
    }
    
    @Test
    /* Starting position + every horse has moved.
     * In this test we assume that the penalty from moving rook too early is big enough so
     * that the AI does not want to move it. */
    public void testAIDoesNotMoveRook() throws InterruptedException {
        Gameboard gameboard = new Gameboard();
        
        gameboard.resetGameboard();
        gameboard.movePieceImmediately(new Move(
                new Cell(8, 7), new Cell(6, 6)));
        gameboard.movePieceImmediately(new Move(
                new Cell(1, 7), new Cell(3, 6)));
        gameboard.movePieceImmediately(new Move(
                new Cell(8, 2), new Cell(6, 3)));
        gameboard.movePieceImmediately(new Move(
                new Cell(1, 2), new Cell(3, 3)));

        AIPlayerLocal playerAi = new AIPlayerLocal(2, gameboard);
        PlayerStatePlayAI playerStatePlayAI = new PlayerStatePlayAI(playerAi);
        AIThread ai = new AIThread(gameboard, 20, playerStatePlayAI);
        ai.start();
        
        /* Wait for the answer (unfortunately the loop does not break without doing anything in the loop
         * for some reason). */
        while (!playerStatePlayAI.isAIAnswerFound()) {
            logger.info("Waitign for the answer...");
            Thread.sleep(1000);
        }
        
        logAnswer(playerStatePlayAI.getMove());

        assertTrue((playerStatePlayAI.getMove().getSourceRow() != 1
                && playerStatePlayAI.getMove().getSourceColumn() != 1)
                && (playerStatePlayAI.getMove().getSourceRow() != 1
                && playerStatePlayAI.getMove().getSourceColumn() != 8));
    }
}
