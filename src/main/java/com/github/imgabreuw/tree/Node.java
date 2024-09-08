package com.github.imgabreuw.tree;

import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.number.NumberToken;
import lombok.Data;

@Data
public abstract class Node {

    protected Node parent;

    public abstract Token getValue();

    public abstract Node getLeft();

    public abstract Node getRight();

    public abstract int getHeight();

    public abstract int getDegree();

    public abstract NumberToken visit();

    public boolean isRoot() {
        return parent == null;
    }

    public int getLevel() {
        if (parent != null) {
            return parent.getLevel() + 1;
        }

        return 0;
    }

}
