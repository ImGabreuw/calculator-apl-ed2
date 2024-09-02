package com.github.imgabreuw;

import com.github.imgabreuw.processor.TokenProcessor;
import com.github.imgabreuw.token.NullToken;
import com.github.imgabreuw.token.Token;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Lexer {

    private final List<TokenProcessor> pipeline;

    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        int[] index = {0};

        while (index[0] < input.length()) {
            Token token = null;

            for (TokenProcessor processor : pipeline) {
                token = processor.process(input, index);

                // try to tokenize using the next processor
                if (token == null) continue;

                if (!(token instanceof NullToken)) tokens.add(token);

                break;
            }

            if (token == null) {
                throw new IllegalArgumentException("Unexpected character: " + input.charAt(index[0]));
            }
        }

        return tokens;
    }

}
