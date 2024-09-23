package com.github.imgabreuw;

import com.github.imgabreuw.helpers.Input;
import com.github.imgabreuw.helpers.StringHelper;
import com.github.imgabreuw.processor.TokenProcessor;
import com.github.imgabreuw.token.NullToken;
import com.github.imgabreuw.token.Token;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Lexer {

    private final List<TokenProcessor> pipeline;

    public List<Token> tokenize(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("expression cannot be null");
        }

        if (!StringHelper.isParenthesisBalanced(expression)) {
            throw new IllegalArgumentException("expression must be balanced");
        }

        List<Token> tokens = new ArrayList<>();
        Input input = new Input(expression);

        while (input.getPosition() < expression.length()) {
            Token token = null;

            for (TokenProcessor processor : pipeline) {
                token = processor.process(input);

                // try to tokenize using the next processor
                if (token == null) continue;

                if (!(token instanceof NullToken)) tokens.add(token);

                break;
            }

            if (token == null) {
                throw new IllegalArgumentException("Unexpected character: " + expression.charAt(input.getPosition()));
            }
        }

        return tokens;
    }

}
