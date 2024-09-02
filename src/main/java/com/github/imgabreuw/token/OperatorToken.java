package com.github.imgabreuw.token;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public final class OperatorToken extends Token {

    private final char value;

}
