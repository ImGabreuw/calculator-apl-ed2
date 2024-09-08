package com.github.imgabreuw.tree;

import lombok.*;

import java.util.LinkedList;
import java.util.Queue;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BinaryExpressionTree {

    private Node root;

    public boolean isEmpty() {
        return root == null;
    }

    public int getDegree() {
        if (root == null) {
            return 0;
        }

        return root.getDegree();
    }

    public int getHeight() {
        if (root == null) {
            return -1;
        }

        return root.getHeight();
    }

    public void traversePreOrder() {
        traversePreOrder(root);
    }

    public void traversePreOrder(Node node) {
        if (node == null) return;

        System.out.print(node + " ");

        if (node instanceof OperatorNode operatorNode) {
            traversePreOrder(operatorNode.getLeft());
            traversePreOrder(operatorNode.getRight());
        }
    }

    public void traverseInOrder() {
        traverseInOrder(root);
    }

    public void traverseInOrder(Node node) {
        if (node == null) return;

        if (node instanceof OperatorNode operatorNode) {
            traverseInOrder(operatorNode.getLeft());
        }

        System.out.print(node + " ");

        if (node instanceof OperatorNode operatorNode) {
            traverseInOrder(operatorNode.getRight());
        }
    }

    public void traversePostOrder() {
        traversePostOrder(root);
    }

    public void traversePostOrder(Node node) {
        if (node == null) return;

        if (node instanceof OperatorNode operatorNode) {
            traversePostOrder(operatorNode.getLeft());
            traversePostOrder(operatorNode.getRight());
        }

        System.out.print(node + " ");
    }

    public void levelOrderTraversal() {
        if (root == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            System.out.println(currentNode);

            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }
    }

    public double calculate() {
        return root.visit().getValue();
    }

}
