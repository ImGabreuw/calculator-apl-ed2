package com.github.imgabreuw.token.operator.binary;

import com.github.imgabreuw.token.AssociativeType;
import com.github.imgabreuw.token.number.NumberToken;

public final class MultiplicationBinaryOperatorToken extends BinaryOperatorToken {

    public MultiplicationBinaryOperatorToken() {
        super('*');
    }

    @Override
    public NumberToken evaluate(NumberToken left, NumberToken right) {
        double result = left.getValue() * right.getValue();
        return new NumberToken(result);
    }

    @Override
    public int getPrecedence() {
        return 2;
    }

    @Override
    public AssociativeType getAssociative() {
        return AssociativeType.LEFT;
    }

    @Override
    public String getType() {
        return "Multiplication";
    }

}
