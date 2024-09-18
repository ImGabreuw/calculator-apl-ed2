package com.github.imgabreuw.processor;

import com.github.imgabreuw.Input;
import com.github.imgabreuw.token.operator.ParenthesisOperatorToken;
import com.github.imgabreuw.token.Token;

public class ParenthesisProcessor implements TokenProcessor {

    @Override
    public Token process(Input input) {
        char currentChar = input.getCurrentCharacter();

        if ("()".indexOf(currentChar) == -1) {
            return null;
        }

        input.next();
        return new ParenthesisOperatorToken(currentChar);
    }

}
