package org.voimala.jarzkachess.gamelogic.players.ai;

public enum AIThreadState {
    AI_THREAD_INITIALIZE,
    AI_THREAD_ANALYZE_OWN_MOVES,
    AI_THREAD_ANALYZE_OPPONENT_MOVES,
    AI_THREAD_SEARCHING_THE_BEST_MOVE,
}
