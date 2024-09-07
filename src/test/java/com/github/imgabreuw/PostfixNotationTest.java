package com.github.imgabreuw;

import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.operator.*;
import com.github.imgabreuw.token.operator.ParenthesisOperatorToken;
import com.github.imgabreuw.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostfixNotationTest {

    private PostfixNotation underTest;

    @BeforeEach
    void setUp() {
        underTest = new PostfixNotation();
    }

    @Test
    void testSimpleExpression() {
        List<Token> tokens = Arrays.asList(
                new NumberToken("1"),
                new SumOperatorToken(),
                new NumberToken("2")
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("1"),
                        new NumberToken("2"),
                        new SumOperatorToken()
                );
    }

    @Test
    void testExpressionWithMultipleOperators() {
        List<Token> tokens = Arrays.asList(
                new NumberToken("1"),
                new SumOperatorToken(),
                new NumberToken("2"),
                new SubtractionOperatorToken(),
                new NumberToken("3"),
                new MultiplicationOperatorToken(),
                new NumberToken("4"),
                new DivisionOperatorToken(),
                new NumberToken("5")
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("1"),
                        new NumberToken("2"),
                        new SumOperatorToken(),
                        new NumberToken("3"),
                        new NumberToken("4"),
                        new MultiplicationOperatorToken(),
                        new NumberToken("5"),
                        new DivisionOperatorToken(),
                        new SubtractionOperatorToken()
                );
    }

    @Test
    void testExpressionWithParentheses() {
        List<Token> tokens = Arrays.asList(
                new ParenthesisOperatorToken('('),
                new NumberToken("3"),
                new SumOperatorToken(),
                new NumberToken("4"),
                new ParenthesisOperatorToken(')'),
                new MultiplicationOperatorToken(),
                new NumberToken("5")
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("3"),
                        new NumberToken("4"),
                        new SumOperatorToken(),
                        new NumberToken("5"),
                        new MultiplicationOperatorToken()
                );
    }

    @Test
    void testComplexExpression() {
        List<Token> tokens = Arrays.asList(
                new NumberToken("10"),
                new SumOperatorToken(),
                new ParenthesisOperatorToken('('),
                new NumberToken("2"),
                new MultiplicationOperatorToken(),
                new NumberToken("3"),
                new ParenthesisOperatorToken(')'),
                new SubtractionOperatorToken(),
                new NumberToken("7"),
                new DivisionOperatorToken(),
                new NumberToken("1.5")
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("10"),
                        new NumberToken("2"),
                        new NumberToken("3"),
                        new MultiplicationOperatorToken(),
                        new SumOperatorToken(),
                        new NumberToken("7"),
                        new NumberToken("1.5"),
                        new DivisionOperatorToken(),
                        new SubtractionOperatorToken()
                );
    }

    @Test
    void testExpressionWithDecimalNumbers() {
        List<Token> tokens = Arrays.asList(
                new NumberToken("3.14"),
                new SumOperatorToken(),
                new NumberToken("2.71")
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("3.14"),
                        new NumberToken("2.71"),
                        new SumOperatorToken()
                );
    }

    @Test
    void testExpressionWithSpaces() {
        List<Token> tokens = Arrays.asList(
                new NumberToken("12"),
                new SumOperatorToken(),
                new NumberToken("4"),
                new MultiplicationOperatorToken(),
                new ParenthesisOperatorToken('('),
                new NumberToken("3"),
                new SubtractionOperatorToken(),
                new NumberToken("1"),
                new ParenthesisOperatorToken(')')
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("12"),
                        new NumberToken("4"),
                        new NumberToken("3"),
                        new NumberToken("1"),
                        new SubtractionOperatorToken(),
                        new MultiplicationOperatorToken(),
                        new SumOperatorToken()
                );
    }

    @Test
    void testNestedParentheses() {
        List<Token> tokens = Arrays.asList(
                new ParenthesisOperatorToken('('),
                new ParenthesisOperatorToken('('),
                new NumberToken("1"),
                new SumOperatorToken(),
                new NumberToken("2"),
                new ParenthesisOperatorToken(')'),
                new MultiplicationOperatorToken(),
                new NumberToken("3"),
                new ParenthesisOperatorToken(')'),
                new DivisionOperatorToken(),
                new ParenthesisOperatorToken('('),
                new NumberToken("4"),
                new SubtractionOperatorToken(),
                new NumberToken("5"),
                new ParenthesisOperatorToken(')')
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("1"),
                        new NumberToken("2"),
                        new SumOperatorToken(),
                        new NumberToken("3"),
                        new MultiplicationOperatorToken(),
                        new NumberToken("4"),
                        new NumberToken("5"),
                        new SubtractionOperatorToken(),
                        new DivisionOperatorToken()
                );
    }

    @Test
    void testExpressionWithMultipleSpaces() {
        List<Token> tokens = Arrays.asList(
                new NumberToken("1"),
                new SumOperatorToken(),
                new NumberToken("2"),
                new MultiplicationOperatorToken(),
                new ParenthesisOperatorToken('('),
                new NumberToken("3"),
                new SubtractionOperatorToken(),
                new NumberToken("4"),
                new ParenthesisOperatorToken(')')
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("1"),
                        new NumberToken("2"),
                        new NumberToken("3"),
                        new NumberToken("4"),
                        new SubtractionOperatorToken(),
                        new MultiplicationOperatorToken(),
                        new SumOperatorToken()
                );
    }

}