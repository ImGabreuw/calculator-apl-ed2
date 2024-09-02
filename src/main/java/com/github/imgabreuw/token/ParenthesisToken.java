package com.github.imgabreuw.token;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public final class ParenthesisToken extends Token {

    private final char value;

}
