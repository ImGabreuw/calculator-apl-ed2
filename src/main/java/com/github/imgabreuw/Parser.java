package com.github.imgabreuw;

import com.github.imgabreuw.token.operator.binary.BinaryOperatorToken;
import com.github.imgabreuw.token.operator.unary.UnaryOperatorToken;
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
                nodeStack.push(new OperandNode(numberToken));
                continue;
            }

            if (current instanceof UnaryOperatorToken unaryOperatorToken) {
                if (nodeStack.isEmpty()) {
                    throw new IllegalArgumentException("Faltando operando para o operador unário");
                }

                Node operand = nodeStack.pop();
                nodeStack.push(new OperatorNode(unaryOperatorToken, null, operand));
                continue;
            }

            if (current instanceof BinaryOperatorToken binaryOperatorToken) {
                if (nodeStack.size() < 2) {
                    throw new IllegalArgumentException("Faltando operandos para o operador binário");
                }

                Node right = nodeStack.pop();
                Node left = nodeStack.pop();

                nodeStack.push(new OperatorNode(binaryOperatorToken, left, right));
            }
        }

        if (nodeStack.size() != 1) {
            throw new IllegalArgumentException("Expressão inválida");
        }

        Node root = nodeStack.pop();

        return new BinaryExpressionTree(root);
    }

}
