package org.voimala.jarzkachess.tests.ai;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.Move;
import org.voimala.jarzkachess.gamelogic.pieces.Bishop;
import org.voimala.jarzkachess.gamelogic.pieces.Knight;
import org.voimala.jarzkachess.gamelogic.pieces.Pawn;
import org.voimala.jarzkachess.gamelogic.pieces.Queen;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

public class GameboardEvaluationTest {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    @Before
    public final void setUp() throws IOException {
    }

    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 0000P000
     * 0000Q000
     * 00000000
     * 00000000
     * 00000000
     */
    public void testEvaluationWhiteMaterialAdvantage() {
        Gameboard gameboard = new Gameboard(null);
        gameboard.insertPieceToTile(new Queen(1), 5, 5);
        gameboard.insertPieceToTile(new Pawn(2), 6, 5);
        
        assertTrue(gameboard.evaluateTotalPositionPoints() > 0);
    }
    
    @Test
    /* 00000000
     * 00000000
     * 00000000
     * 0000P000
     * 0000Q000
     * 00000000
     * 00000000
     * 00000000
     */
    public void testEvaluationBlackMaterialAdvantage() {
        Gameboard gameboard = new Gameboard(null);
        gameboard.insertPieceToTile(new Queen(2), 5, 5);
        gameboard.insertPieceToTile(new Pawn(1), 6, 5);
        
        assertTrue(gameboard.evaluateTotalPositionPoints() < 0);
    }
    
    @Test
    /* 00000000
     * P0000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * P0000000
     * 00000000
     */
    public void testEvaluationEqual() {
        Gameboard gameboard = new Gameboard(null);
        gameboard.insertPieceToTile(new Pawn(1), 2, 1);
        gameboard.insertPieceToTile(new Pawn(2), 7, 1);
        
        assertTrue((int) gameboard.evaluateTotalPositionPoints() == 0);
    }
    
    @Test
    public void testEvaluationEqual2() {
        Gameboard gameboard = new Gameboard(null);
        gameboard.resetGameboard();
        
        assertTrue((int) gameboard.evaluateTotalPositionPoints() == 0);
    }
    
    @Test
    // Starting position + move white's horse and black's horse.
    public void testEvaluationEqual3() {
        Gameboard gameboard = new Gameboard(null);
        gameboard.resetGameboard();
        gameboard.movePieceImmediately(new Move(
                new Cell(8, 7), new Cell(6, 6)));
        gameboard.movePieceImmediately(new Move(
                new Cell(1, 7), new Cell(3, 6)));
        
        NumberFormat nf = NumberFormat.getNumberInstance();  
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        
        assertTrue(Math.round(gameboard.evaluateTotalPositionPoints()) == 0);  
    }
    
    @Test
    /* 0N000000
     * P0000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * P0000000
     * 00000000
     */
    public void testEvaluationBlackMaterialAdvantage2() {
        Gameboard gameboard = new Gameboard(null);
        gameboard.insertPieceToTile(new Pawn(1), 2, 1);
        gameboard.insertPieceToTile(new Pawn(2), 7, 1);
        gameboard.insertPieceToTile(new Knight(2), 1, 2);
        
        assertTrue(gameboard.evaluateTotalPositionPoints() < 0);
    }
    
    @Test
    /* 00000000
     * P0000000
     * 0N000000
     * 00000000
     * 00000000
     * 00000000
     * PN000000
     * 00000000
     */
    public void testEvaluationBlackProtectionAdvantage() {
        Gameboard gameboard = new Gameboard(null);
        gameboard.insertPieceToTile(new Pawn(2), 2, 1);
        gameboard.insertPieceToTile(new Knight(2), 3, 2);
        gameboard.insertPieceToTile(new Pawn(1), 7, 1);
        gameboard.insertPieceToTile(new Knight(1), 7, 2);
        
        assertTrue(gameboard.evaluateTotalPositionPoints() < 0);
    }
    
    
    @Test
    /* 0N000000
     * P0000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * P0000000
     * 0Q000000
     */
    public void testEvaluationWhiteMaterialAdvantage2() {
        Gameboard gameboard = new Gameboard(null);
        gameboard.insertPieceToTile(new Pawn(1), 2, 1);
        gameboard.insertPieceToTile(new Queen(1), 8, 2);
        gameboard.insertPieceToTile(new Pawn(2), 7, 1);
        gameboard.insertPieceToTile(new Knight(2), 1, 2);
        
        assertTrue(gameboard.evaluateTotalPositionPoints() > 0);
    }
    
    @Test
    /* 00000000
     * 0B000000
     * 000N0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public void testEvaluationWhiteAttackAdvantage() {
        Gameboard gameboard = new Gameboard(null);
        gameboard.insertPieceToTile(new Bishop(2), 2, 2);
        gameboard.insertPieceToTile(new Knight(1), 3, 4);
        
        assertTrue(gameboard.evaluateTotalPositionPoints() > 0);
    }
    
    @Test
    /* 00000000
     * 0B000000
     * 000N0000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     * 00000000
     */
    public void testEvaluationBlackAttackAdvantage() {
        Gameboard gameboard = new Gameboard(null);
        gameboard.insertPieceToTile(new Bishop(1), 2, 2);
        gameboard.insertPieceToTile(new Knight(2), 3, 4);
        
        assertTrue(gameboard.evaluateTotalPositionPoints() < 0);
    }
}
