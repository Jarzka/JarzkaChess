package org.voimala.jarzkachess.gamelogic;

public class TurnManager {
    private int turnCurrent = 1;

    public final int getTurn() {
        return turnCurrent;
    }

    public final void nextTurn() {
        turnCurrent++;

        int turnMax = 2;
        if (turnCurrent > turnMax) {
            turnCurrent = 1;
        }
    }

    public final void setCurrentTurn(int turnNumber) {
        turnCurrent = turnNumber; 
    }
}
