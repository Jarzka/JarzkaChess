package org.voimala.jarzkachess.graphics;

import java.io.File;
import java.io.IOException;

import org.voimala.jarzkaengine.graphics.AnimationContainer;
import org.voimala.jarzkaengine.graphics.SpriteContainer;

public class ChessSpriteContainer extends SpriteContainer {
    private static ChessSpriteContainer instanceOfThis;
    
    public static ChessSpriteContainer getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new ChessSpriteContainer();
        }
        
        return instanceOfThis;
    }
    
    public void loadAllSingleSprites() throws IOException {
        char separator = File.separatorChar;
        instanceOfThis.loadSpriteFromFile("pawn_white", "Graphics" + separator + "Gameboard" + separator + "pawn_white.png");
        instanceOfThis.loadSpriteFromFile("pawn_black", "Graphics" + separator + "Gameboard" + separator + "pawn_black.png");
        instanceOfThis.loadSpriteFromFile("king_white", "Graphics" + separator + "Gameboard" + separator + "king_white.png");
        instanceOfThis.loadSpriteFromFile("king_black", "Graphics" + separator + "Gameboard" + separator + "king_black.png");
        instanceOfThis.loadSpriteFromFile("rook_white", "Graphics" + separator + "Gameboard" + separator + "rook_white.png");
        instanceOfThis.loadSpriteFromFile("rook_black", "Graphics" + separator + "Gameboard" + separator + "rook_black.png");
        instanceOfThis.loadSpriteFromFile("bishop_white", "Graphics" + separator + "Gameboard" + separator + "bishop_white.png");
        instanceOfThis.loadSpriteFromFile("bishop_black", "Graphics" + separator + "Gameboard" + separator + "bishop_black.png");
        instanceOfThis.loadSpriteFromFile("knight_white", "Graphics" + separator + "Gameboard" + separator + "knight_white.png");
        instanceOfThis.loadSpriteFromFile("knight_black", "Graphics" + separator + "Gameboard" + separator + "knight_black.png");
        instanceOfThis.loadSpriteFromFile("queen_white", "Graphics" + separator + "Gameboard" + separator + "queen_white.png");
        instanceOfThis.loadSpriteFromFile("queen_black", "Graphics" + separator + "Gameboard" + separator + "queen_black.png");
        
        instanceOfThis.loadSpriteFromFile("tile_black", "Graphics" + separator + "Gameboard" + separator + "tile_black.png");
        instanceOfThis.loadSpriteFromFile("tile_white", "Graphics" + separator + "Gameboard" + separator + "tile_white.png");
        
        instanceOfThis.loadSpriteFromFile("selected_pawn", "Graphics" + separator + "Gameboard" + separator + "selected_pawn.png");
    }
    
    public final void loadAllAnimationSprites() throws IOException {
        char separator = File.separatorChar;
        loadAnimationSprites("loading_icon", "Graphics" + separator + "Animations" + separator + 
                "Icons" + separator + "loading_icon" + separator);
    }
}
