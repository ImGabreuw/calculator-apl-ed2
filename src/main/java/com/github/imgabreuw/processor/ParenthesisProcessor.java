package com.github.imgabreuw.processor;

import com.github.imgabreuw.helpers.Input;
import com.github.imgabreuw.token.operator.binary.ParenthesisBinaryOperatorToken;
import com.github.imgabreuw.token.Token;

public class ParenthesisProcessor implements TokenProcessor {

    @Override
    public Token process(Input input) {
        char currentChar = input.getCurrentCharacter();

        if ("()".indexOf(currentChar) == -1) {
            return null;
        }

        input.next();
        return new ParenthesisBinaryOperatorToken(currentChar);
    }

}
