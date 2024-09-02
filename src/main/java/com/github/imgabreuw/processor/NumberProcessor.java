package com.github.imgabreuw.processor;

import com.github.imgabreuw.token.NumberToken;
import com.github.imgabreuw.token.Token;

public class NumberProcessor implements TokenProcessor {

    @Override
    public Token process(String input, int[] index) {
        StringBuilder sb = new StringBuilder();

        while (index[0] < input.length() && (Character.isDigit(input.charAt(index[0])) || input.charAt(index[0]) == '.')) {
            sb.append(input.charAt(index[0]++));
        }

        if (sb.isEmpty()) {
            return null;
        }

        return new NumberToken(sb.toString());

    }

}
