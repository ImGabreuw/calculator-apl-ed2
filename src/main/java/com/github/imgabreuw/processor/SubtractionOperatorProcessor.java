package com.github.imgabreuw.processor;

import com.github.imgabreuw.Input;
import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.operator.binary.SubtractionBinaryOperatorToken;

public class SubtractionOperatorProcessor implements TokenProcessor {

    @Override
    public Token process(Input input) {
        char currentChar = input.getCurrentCharacter();

        if (currentChar != '-') {
            return null;
        }

        if (!isBinaryContext(input)) {
            return null;
        }

        input.next();
        return new SubtractionBinaryOperatorToken();
    }

    private boolean isBinaryContext(Input input) {
        if (input.getPosition() == 0) {
            return false;
        }

        int position = input.getPosition() - 1;

        while (position >= 0 && Character.isWhitespace(input.getExpression().charAt(position))) {
            position--;
        }

        if (position >= 0) {
            char previousChar = input.getExpression().charAt(position);
            return Character.isDigit(previousChar) || previousChar == ')';
        }

        return false;
    }

}
