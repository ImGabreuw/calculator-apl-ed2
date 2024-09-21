package com.github.imgabreuw.token.operator.binary;

import com.github.imgabreuw.token.AssociativeType;
import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.number.NumberToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public abstract class BinaryOperatorToken extends Token {

    private final char operator;

    public abstract NumberToken evaluate(NumberToken left, NumberToken right);

    public abstract int getPrecedence();

    public abstract AssociativeType getAssociative();

}
