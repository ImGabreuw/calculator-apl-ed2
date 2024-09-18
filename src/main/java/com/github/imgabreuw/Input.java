package com.github.imgabreuw;

import lombok.Data;

@Data
public class Input {

    private final String expression;

    private int position = 0;

    public Input(String expression) {
        this.expression = expression == null ? "" : expression;
    }

    public boolean hasNext() {
        return position < expression.length();
    }

    public void next() {
        if (hasNext()) {
            ++position;
        }
    }

    public char getCurrentCharacter() {
        return expression.charAt(position);
    }

}
