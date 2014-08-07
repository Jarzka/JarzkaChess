package org.voimala.jarzkachess.gamelogic;

import org.voimala.jarzkachess.gamelogic.players.AbstractPlayer;
import org.voimala.jarzkachess.gamelogic.players.PlayerStateIdle;
import org.voimala.jarzkaengine.scenes.Scene;

import java.util.ArrayList;

/**
 * Represents a single playable game session.
 * A single game session consists of players and gameboard.
 */
public class GameSession {
    private Scene ownerScene = null;
    private Gameboard gameboard = new Gameboard(this);
    private ArrayList<AbstractPlayer> players = new ArrayList<>();
    private TurnManager turnManager = new TurnManager();
    private GameSessionState stateCurrent = new GameSessionStatePlay(this);
    private int winner = 0; /** The player number who won this game session. */
    
    public GameSession(final Scene ownerScene) {
        this.ownerScene = ownerScene;
    }
    
    public final void resetPiecesToInitialPosition() {
        gameboard.resetGameboard();
    }
    
    public final void addPlayer(AbstractPlayer player) {
        players.add(player);
    }
    
    public final ArrayList<AbstractPlayer> getPlayers() {
        return players;
    }
    
    public final void updateState() {
        stateCurrent.updateState();

    }
    
    public final void changeState(final GameSessionState newState) {
        this.stateCurrent = newState;
    }

    public final Gameboard getGameboard() {
        return gameboard;
    }

    public final TurnManager getTurnManager() {
        return turnManager;
    }
    
    public final GameSessionStateName getStateName() {
        return stateCurrent.getStateName();
    }

    public final int getWinner() {
        return winner;
    }
    
    public final String getWinnerSide() {
        if (winner == 1) {
            return "White";
        }
        
        return "Black";
    }

    /** Can be set only if the gamesession is in the end state. */
    public final void setWinner(final int winner) {
        if (stateCurrent.getStateName() == GameSessionStateName.GAME_OVER) {
            this.winner = winner;
        }
    }

    /** Resets the whole gamesession to the starting point. */
    public final void reset() {
        gameboard.resetGameboard();
        resetPlayers();
        resetTurnManager();
        stateCurrent = new GameSessionStatePlay(this);
    }

    /** Resets the turn manager to the first turn. */
    private void resetTurnManager() {
        turnManager.reset();
        
    }

    /** Resets all players to the idle state. */
    private void resetPlayers() {
        for (AbstractPlayer player : players) {
            player.changeState(new PlayerStateIdle(player));
        }
    }
    
    public Scene getOwnerScene() {
        return ownerScene;
    }
}
