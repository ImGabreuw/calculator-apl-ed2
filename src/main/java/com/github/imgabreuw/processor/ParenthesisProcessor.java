package com.github.imgabreuw.processor;

import com.github.imgabreuw.token.ParenthesisToken;
import com.github.imgabreuw.token.Token;

public class ParenthesisProcessor implements TokenProcessor {

    @Override
    public Token process(String input, int[] index) {
        char currentChar = input.charAt(index[0]);

        if ("()".indexOf(currentChar) == -1) {
            return null;
        }

        index[0]++;
        return new ParenthesisToken(currentChar);
    }

}
