package org.voimala.jarzkachess.gamelogic.players;

import org.voimala.jarzkachess.exceptions.ChessException;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.gamelogic.pieces.Piece;
import org.voimala.jarzkaengine.scenes.Scene;

public abstract class Player {
    private int number = 0; /** 1 = White, 2 = Black. */
    private Gameboard gameboard;
    private PlayerState stateCurrent = new PlayerStateIdle(this);

    public Player(final int playerNumber, final Gameboard gameboard) {
        this.setNumber(playerNumber);
        this.setGameboard(gameboard);
    }

    public final int getNumber() {
        return number;
    }

    public final void setNumber(final int number) {
        if (number != 1 && number != 2) {
            throw new ChessException("Player number should be 1 or 2");
        }
        
        this.number = number;
    }
    
    public final Gameboard getGameboard() {
        return gameboard;
    }

    public final void setGameboard(final Gameboard gameboard) {
        this.gameboard = gameboard;
    }
    
    /** Returns the opponent's player number. */ 
    public static final int findOpponentForPlayer(final int playerNumber) {
        if (playerNumber == 1) { return 2; }
        return 1;
    }
    
    public final PlayerStateName getStateName() {
        return stateCurrent.getStateName();
    }
    
    /** @param state A new State object. */
    public final void changeState(final PlayerState state) {
        stateCurrent = state;
    }
    
    public final void updateState() {
        stateCurrent.updateState();
    }
    
    public final void performMove(Piece piece, Cell target) {
        piece.setSelected(false);
        piece.moveAnimated(target);
        changeState(new PlayerStateMoving(this, piece));
    }
    
    /** The reason why there is a separate method for changing state to play is that
     * human players use a different Play state than AI players. So the inherited
     * Player classes know which class they need to use for Play state.
     */
    public abstract void changeStateToPlay();
    public abstract boolean isHuman();
}
