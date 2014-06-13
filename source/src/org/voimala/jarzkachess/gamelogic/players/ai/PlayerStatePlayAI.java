package org.voimala.jarzkachess.gamelogic.players.ai;

import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.Move;
import org.voimala.jarzkachess.gamelogic.pieces.Piece;
import org.voimala.jarzkachess.gamelogic.players.AbstractPlayer;
import org.voimala.jarzkachess.gamelogic.players.PlayerStatePlay;
import org.voimala.jarzkachess.graphics.ChessAnimationContainer;

public class PlayerStatePlayAI extends PlayerStatePlay {
    private AIThread aiThread = null;
    /** The AI thread will search for the best move and place it here. */
    private Move aiThreadMove = null;

    public PlayerStatePlayAI(final AbstractPlayer owner) {
        super(owner);
    }
    
    @Override
    public final void updateState() {
        findBestMove();
        handleLoadingIcon();
    }
    
    private void findBestMove() {
        if (isAIAnswerFound()) {
            performTheBestMove();
        } else {
            if (aiThread == null) {
                startAiThread();
            }
        }
    }

    private void handleLoadingIcon() {
        double timeDelta = getOwnerPlayer().getGameboard().getOwnerGameSession()
                .getOwnerScene().getOwnerCanvas().getTimeDelta();
        ChessAnimationContainer.getInstance().getAnimation("loading_icon").animate(timeDelta);
    }

    private void startAiThread() {
        this.aiThread = new AIThread(getOwnerPlayer().getGameboard(),
                getOwnerPlayer().getNumber(),
                getOwnerPlayer().getGameboard().getNumberOfPerformedMoves(),
                this);
        this.aiThread.start();
    }

    private void performTheBestMove() {
        if (aiThreadMove != null) {
            Piece piece = getOwnerPlayer().getGameboard().getTileAtPosition(aiThreadMove.getSource()).getPiece();
            Cell target = aiThreadMove.getTarget();
            getOwnerPlayer().performMove(piece, target);
            aiThreadMove = null;
        }
    }

    public final Move getMove() {
        return aiThreadMove;
    }
    
    public final boolean isAIAnswerFound() {
        return aiThreadMove != null;
    }

    /** Note: Only AiThread should call this method when it has found the best move. */
    public void setAnswer(final Move answer) {
        aiThreadMove = answer;
        aiThread = null;
    }
}
