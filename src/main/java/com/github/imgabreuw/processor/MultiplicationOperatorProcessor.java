package com.github.imgabreuw.processor;

import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.operator.MultiplicationOperatorToken;
import com.github.imgabreuw.token.operator.SumOperatorToken;

public class MultiplicationOperatorProcessor implements TokenProcessor {

    @Override
    public Token process(String input, int[] index) {
        char currentChar = input.charAt(index[0]);

        if (currentChar != '*') {
            return null;
        }

        index[0]++;
        return new MultiplicationOperatorToken();
    }

}
