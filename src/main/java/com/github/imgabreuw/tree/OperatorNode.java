package com.github.imgabreuw.tree;

import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.operator.binary.BinaryOperatorToken;
import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.operator.unary.UnaryOperatorToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OperatorNode extends Node {

    private final Token operator;

    private final Node left;
    private final Node right;

    @Override
    public NumberToken visit() {
        if (operator instanceof BinaryOperatorToken bToken) {
            return bToken.evaluate(
                    left != null ? left.visit() : null,
                    right != null ? right.visit() : null
            );
        }

        if (operator instanceof UnaryOperatorToken uToken) {
            return uToken.evaluate(right != null ? right.visit() : null);
        }

        throw new IllegalArgumentException("Unsupported operator: " + operator);
    }

    @Override
    public int getHeight() {
        if (isLeaf()) {
            return 0;
        }

        int leftHeight = left != null ? left.getHeight() : -1;
        int rightHeight = right != null ? right.getHeight() : -1;

        return Math.max(leftHeight, rightHeight) + 1;
    }

    @Override
    public int getDegree() {
        int degree = 0;

        if (left != null) {
            ++degree;
        }

        if (right != null) {
            ++degree;
        }

        return degree;
    }

    @Override
    public Token getValue() {
        return operator;
    }

    @Override
    public Node getLeft() {
        return left;
    }

    @Override
    public Node getRight() {
        return right;
    }

    @Override
    public String toString() {
        if (operator instanceof BinaryOperatorToken bToken) {
            return "%s".formatted(bToken.getOperator());
        }

        if (operator instanceof UnaryOperatorToken unaryToken) {
            return "%s".formatted(unaryToken.getOperator());
        }

        throw new IllegalArgumentException("Unsupported operator: " + operator);
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

}

