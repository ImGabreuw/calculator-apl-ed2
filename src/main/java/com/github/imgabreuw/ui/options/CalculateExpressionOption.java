package com.github.imgabreuw.ui.options;

import com.github.imgabreuw.tree.BinaryExpressionTree;
import lombok.Data;

import java.util.Scanner;

@Data
public class CalculateExpressionOption implements MenuOption {

    private final CreateExpressionTreeOption previous;

    private BinaryExpressionTree tree;

    @Override
    public void execute(Scanner scanner) {
        tree = previous != null ? previous.getTree() : null;

        if (tree == null) {
            System.out.println("Não há nenhuma expressão para ser avaliada.");
            return;
        }

        double result = tree.calculate();
        tree.traverseInOrder();

        if (result % 1 == 0) {
            System.out.printf(" = %d\n", (int) result);
            return;
        }

        System.out.printf(" = %.2f\n", result);
    }

    @Override
    public String getMessage() {
        return "Criação da árvore binária de expressão aritmética.";
    }
}
