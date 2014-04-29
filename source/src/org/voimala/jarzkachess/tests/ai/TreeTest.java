package org.voimala.jarzkachess.tests.ai;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.players.ai.Tree;
import org.voimala.jarzkachess.gamelogic.players.ai.TreeNode;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class TreeTest {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    @Before
    public final void setUp() throws IOException {
        logger.setLevel(Level.OFF);
    }
    
    @Test
    public final void testGetAllNodes() {
        Tree tree = new Tree();
        TreeNode node = new TreeNode(null);
        tree.addNodeOnTopOfTree(node);
        
        node.addChild(new TreeNode(null));
        
        assertEquals(tree.getAllNodes().size(), 2);
    }
    
    @Test
    public final void testFindNodesWithoutChildren() {
        Tree tree = new Tree();
        TreeNode node = new TreeNode(null);
        tree.addNodeOnTopOfTree(node);
        
        node.addChild(new TreeNode(null));
        
        assertEquals(tree.findNodesWithoutChildren().size(), 1);
    }
    
    @Test
    public final void testGetNumberOfNodes() {
        Tree tree = new Tree();
        TreeNode node = new TreeNode(null);
        tree.addNodeOnTopOfTree(node);
        
        node.addChild(new TreeNode(null));
        
        assertEquals(tree.getNumberOfNodes(), 2);
    }
}
