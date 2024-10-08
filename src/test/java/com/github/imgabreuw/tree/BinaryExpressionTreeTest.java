package com.github.imgabreuw.tree;

import com.github.imgabreuw.Parser;
import com.github.imgabreuw.helpers.PostfixNotation;
import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.operator.binary.DivisionBinaryOperatorToken;
import com.github.imgabreuw.token.operator.binary.MultiplicationBinaryOperatorToken;
import com.github.imgabreuw.token.operator.binary.ParenthesisBinaryOperatorToken;
import com.github.imgabreuw.token.operator.binary.SumBinaryOperatorToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryExpressionTreeTest {

    private BinaryExpressionTree underTest;

    @BeforeEach
    public void setUp() {
        List<Token> tokens = List.of(
                new NumberToken("5"),
                new MultiplicationBinaryOperatorToken(),
                new NumberToken("2"),
                new SumBinaryOperatorToken(),
                new ParenthesisBinaryOperatorToken('('),
                new NumberToken("3"),
                new SumBinaryOperatorToken(),
                new NumberToken("6"),
                new ParenthesisBinaryOperatorToken(')'),
                new DivisionBinaryOperatorToken(),
                new NumberToken("3")
        );

        PostfixNotation postfixNotation = new PostfixNotation();
        Deque<Token> postFixTokens = postfixNotation.convert(tokens);

        Parser parser = new Parser();
        underTest = parser.parse(postFixTokens);
    }

    @Test
    public void testIsEmpty() {
        BinaryExpressionTree emptyTree = new BinaryExpressionTree();
        assertTrue(emptyTree.isEmpty(), "Tree should be empty");
        assertFalse(underTest.isEmpty(), "Tree should not be empty");
    }

    @Test
    public void testGetDegree() {
        assertEquals(2, underTest.getDegree(), "Degree of root should be 2");
    }

    @Test
    public void testGetHeight() {
        assertEquals(3, underTest.getHeight(), "Height of the tree should be 3");
    }

    @Test
    public void testInOrderTraversal() {
        // https://notation-visualizer.ajayliu.com/tree
        // Expected order: 5,50 * 2 + 3 + 6 / 3
        System.out.println("In-Order Traversal:");
        underTest.traverseInOrder();
    }

    @Test
    public void testPreOrderTraversal() {
        // https://notation-visualizer.ajayliu.com/tree
        // Expected order: + + * 5,50 2 3 / 6 3
        System.out.println("Pre-Order Traversal:");
        underTest.traversePreOrder();
    }

    @Test
    public void testPostOrderTraversal() {
        // https://notation-visualizer.ajayliu.com/tree
        // Expected order: 5,50 2 * 3 + 6 3 / +
        System.out.println("Post-Order Traversal:");
        underTest.traversePostOrder();
    }

    @Test
    public void testLevelOrderTraversal() {
        // https://notation-visualizer.ajayliu.com/tree
        // Expected order: 10, 5, 20, 3, 7, 15, 25, 1, 17
        System.out.println("Level-Order Traversal:");
        underTest.levelOrderTraversal();
    }

    @Test
    void testCalculate() {
        double calculate = underTest.calculate();
        assertEquals(13, calculate);
    }
}