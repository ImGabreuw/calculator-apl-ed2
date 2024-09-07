package com.github.imgabreuw;

import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.tree.BinaryNode;
import com.github.imgabreuw.tree.BinaryTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest {

    private BinaryTree binaryTree;

    @BeforeEach
    public void setUp() {
        // Create a BinaryTree and set up a longer tree
        binaryTree = new BinaryTree();
        BinaryNode root = new BinaryNode();
        root.setValue(new NumberToken(10));

        BinaryNode leftChild = new BinaryNode();
        leftChild.setValue(new NumberToken(5));

        BinaryNode rightChild = new BinaryNode();
        rightChild.setValue(new NumberToken(20));

        BinaryNode leftLeftChild = new BinaryNode();
        leftLeftChild.setValue(new NumberToken(3));

        BinaryNode leftRightChild = new BinaryNode();
        leftRightChild.setValue(new NumberToken(7));

        BinaryNode rightLeftChild = new BinaryNode();
        rightLeftChild.setValue(new NumberToken(15));

        BinaryNode rightRightChild = new BinaryNode();
        rightRightChild.setValue(new NumberToken(25));

        BinaryNode leftLeftLeftChild = new BinaryNode();
        leftLeftLeftChild.setValue(new NumberToken(1));

        BinaryNode rightLeftRightChild = new BinaryNode();
        rightLeftRightChild.setValue(new NumberToken(17));

        // Building the tree
        root.setLeft(leftChild);
        root.setRight(rightChild);

        leftChild.setLeft(leftLeftChild);
        leftChild.setRight(leftRightChild);

        rightChild.setLeft(rightLeftChild);
        rightChild.setRight(rightRightChild);

        leftLeftChild.setLeft(leftLeftLeftChild);

        rightLeftChild.setRight(rightLeftRightChild);

        binaryTree.setRoot(root);
    }

    @Test
    public void testIsEmpty() {
        BinaryTree emptyTree = new BinaryTree();
        assertTrue(emptyTree.isEmpty(), "Tree should be empty");
        assertFalse(binaryTree.isEmpty(), "Tree should not be empty");
    }

    @Test
    public void testGetDegree() {
        assertEquals(2, binaryTree.getDegree(), "Degree of root should be 2");
    }

    @Test
    public void testGetHeight() {
        assertEquals(3, binaryTree.getHeight(), "Height of the tree should be 3");
    }

    @Test
    public void testInOrderTraversal() {
        // Expected order: 1, 3, 5, 7, 10, 15, 17, 20, 25
        System.out.println("In-Order Traversal:");
        binaryTree.inOrderTraversal();
    }

    @Test
    public void testPreOrderTraversal() {
        // Expected order: 10, 5, 3, 1, 7, 20, 15, 17, 25
        System.out.println("Pre-Order Traversal:");
        binaryTree.preOrderTraversal();
    }

    @Test
    public void testPostOrderTraversal() {
        // Expected order: 1, 3, 7, 5, 17, 15, 25, 20, 10
        System.out.println("Post-Order Traversal:");
        binaryTree.postOrderTraversal();
    }

    @Test
    public void testLevelOrderTraversal() {
        // Expected order: 10, 5, 20, 3, 7, 15, 25, 1, 17
        System.out.println("Level-Order Traversal:");
        binaryTree.levelOrderTraversal();
    }
}
