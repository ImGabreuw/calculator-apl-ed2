package com.github.imgabreuw.processor;

import com.github.imgabreuw.token.NumberToken;
import com.github.imgabreuw.token.Token;

public class NumberProcessor implements TokenProcessor {

    @Override
    public Token process(String input, int[] index) {
        StringBuilder sb = new StringBuilder();

        while (index[0] < input.length() && (Character.isDigit(input.charAt(index[0])) || isDecimalSeparator(input, index))) {
            char c = input.charAt(index[0]++);
            sb.append(String.valueOf(c).replace(",", "."));
        }

        if (sb.isEmpty()) {
            return null;
        }

        return new NumberToken(sb.toString());

    }

    private boolean isDecimalSeparator(String input, int[] index) {
        return input.charAt(index[0]) == '.' || input.charAt(index[0]) == ',';
    }

}
