package org.voimala.jarzkachess.gamelogic.players.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tree {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
    
    public Tree() {
        setupLogger();
    }
    
    private void setupLogger() {
        logger.setLevel(Level.OFF);
    }

    public final ArrayList<TreeNode> getTree() {
        return nodes;
    }

    public final void addNodeOnTopOfTree(TreeNode node) {
        nodes.add(node);
    }
    
    public final TreeNode getFirstTopNode() {
        return nodes.get(0);
    }
    
    public final List<TreeNode> getAllNodes() {
        ArrayList<TreeNode> nodes = new ArrayList<>();
        nodes.addAll(this.nodes);
        
        for (TreeNode node : this.nodes) {
            nodes.addAll(node.getDescendants());
        }
        
        logger.info("Found" + " " + nodes.size() + " " + "nodes.");
        
        return nodes;
    }
    
    public List<TreeNode> findNodesWithoutChildren() {
        ArrayList<TreeNode> nodes = (ArrayList<TreeNode>) getAllNodes();
        
        // Delete nodes that have children
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).hasChildren()) {
                nodes.remove(i);
                i--;
            }
        }
        
        logger.info("Found" + " " + nodes.size() + " " + "nodes that have no children.");
        
        return nodes;
    }
    
    public final int getNumberOfNodes() {
        int count = nodes.size();
        
        for (TreeNode node : nodes) {
            count += node.getNumberOfChildrenAndGrandchildren();
        }
        
        return count;
    }
}
