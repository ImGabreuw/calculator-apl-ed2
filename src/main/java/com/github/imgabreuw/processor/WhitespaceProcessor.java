package com.github.imgabreuw.processor;

import com.github.imgabreuw.token.NullToken;
import com.github.imgabreuw.token.Token;

public class WhitespaceProcessor implements TokenProcessor {

    @Override
    public Token process(String input, int[] index) {
        int numberOfWhitespaces = 0;

        while (index[0] < input.length() && Character.isWhitespace(input.charAt(index[0]))) {
            index[0]++;
            numberOfWhitespaces++;
        }

        if (numberOfWhitespaces == 0) {
            return null;
        }

        return new NullToken();
    }

}
