package com.github.imgabreuw;

import com.github.imgabreuw.helpers.PostfixNotation;
import com.github.imgabreuw.processor.*;
import com.github.imgabreuw.ui.menu.Menu;
import com.github.imgabreuw.ui.options.*;

import java.util.List;
import java.util.Map;

/**
 * Integrantes:
 * Enzo Ribeiro                 - 10418262
 * Gabriel Ken Kazama Geronazzo - 10418247
 * Lucas Zanini da Silva        - 10417361
 */
public class Main {

    public static void main(String[] args) {
        List<TokenProcessor> pipeline = List.of(
                new WhitespaceProcessor(),
                new ParenthesisProcessor(),
                new SumOperatorProcessor(),
                new SubtractionOperatorProcessor(),
                new MultiplicationOperatorProcessor(),
                new DivisionOperatorProcessor(),
                new NegativeOperatorProcessor(),
                new NumberProcessor()
        );

        Lexer lexer = new Lexer(pipeline);
        PostfixNotation postfixNotation = new PostfixNotation();
        Parser parser = new Parser();

        InputExpressionOption inputOption = new InputExpressionOption(lexer);
        CreateExpressionTreeOption createTreeOption = new CreateExpressionTreeOption(inputOption, postfixNotation, parser);
        DisplayExpressionTreeOption displayTreeOption = new DisplayExpressionTreeOption(createTreeOption);
        CalculateExpressionOption calculateOption = new CalculateExpressionOption(createTreeOption);
        Map<Integer, MenuOption> options = Map.of(
                1, inputOption,
                2, createTreeOption,
                3, displayTreeOption,
                4, calculateOption
        );

        Menu menu = new Menu(options);
        menu.display();
    }

}