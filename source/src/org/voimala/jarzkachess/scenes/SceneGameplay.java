package org.voimala.jarzkachess.scenes;

import org.voimala.jarzkachess.gamelogic.GameSession;
import org.voimala.jarzkachess.gamelogic.GameSessionStateName;
import org.voimala.jarzkachess.gamelogic.Tile;
import org.voimala.jarzkachess.gamelogic.pieces.Piece;
import org.voimala.jarzkachess.gamelogic.pieces.PieceStateName;
import org.voimala.jarzkachess.gamelogic.players.HumanPlayerLocal;
import org.voimala.jarzkachess.gamelogic.players.Player;
import org.voimala.jarzkachess.gamelogic.players.PlayerStateName;
import org.voimala.jarzkachess.gamelogic.players.ai.AIPlayerLocal;
import org.voimala.jarzkachess.graphics.ChessAnimationContainer;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;
import org.voimala.jarzkachess.inputdevices.ChessMouse;
import org.voimala.jarzkachess.programbody.ChessProgram;
import org.voimala.jarzkaengine.exceptions.SpriteNotFoundException;
import org.voimala.jarzkaengine.graphics.Sprite;
import org.voimala.jarzkaengine.inputdevices.MouseButtonState;
import org.voimala.jarzkaengine.scenes.Scene;
import org.voimala.jarzkaengine.utility.PositionPoint;
import org.voimala.jarzkaengine.windows.mainwindow.ExtendedCanvas;

import java.awt.*;
import java.text.NumberFormat;
import java.util.logging.Logger;

public class SceneGameplay extends Scene {
    private GameSession gameSession = new GameSession(this);
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String lastGameboardHash = ""; // For testing purposes only
    
    public SceneGameplay(final ExtendedCanvas ownerCanvas) {
        super(ownerCanvas);
        HumanPlayerLocal player1 = new HumanPlayerLocal(1, gameSession.getGameboard());
        //HumanPlayerLocal player2 = new HumanPlayerLocal(2, gameSession.getGameboard());
        AIPlayerLocal player2 = new AIPlayerLocal(2, gameSession.getGameboard());
        
        gameSession.addPlayer(player1);
        gameSession.addPlayer(player2);
        gameSession.resetPiecesToInitialPosition();
        
        setupLogger();
    }
    
    private void setupLogger() {
        logger.setLevel(ChessProgram.LOG_LEVEL);
    }

    private void logEvaluationPoints() {
        NumberFormat nf = NumberFormat.getNumberInstance();  
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        
        if (!gameSession.getGameboard().getHash().equals(lastGameboardHash)) {
            lastGameboardHash = gameSession.getGameboard().getHash();
            logger.info("Board evaluation points" + ": " + nf.format(gameSession.getGameboard().evaluateTotalPositionPoints()));
        }
    }

    public final void handleLogic() {
        logEvaluationPoints();
        gameSession.updateState();
    }
    
    /** Renders the graphics immediately. */
    protected final void renderGameGraphics() {
        drawTiles();
        // drawHints(); // Used for testing purposes only
        drawSelections();
        drawPieces();
        drawLoadingIcon();
        drawEndStateText();
    }


    private void drawTiles() {
        final int TILE_SIZE_PIXELS = ChessProgram.getInstance().getTileSize();
        
        for (Tile tile : gameSession.getGameboard().getTiles()) {
            try {
                tile.getSprite().draw(ownerCanvas.getBufferStrategy().getDrawGraphics(), new PositionPoint(
                        (gameSession.getGameboard().getPositionX() + (tile.getColumn() - 1) * TILE_SIZE_PIXELS),
                        gameSession.getGameboard().getPositionY() +  (tile.getRow() - 1) * TILE_SIZE_PIXELS),
                        TILE_SIZE_PIXELS,
                        TILE_SIZE_PIXELS);
            } catch (SpriteNotFoundException e) {
                // Maybe we can continue without drawing this sprite
            }
        }
    }

    /** If the player holds the right mouse button down and hovers
     * the mouse on his/her own piece, shows the pieces that protect the
     * selected piece (for testing purposes only). */
    private boolean drawHints() {
        if (ChessMouse.getInstance().getRightButtonState() != MouseButtonState.PRESSED) {
            return false;
        }
        
        for (Tile tile : gameSession.getGameboard().getTiles()) {
            if (!ChessMouse.getInstance().isOnTile(tile)) { continue; }
            if (!tile.hasPiece()) { continue; }
            final int TILE_SIZE_PIXELS = ChessProgram.getInstance().getTileSize();
            Sprite sprite = ChessSpriteContainer.getInstance().getSprite("selected_pawn");
            
            for (Piece protector : tile.getPiece().findProtectors()) {
                sprite.draw(ownerCanvas.getBufferStrategy().getDrawGraphics(), new PositionPoint(
                        gameSession.getGameboard().getPositionX() + (protector.getColumn() - 1) * TILE_SIZE_PIXELS,
                        gameSession.getGameboard().getPositionY() + (protector.getRow() - 1) * TILE_SIZE_PIXELS),
                        TILE_SIZE_PIXELS,
                        TILE_SIZE_PIXELS); 
            }

            
        }
        
        return false;
    }
    
    private void drawSelections() {
        final int TILE_SIZE_PIXELS = ChessProgram.getInstance().getTileSize();
        
        for (Piece piece : gameSession.getGameboard().getPieces()) {
            if (piece.isSelected()) {
                    try {
                        Sprite sprite = ChessSpriteContainer.getInstance().getSprite("selected_pawn");
                        sprite.draw(ownerCanvas.getBufferStrategy().getDrawGraphics(), new PositionPoint(
                                gameSession.getGameboard().getPositionX() + (piece.getColumn() - 1) * TILE_SIZE_PIXELS,
                                gameSession.getGameboard().getPositionY() + (piece.getRow() - 1) * TILE_SIZE_PIXELS),
                                TILE_SIZE_PIXELS,
                                TILE_SIZE_PIXELS);
                    } catch (SpriteNotFoundException e) {
                        // Maybe we can continue without drawing this sprite
                    }
            }
        }
    }

    private void drawPieces() {
        /** The used game engine offers a position points for every gameplay object. However, in
         * this game the position of the pieces depends of it's parent Tile, so we just draw the
         * sprites in the position that is depended on theyr parent tile.
         */
        
        final int TILE_SIZE_PIXELS = ChessProgram.getInstance().getTileSize();
        
        for (Piece piece : gameSession.getGameboard().getPieces()) {
            try {
                // The piece is moving, calculate it's position
                if (piece.getStateName() == PieceStateName.PIECE_STATE_MOVE) {
                    /**
                     * The position depends on the movement's progress
                     * Formula for calculating the position in the X axis:
                     * sourceX + ((targetX - sourceX) * movementProgress)
                     */
                    double sourceX = gameSession.getGameboard().getPositionX()
                            + (piece.getColumn() - 1) * TILE_SIZE_PIXELS;
                    double targetX = gameSession.getGameboard().getPositionX()
                            + (piece.getTargetColumn() - 1) * TILE_SIZE_PIXELS;
                    double positionX = sourceX + ((targetX - sourceX) * (piece.getMovementProgress() / 100));
                    double sourceY = gameSession.getGameboard().getPositionY()
                            + (piece.getRow() - 1) * TILE_SIZE_PIXELS;
                    double targetY = gameSession.getGameboard().getPositionY()
                            + (piece.getTargetRow() - 1) * TILE_SIZE_PIXELS;
                    double positionY = sourceY + ((targetY - sourceY) * (piece.getMovementProgress() / 100));
                    piece.getSprite().draw(ownerCanvas.getBufferStrategy().getDrawGraphics(),
                            new PositionPoint((int) positionX, (int) positionY), TILE_SIZE_PIXELS, TILE_SIZE_PIXELS);
                } else {
                    // The piece is not moving, draw it on the corresponding tile
                    piece.getSprite().draw(ownerCanvas.getBufferStrategy().getDrawGraphics(), new PositionPoint(
                            gameSession.getGameboard().getPositionX() + (piece.getColumn() - 1) * TILE_SIZE_PIXELS,
                            gameSession.getGameboard().getPositionY() + (piece.getRow() - 1) * TILE_SIZE_PIXELS),
                            TILE_SIZE_PIXELS,
                            TILE_SIZE_PIXELS);
                }
            } catch (SpriteNotFoundException e) {
                // Maybe we can continue without drawing this sprite
            }
        }
    }

    private void drawLoadingIcon() {
        // Draw this if there is an AI player in the game and it is in the state play
        for (Player player : gameSession.getPlayers()) {
            if (!player.isHuman() && player.getStateName() == PlayerStateName.PLAY) {
                Sprite sprite = ChessAnimationContainer.getInstance().getAnimation("loading_icon").getCurrentSprite();
                sprite.draw(ownerCanvas.getBufferStrategy().getDrawGraphics(), new PositionPoint(
                        ownerCanvas.getWidth() / 2 - sprite.getWidth() / 2,
                        ownerCanvas.getHeight() / 2 - sprite.getHeight() / 2));
                break;
            }
        }
    }

    private boolean drawEndStateText() {
        if (gameSession.getStateName() != GameSessionStateName.GAME_OVER) {
            return false;
        }
       
        // Find the text
        String text;
        if (gameSession.getWinner() == 0) {
            text = "Stealmate!";
        } else {
            text = gameSession.getWinnerSide() + " " + "won the match!";
        }
        
        // Find the text's color
        if (gameSession.getWinner() == 0) {
            ownerCanvas.setForeground(Color.RED);
        } else if (gameSession.getWinner() == 1) {
            ownerCanvas.setForeground(Color.WHITE);
        } else if (gameSession.getWinner() == 2) {
            ownerCanvas.setForeground(Color.BLACK);
        }
        
        // Find the text's dimensions
        double textWidth = ownerCanvas.getBufferStrategy().getDrawGraphics()
                .getFontMetrics().getStringBounds(text, ownerCanvas.getBufferStrategy().getDrawGraphics()).getWidth();
        double textHeight  = ownerCanvas.getBufferStrategy().getDrawGraphics()
                .getFontMetrics().getStringBounds(text, ownerCanvas.getBufferStrategy().getDrawGraphics()).getHeight();  
        
        // Draw the text
        ownerCanvas.getBufferStrategy().getDrawGraphics().drawString(text,
                (int) (ownerCanvas.getWidth() / 2 - textWidth / 2),
                (int) (ownerCanvas.getHeight() / 2 - textHeight / 2));
        
        ownerCanvas.setDefaultFont();
        
        return true;
    }
    
    public final GameSession getGameSession() {
        return gameSession;
    }

    @Override
    public String getName() {
        return "GAMEPLAY";
    }
}