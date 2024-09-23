package com.github.imgabreuw.helpers;

import lombok.Data;

@Data
public class Input {

    private String expression;

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

    public void setExpression(String expression) {
        if (expression == null || expression.isEmpty()) {
            this.expression = "";
            return;
        }

        this.expression = expression;
    }

}
