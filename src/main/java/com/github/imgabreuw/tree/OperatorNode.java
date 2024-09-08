package com.github.imgabreuw.tree;

import com.github.imgabreuw.token.OperatorToken;
import com.github.imgabreuw.token.number.NumberToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OperatorNode extends Node {

    private final OperatorToken operator;

    private final Node left;
    private final Node right;

    @Override
    public NumberToken visit() {
        return operator.evaluate(
                left.visit(),
                right.visit()
        );
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
    public OperatorToken getValue() {
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
        return "%s".formatted(operator.getOperator());
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

}

