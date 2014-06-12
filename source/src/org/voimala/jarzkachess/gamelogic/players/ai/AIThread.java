package org.voimala.jarzkachess.gamelogic.players.ai;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.voimala.jarzkachess.exceptions.ChessException;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.gamelogic.pieces.Piece;
import org.voimala.jarzkachess.gamelogic.players.Player;
import org.voimala.jarzkachess.programbody.ChessProgram;

/***
 * A simple definition of the AI:
 * Do once:
 * Find all possible moves for the current player. Then find the opponent's best possible
 * counter-move.
 *
 * Loop:
 * Find all the nodes that have no children. Pick the one that has the best route position points
 * for the current player. Analyze own and opponent's possible moves for the picked node.
 */

public class AIThread extends Thread {
    private Tree tree = null;
    
    private int playerNumber = 0;
    private Gameboard gameboard = null;
    private long timestampAIBegin = 0;
    
    private long treeDevelopmentTimeMaxInMs = 4000;
    
    private int turnNumber = 0;
    
    private int treeDevelopmentTimes = 0; // How many times developTree added nodes to the tree.
    
    private HalfMove answer = null;
    
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    /** @param answer The place where to put the answer when we got it. It is assumed that the
     * caller has created the object (and it is not set) and can check it's value later. */
    public AIThread(final Gameboard gameboard, final HalfMove answer, final int playerNumber,
            final int turnNumber) {
        super("AIThread");
        
        setPriority(Thread.MAX_PRIORITY);
        
        this.answer = answer;
        this.playerNumber = playerNumber;
        this.gameboard = gameboard;
        this.turnNumber = turnNumber;
        
        analyseTurnNumber();
        
        setupLogger();
    }

    private void analyseTurnNumber() {
        if (turnNumber < 10) {
            treeDevelopmentTimeMaxInMs = 1000;
        } else {
            treeDevelopmentTimeMaxInMs = 4000;
        }
    }

    private void setupLogger() {
        logger.setLevel(ChessProgram.LOG_LEVEL);
    }
    
    private void printFirstMovesOfTree() {
        String output = "Current position" + ": ";
        output += "\n";
        output += printEvaluationPoints(tree.getFirstTopNode(), "");
        output += "\n";
        output += "\n";
        
        for(TreeNode ownMove : tree.getFirstTopNode().getChildren()) {
            // Print all own moves
            output += "Our move" + ": ";
            output += String.valueOf(ownMove.getMove().getSourceRow()) + ","
                    + String.valueOf(ownMove.getMove().getSourceColumn());
            output += " -> ";
            output += String.valueOf(ownMove.getMove().getTargetRow()) + ","
                    + String.valueOf(ownMove.getMove().getTargetColumn());
            output += "\n";
            output += printEvaluationPoints(ownMove, "    ");
            output += "\n";
            
            for (TreeNode opponentMove : ownMove.getChildren()) {
                // Print opponent's moves
                output += "    ";
                output += "Opponent's move" + ": ";
                output += String.valueOf(opponentMove.getMove().getSourceRow()) + ","
                        + String.valueOf(opponentMove.getMove().getSourceColumn());
                output += " -> ";
                output += String.valueOf(opponentMove.getMove().getTargetRow()) + ","
                        + String.valueOf(opponentMove.getMove().getTargetColumn());
                output += "\n";
                output += printEvaluationPoints(ownMove, "    " + "    ");
                output += "\n";
                output += "\n";

            }
        }
        
        output += "\n";
        output += "\n";
        
        logger.info(output);
    }
    
    public final String printEvaluationPoints(TreeNode node, String prefix) {
        NumberFormat nf = NumberFormat.getNumberInstance();  
        nf.setMaximumFractionDigits(2);
        
        String output = "";
        output += prefix + "Pieces" + " " + nf.format(node.getGameboard().evaluatePieces()) + "\n";
        output += prefix + "Advanced Pawns" + " " + nf.format(node.getGameboard().evaluateAdvancedPawns()) + "\n";
        output += prefix + "Doubled Pawns" + " " + nf.format(node.getGameboard().evaluateDoubledPawns()) + "\n";
        output += prefix + "Isolated Pawns" + " " + nf.format(node.getGameboard().evaluateIsolatedPawns()) + "\n";
        output += prefix + "Bishop Pair" + " " + nf.format(node.getGameboard().evaluateBishopPair()) + "\n";
        
        
        output += prefix + "Game Phases" + " " + nf.format(node.getGameboard().evaluateGamePhases()) + "\n";
        
        output += prefix + "Attacks" + " " + nf.format(node.getGameboard().evaluateAttacks()) + "\n";
        output += prefix + "Protected Pieces" + " " + nf.format(node.getGameboard().evaluateProtectedPieces()) + "\n";
        output += prefix + "Center Control" + " " + nf.format(node.getGameboard().evaluateCenterControl()) + "\n";
        output += prefix + "Mobility" + " " + nf.format(node.getGameboard().evaluateMobility()) + "\n";
        
        output += prefix + "King Protection" + " " + nf.format(node.getGameboard().evaluateKingProtection()) + "\n";
        output += prefix + "Check" + " " + nf.format(node.getGameboard().evaluateCheck()) + "\n";
        output += prefix + "Checkmate" + " " + nf.format(node.getGameboard().evaluateCheckmate()) + "\n";
        
        output += prefix + "POSITION" + " " + nf.format(node.getPositionPoints()) + "\n";
        output += prefix + "ROUTE" + " " + nf.format(node.getRoutePoints());
        return output;
    }
    
    @Override
    public final void run() {
        logger.info("AIThread logging started.");
        HalfMove answer = runAI();
        
        this.answer.setSource(answer.getSource());
        this.answer.setTarget(answer.getTarget());
        this.answer.setPlayerNumber(2);
        
        logger.info("Answer found from the three of" + " " + tree.getNumberOfNodes() + " " + "nodes" + ".");

        /* For testing purposes only
        if (logger.isLoggable(Level.ALL)) {
            printFirstMovesOfTree();
        } */
        
        this.answer.setSet(true);
    }

    public final HalfMove runAI() {
        timestampAIBegin = System.currentTimeMillis();
        
        constructTree();
        
        if (tree.getFirstTopNode().hasChildren()) { // The player can move at least one piece?
            developTree();
            logger.info("Tree development completed.");
            logger.info("developTree loop was executed" + " " + treeDevelopmentTimes + " " + "times.");
            logger.info("The tree has" + " " + tree.findNodesWithoutChildren().size() + " " +
                    "nodes that have no children.");
            TreeNode bestNode =  findNodeThatHasBestRoutePointsForCurrentPlayerAtBottomOfTree();
            logger.info("AI execution took" + " "
                    + String.valueOf(System.currentTimeMillis() - timestampAIBegin) + " " + "ms.");
            return createMoveTowardsNode(bestNode);
        }
        
        // Stalemate, the gamesession should notice this and change it's state.
        return null;
    }
    
    /** Creates the tree and adds the first own and opponent's moves to the tree. */
    public final void constructTree() {
        tree = new Tree();
        TreeNode topNode = new TreeNode(gameboard.clone());
        tree.addNodeOnTopOfTree(topNode);
        logger.info("Top node added to the tree.");
        
        addFullMoveAsChild(topNode);
        
        logger.info("Tree of" + " " + tree.getNumberOfNodes() + " " + "nodes constructed");
    }
    
    /** 
     * Find the player's all possible moves and add them to the tree.
     * Find the opponent's all possible counter-moves and add the best to the tree.
     * 
     * TODO If the algorithm finds a "very bad move" for the current player, it does not
     * add it to the tree.
     * 
     * TODO If the algorithm finds a "killer move" for the opponent, it does not
     * continue to find the opponent's other possible moves. The algorithm suggest that
     * this is the move that the opponent is going to perform.
     */
    public final void addFullMoveAsChild(final TreeNode node) {
        logger.info("About to add full move as a new child node.");
        // Find all possible moves for this player
        ArrayList<TreeNode> ownMoves = (ArrayList<TreeNode>) findPossibleMovesForPlayer(node, playerNumber);
        logger.info("Current player's possible moves found.");
        
        // For every own move that we just found, find the opponent's possible counter-moves
        int opponentNumber = Player.findOpponentForPlayer(playerNumber);
        for (TreeNode ownMove : ownMoves) {
            logger.info("Finding opponent's possible counter-moves against our own move.");
            ArrayList<TreeNode> opponentMoves = (ArrayList<TreeNode>) findPossibleMovesForPlayer(ownMove, opponentNumber);
            
            // Find the opponent's best counter-move and add it to the tree
            if (!opponentMoves.isEmpty()) {
                TreeNode opponentBestMove = findNodeThatHasBestPositionPointsForPlayer(opponentMoves, opponentNumber);
                ownMove.addChild(opponentBestMove);
            }
            
            logger.info("The opponent's best counter-move found.");
        }
        
        logger.info("All opponent moves analysed.");
        
        // Add the nodes to the tree
        node.addChild(ownMoves);
    }
    
    /** 
     * Takes a tree node as an argument, finds all the player's possible moves and
     * creates a new node with a new gameboard where the move is performed and
     * Finally returns a list of the nodes.
     * */
    private List<TreeNode> findPossibleMovesForPlayer(final TreeNode parentNode, final int playerNumber) {
        if (parentNode.getMove() != null) {
            logger.info("Trying to find the possible moves for the player " + playerNumber);
        }
        
        ArrayList<TreeNode> newNodes = new ArrayList<>();
        
        // Find every piece owned by this player
        for (Piece piece : parentNode.getGameboard().findPiecesOwnedByPlayer(playerNumber)) { 
             // Find every possible move for this piece
            for (HalfMove move : piece.findPossibleMoves(true)) {
                // Clone the gameboard and perform the move
                Gameboard newGameboard = parentNode.getGameboard().clone();
                newGameboard.movePieceImmediately(move);
                TreeNode newNode = new TreeNode(newGameboard, parentNode, playerNumber, move);
                newNodes.add(newNode);
                
                logger.info("Found possible move. Source: " + newNode.getMove().getSourceRow() + "," + 
                        newNode.getMove().getSourceColumn() + " Target: " +
                        newNode.getMove().getTargetRow() + "," + newNode.getMove().getTargetColumn());
            }
        }
        
        logger.info("Move found.");
        Collections.shuffle(newNodes);
        return newNodes;
    }

    private boolean isNode1ConsiderablyBetterThanNode2ForPlayer(TreeNode node1, TreeNode node2, final int playerNumber) {
        if (playerNumber == 1) {
            return node1.getPositionPoints() > node2.getPositionPoints() + 10;
        } else {
            return node1.getPositionPoints() < node2.getPositionPoints() - 10;
        }
    }

    private boolean isNode1ConsiderablyWorseThanNode2ForPlayer(TreeNode node1, TreeNode node2, final int playerNumber) {
        if (playerNumber == 1) {
            return node1.getPositionPoints() + 4 < node2.getPositionPoints();
        } else {
            return node1.getPositionPoints() - 4 > node2.getPositionPoints();
        }
    }
    
    /**
     * If the player is 1 (white), returns the node with the highest position point value.
     * If the player is 2 (black), return the node with the lowest position point value.
     * Does not check children.
     * */
    private TreeNode findNodeThatHasBestPositionPointsForPlayer(final List<TreeNode> nodes, final int playerNumber) {
        logger.info("Finding the node with best route position points.");
        if (nodes.isEmpty()) {
            throw new ChessException("The list can not be empty!");
        }

        TreeNode bestMoveSoFar = nodes.get(0); // Start from the first element
        
        for (TreeNode node : nodes) {
            bestMoveSoFar = findNodeThatHasBetterPositionPointsPlayer(bestMoveSoFar, node, playerNumber);
            logger.info("Best node so far: " + bestMoveSoFar.getPositionPoints());
        }
        
        return bestMoveSoFar;
    }
    
    /** 
     * Compares the two given nodes and returns the better node for the given player.
     * If the player is 1 (white), returns the node which has bigger position points.
     * If the player is 2 (black), return the node which has lower position points.
     * */
    public final TreeNode findNodeThatHasBetterPositionPointsPlayer(TreeNode node1, TreeNode node2, final int playerNumber) {
        if (playerNumber == 1) {
            if (node1.getPositionPoints() > node2.getPositionPoints()) {
                return node1;
            } else {
                return node2;
            }
        } else {
            if (node1.getPositionPoints() > node2.getPositionPoints()) {
                return node2;
            } else {
                return node1;
            }
        }
    }
    
    /** Find the best routenode in the tree and add it as a new child. */
    private void developTree() {
        while (System.currentTimeMillis() < timestampAIBegin + treeDevelopmentTimeMaxInMs) {
            treeDevelopmentTimes++;
            
            logger.info("Developing tree...");
            
            addFullMoveAsChild(findNodeThatHasBestRoutePointsForPlayer(tree.findNodesWithoutChildren(), playerNumber));
            
            logger.info("Tree development ended up with the tree of" + " "
                    + tree.getNumberOfNodes() + " " + "nodes.");
        }
    }

    /**
     * If the player is 1 (white), returns the node with the highest route position point value.
     * If the player is 2 (black), return the node with the lowest route position point value.
     * Does not check children.
     * */
    private TreeNode findNodeThatHasBestRoutePointsForPlayer(List<TreeNode> nodes, final int playerNumber) {
        logger.info("Finding the node with best route points.");
        if (nodes.isEmpty()) {
            throw new ChessException("The list can not be empty!");
        }

        TreeNode bestMoveSoFar = nodes.get(0); // Start from the first element
        logger.info("Best node so far: " + bestMoveSoFar.getRoutePoints());
        
        for (TreeNode node : nodes) {
            bestMoveSoFar = findNodeThatHasBetterRoutePointsForPlayer(bestMoveSoFar, node, playerNumber);
            logger.info("Best node so far: " + bestMoveSoFar.getRoutePoints());
        }
        
        return bestMoveSoFar;
    }
    
    /** 
     * Compares the two given nodes and returns the better node for the given player.
     * If the player is 1 (white), returns the node which has bigger position points.
     * If the player is 2 (black), return the node which has lower position points.
     * */
    public final TreeNode findNodeThatHasBetterRoutePointsForPlayer(TreeNode node1, TreeNode node2, final int playerNumber) {
        if (playerNumber == 1) {
            if (node1.getRoutePoints() > node2.getRoutePoints()) {
                return node1;
            } else {
                return node2;
            }
        } else {
            if (node1.getRoutePoints() > node2.getRoutePoints()) {
                return node2;
            } else {
                return node1;
            }
        }
    }
    
    /** Returns the move that leads to the best known outcome. */
    private TreeNode findNodeThatHasBestRoutePointsForCurrentPlayerAtBottomOfTree() {
        // Select the nodes that have no children.
        ArrayList<TreeNode> endPositionNodes = (ArrayList<TreeNode>) tree.findNodesWithoutChildren();
        logger.info("Found" + " " + endPositionNodes.size() + " " + "nodes with no children from the tree.");
        
        // Find the node with the best route points for this player.
        TreeNode bestNode = findNodeThatHasBestRoutePointsForPlayer(endPositionNodes, playerNumber);
        logger.info("Found the best node.");
        return bestNode;
    }
    
    /**
     * Takes a tree node as an argument. The node is supposed to be the "best"
     * node that we have found in the tree. The best node represents a position
     * that we want to achieve.
     */
    public HalfMove createMoveTowardsNode(TreeNode node) {
        if (node == tree.getFirstTopNode()) {
            throw new ChessException("The node can not be the top node.");
        }
        
        // Move up in the tree until we find the top node's child.
        TreeNode currentlySelectedNode = node;
        logger.info("Starting finding the top node's child from level" + " " + node.getLevelInTree());
        while (true) {
            if (currentlySelectedNode.getParent().getLevelInTree() == 1) {
                // Top node's child found!
                logger.info("Top node's child found.");
                break;
            } else {
                currentlySelectedNode = currentlySelectedNode.getParent();
                logger.info("Moved up in the tree to the level" + " " + currentlySelectedNode.getLevelInTree());
            }
        }
        
        logger.info("Created a move towards the given node.");
        return currentlySelectedNode.getMove();
    }
}
