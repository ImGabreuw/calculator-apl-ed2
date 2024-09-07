package com.github.imgabreuw;

import com.github.imgabreuw.processor.*;
import com.github.imgabreuw.token.Token;

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

        List<Token> tokens;

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
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        System.out.print("Expressão: ");
                        scanner.nextLine();
                        String expression = scanner.nextLine();

                        try {
                            tokens = lexer.tokenize(expression);
                            System.out.println(tokens.size());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Expressão inválida.");
                        }
                        break;
                }

            } while (option != 5);
        }
    }

}