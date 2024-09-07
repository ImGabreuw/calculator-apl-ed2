package com.github.imgabreuw.token.operator;

import com.github.imgabreuw.token.AssociativeType;
import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.OperatorToken;

public final class DivisionOperatorToken extends OperatorToken {

    public DivisionOperatorToken() {
        super('/');
    }

    @Override
    public NumberToken evaluate(NumberToken left, NumberToken right) {
        double rightValue = right.getValue();

        if (rightValue == 0) {
            throw new IllegalArgumentException("Denominator cannot be 0.");
        }

        double result = left.getValue() / rightValue;
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
        return "Division";
    }

}
