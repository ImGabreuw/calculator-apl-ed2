package com.github.imgabreuw.processor;

import com.github.imgabreuw.token.Token;

public interface TokenProcessor {

    Token process(String input, int[] index);

}
