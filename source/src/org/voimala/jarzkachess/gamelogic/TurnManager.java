package org.voimala.jarzkachess.gamelogic;

public class TurnManager {
    private int turnCurrent = 1;
    private int turnMax = 2;
    
    public final int getTurn() {
        return turnCurrent;
    }

    public final void nextTurn() {
        turnCurrent++;
        
        if (turnCurrent > turnMax) {
            turnCurrent = 1;
        }
    }

    public final void setCurrentTurn(int turnNumber) {
        turnCurrent = turnNumber; 
    }
}
