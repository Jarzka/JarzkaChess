package org.voimala.jarzkachess.tests.ai;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.voimala.jarzkachess.gamelogic.Cell;
import org.voimala.jarzkachess.gamelogic.GameSession;
import org.voimala.jarzkachess.gamelogic.Gameboard;
import org.voimala.jarzkachess.gamelogic.HalfMove;
import org.voimala.jarzkachess.gamelogic.pieces.*;
import org.voimala.jarzkachess.gamelogic.players.HumanPlayerLocal;
import org.voimala.jarzkachess.gamelogic.players.ai.AIPlayerLocal;
import org.voimala.jarzkachess.gamelogic.players.ai.AIThread;
import org.voimala.jarzkachess.gamelogic.players.ai.PlayerStatePlayAI;
import org.voimala.jarzkachess.gamelogic.players.ai.Tree;
import org.voimala.jarzkachess.gamelogic.players.ai.TreeNode;
import org.voimala.jarzkachess.graphics.ChessAnimationContainer;
import org.voimala.jarzkachess.graphics.ChessSpriteContainer;

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
