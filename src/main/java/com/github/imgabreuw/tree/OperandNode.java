package com.github.imgabreuw.tree;

import com.github.imgabreuw.token.number.NumberToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OperandNode extends Node {

    private final NumberToken value;

    @Override
    public NumberToken visit() {
        return value;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getDegree() {
        return 0;
    }

    @Override
    public NumberToken getValue() {
        return value;
    }

    @Override
    public OperandNode getLeft() {
        return null;
    }

    @Override
    public OperandNode getRight() {
        return null;
    }

    @Override
    public String toString() {
        if (value.getValue() % 1 == 0) {
            return "%d".formatted((int) value.getValue());
        }

        return "%.2f".formatted(value.getValue());
    }

}
