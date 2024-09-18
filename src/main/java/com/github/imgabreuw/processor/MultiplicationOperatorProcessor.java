package com.github.imgabreuw.processor;

import com.github.imgabreuw.Input;
import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.operator.MultiplicationOperatorToken;

public class MultiplicationOperatorProcessor implements TokenProcessor {

    @Override
    public Token process(Input input) {
        char currentChar = input.getCurrentCharacter();

        if (currentChar != '*') {
            return null;
        }

        input.next();
        return new MultiplicationOperatorToken();
    }

}
