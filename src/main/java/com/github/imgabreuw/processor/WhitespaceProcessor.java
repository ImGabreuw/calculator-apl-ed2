package com.github.imgabreuw.processor;

import com.github.imgabreuw.Input;
import com.github.imgabreuw.token.NullToken;
import com.github.imgabreuw.token.Token;

public class WhitespaceProcessor implements TokenProcessor {

    @Override
    public Token process(Input input) {
        int numberOfWhitespaces = 0;

        while (input.hasNext() && Character.isWhitespace(input.getCurrentCharacter())) {
            input.next();
            numberOfWhitespaces++;
        }

        if (numberOfWhitespaces == 0) {
            return null;
        }

        return new NullToken();
    }

}
