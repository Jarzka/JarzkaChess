package org.voimala.jarzkachess.gamelogic.players;

import org.voimala.jarzkachess.gamelogic.Move;
import org.voimala.jarzkachess.gamelogic.Tile;
import org.voimala.jarzkachess.gamelogic.pieces.Piece;
import org.voimala.jarzkachess.inputdevices.ChessMouse;

public class PlayerStatePlayHuman extends PlayerStatePlay {
    public PlayerStatePlayHuman(final Player owner) {
        super(owner);
    }
    
    @Override
    public final void updateState() {
        handleEventPlayerSelectsPiece();
        handleEventPlayerMovesPiece();
    }

    private boolean handleEventPlayerSelectsPiece() {
        // Player clicks a tile which contains his/her own piece.
        if (ChessMouse.getInstance().clickedLeftButton()) {
            return false;
        }
        
        for (Piece piece : getOwnerPlayer().getGameboard().findPiecesOwnedByPlayer(getOwnerPlayer().getNumber())) {
            if (ChessMouse.getInstance().isOnTile(piece.getOwnerTile()) && !piece.isSelected()) {
                getOwnerPlayer().getGameboard().markAllPiecesUnselected();
                piece.setSelected(true);
                return true; // Only one piece can be selected at a time
            }
        }
        
        return false;
    }
    
    private boolean handleEventPlayerMovesPiece() {
        // Piece is selected and player clicks a tile which is a target tile for at least one possible move.
        if (ChessMouse.getInstance().clickedLeftButton()) {
            return false;
        }
        
        for (Piece piece : getOwnerPlayer().getGameboard().findPiecesOwnedByPlayer(getOwnerPlayer().getNumber())) {
            if (piece.isSelected()) {
                for (Move move : piece.findPossibleMoves(true)) {
                    Tile targetTile = getOwnerPlayer().getGameboard().getTileAtPosition(
                            move.getTargetRow(), move.getTargetColumn());
                    if (ChessMouse.getInstance().isOnTile(targetTile)) {
                        getOwnerPlayer().performMove(piece, move.getTarget());
                    }
                }
                
                return true; // Only one piece can be selected at a time
            }
        }
        
        return true;
    }
}
