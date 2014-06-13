package org.voimala.jarzkachess.gamelogic;

import org.voimala.jarzkachess.exceptions.ChessException;
import org.voimala.jarzkachess.exceptions.IllegalMoveException;
import org.voimala.jarzkachess.exceptions.KingNotFoundException;
import org.voimala.jarzkachess.gamelogic.pieces.*;
import org.voimala.jarzkachess.programbody.ChessProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a gameboard, which consists of tiles.
 *  ABCDEFGH
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * 7
 * 8
 * 
 * In the beginning player 1 (white) starts from the rows 7 and 8.
 * Player 2 (black) starts from the rows 1 and 2.
 */
public class Gameboard implements Cloneable {
    private ArrayList<Tile> tiles = null;
    private GameSession ownerGameSession = null;
    private int boardSize = 8; /** Number of rows and tiles. */
    private int countPerformedMoves = 0;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    private int positionX = 0; /** The gameboard's current position in the 2D space. */
    private int positionY = 0;
    
    public Gameboard(final GameSession owner) {
        this.ownerGameSession = owner;
        setupLogger();
        initializeTiles();
    }
    
    public Gameboard() {
        initializeTiles();
    }
    
    private void setupLogger() {
        logger.setLevel(ChessProgram.LOG_LEVEL);
    }

    public final GameSession getOwnerGameSession() {
        return ownerGameSession;
    }

    /** The clones has the same gamesession owner as the source. */
    public final Gameboard clone() {
        Gameboard clone;
        try {
            clone = (Gameboard) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
        
        cloneTilesAndPieces(clone);

        return clone;
    }

    private void cloneTilesAndPieces(final Gameboard clone) {
        /* Currently the Gameboard is a shallow copy,
         * containing references to the source object's tiles and pieces
         * We have the make a deep copy of the tiles and pieces
         */
        
        // Make deep copy of the array of tiles
        clone.initializeTiles();
        
        // Make deep copies of the source tiles' pieces.
        for (Tile tileSource : this.tiles) {
            if (tileSource.hasPiece()) {
                // Find the same tile in the clone
                Tile tileClone = clone.getTileAtPosition(tileSource.getPosition().clone());
                // Make a deep copy of the piece
                Piece pieceClone = null;
                try {
                    pieceClone = tileSource.getPiece().clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

                tileClone.setPiece(pieceClone);
            }
        }
    }
    
    public final void markAllPiecesUnselected() {
        for (Piece piece : getPieces()) {
            piece.setSelected(false);
        }
    }

    public final GamePhase getCurrentGamePhase() {
        GamePhase gamePhase = GamePhase.GAME_PHASE_OPENING;
        
        if (countPerformedMoves > 20) {
            gamePhase = GamePhase.GAME_PHASE_MIDDLEGAME;
        }
        
        if (getPieces().size() <= 10) {
            gamePhase = GamePhase.GAME_PHASE_ENDGAME;
        }
        
        return gamePhase;
    }
    
    /** Initializes the tiles list and adds tiles to it. */
    private void initializeTiles() {
        tiles = new ArrayList<>();

        TileColor tileColor = TileColor.WHITE;
        for (int i = 1; i <= boardSize; i++) {
            if (tileColor == TileColor.WHITE) {
                tileColor = TileColor.BLACK;
            } else {
                tileColor = TileColor.WHITE;
            }

            for (int j = 1; j <= boardSize; j++) {
                if (tileColor == TileColor.WHITE) {
                    tileColor = TileColor.BLACK;
                } else {
                    tileColor = TileColor.WHITE;
                }
                
                Tile tile = new Tile(this, new Cell(i, j), tileColor);
                tiles.add(tile);
            }
        }
    }

    /** First clears the tiles from pieces. Then adds the initial position's pieces. */
    public final void resetGameboard() {
        removeAllPieces();
        resetWhitePieces();
        resetBlackPieces();
        countPerformedMoves = 0;
    }

    private void resetBlackPieces() {
        // Add black pawns to the 2nd row
        for (int i = 1; i <= boardSize; i++) {
            getTileAtPosition(new Cell(2, i)).setPiece(new Pawn(2));
        }
        
        getTileAtPosition(new Cell(1, 5)).setPiece(new King(2));
        
        getTileAtPosition(new Cell(1, 4)).setPiece(new Queen(2));
        
        getTileAtPosition(new Cell(1, 6)).setPiece(new Bishop(2));
        getTileAtPosition(new Cell(1, 3)).setPiece(new Bishop(2));
        
        getTileAtPosition(new Cell(1, 2)).setPiece(new Knight(2));
        getTileAtPosition(new Cell(1, 7)).setPiece(new Knight(2));
        
        getTileAtPosition(new Cell(1, 8)).setPiece(new Rook(2));
        getTileAtPosition(new Cell(1, 1)).setPiece(new Rook(2));
    }

    private void resetWhitePieces() {
        // Add white pawns to the 7th row
        for (int i = 1; i <= boardSize; i++) {
            getTileAtPosition(new Cell(7, i)).setPiece(new Pawn(1));
        }
        
        getTileAtPosition(new Cell(8, 5)).setPiece(new King(1));
        
        getTileAtPosition(new Cell(8, 4)).setPiece(new Queen(1));
        
        getTileAtPosition(new Cell(8, 6)).setPiece(new Bishop(1));
        getTileAtPosition(new Cell(8, 3)).setPiece(new Bishop(1));
        
        getTileAtPosition(new Cell(8, 2)).setPiece(new Knight(1));
        getTileAtPosition(new Cell(8, 7)).setPiece(new Knight(1));
        
        getTileAtPosition(new Cell(8, 8)).setPiece(new Rook(1));
        getTileAtPosition(new Cell(8, 1)).setPiece(new Rook(1));
    }

    private void removeAllPieces() {
        for (Tile tile : tiles) {
            tile.removePiece();
        }
    }
    
    /**
     * Finds the source tile's piece and moves it to the target tile.
     * Does not check if piece type is allowed to make the asked move.
     * 
     * Throws an IllegalMoveException if the player tries to move the piece to
     * a tile which already contains a piece owned by the same player.
     * 
     * Checks if the piece is pawn and it has reached the final row.
     * 
     * If the target tile has an enemy piece, it will be killed
     */
    public final void movePieceImmediately(final Move move) {
        Tile tileSource = getTileAtPosition(move.getSource());
        Tile tileTarget = getTileAtPosition(move.getTarget());
        
        if (tileSource == null) {
            throw new ChessException("Source tile is null.");
        }
        
        if (tileSource.getPiece() == null) {
            String errorMessage = "Source tile" + " (" + tileSource.getRow() + "," + tileSource.getColumn();
            errorMessage += ") does not have a piece.";
            throw new IllegalMoveException(errorMessage);
        }

        // Target target tile is not empty
        if (tileTarget.hasPiece()) {
            // The target tile has a piece which is not owned by the same player
            if (tileTarget.getPiece().getOwnerPlayerNumber() != tileSource.getPiece().getOwnerPlayerNumber()) {
                // Kill the target tile
                tileTarget.getPiece().die();
            } else {
                String errorMessage = "Can not move the piece to the tile which already has a piece owned by the same player";
                errorMessage += " (from " + move.getSourceRow() + "," + move.getSourceColumn();
                errorMessage += " to " + move.getTargetRow() + "," + move.getTargetColumn() + ").";
                throw new IllegalMoveException(errorMessage);
            }
        }
        
        // Move the piece
        Piece piece = tileSource.getPiece();
        tileSource.removePiece();
        tileTarget.setPiece(piece);
        
        checkFinalRowForPawns(piece);
        piece.setHasMoved(true);
        
        countPerformedMoves++;
}

    /** If the piece's type is pawn and it has reached the final row,
     * promote it. */
    private boolean checkFinalRowForPawns(Piece piece) {
        if (piece.getName() != PieceName.PAWN) {
            return false;
        }

        Pawn pawn = (Pawn) piece;

        if ((pawn.getOwnerPlayerNumber() == 1 && pawn.getOwnerTile().getPosition().getRow() == 1)
                || (pawn.getOwnerPlayerNumber() == 2 && pawn.getOwnerTile().getPosition().getRow() == 8)) {
            pawn.promote();
            return true;
        }
        
        return false;
    }

    /** Converts the given paremeters to an HalfMove object and calls movePieceImmediately(Halfmove). */
    public final void movePieceImmediately(final Piece piece, final Cell target) {
        Cell source = new Cell(piece.getPosition().getRow(),
                piece.getPosition().getColumn());
        Move move = new Move(source, target);
        movePieceImmediately(move);
    }
    
    /** More than 0 means that white has an advantage.
     * Less than 0 means that black has an advantage. */
    public final double evaluateTotalPositionPoints() {
        double totalPoints = 0;
        
        totalPoints += evaluatePieces();
        totalPoints += evaluateAdvancedPawns();
        totalPoints += evaluateDoubledPawns();
        totalPoints += evaluateIsolatedPawns();
        totalPoints += evaluateBishopPair();
        
        totalPoints += evaluateGamePhases();
        
        totalPoints += evaluateAttacks();
        totalPoints += evaluateProtectedPieces();
        totalPoints += evaluateCenterControl();
        totalPoints += evaluateMobility();
        
        totalPoints += evaluateKingProtection();
        totalPoints += evaluateCheck();
        totalPoints += evaluateCheckmate();
        
        return totalPoints;
    }

    /** Give points for every piece that the player owns.
     * The points are based on the piece's type.
     * A pawn is worth 1 point. */
    public final double evaluatePieces() {
        double points = 0;
        
        for (Piece piece : getPieces()) {
            double pieceValue = piece.getFightingValue();
            if (piece.getOwnerPlayerNumber() == 2) {
                pieceValue = -pieceValue; // Negative for black
            }
            points += pieceValue;
        }
        
        return points;
    }

    /** Give bonus points from pawns that have moved forward. */
    public final double evaluateAdvancedPawns() {
        double points = 0;
        double oneMovePoint = 0.1;

        for (Piece piece : findPiecesByType(PieceName.PAWN)) {
            Pawn pawn = (Pawn) piece;
            double movePoint = 0;
            if (pawn.getOwnerPlayerNumber() == 1) {
                movePoint += Math.abs(pawn.getRow() - 7) * oneMovePoint;
            } else {
                movePoint += Math.abs(pawn.getRow() - 2) * oneMovePoint;
            }
            
            if (piece.getOwnerPlayerNumber() == 2) {
                movePoint = -movePoint; // Negative for black
            }
            
            points += movePoint;
        }
        
        // Give more points when the pawn has almost reached the final row.
        points += evaluateVeryAdvancedPawns();
        
        return points;
    }
    
    private double evaluateVeryAdvancedPawns() {
        double points = 0;
        double finalRowMinusOnePoints = 4;
        double finalRowMinusTwoPoints = 2;
        
        for (Piece piece : findPiecesByType(PieceName.PAWN)) {
            Pawn pawn = (Pawn) piece;
            double advancedValue = 0;
            if (pawn.getOwnerPlayerNumber() == 1) {
                 if (pawn.getRow() == 3) {
                     advancedValue = finalRowMinusTwoPoints;
                 }
                 
                 if (pawn.getRow() == 2) {
                     advancedValue = finalRowMinusOnePoints;
                 }
            } else {
                if (pawn.getRow() == 6) {
                    advancedValue = -finalRowMinusTwoPoints;
                }
                
                if (pawn.getRow() == 7) {
                    advancedValue = -finalRowMinusOnePoints;
                }
            }
            
            points += advancedValue;
        }
        
        return points;
    }

    /** Doubled pawns are two pawns of the same color residing on the same column. */
    public final double evaluateDoubledPawns() {
        double points = 0;
        
        points += evaluateDoubledPawnsForPlayer(1);
        points += evaluateDoubledPawnsForPlayer(2);
        
        return points;       
    }

    private double evaluateDoubledPawnsForPlayer(final int playerNumber) {
        double points = 0;
        double doubledPawnsPenalty = -0.5;
        
        for (int i = 0; i < 8; i++) {
            int numberOfPawnsInColumn = 0;
            for (int j = 0; j < 8; j++) {
                if (getTileAtPosition(j, i) == null) { continue; }
                if (!getTileAtPosition(j, i).hasPiece()) { continue; }
                if (getTileAtPosition(j, i).getPiece().getName() != PieceName.PAWN) { continue; }
                if (getTileAtPosition(j, i).getPiece().getOwnerPlayerNumber() != playerNumber) { continue; }
                
                numberOfPawnsInColumn++;
            }
            
            if (numberOfPawnsInColumn > 1 && playerNumber == 1) {
                points += doubledPawnsPenalty;
            } else if (numberOfPawnsInColumn > 1 && playerNumber == 2) {
                points -= doubledPawnsPenalty;
            }
        }
        
        return points;       
    }

    /** An isolated pawn is a pawn which has no friendly pawn on an adjacent column. */
    public final double evaluateIsolatedPawns() {
        double points = 0;
        double isolatedPawnPenalty = -0.5;
        
        for (Piece pawn : findPiecesByType(PieceName.PAWN)) {
            int numberOfOwnAdjantacedPieces = 0;
            for (int i = pawn.getRow() - 1; i <= pawn.getRow() + 1; i++) {
                for (int j = pawn.getColumn() - 1; j <= pawn.getColumn() + 1; j++) {
                    if (i == pawn.getRow() && j == pawn.getColumn()) { continue; }   
                    if (getTileAtPosition(i, j) == null) { continue; }
                    if (!getTileAtPosition(i, j).hasPiece()) { continue; }
                    if (getTileAtPosition(i, j).getPiece().getOwnerPlayerNumber() != pawn.getOwnerPlayerNumber()) {
                        continue;
                    }
                    
                    numberOfOwnAdjantacedPieces++;
                }
            }
            
            if (pawn.getOwnerPlayerNumber() == 1 && numberOfOwnAdjantacedPieces == 0) {
                points += isolatedPawnPenalty;
            } else if (pawn.getOwnerPlayerNumber() == 2 && numberOfOwnAdjantacedPieces == 0) {
                points -= isolatedPawnPenalty;
            }
        }
        
        return points;
    }

    /** Give a bonus if the player has two bishops. */
    public final double evaluateBishopPair() {
        double points = 0;
        int numberOfBishops;
        int multipleBishopsPoints = 2;
    
    
        numberOfBishops = findPiecesByTypeAndOwnerPlayer(PieceName.BISHOP, 1).size();
        if (numberOfBishops >= 2) { points += multipleBishopsPoints; }
        
        numberOfBishops = findPiecesByTypeAndOwnerPlayer(PieceName.BISHOP, 2).size();
        if (numberOfBishops >= 2) { points -= multipleBishopsPoints; }
        
        return points;
    }

    public final double evaluateGamePhases() {
        double points = 0;
        
        points += evaluateOpeningGame();
        
        return points;
    }

    private double evaluateOpeningGame() {
        double points = 0;
        
        if (getCurrentGamePhase() == GamePhase.GAME_PHASE_OPENING) {
            points += evaluatePenaltyFromMovingPiecesTooEarly();
        }
        
        return points;
    }

    private double evaluatePenaltyFromMovingPiecesTooEarly() {
        double points = 0;
        
        if (getCurrentGamePhase() == GamePhase.GAME_PHASE_OPENING) {
            points += evaluatePenaltyFromMovingPiecesTooEarlyForPlayer(1);
            points += evaluatePenaltyFromMovingPiecesTooEarlyForPlayer(2);
        }
        
        return points;
    }

    /** Should be called only when the game phase is opening. */
    private double evaluatePenaltyFromMovingPiecesTooEarlyForPlayer(final int playerNumber) {
        double points = 0;
        double penaltyPoints = -4;
        
        for (Piece piece : findPiecesByTypeAndOwnerPlayer(PieceName.ROOK, playerNumber)) {
            if (playerNumber == 1 && piece.hasMoved()) {
                points += penaltyPoints;
            } else if (playerNumber == 2 && piece.hasMoved()) { 
                points -= penaltyPoints;
            }
        }
        
        for (Piece piece : findPiecesByTypeAndOwnerPlayer(PieceName.QUEEN, playerNumber)) {
            if (playerNumber == 1 && piece.hasMoved()) {
                points += penaltyPoints;
            } else if (playerNumber == 2 && piece.hasMoved()) { 
                points -= penaltyPoints;
            }
        }
        
        return points;
    }

    /** Give points for every possible attack.
     * The points are based on the attackable piece's type and how likely it is that
     * the attack will succeed. */
    public final double evaluateAttacks() {
        double points = 0;
        double safeAttackValuePoints = 0.6;
        double normalAttackValuePoints = 0.2;
        
        for (Piece piece : getPieces()) {
            for (Piece pieceThatCanBeKilled : piece.findPiecesThatCanBeKilledByThisPiece(true)) {
                double attackPoints;
                
                // Is the attacker under attack
                double attackValue;
                if (piece.findPiecesThatCanKillThisPiece(true).size() == 0) {
                    attackValue = safeAttackValuePoints;
                } else {
                    attackValue = normalAttackValuePoints;
                }
                
                attackPoints = pieceThatCanBeKilled.getFightingValue() * attackValue;
                
                if (piece.getOwnerPlayerNumber() == 2) {
                    attackPoints = -attackPoints; // Negative for black, because black can kill white's piece
                }
                
                points += attackPoints;
            }
        }
        
        return points;
    }

    /** Give points from every protected piece. */
    public final double evaluateProtectedPieces() {
        double points = 0;
        double protectionPoint = 0.2;
    
        for (Piece piece : getPieces()) {
            // Give points for each protector
            double protectValue = piece.getFightingValue() * piece.findProtectors().size() * protectionPoint;
            
            if (piece.getOwnerPlayerNumber() == 2) {
                protectValue = -protectValue; // Negative for black
            }
            
            points += protectValue;
        }
        
        return points;
    }

    /** It is assumed that the control of the gameboard's center gives an advantage.
     * Give bonus points from possible moves that are located in the gameboard's center. */
    public final double evaluateCenterControl() {
        double points = 0;
        double centerControlPoint = 0.15;
        
        for (Piece piece : getPieces()) {
            for (Move move : piece.findPossibleMoves(true)) {
                if (move.getTarget().isLocatedInCenter()) {
                    double controlValue = centerControlPoint;
                    if (piece.getOwnerPlayerNumber() == 2) {
                        controlValue = - controlValue; // Negative for black
                    } 
                    points +=  controlValue;
                }
            }
        }
        
        return points;
    }

    /**
     * Every possible move the player can make gives the player more choices and wider
     * control of the gameboard. Give points from every possible move.
     */
    public final double evaluateMobility() {
        double points = 0;
        double movePoint = 0.08;
        
        for (Piece piece : getPieces()) {
            double moveValue = piece.findPossibleMoves(true).size() * movePoint;
            if (piece.getOwnerPlayerNumber() == 2) {
                moveValue = -moveValue; // Negative for black
            }
            points += moveValue;
        }
        
        return points;
    }

    /** Give points from every piece that protects the king (is located close the king). */
    public final double evaluateKingProtection() {
        double kingProtectionPoints = 0;
        
        kingProtectionPoints += getKingProtectionPoints(1);
        kingProtectionPoints += getKingProtectionPoints(2);
        
        return kingProtectionPoints;
    }

    /** When the player's king is in check, the player has fewer possible moves.
     * Give points for the player that has made the check. */
    public final double evaluateCheck() {
        double pointsFromCheck = 1.5;
        
        try {
            if (findKing(1).isInCheck()) {
                return -pointsFromCheck;
            }
            
            if (findKing(2).isInCheck()) {
                return pointsFromCheck;
            }
        } catch (KingNotFoundException e) {
            return 0;
        }
        
        return 0;
    }

    /** Give a huge amount of points if the player has made a checkmate. */
    public final double evaluateCheckmate() {
        double pointsFromCheckmate = 10000;
        
        try {
            if (findKing(1).isInCheckMate()) {
                return -pointsFromCheckmate;
            }
            
            if (findKing(2).isInCheckMate()) {
                return pointsFromCheckmate;
            }
        } catch (KingNotFoundException e) {
            return 0;
        }
        
        return 0;
    }

    private double getKingProtectionPoints(int playerNumber) {
        double kingProtectionPoints = 0;
        double kingProtectionPoint = 0.1;
        
        // Find the king
        Cell kingLocation;
        try {
            kingLocation = findKing(playerNumber).getPosition();
        } catch (KingNotFoundException e) {
            return 0;
        }
        
        // Find the pieces that are protecting the king
        for (int i = kingLocation.getRow() - 1; i <= kingLocation.getRow() + 1; i++) {
            for (int j = kingLocation.getColumn() - 1; j <= kingLocation.getColumn() + 1; j++) {
                if (i == kingLocation.getRow() && j == kingLocation.getColumn()) { continue; }
                
                if (getTileAtPosition(i, j) == null) {
                    continue;
                }
                
                if (!getTileAtPosition(i, j).hasPiece()) {
                    continue;
                }
                
                if (getTileAtPosition(i, j).getPiece().getOwnerPlayerNumber() == playerNumber) {
                    double protectionPoints = kingProtectionPoint;
                    if (playerNumber == 2) {
                        protectionPoints = -protectionPoints; // Negative for black
                    }
                    kingProtectionPoints += protectionPoints;
                }
            }
        }
        
        return kingProtectionPoints;
    }
    
    public final void removePiece(Piece piece) {
        piece.getOwnerTile().removePiece();
        piece = null;
    }

    public final ArrayList<Tile> getTiles() {
        return tiles;
    }

    public final ArrayList<Piece> getPieces() {
        ArrayList<Piece> pieces = new ArrayList<>();

        for (Tile tile : getTiles()) {
            if (tile.getPiece() != null) {
                pieces.add((tile.getPiece()));
            }
        }

        return pieces;
    }
    
    private ArrayList<Piece> findPiecesByType(PieceName pieceName) {
        ArrayList<Piece> pieces = new ArrayList<>();

        for (Tile tile : getTiles()) {
            if (tile.getPiece() != null) {
                if (tile.getPiece().getName() == pieceName) {
                    pieces.add((tile.getPiece()));
                }
            }
        }

        return pieces;
    }
    
    private ArrayList<Piece> findPiecesByTypeAndOwnerPlayer(final PieceName pieceName, final int playerNumber) {
        ArrayList<Piece> pieces = new ArrayList<>();

        for (Tile tile : getTiles()) {
            if (tile.getPiece() != null) {
                if (tile.getPiece().getName() == pieceName
                        && tile.getPiece().getOwnerPlayerNumber() == playerNumber) {
                    pieces.add((tile.getPiece()));
                }
            }
        }

        return pieces;
    }
    
    public final ArrayList<Piece> findPiecesOwnedByPlayer(final int playerNumber) {
        ArrayList<Piece> pieces = new ArrayList<>();

        for (Piece piece : getPieces()) {
            if (piece.getOwnerPlayerNumber() == playerNumber) {
                pieces.add(piece);
            }
        }

        return pieces;
    }

    public final void insertPieceToTile(final Piece piece, final int row, final int column) {
        insertPieceToTile(piece, new Cell(row, column));
    }

    public final void insertPieceToTile(final Piece piece, final Cell targetCell) {
        if (targetCell == null) {
            throw new NullPointerException("Cell is null!");
        }
        
        if (piece == null) {
            throw new NullPointerException("Piece is null!");
        }
        
        Tile tile = getTileAtPosition(targetCell);
        tile.setPiece(piece);
    }

    /**
     * @return Return null if tile is not found
     */
    public final Tile getTileAtPosition(final int row, final int column) {
        if (row < 1 || row > 8) {
            return null;
        }
        
        if (column < 1 || column > 8) {
            return null;
        }
        
        // Try to find the tile from the corresponding position in the array
        Tile tileGuess = tiles.get(((row - 1) * 8) + (column - 1));
        if (tileGuess.getRow() == row && tileGuess.getColumn() == column) {
            return tileGuess;
        }
        
        for (Tile tile : tiles) {
            if (tile.getPosition().getRow() == row
                    && tile.getPosition().getColumn() == column) {
                return tile;
            }
        }
        
        return null;
    }

    /**
     * @return Returns the asked tile. If the tile does not found, returns null.
     * Throwing a TileNotFoundException would have been a better architecture, but
     * since this method is called often and it is somewhat likely that the tile does not found,
     * returning a null pointer is much faster way to handle the exception.
     * */
    public final Tile getTileAtPosition(final Cell cell) {
        if (cell == null) {
            throw new NullPointerException("Cell is null!");
        }
        
        return getTileAtPosition(cell.getRow(), cell.getColumn());
    }

    public final int getPositionX() {
        return positionX;
    }

    public final void setPositionX(final int positionX) {
        this.positionX = positionX;
    }

    public final int getPositionY() {
        return positionY;
    }

    public final void setPositionY(final int positionY) {
        this.positionY = positionY;
    }

    public final King findKing(final int ownerPlayer) {
        for (Piece piece : findPiecesOwnedByPlayer(ownerPlayer)) {
            if (piece.getName() == PieceName.KING) {
                return (King) piece;
            }
        }
        
        throw new KingNotFoundException("The king does not found.");
    }
    
    /** 
     * Removes the piece from the game and creates a new piece
     * of different type with the same information.
     * Currently the new type can only be Queen.
     */
    public final void changePieceType(Piece piece, PieceName newType) {
        Tile tile = piece.getOwnerTile();
        int playerOwner = piece.getOwnerPlayerNumber();
        piece.die();
        
        if (newType == PieceName.QUEEN) {
            Queen queen = new Queen(playerOwner);
            tile.setPiece(queen);
        }
    }

    /** Returns the pieces between the cells, but not the pieces which are located in the given cells). */
    public final List<Piece> findPiecesBetweenCellsInRow(Cell cell1, Cell cell2) {
        if (cell1.getRow() != cell2.getRow()) {
            throw new ChessException("Cells should be located in the same row.");
        }
        
        // If the cell1 is on cell2's right side, swap them.
        if (cell1.getColumn() > cell2.getColumn()) {
            Cell cell1Temp = cell1;
            cell1 = cell2;
            cell2 = cell1Temp;
        }
        
        ArrayList<Piece> pieces = new ArrayList<>();
        
        int row = cell1.getRow();
        
        for (int i = cell1.getColumn() + 1; i < cell2.getColumn(); i++) {
            if (getTileAtPosition(row, i).hasPiece()) {
                pieces.add(getTileAtPosition(row, i).getPiece());
            }
        }
        
        return pieces;
    }
    
    /** Returns the tiles between the cells, but not the tiles which are located in the given cells). */
    public final List<Tile> findTilesBetweenCellsInRow(Cell cell1, Cell cell2) {
        if (cell1.getRow() != cell2.getRow()) {
            throw new ChessException("Cells should be located in the same row.");
        }
        
        // If the cell1 is on cell2's right side, swap them.
        if (cell1.getColumn() > cell2.getColumn()) {
            Cell cell1Temp = cell1;
            cell1 = cell2;
            cell2 = cell1Temp;
        }
        
        ArrayList<Tile> tiles = new ArrayList<>();
        
        int row = cell1.getRow();
        
        for (int i = cell1.getColumn() + 1; i < cell2.getColumn(); i++) {
            if (getTileAtPosition(row, i).hasPiece()) {
                tiles.add(getTileAtPosition(row, i));
            }
        }
        
        return tiles;
    }

    public final void removePieces() {
        for (Tile tile : getTiles()) {
            tile.removePiece();
        }  
    }
    
    public final int getNumberOfPerformedMoves() {
        return countPerformedMoves;
    }
    
    /** Returns a string consisting of random characters. The idea is that every gameboard
     * gets a different hash value. */
    public final String getHash() {
        String hash = "";
        for (Tile tile : getTiles()) {
            if (!tile.hasPiece()) {
                hash += "e";
                continue;
            }
            
            hash += tile.getPiece().getName();
            hash += tile.getPiece().getOwnerPlayerNumber();
        }
        
        return hash;
    }
}
