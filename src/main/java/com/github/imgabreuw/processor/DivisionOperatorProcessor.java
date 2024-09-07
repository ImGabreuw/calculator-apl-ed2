package com.github.imgabreuw.processor;

import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.operator.DivisionOperatorToken;
import com.github.imgabreuw.token.operator.SumOperatorToken;

public class DivisionOperatorProcessor implements TokenProcessor {

    @Override
    public Token process(String input, int[] index) {
        char currentChar = input.charAt(index[0]);

        if (currentChar != '/') {
            return null;
        }

        index[0]++;
        return new DivisionOperatorToken();
    }

}
