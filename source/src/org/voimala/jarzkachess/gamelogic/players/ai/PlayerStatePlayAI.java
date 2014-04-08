package org.voimala.jarzkachess.gamelogic.players.ai;

import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.gamelogic.pieces.Piece;
import org.voimala.jarzkachess.gamelogic.players.Player;
import org.voimala.jarzkachess.gamelogic.players.PlayerStatePlay;
import org.voimala.jarzkachess.graphics.ChessAnimationContainer;

public class PlayerStatePlayAI extends PlayerStatePlay {
    private AIThread aiThread = null;
    /** The AI thread will search for the best move and place it here. */
    private HalfMove aiThreadMove = new HalfMove();

    public PlayerStatePlayAI(final Player owner) {
        super(owner);
    }
    
    @Override
    public final void updateState() {
        findBestMove();
        handleLoadingIcon();
    }
    
    private void findBestMove() {
        handleAiThread();
        
        if (isAnswerFound()) {
            performTheBestMove();
        }
    }

    private void handleLoadingIcon() {
        double timeDelta = getOwnerPlayer().getGameboard().getOwnerGameSession()
                .getOwnerScene().getOwnerCanvas().getTimeDelta();
        ChessAnimationContainer.getInstance().getAnimation("loading_icon").animate(timeDelta);
    }

    private void handleAiThread() {
        if (aiThread == null && !isAnswerFound()) {
            this.aiThread = new AIThread(getOwnerPlayer().getGameboard(), aiThreadMove, getOwnerPlayer().getNumber(), getOwnerPlayer().getGameboard().getNumberOfPerformedMoves());
            this.aiThread.start();
        }
    }

    private void performTheBestMove() {
        if (aiThreadMove != null) {
            Piece piece = getOwnerPlayer().getGameboard().getTileAtPosition(aiThreadMove.getSource()).getPiece();
            Cell target = aiThreadMove.getTarget();
            getOwnerPlayer().performMove(piece, target);
            aiThreadMove = null;
        }
    }

    public final HalfMove getMove() {
        return aiThreadMove;
    }
    
    public final boolean isAnswerFound() {
        return aiThreadMove.isSet();
    }
}
