package com.github.imgabreuw.processor;

import com.github.imgabreuw.helpers.Input;
import com.github.imgabreuw.token.Token;

/**
 * References: <br>
 * <ul>
 *     <li><a href="https://refactoring.guru/design-patterns/chain-of-responsibility">Chain of Responsibility</a></li>
 * </ul>
 */
public interface TokenProcessor {

    Token process(Input input);

}
