package com.github.imgabreuw.processor;

import com.github.imgabreuw.Input;
import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.Token;

public class NumberProcessor implements TokenProcessor {

    @Override
    public Token process(Input input) {
        StringBuilder sb = new StringBuilder();
        boolean hasDecimalSeparator = false;

        while (input.hasNext()) {
            char currentCharacter = input.getCurrentCharacter();

            // Process digits and decimal separators
            if (Character.isDigit(currentCharacter)) {
                sb.append(currentCharacter);
                input.next();
                continue;
            }

            if (isDecimalSeparator(currentCharacter)) {
                if (hasDecimalSeparator) {
                    // More than one decimal separator is invalid
                    return null;
                }

                hasDecimalSeparator = true;
                sb.append('.');
                input.next();
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
