package com.github.imgabreuw.token.operator;

import com.github.imgabreuw.token.AssociativeType;
import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.OperatorToken;

public final class ParenthesisOperatorToken extends OperatorToken {

    public ParenthesisOperatorToken(char operator) {
        super(operator);

        if (!(isOpening() || isClosing())) {
            throw new IllegalArgumentException("Parenthesis operators can only be open or close");
        }
    }

    @Override
    public NumberToken evaluate(NumberToken left, NumberToken right) {
        return null;
    }

    @Override
    public int getPrecedence() {
        return 3;
    }

    @Override
    public AssociativeType getAssociative() {
        return null;
    }

    @Override
    public String getType() {
        return "Parenthesis";
    }

    public boolean isOpening() {
        return getOperator() == '(';
    }

    public boolean isClosing() {
        return getOperator() == ')';
    }

}
