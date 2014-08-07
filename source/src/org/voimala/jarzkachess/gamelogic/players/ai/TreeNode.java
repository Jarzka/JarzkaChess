package org.voimala.jarzkachess.gamelogic.players.ai;

import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.Move;
import org.voimala.jarzkachess.programbody.ChessProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TreeNode {
    private Gameboard gameboard = null;
    private TreeNode parent = null;
    private ArrayList<TreeNode> children = new ArrayList<>();
    private int lastMovePlayer = 0; /** The player who made the last move (the move which lead to this position). */
    private Move lastMove = null; /** The move which led to this state.*/
    private int levelInTree = 0; /** The top node operates on level 1, it's children operate on level 2 etc.*/
    private double positionPoints = 0;
    private double routePoints = 0; /** The average of the position points starting from the top parent node. */
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    /**
     * @param gameboard The gameboard in this node.
     * @param parent Parent node.
     * @param lastMovePlayer Player number who made the last move.
     * @param lastMove The move which led to this state.
     */
    public TreeNode(final Gameboard gameboard, final TreeNode parent, final int lastMovePlayer, final Move lastMove) {
        this.gameboard = gameboard;
        this.parent = parent;
        this.lastMovePlayer = lastMovePlayer;
        this.lastMove = lastMove;

        setLevelInTree();
        evaluatePosition();
        setupLogger();
    }
   
    /**
     * @param gameboard The gameboard in this node.
     */
    public TreeNode(final Gameboard gameboard) {
        this.gameboard = gameboard;
        
        setLevelInTree();
        evaluatePosition();
        setupLogger();
    }
    
    private void setupLogger() {
        logger.setLevel(ChessProgram.LOG_LEVEL);
    }
    
    private void setLevelInTree() {
        if (getParent() == null) {
            // This is the first node in the tree
            levelInTree = 1;
        } else {
            this.levelInTree = parent.getLevelInTree() + 1;
        } 
    }

    public int getLevelInTree() {
        return levelInTree;
    }

    private void evaluatePosition() {
        evaluatePositionPoints();
        evaluateRoutePoints();
    }

    private void evaluatePositionPoints() {
        if (gameboard != null) {
            positionPoints = gameboard.evaluateTotalPositionPoints();
        }
    }

    private void evaluateRoutePoints() {
        double routePositionPointsSum = getTotalRoutePoints(this);
        this.routePoints = routePositionPointsSum / ((double) getNumberOfParents(this) + 1);
    }

    private double getTotalRoutePoints(TreeNode node) {
        double points = node.getPositionPoints();

        if (node.getParent() != null) {
            points += getTotalRoutePoints(node.getParent());
        }

        return points;
    }

    private int getNumberOfParents(TreeNode node) {
        int parents = 0;

        if (node.getParent() != null) {
            parents += 1;
            parents += getNumberOfParents(node.getParent());
        }

        return parents;
    }

    public final double getPositionPoints() {
        return positionPoints;
    }

    public final double getRoutePoints() {
        return routePoints;
    }

    /** The parent can be null! */
    public final TreeNode getParent() {
        return parent;
    }

    public final void setParent(final TreeNode parent) {
        this.parent = parent;
    }

    public final Gameboard getGameboard() {
        return gameboard;
    }

    public final void setGameboard(final Gameboard gameboard) {
        this.gameboard = gameboard;
    }

    public final List<TreeNode> getChildren() {
        return children;
    }

    public final List<TreeNode> getDescendants() {
        ArrayList<TreeNode> descendants = new ArrayList<>();
        descendants.addAll(children);

        for (TreeNode child : children) {
            descendants.addAll(child.getDescendants());
        }

        return descendants;
    }

    public final void addChild(final TreeNode node) {
        if (node == null) {
            throw new NullPointerException("Can not add null node as a child node.");
        }

        children.add(node);
    }

    public void addChild(final List<TreeNode> nodes) {
        children.addAll(nodes);

        if (nodes.isEmpty()) {
            logger.warning("nodes list was empty!");
        }
    }

    public final boolean hasChildren() {
        return !children.isEmpty();
    }

    public final TreeNode findBottomChild() {
        TreeNode bottomNode = new TreeNode(null);

        for (TreeNode childNode : children) {
            if (childNode.hasChildren()) {
                TreeNode youngestChild = childNode.findBottomChild();
                bottomNode = findLowerNode(bottomNode, youngestChild);
            } else {
                bottomNode = findLowerNode(bottomNode, childNode);
            }
        }

        return bottomNode;
    }

    /** Returns the node which operates on the lower level (level variable is higher). */
    private TreeNode findLowerNode(final TreeNode node1, final TreeNode node2) {
        if (node1.getLevelInTree() > node2.getLevelInTree()) {
            return node1;
        }
        
        return node2;
    }

    public final Move getMove() {
        return lastMove;
    }

    public final int getNumberOfChildrenAndGrandchildren() {
        int count = children.size();
        
        for (TreeNode node : children) {
            count += node.getNumberOfChildrenAndGrandchildren();
        }
        
        return count;
    }
}
