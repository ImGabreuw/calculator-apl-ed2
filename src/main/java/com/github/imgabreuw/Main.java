package com.github.imgabreuw;

import com.github.imgabreuw.processor.*;
import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.tree.BinaryExpressionTree;

import java.util.Deque;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int option = 0;

        List<TokenProcessor> pipeline = List.of(
                new WhitespaceProcessor(),
                new ParenthesisProcessor(),
                new SumOperatorProcessor(),
                new SubtractionOperatorProcessor(),
                new MultiplicationOperatorProcessor(),
                new DivisionOperatorProcessor(),
                new NumberProcessor()
        );
        Lexer lexer = new Lexer(pipeline);
        PostfixNotation postfixNotation = new PostfixNotation();
        Parser parser = new Parser();

        List<Token> tokens = null;
        BinaryExpressionTree tree = null;

        try (Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.println("""
                        -------------------------- MENU --------------------------
                        1. Entrada da expressão aritmética na notação infixa.
                        2. Criação da árvore binária de expressão aritmética.
                        3. Exibição da árvore binária de expressão aritmética.
                        4. Cálculo da expressão (realizando o percurso da árvore).
                        5. Encerramento do programa.
                        ----------------------------------------------------------
                        """);

                System.out.print("Opção: ");
                try {
                    String rawOption = scanner.next();
                    option = Integer.parseInt(rawOption);
                    scanner.nextLine();
                } catch (NumberFormatException e) {
                    System.out.println("Opção inválida.");
                }

                switch (option) {
                    case 1:
                        System.out.print("Expressão: ");
                        String expression = scanner.nextLine();

                        try {
                            tokens = lexer.tokenize(expression);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Expressão inválida.");
                        }
                        break;
                    case 2:
                        if (tokens == null || tokens.isEmpty()) {
                            System.out.println("Não há nenhum expressão para ser avaliada.");
                            break;
                        }

                        try {
                            Deque<Token> postfix = postfixNotation.convert(tokens);

                            if (postfix.isEmpty()) {
                                System.out.println("Expressão inválida.");
                                break;
                            }

                            tree = parser.parse(postfix);
                            System.out.println("Expressão foi avaliada com sucesso.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Expressão inválida.");
                        }
                        break;
                    case 3:
                        if (tree == null) {
                            System.out.println("Não há nenhum expressão para ser avaliada.");
                            break;
                        }

                        System.out.print("Expressão: ");
                        tree.traverseInOrder();
                        System.out.println();
                        break;
                    case 4:
                        if (tree == null) {
                            System.out.println("Não há nenhum expressão para ser avaliada.");
                            break;
                        }

                        double result = tree.calculate();

                        tree.traverseInOrder();

                        if (result % 1 == 0) {
                            System.out.printf("= %d\n", (int) result);
                            break;
                        }

                        System.out.printf(" = %.2f\n", result);
                        break;
                }

            } while (option != 5);
        }
    }

}