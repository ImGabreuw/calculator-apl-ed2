package com.github.imgabreuw.token;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public final class NumberToken extends Token {

    private final String value;

}
