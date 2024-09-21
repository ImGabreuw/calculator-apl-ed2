package com.github.imgabreuw.token.operator.unary;

import com.github.imgabreuw.token.number.NumberToken;

public class NegativeOperatorToken extends UnaryOperatorToken {

    public NegativeOperatorToken() {
        super('-', '$');
    }

    @Override
    public NumberToken evaluate(NumberToken number) {
        return new NumberToken(-number.getValue());
    }

    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public String getType() {
        return "Negative";
    }
}
