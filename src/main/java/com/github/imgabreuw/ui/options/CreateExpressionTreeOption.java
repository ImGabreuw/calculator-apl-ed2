package com.github.imgabreuw.ui.options;

import com.github.imgabreuw.Parser;
import com.github.imgabreuw.PostfixNotation;
import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.tree.BinaryExpressionTree;
import lombok.Data;

import java.util.Deque;
import java.util.List;
import java.util.Scanner;

@Data
public class CreateExpressionTreeOption implements MenuOption {

    private final InputExpressionOption previous;

    private final PostfixNotation postfixNotation;
    private final Parser parser;

    private List<Token> tokens;
    private BinaryExpressionTree tree;

    @Override
    public void execute(Scanner scanner) {
        tokens = previous != null ? previous.getTokens() : null;

        if (tokens == null || tokens.isEmpty()) {
            System.out.println("Não há nenhuma expressão para ser avaliada.");
            return;
        }

        try {
            Deque<Token> postfix = postfixNotation.convert(tokens);

            if (postfix.isEmpty()) {
                System.out.println("Expressão inválida.");
                return;
            }

            tree = parser.parse(postfix);
            System.out.println("Expressão foi avaliada com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("Expressão inválida.");
        }
    }

    @Override
    public String getMessage() {
        return "Cálculo da expressão (realizando o percurso da árvore).";
    }

}