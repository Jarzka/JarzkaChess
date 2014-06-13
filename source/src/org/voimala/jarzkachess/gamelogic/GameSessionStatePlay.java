package org.voimala.jarzkachess.gamelogic;

import org.voimala.jarzkachess.exceptions.ChessException;
import org.voimala.jarzkachess.exceptions.KingNotFoundException;
import org.voimala.jarzkachess.gamelogic.pieces.King;
import org.voimala.jarzkachess.gamelogic.pieces.Piece;
import org.voimala.jarzkachess.gamelogic.pieces.PieceName;
import org.voimala.jarzkachess.gamelogic.players.Player;
import org.voimala.jarzkachess.gamelogic.players.PlayerStateIdle;
import org.voimala.jarzkachess.gamelogic.players.PlayerStateName;

import java.util.List;

public class GameSessionStatePlay extends GameSessionState {

    private String lastGameboardHash = "";
    
    public GameSessionStatePlay(final GameSession ownerGameSession) {
        super(ownerGameSession);
    }

    @Override
    public final void updateState() {
        updatePlayers();
        updatePieces();
        updateTurn();
        checkEnd();
    }

    private void checkEnd() {
        /* Checking if the game has ended takes lots of processing power so do it only if the
         * game board has been changed. */
        if (!lastGameboardHash.equals(getOwnerGameSession().getGameboard().getHash())) {
            lastGameboardHash = getOwnerGameSession().getGameboard().getHash();
            checkCheckMate();
            checkStalemate();
        }
    }

    private void checkCheckMate() {
        try {
            // Find the king and check if it is in checkmate
            for (Piece piece : getOwnerGameSession().getGameboard().getPieces()) {
                if (piece.getName() == PieceName.KING) {
                    King king = (King) piece;
                    if (king.isInCheckMate()) {
                        getOwnerGameSession().changeState(new GameSessionStateGameOver(getOwnerGameSession()));
                        if (king.getOwnerPlayerNumber() == 1) {
                            getOwnerGameSession().setWinner(2);
                        } else {
                            getOwnerGameSession().setWinner(1);
                        }
                    }
                }
            }
        } catch (KingNotFoundException e) {
            // Continue without doing anything
        }
    }
    
    private boolean checkStalemate() {
        // The current player's king is not in check and the player can not move any piece.
        King king;
        try {
            king = getOwnerGameSession().getGameboard().findKing(getCurrentlyPlayingPlayer().getNumber());
        } catch (KingNotFoundException e) {
            return false;
        }
        
        if (king.isInCheck()) {
            return false;
        }
            
        List<Piece> playersPieces = getOwnerGameSession().getGameboard().findPiecesOwnedByPlayer(getCurrentlyPlayingPlayer().getNumber());
        for (Piece piece : playersPieces) {
            if (piece.findPossibleMoves(true).size() > 0) {
                return false;
            }
        }
        
        getOwnerGameSession().changeState(new GameSessionStateGameOver(getOwnerGameSession()));
        return true;
    }
    
    private Player getCurrentlyPlayingPlayer() {
        for (Player player : getOwnerGameSession().getPlayers()) {
            if (player.getNumber() == getOwnerGameSession().getTurnManager().getTurn()) {
                    return player;
            }
        }
        
        throw new ChessException("Currently no-one is playing the game.");
    }

    /** Finds the current player and lets him/her play. */
    private void updatePlayers() {
        
        for (Player player : getOwnerGameSession().getPlayers()) {
            if (player.getNumber() == getOwnerGameSession().getTurnManager().getTurn()) {
                if (player.getStateName() == PlayerStateName.IDLE) {
                    player.changeStateToPlay();
                }
                
                player.updateState();
                break;
            }
        }
    }

    private void updatePieces() {
        for (Piece piece : getOwnerGameSession().getGameboard().getPieces()) {
            piece.updateState();
        }
    }

    /** If one player is in the final state, we can change a turn. */
    private void updateTurn() {
        for (Player player : getOwnerGameSession().getPlayers()) {
            if (player.getStateName() == PlayerStateName.FINAL) {
                player.changeState(new PlayerStateIdle(player));
                getOwnerGameSession().getTurnManager().nextTurn();
                break;
            }
        }
    }

    @Override
    public final GameSessionStateName getStateName() {
        return GameSessionStateName.PLAY;
    }

}
