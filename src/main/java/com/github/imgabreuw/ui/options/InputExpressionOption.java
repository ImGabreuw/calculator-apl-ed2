package com.github.imgabreuw.ui.options;

import com.github.imgabreuw.Lexer;
import com.github.imgabreuw.token.Token;
import lombok.Data;

import java.util.List;
import java.util.Scanner;

@Data
public class InputExpressionOption implements MenuOption {

    private final Lexer lexer;

    private List<Token> tokens;

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Expressão: ");

        String expression = scanner.nextLine();

        try {
            tokens = lexer.tokenize(expression);
        } catch (IllegalArgumentException e) {
            System.out.println("Expressão inválida.");
        }
    }

}
