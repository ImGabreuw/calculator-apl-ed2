package com.github.imgabreuw.tree;

import com.github.imgabreuw.token.number.NumberToken;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinaryNode {

    private NumberToken value;

    private BinaryNode left;

    private BinaryNode right;

    private BinaryNode parent;

    public boolean isRoot(){
        return parent == null;
    }

    public boolean isLeaf(){
        return left == null && right == null;
    }

    public int getDegree() {
        int degree = 0;
        if (left != null){
            ++degree;
        }
        if (right != null){
            ++degree;
        }
        return degree;
    }

    public int getLevel() {
        if (parent != null){
            return parent.getLevel() + 1;
        }
        return 0;
    }

    public int getHeight() {
        if (isLeaf()) {
            return 0;
        }

        int leftHeight = left != null ? left.getHeight() : -1;
        int rightHeight = right != null ? right.getHeight() : -1;

        return Math.max(leftHeight, rightHeight) + 1;
    }

}
