package com.github.imgabreuw.ui.options;

import com.github.imgabreuw.tree.BinaryExpressionTree;
import lombok.Data;

import java.util.Scanner;

@Data
public class DisplayExpressionTreeOption implements MenuOption {

    private final CreateExpressionTreeOption previous;

    private BinaryExpressionTree tree;

    @Override
    public void execute(Scanner scanner) {
        tree = previous != null ? previous.getTree() : null;

        if (tree == null) {
            System.out.println("Não há nenhuma expressão para ser avaliada.");
            return;
        }

        System.out.print("Expressão: ");
        tree.traverseInOrder();
        System.out.println();
    }

    @Override
    public String getMessage() {
        return "Exibição da árvore binária de expressão aritmética.";
    }

}