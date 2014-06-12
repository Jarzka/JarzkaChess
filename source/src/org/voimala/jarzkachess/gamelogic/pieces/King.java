package org.voimala.jarzkachess.gamelogic.pieces;

import org.voimala.jarzkachess.exceptions.ChessException;
import org.voimala.jarzkachess.gamelogic.*;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece implements Cloneable {

    public King(final int playerOwner) {
        super(playerOwner);
        try {
            loadSprite();
        } catch (SpriteNotFoundException e) {
            // Continue without the sprite.
        }
    }
    
    @Override
    protected final List<HalfMove> findPossibleRegularMoves() {
        ArrayList<HalfMove> moves = new ArrayList<>();
        
        for (int row = -1; row <= 1; row++) {
            for (int column = -1; column <= 1; column++) {
                Cell possibleTarget = new Cell(getRow() + row, getColumn() + column);
                if (getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget) == null) {
                    continue;
                }
                
                if (!getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget).hasPiece()) {
                    moves.add(new HalfMove(
                            new Cell(getRow(), getColumn()),
                            new Cell(possibleTarget)));
                }
            }
        }

        return moves;
    }

    @Override
    protected final List<HalfMove> findPossibleAttackMoves() {
        ArrayList<HalfMove> moves = new ArrayList<>();
        
        for (int row = -1; row <= 1; row++) {
            for (int column = -1; column <= 1; column++) {
                Cell possibleTarget = new Cell(getRow() + row, getColumn() + column);
                if (getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget) == null) {
                    continue;
                }
                
                Piece foundPiece = getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget).getPiece();
                if (foundPiece != null) {
                    if (foundPiece.getOwnerPlayerNumber() != getOwnerPlayerNumber()) {
                        moves.add(new HalfMove(
                                new Cell(getRow(), getColumn()),
                                new Cell(possibleTarget)));
                    }
                }
            }
        }
        
        return moves;
    }

    @Override
    protected final List<HalfMove> findPossibleSpecialMoves() {
        /* Castling is possible, if:
         * - The king and the rook have not moved
         * - There are no pieces between the king and the rook
         * - The king's tile, the rook's tile or any tile between the two is not attackable by the enemy
         */
        
        ArrayList<HalfMove> moves = new ArrayList<>();

        /* TODO Not yet implemented.
        moves.addAll(checkCastlingMoves(CastlingDirection.CASTLING_DIRECTION_LEFT));
        moves.addAll(checkCastlingMoves(CastlingDirection.CASTLING_DIRECTION_RIGHT));
        */
        
        return moves;
    }

    private List<HalfMove> checkCastlingMoves(CastlingDirection direction) {
        ArrayList<HalfMove> moves = new ArrayList<>();
        
        if (!isKingReadyForCastling()) {
            return moves;
        }
        
        // Find the rook
        int rooksColumn = findRooksColumn(direction);
        Rook rook = null;
        try {
            rook = findRookForCastling(rooksColumn);
        } catch (ChessException e) {
            return moves;
        }
        
        if (areTherePiecesBetweenTheKingAndTheRook(rook)) {
            return moves;
        }
        
        if (castlingTilesAreAttacklabe(rook)) {
            return moves;
        }
        
        // The castling is possible, add the move
        HalfMove castlingMove = new HalfMove(
                getPosition(),
                new Cell(getOwnerTile().getRow(), rooksColumn),
                getOwnerPlayerNumber(),
                HalfMoveType.SPECIAL_CASTLING);
        moves.add(castlingMove);
        
        return moves;
    }

    private boolean castlingTilesAreAttacklabe(Rook rook) {
        ArrayList<Tile> tilesBetween = (ArrayList) getOwnerTile().getOwnerGameboard().findTilesBetweenCellsInRow(
                getPosition(),
                new Cell(getPosition().getRow(), rook.getColumn()));
        for (Tile tile : tilesBetween) {
            // Are there any enemy piece that can move to this tile?
            ArrayList<Piece> opponentPieces = getOwnerTile().getOwnerGameboard().findPiecesOwnedByPlayer(findOpponentPlayerNumber());
            for (Piece opponentPiece : opponentPieces) {
                ArrayList<HalfMove> opponentMoves = (ArrayList) opponentPiece.findPossibleMoves(false);
                for (HalfMove opponentMove : opponentMoves) {
                    if (opponentMove.getTarget().hasSameRowAndColumn(tile.getPosition())) {
                        return true;
                    }
                    
                    if (opponentMove.getTarget().hasSameRowAndColumn(getPosition())) {
                        return true;
                    }
                    
                    if (opponentMove.getTarget().hasSameRowAndColumn(rook.getPosition())) {
                        return true;
                    }
                } 
            }
        }
        
        return false;
    }

    /** Find the corresponding column based on the castling direction (1 or 8). */
    private int findRooksColumn(CastlingDirection direction) {
        if (direction == CastlingDirection.CASTLING_DIRECTION_LEFT) {
            return 1;
        } else {
            return 8;
        } 
    }

    private boolean areTherePiecesBetweenTheKingAndTheRook(Rook rook) {
        return getOwnerTile().getOwnerGameboard().findPiecesBetweenCellsInRow(
                getPosition(),
                new Cell(getPosition().getRow(), rook.getColumn())).size() != 0;

    }

    /** Checks that the king has not moved and it is not in check. */
    private boolean isKingReadyForCastling() {
        return !hasMoved();

    }

    /** Tries to find the Rook from the given column located in the same row as the king. */
    private Rook findRookForCastling(int column) {
        Tile rooksTile = getOwnerTile().getOwnerGameboard().getTileAtPosition(getPosition().getRow(), column);
        if (!rooksTile.hasPiece()) {
            throw new ChessException("Rook not found!");
        }
        
        if (rooksTile.getPiece().getName() != PieceName.PIECE_NAME_ROOK) {
            throw new ChessException("Rook not found!");
        }
        
        Rook rook = (Rook) rooksTile.getPiece();
        
        if (rook.getOwnerPlayerNumber() != getOwnerPlayerNumber()) {
            throw new ChessException("The Rook is not ours!");
        }
        if (rook.hasMoved()) {
            throw new ChessException("The Rook has moved!");
        }
        
        return rook;
    }

    @Override
    public final King clone() throws CloneNotSupportedException {
        return (King) super.clone();
    }

    @Override
    public final void loadSprite() {
        if (getOwnerPlayerNumber() == 1) {
            setSprite(ChessSpriteContainer.getInstance().getSprite("king_white"));
        } else {
            setSprite(ChessSpriteContainer.getInstance().getSprite("king_black"));
        }
    }
    
    /** This method checks is the king in check by asking all the enemy's pieces
     * the question: "Can you kill me?"
     * This is an accurate way of checking the check, but it is also a very slow method! */
    /*public final boolean isInCheckAccurateCheck() {
        // Is there any piece that can kill this piece
        List<Piece> piecesEnemy = getOwnerTile().getOwnerGameboard().findPiecesOwnedByPlayer(findOpponentPlayerNumber());
        
        for (Piece pieceEnemy : piecesEnemy) {*/
            /* We ask all the enemy pieces: "Can you kill me? Do not care about leaving your own king
             * in check, I just want to know if you can move to the same cell where I am".
             */ /*
            for (Piece pieceThatCanBeKilled : pieceEnemy.findPiecesThatCanBeKilledByThisPiece(false)) {
                if (pieceThatCanBeKilled == this) {
                    return true;
                }
            }
        }
        
        return false;
    }*/

    /** Checks is the king in check by checking every possible way to attack the king.
     * Checks the row, the column, diagonal directions, and possible pawn and knight attacks. */
    public final boolean isInCheck() {
        // If any of the following checks returns true, the king is in check
        return checkCheckPossibleBishopAndQueenAttacks(Direction.DIRECTION_DOWN_LEFT)
                || checkCheckPossibleBishopAndQueenAttacks(Direction.DIRECTION_DOWN_RIGHT)
                || checkCheckPossibleBishopAndQueenAttacks(Direction.DIRECTION_UP_LEFT)
                || checkCheckPossibleBishopAndQueenAttacks(Direction.DIRECTION_UP_RIGHT)
                || checkCheckPossibleRookAndQueenAttacks(Direction.DIRECTION_UP)
                || checkCheckPossibleRookAndQueenAttacks(Direction.DIRECTION_DOWN)
                || checkCheckPossibleRookAndQueenAttacks(Direction.DIRECTION_LEFT)
                || checkCheckPossibleRookAndQueenAttacks(Direction.DIRECTION_RIGHT)
                || checkCheckPossiblePawnAttacks()
                || checkCheckPossibleKnightAttacks()
                || checkCheckPossibleKingAttack();
    }
    
    private boolean checkCheckPossibleRookAndQueenAttacks(Direction direction) {
        if (direction != Direction.DIRECTION_DOWN
                && direction != Direction.DIRECTION_UP
                && direction != Direction.DIRECTION_LEFT
                && direction != Direction.DIRECTION_RIGHT) {
            throw new ChessException("Direction should be down, up, left or right.");
        }
        
        for (int i = 1; i <= 7; i++) {
            Cell possibleTarget = nextCellFromSource(direction, i);
            if (getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget) == null) {
                break;
            }
            
            if (!getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget).hasPiece()) {
                continue;
            }
            
            Piece piece = getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget).getPiece();
            if (piece.getOwnerPlayerNumber() == getOwnerPlayerNumber()) {
                break;
            }
            
            if (piece.getName() != PieceName.PIECE_NAME_QUEEN && piece.getName() != PieceName.PIECE_NAME_ROOK) {
                break;
            }
            
            return true;
        }
        
        return false;
    }
    
    private boolean checkCheckPossibleBishopAndQueenAttacks(Direction direction) {
        if (direction != Direction.DIRECTION_DOWN_LEFT
                && direction != Direction.DIRECTION_DOWN_RIGHT
                && direction != Direction.DIRECTION_UP_LEFT
                && direction != Direction.DIRECTION_UP_RIGHT) {
            throw new ChessException("Direction should be down left, down right, up left or up right.");
        }
        
        for (int i = 1; i <= 7; i++) {
            Cell possibleTarget = nextCellFromSource(direction, i);
            if (getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget) == null) {
                break;
            }
            
            if (!getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget).hasPiece()) {
                continue;
            }
            
            Piece piece = getOwnerTile().getOwnerGameboard().getTileAtPosition(possibleTarget).getPiece();
            if (piece.getOwnerPlayerNumber() == getOwnerPlayerNumber()) {
                break;
            }
            
            if (piece.getName() != PieceName.PIECE_NAME_QUEEN && piece.getName() != PieceName.PIECE_NAME_BISHOP) {
                break;
            }
            
            return true;
        }
        
        return false;
    }

    private boolean checkCheckPossiblePawnAttacks() {
        if (getOwnerPlayerNumber() == 1) {
            return checkCheckPossiblePawnAttacksForWhite();
        }
        
        return checkCheckPossiblePawnAttacksForBlack();
    }
    
    private boolean checkCheckPossiblePawnAttacksForWhite() {
        if (getOwnerTile().getOwnerGameboard().getTileAtPosition(getRow() - 1, getColumn() - 1) != null) {
            Piece piece = getOwnerTile().getOwnerGameboard().getTileAtPosition(getRow() - 1, getColumn() - 1).getPiece();
            if (piece != null) {
                if (piece.getOwnerPlayerNumber() != getOwnerPlayerNumber() && piece.getName() == PieceName.PIECE_NAME_PAWN) {
                        return true;
                }
            }
        }
        
        if (getOwnerTile().getOwnerGameboard().getTileAtPosition(getRow() - 1, getColumn() + 1) != null) {
            Piece piece = getOwnerTile().getOwnerGameboard().getTileAtPosition(getRow() - 1, getColumn() + 1).getPiece();
            if (piece != null) {
                if (piece.getOwnerPlayerNumber() != getOwnerPlayerNumber() && piece.getName() == PieceName.PIECE_NAME_PAWN) {
                        return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean checkCheckPossiblePawnAttacksForBlack() {
        if (getOwnerTile().getOwnerGameboard().getTileAtPosition(getRow() + 1, getColumn() + 1) != null) {
            Piece piece = getOwnerTile().getOwnerGameboard().getTileAtPosition(getRow() + 1, getColumn() + 1).getPiece();
            if (piece != null) {
                if (piece.getOwnerPlayerNumber() != getOwnerPlayerNumber() && piece.getName() == PieceName.PIECE_NAME_PAWN) {
                        return true;
                }
            }
        }
        
        if (getOwnerTile().getOwnerGameboard().getTileAtPosition(getRow() + 1, getColumn() - 1) != null) {
            Piece piece = getOwnerTile().getOwnerGameboard().getTileAtPosition(getRow() + 1, getColumn() - 1).getPiece();
            if (piece != null) {
                if (piece.getOwnerPlayerNumber() != getOwnerPlayerNumber() && piece.getName() == PieceName.PIECE_NAME_PAWN) {
                        return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean checkCheckPossibleKnightAttacks() {
        return findPossibleKnightAttack(-2, -1)
                || findPossibleKnightAttack(-1, -2)
                || findPossibleKnightAttack(-1, 2)
                || findPossibleKnightAttack(-2, 1)
                || findPossibleKnightAttack(2, 1)
                || findPossibleKnightAttack(1, 2)
                || findPossibleKnightAttack(1, -2)
                || findPossibleKnightAttack(2, -1);
    }
    
    public final boolean findPossibleKnightAttack(final int rowFromSource, final int columnFromSource) {
        if (getOwnerTile().getAdjacentTile(rowFromSource, columnFromSource) == null) {
            return false;
        }
        
        if (!getOwnerTile().getAdjacentTile(rowFromSource, columnFromSource).hasPiece()) {
            return false;
        }
        
        Piece piece = getOwnerTile().getAdjacentTile(rowFromSource, columnFromSource).getPiece();
        if (piece.getName() != PieceName.PIECE_NAME_KNIGHT) {
            return false;
        }

        if (piece.getOwnerPlayerNumber() == getOwnerPlayerNumber()) {
            return false;
        }

        return true;
    }
    
    private boolean checkCheckPossibleKingAttack() {
        for (int i = getRow() - 1; i <= getRow() + 1; i++) {
            for (int j = getColumn() - 1; j <= getColumn() + 1; j++) {
                if (i == getRow() && j == getColumn()) { continue; }
                
                if (getOwnerTile().getOwnerGameboard().getTileAtPosition(i, j) == null) {
                    continue;
                }
                
                if (!getOwnerTile().getOwnerGameboard().getTileAtPosition(i, j).hasPiece()) {
                    continue;
                }
                
                Piece piece = getOwnerTile().getOwnerGameboard().getTileAtPosition(i, j).getPiece();
                if (piece.getName() != PieceName.PIECE_NAME_KING) {
                    continue;
                }
                
                if (piece.getOwnerPlayerNumber() == getOwnerPlayerNumber()) {
                    continue;
                }
                
                return true;
            }
        }
        
        return false;
    }

    public final boolean isInCheckMate() {
        if (!isInCheck()) {
            return false;
        }
        
        // Is there any way the current player can block the attack.
        for (Piece piece : getOwnerTile().getOwnerGameboard().findPiecesOwnedByPlayer(getOwnerPlayerNumber())) {
            for (HalfMove move : piece.findPossibleMoves(true)) {
                // Clone the current gameboard and perform the move.
                Gameboard gameboardClone = getOwnerTile().getOwnerGameboard().clone();
                gameboardClone.movePieceImmediately(move);
                if (!gameboardClone.findKing(getOwnerPlayerNumber()).isInCheck()) {
                    return false; // This move will save the king, so this is not checkmate
                }
            }
        }
        
        return true;
    }

    @Override
    public final PieceName getName() {
        return PieceName.PIECE_NAME_KING;
    }

    @Override
    public int getFightingValue() {
        if (getOwnerTile().getOwnerGameboard().getCurrentGamePhase() == GamePhase.GAME_PHASE_ENDGAME) {
            return 4;
        } else {
            return 0;
        }
    }
}
