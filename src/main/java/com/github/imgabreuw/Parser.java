package com.github.imgabreuw;

import com.github.imgabreuw.token.OperatorToken;
import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.tree.BinaryExpressionTree;
import com.github.imgabreuw.tree.Node;
import com.github.imgabreuw.tree.OperandNode;
import com.github.imgabreuw.tree.OperatorNode;

import java.util.Deque;
import java.util.LinkedList;

public class Parser {

    public BinaryExpressionTree parse(Deque<Token> tokens) {
        Deque<Node> nodeStack = new LinkedList<>();

        for (Token token : tokens) {
            if (token instanceof NumberToken numberToken) {
                nodeStack.push(new OperandNode(numberToken));
                continue;
            }

            if (token instanceof OperatorToken operatorToken) {
                Node right = nodeStack.pop();
                Node left = nodeStack.pop();
                nodeStack.push(new OperatorNode(operatorToken, left, right));
            }
        }

        Node root = nodeStack.pop();

        return new BinaryExpressionTree(root);
    }

}
