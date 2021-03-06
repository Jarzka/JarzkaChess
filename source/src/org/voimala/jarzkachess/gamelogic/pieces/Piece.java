package org.voimala.jarzkachess.gamelogic.pieces;

import org.voimala.jarzkachess.exceptions.ChessException;
import org.voimala.jarzkachess.exceptions.KingNotFoundException;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.Move;
import org.voimala.jarzkachess.gamelogic.Tile;
import org.voimala.jarzkachess.programbody.ChessProgram;
import org.voimala.jarzkaengine.gamelogic.GameplayObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class represents a piece in the gameboard.
 * The piece knows the player number how owns this piece.
 * The piece also knows it's owner tile
 */
public abstract class Piece extends GameplayObject implements Cloneable {
    private AbstractPieceState stateCurrent = new PieceStateIdle(this);
    private Tile ownerTile = null;
    private int ownerPlayer = 0; /** Should always be 1 or 2. */
     /** The target cell where this piece is moving. May be null if the piece is idling. */
    private Cell target = null;
    /** Selected means that the player wants to move this piece. Only one piece should be selected at a time. */
    private boolean isSelected = false;
    private boolean hasMoved = false; /** This piece has moved at least once. */
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    public Piece() {
        setupLogger();
    }
    
    public Piece(final int ownerPlayer) {
        setOwnerPlayer(ownerPlayer);
        setupLogger();
    }
    
    private void setupLogger() {
        logger.setLevel(ChessProgram.LOG_LEVEL);
    }

    /** When the Piece is cloned, it has the same ownerTile as the source. */
    public Piece clone() throws CloneNotSupportedException {
        return (Piece) super.clone();
    }
    
    private Gameboard getOwnerGameboard() {
        return ownerTile.getOwnerGameboard();
    }
    
    protected final void removeMovesThatWouldLeftKingInCheck(List<Move> moves) {
        for (int i = 0; i < moves.size(); i++) {
            // Clone the current gameboard and perform the move.
            Gameboard gameboardClone = getOwnerGameboard().clone();
            gameboardClone.movePieceImmediately(moves.get(i));
            
            // Check if the king is in check.
            try {
            if (gameboardClone.findKing(getOwnerPlayerNumber()).isInCheck()) {
                moves.remove(i);
                i--; // Without this the loop would skip the next element!
            }
            } catch (KingNotFoundException e) {
                logger.warning("King does not found!");
            }
        }
    }
    
    public final Cell getPosition() {
        return getOwnerTile().getPosition();
    }
    
    public final int getRow() {
        return getPosition().getRow();
    }
    
    public final int getColumn() {
        return getPosition().getColumn();
    }
    
    public final Cell getTargetCell() {
        return target;
    }
    
    public final double getMovementProgress() {
        return stateCurrent.getMovementProgress();
    }
    
    public final void die() {
        // Ask the owner gameboard to remove this piece from the game.
        getOwnerTile().getOwnerGameboard().removePiece(this);
    }
    
    public final void changeState(final AbstractPieceState newState) {
        if (newState.getStateName() == PieceStateName.IDLE) {
            target = null;
        }
        
        stateCurrent = newState;
    }
    
    public final void moveAnimated(final Cell target) {
        if (stateCurrent.getStateName() == PieceStateName.IDLE) {
            changeState(new PieceStateMove(this, ownerTile.getPosition(), target));
            this.target = target;
        }
    }
    
    public final void updateState() {
        stateCurrent.updateState();
    }
    
    public final PieceStateName getStateName() {
        return stateCurrent.getStateName();
    }
    
    public final void moveImmediately(final Cell target) {
        getOwnerGameboard().movePieceImmediately(new Move(new Cell(getRow(), getColumn()), target));
    }
    
    public final int getOwnerPlayerNumber() {
        return ownerPlayer;
    }
    
    public final void setOwnerPlayer(final int playerOwner) {
        if (playerOwner != 1 && playerOwner != 2) {
            throw new ChessException("Piece's owner player number should be 1 or 2!");
        }
        
        this.ownerPlayer = playerOwner;
    }

    public final Tile getOwnerTile() {
        return ownerTile;
    }
    
    public final void setOwnerTile(final Tile tile) {
        this.ownerTile = tile;
    }

    public final boolean isSelected() {
        return isSelected;
    }

    public final void setSelected(final boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    public final int getTargetRow() {
        if (target == null) {
            throw new NullPointerException("The piece has no target.");
        }
        
        return target.getRow();
    }
    
    public final int getTargetColumn() {
        if (target == null) {
            throw new NullPointerException("The piece has no target.");
        }
        
        return target.getColumn();
    }
    
    /** This piece can kill another piece if this piece can move to a cell where is an opponent piece.
     * @param includeCheck If set to false, the end result does not include the moves that would leave
     * the piece's team's king in check
     * */
    public final List<Piece> findPiecesThatCanBeKilledByThisPiece(final boolean includeCheck) {
        List<Move> attackMoves = findPossibleMoves(includeCheck);
        ArrayList<Piece> piecesThatCanBeKilled = new ArrayList<>();
        
        // Lets see if there exists enemy pieces in the target cells
        for (Move move : attackMoves) {
            Piece pieceInTargetTile = getOwnerTile().getOwnerGameboard().
                    getTileAtPosition(move.getTarget()).getPiece();
            
            if (pieceInTargetTile != null) {
                if (pieceInTargetTile.getOwnerPlayerNumber() != getOwnerPlayerNumber()) {
                    piecesThatCanBeKilled.add(pieceInTargetTile);
                }
            }
        }
        
        return piecesThatCanBeKilled;
    }
    
    /**
     * Returns an empty array if no-one can kill this piece.
     * @param includeCheck If set to false, the end result does not include the moves that would leave
     * the piece's team's king in check
     */
    public final List<Piece> findPiecesThatCanKillThisPiece(final boolean includeCheck) {
        int opponentPlayerNumber = findOpponentPlayerNumber();
        
        ArrayList<Piece> piecesThatCanKillThisPiece = new ArrayList<>();
        
        // Find all enemy pieces
        for (Piece enemyPiece : getOwnerTile().getOwnerGameboard().findPiecesOwnedByPlayer(opponentPlayerNumber)) {
            // Check if one of the attack move's target pieces is this piece
            for (Piece pieceThatCanBeKilled : enemyPiece.findPiecesThatCanBeKilledByThisPiece(includeCheck)) {
                if (pieceThatCanBeKilled == this) {
                    piecesThatCanKillThisPiece.add(enemyPiece);
                    // The enemy piece can kill this piece. We got it, no reason to continue this loop.
                    break;
                }
            }
        }
        
        return piecesThatCanKillThisPiece;
    }
    
    public List<Piece> findProtectors() {
        // Change this piece's owner player temporarily
        this.setOwnerPlayer(findOpponentPlayerNumber());
        
        // Find all the pieces that can kill this piece
        // The attackers we find are the protectors for this piece.
        ArrayList<Piece> attackers = (ArrayList<Piece>) findPiecesThatCanKillThisPiece(false);
        
        // Set the original owner back.
        this.setOwnerPlayer(findOpponentPlayerNumber());
        
        return attackers;
    }
    
   /** Returns the opponent's player number. */ 
    protected final int findOpponentPlayerNumber() {
        int enemyPlayerNumber = 0;
        
        if (getOwnerPlayerNumber() == 1) {
            enemyPlayerNumber = 2;
        } else {
            enemyPlayerNumber = 1;
        }
        
        return enemyPlayerNumber;
    }

    public abstract PieceName getName();

    public final List<Move> findPossibleMoves(final boolean includeCheck) {
        ArrayList<Move> moves = new ArrayList<>();
        moves.addAll(findPossibleRegularMoves());
        moves.addAll(findPossibleAttackMoves());
        // moves.addAll(findPossibleSpecialMoves());

        if (includeCheck) {
            removeMovesThatWouldLeftKingInCheck(moves);
        }
        
        return moves;
    }

    /** Returns true if this piece has moved at least once. */
    public final boolean hasMoved() {
        return hasMoved;
    }

    public final void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    
    /** Returns an empty list if there are no moves available. */
    protected abstract List<Move> findPossibleRegularMoves();
    /** Returns an empty list if there are no moves available. */
    protected abstract List<Move> findPossibleAttackMoves();
    /** Returns an empty list if there are no moves available. */
    protected abstract List<Move> findPossibleSpecialMoves();
    /** Returns a value which represents how valuable this piece is. */
    public abstract int getFightingValue();

    protected Cell nextCellFromSource(final Direction direction, final int i) {
        Cell cell = null;
        switch (direction) {
        case DOWN_LEFT:
            cell = new Cell(getRow() + i, getColumn() - i);
            break;
        case DOWN_RIGHT:
            cell = new Cell(getRow() + i, getColumn() + i);
            break;
        case UP_LEFT:
            cell = new Cell(getRow() - i, getColumn() - i);
            break;
        case UP_RIGHT:
            cell = new Cell(getRow() - i , getColumn() + i);
            break;
        case LEFT:
            cell = new Cell(getRow(), getColumn() - i);
            break;
        case UP:
            cell = new Cell(getRow() - i, getColumn());
            break;
        case RIGHT:
            cell = new Cell(getRow(), getColumn() + i);
            break;
        case DOWN:
            cell = new Cell(getRow() + i , getColumn());
            break;
        }
        
        return cell;
    }
}
