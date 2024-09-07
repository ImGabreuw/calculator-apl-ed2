package com.github.imgabreuw.token.number;

import com.github.imgabreuw.token.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public final class NumberToken extends Token {

    private final double value;

    public NumberToken(double value) {
        this.value = value;
    }

    public NumberToken(String value) {
        try {
            this.value = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse number: " + value);
        }
    }

    @Override
    public String getType() {
        return "Number";
    }

}
