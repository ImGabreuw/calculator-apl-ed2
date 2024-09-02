package com.github.imgabreuw.processor;

import com.github.imgabreuw.token.OperatorToken;
import com.github.imgabreuw.token.Token;

public class OperatorProcessor implements TokenProcessor {

    @Override
    public Token process(String input, int[] index) {
        char currentChar = input.charAt(index[0]);

        if ("+-*/".indexOf(currentChar) == -1) {
            return null;
        }

        index[0]++;
        return new OperatorToken(currentChar);
    }

}
