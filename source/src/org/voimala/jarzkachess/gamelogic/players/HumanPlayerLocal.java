package org.voimala.jarzkachess.gamelogic.players;

import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.pieces.Piece;
import org.voimala.jarzkaengine.inputdevices.Mouse;
import org.voimala.jarzkaengine.inputdevices.MouseButtonState;

public class HumanPlayerLocal extends Player {
    public HumanPlayerLocal(final int playerNumber, final Gameboard gameboard) {
        super(playerNumber, gameboard);
    }

    @Override
    public final void changeStateToPlay() {
        changeState(new PlayerStatePlayHuman(this));
    }

    @Override
    public final boolean isHuman() {
        return true;
    }
}
