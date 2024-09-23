package com.github.imgabreuw;

import com.github.imgabreuw.helpers.PostfixNotation;
import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.operator.binary.*;
import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.operator.unary.NegativeOperatorToken;
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
                new SumBinaryOperatorToken(),
                new NumberToken("2")
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("1"),
                        new NumberToken("2"),
                        new SumBinaryOperatorToken()
                );
    }

    @Test
    void testExpressionWithMultipleOperators() {
        List<Token> tokens = Arrays.asList(
                new NumberToken("1"),
                new SumBinaryOperatorToken(),
                new NumberToken("2"),
                new SubtractionBinaryOperatorToken(),
                new NumberToken("3"),
                new MultiplicationBinaryOperatorToken(),
                new NumberToken("4"),
                new DivisionBinaryOperatorToken(),
                new NumberToken("5")
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("1"),
                        new NumberToken("2"),
                        new SumBinaryOperatorToken(),
                        new NumberToken("3"),
                        new NumberToken("4"),
                        new MultiplicationBinaryOperatorToken(),
                        new NumberToken("5"),
                        new DivisionBinaryOperatorToken(),
                        new SubtractionBinaryOperatorToken()
                );
    }

    @Test
    void testExpressionWithParentheses() {
        List<Token> tokens = Arrays.asList(
                new ParenthesisBinaryOperatorToken('('),
                new NumberToken("3"),
                new SumBinaryOperatorToken(),
                new NumberToken("4"),
                new ParenthesisBinaryOperatorToken(')'),
                new MultiplicationBinaryOperatorToken(),
                new NumberToken("5")
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("3"),
                        new NumberToken("4"),
                        new SumBinaryOperatorToken(),
                        new NumberToken("5"),
                        new MultiplicationBinaryOperatorToken()
                );
    }

    @Test
    void testComplexExpression() {
        List<Token> tokens = Arrays.asList(
                new NumberToken("10"),
                new SumBinaryOperatorToken(),
                new ParenthesisBinaryOperatorToken('('),
                new NumberToken("2"),
                new MultiplicationBinaryOperatorToken(),
                new NumberToken("3"),
                new ParenthesisBinaryOperatorToken(')'),
                new SubtractionBinaryOperatorToken(),
                new NumberToken("7"),
                new DivisionBinaryOperatorToken(),
                new NumberToken("1.5")
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("10"),
                        new NumberToken("2"),
                        new NumberToken("3"),
                        new MultiplicationBinaryOperatorToken(),
                        new SumBinaryOperatorToken(),
                        new NumberToken("7"),
                        new NumberToken("1.5"),
                        new DivisionBinaryOperatorToken(),
                        new SubtractionBinaryOperatorToken()
                );
    }

    @Test
    void testExpressionWithDecimalNumbers() {
        List<Token> tokens = Arrays.asList(
                new NumberToken("3.14"),
                new SumBinaryOperatorToken(),
                new NumberToken("2.71")
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("3.14"),
                        new NumberToken("2.71"),
                        new SumBinaryOperatorToken()
                );
    }

    @Test
    void testExpressionWithSpaces() {
        List<Token> tokens = Arrays.asList(
                new NumberToken("12"),
                new SumBinaryOperatorToken(),
                new NumberToken("4"),
                new MultiplicationBinaryOperatorToken(),
                new ParenthesisBinaryOperatorToken('('),
                new NumberToken("3"),
                new SubtractionBinaryOperatorToken(),
                new NumberToken("1"),
                new ParenthesisBinaryOperatorToken(')')
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("12"),
                        new NumberToken("4"),
                        new NumberToken("3"),
                        new NumberToken("1"),
                        new SubtractionBinaryOperatorToken(),
                        new MultiplicationBinaryOperatorToken(),
                        new SumBinaryOperatorToken()
                );
    }

    @Test
    void testNestedParentheses() {
        List<Token> tokens = Arrays.asList(
                new ParenthesisBinaryOperatorToken('('),
                new ParenthesisBinaryOperatorToken('('),
                new NumberToken("1"),
                new SumBinaryOperatorToken(),
                new NumberToken("2"),
                new ParenthesisBinaryOperatorToken(')'),
                new MultiplicationBinaryOperatorToken(),
                new NumberToken("3"),
                new ParenthesisBinaryOperatorToken(')'),
                new DivisionBinaryOperatorToken(),
                new ParenthesisBinaryOperatorToken('('),
                new NumberToken("4"),
                new SubtractionBinaryOperatorToken(),
                new NumberToken("5"),
                new ParenthesisBinaryOperatorToken(')')
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("1"),
                        new NumberToken("2"),
                        new SumBinaryOperatorToken(),
                        new NumberToken("3"),
                        new MultiplicationBinaryOperatorToken(),
                        new NumberToken("4"),
                        new NumberToken("5"),
                        new SubtractionBinaryOperatorToken(),
                        new DivisionBinaryOperatorToken()
                );
    }

    @Test
    void testExpressionWithMultipleSpaces() {
        List<Token> tokens = Arrays.asList(
                new NumberToken("1"),
                new SumBinaryOperatorToken(),
                new NumberToken("2"),
                new MultiplicationBinaryOperatorToken(),
                new ParenthesisBinaryOperatorToken('('),
                new NumberToken("3"),
                new SubtractionBinaryOperatorToken(),
                new NumberToken("4"),
                new ParenthesisBinaryOperatorToken(')')
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("1"),
                        new NumberToken("2"),
                        new NumberToken("3"),
                        new NumberToken("4"),
                        new SubtractionBinaryOperatorToken(),
                        new MultiplicationBinaryOperatorToken(),
                        new SumBinaryOperatorToken()
                );
    }

    @Test
    void shouldConvertWithUnaryOperator1() {
        List<Token> tokens = Arrays.asList(
                new NegativeOperatorToken(),
                new NumberToken("9"),
                new SubtractionBinaryOperatorToken(),
                new ParenthesisBinaryOperatorToken('('),
                new NegativeOperatorToken(),
                new NumberToken("8"),
                new ParenthesisBinaryOperatorToken(')')
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("9"),
                        new NegativeOperatorToken(),
                        new NumberToken("8"),
                        new NegativeOperatorToken(),
                        new SubtractionBinaryOperatorToken()
                );
    }

    @Test
    void shouldConvertWithUnaryOperator2() {
        List<Token> tokens = Arrays.asList(
                new NegativeOperatorToken(),
                new NumberToken("4"),
                new SubtractionBinaryOperatorToken(),
                new ParenthesisBinaryOperatorToken('('),
                new NegativeOperatorToken(),
                new ParenthesisBinaryOperatorToken('('),
                new NegativeOperatorToken(),
                new NumberToken("3"),
                new ParenthesisBinaryOperatorToken(')'),
                new ParenthesisBinaryOperatorToken(')')
        );

        Deque<Token> result = underTest.convert(tokens);

        assertThat(result)
                .containsExactly(
                        new NumberToken("4"),
                        new NegativeOperatorToken(),
                        new NumberToken("3"),
                        new NegativeOperatorToken(),
                        new NegativeOperatorToken(),
                        new SubtractionBinaryOperatorToken()
                );
    }
}