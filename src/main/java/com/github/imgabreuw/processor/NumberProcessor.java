package com.github.imgabreuw.processor;

import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.Token;

public class NumberProcessor implements TokenProcessor {

    @Override
    public Token process(String input, int[] index) {
        StringBuilder sb = new StringBuilder();
        boolean hasDecimalSeparator = false;

        while (index[0] < input.length()) {
            char c = input.charAt(index[0]);

            // Process digits and decimal separators
            if (Character.isDigit(c)) {
                sb.append(c);
                index[0]++;
                continue;
            }

            if (isDecimalSeparator(c)) {
                if (hasDecimalSeparator) {
                    // More than one decimal separator is invalid
                    return null;
                }

                hasDecimalSeparator = true;
                sb.append('.');
                index[0]++;
                continue;
            }

            // Stop processing if encountering an invalid character
            break;
        }

        // No valid number found
        if (sb.isEmpty()) {
            return null;
        }

        return new NumberToken(sb.toString());

    }

    private boolean isDecimalSeparator(Character c) {
        return c == '.' || c == ',';
    }

}
