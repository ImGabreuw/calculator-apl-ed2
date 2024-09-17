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

        while (!tokens.isEmpty()) {
            Token current = tokens.pop();

            if (current instanceof NumberToken numberToken) {
                if (tokens.peek() instanceof OperatorToken) {
                    OperatorToken unaryOperator = (OperatorToken) tokens.pop();

                    nodeStack.push(new OperatorNode(unaryOperator, null, new OperandNode(numberToken)));
                    continue;
                }

                nodeStack.push(new OperandNode(numberToken));
                continue;
            }

            if (current instanceof OperatorToken operatorToken) {
                if (nodeStack.size() < 2) {
                    continue;
                }

                Node right = nodeStack.pop();
                Node left = nodeStack.pop();

                nodeStack.push(new OperatorNode(operatorToken, left, right));
            }
        }

        Node root = nodeStack.pop();

        return new BinaryExpressionTree(root);
    }

}
