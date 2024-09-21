package com.github.imgabreuw.token.operator.unary;

import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.number.NumberToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public abstract class UnaryOperatorToken extends Token {

    private final char operator;

    private final char uniqueSymbol;

    public abstract NumberToken evaluate(NumberToken number);

    public abstract int getPrecedence();

}
