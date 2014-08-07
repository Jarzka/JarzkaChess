package org.voimala.jarzkachess.gamelogic.players.ai;

import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.players.AbstractPlayer;

public class AIPlayerLocal extends AbstractPlayer {
    public AIPlayerLocal(final int playerNumber, final Gameboard gameboard) {
        super(playerNumber, gameboard);
    }

    @Override
    public final void changeStateToPlay() {
        changeState(new PlayerStatePlayAI(this));
    }

    @Override
    public final boolean isHuman() {
        return false;
    }
}