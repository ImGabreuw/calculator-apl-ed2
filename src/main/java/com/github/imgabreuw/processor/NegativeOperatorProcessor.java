package com.github.imgabreuw.processor;

import com.github.imgabreuw.helpers.Input;
import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.operator.unary.NegativeOperatorToken;

public class NegativeOperatorProcessor implements TokenProcessor {

    @Override
    public Token process(Input input) {
        if (input.getCurrentCharacter() != '-' || (input.getPosition() != 0 && !isUnaryContext(input))) {
            return null;
        }

        NegativeOperatorToken token = new NegativeOperatorToken();

        StringBuilder updatedExpression = new StringBuilder(input.getExpression());
        updatedExpression.setCharAt(input.getPosition(), token.getUniqueSymbol());
        input.setExpression(updatedExpression.toString());

        input.next();
        return token;
    }

    private boolean isUnaryContext(Input input) {
        int position = input.getPosition() - 1;

        while (position >= 0 && Character.isWhitespace(input.getExpression().charAt(position))) {
            position--;
        }

        if (position >= 0) {
            char previousChar = input.getExpression().charAt(position);
            return previousChar == '(' || isOperator(previousChar);
        }

        return true;
    }

    // FIXME refatorar utilizando Reflections
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

}
