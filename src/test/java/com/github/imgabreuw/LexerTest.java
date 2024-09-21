package com.github.imgabreuw;

import com.github.imgabreuw.processor.*;
import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.operator.binary.*;
import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.operator.unary.NegativeOperatorToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LexerTest {

    private Lexer underTest;

    @BeforeEach
    void setUp() {
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

        underTest = new Lexer(pipeline);
    }


    @Test
    void testSimpleExpression() {
        List<Token> tokens = underTest.tokenize("1+2");
        assertThat(tokens)
                .containsExactly(new NumberToken("1"), new SumBinaryOperatorToken(), new NumberToken("2"));
    }

    @Test
    void testExpressionWithParentheses() {
        List<Token> tokens = underTest.tokenize("(3+4)*5");
        assertThat(tokens)
                .containsExactly(
                        new ParenthesisBinaryOperatorToken('('),
                        new NumberToken("3"),
                        new SumBinaryOperatorToken(),
                        new NumberToken("4"),
                        new ParenthesisBinaryOperatorToken(')'),
                        new MultiplicationBinaryOperatorToken(),
                        new NumberToken("5")
                );
    }

    @Test
    void testComptokenizeExpression() {
        List<Token> tokens = underTest.tokenize("10+(2*3)-7/1.5");
        assertThat(tokens)
                .containsExactly(
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
    }

    @Test
    void testExpressionWithSpaces() {
        List<Token> tokens = underTest.tokenize(" 12   +  4  * ( 3 - 1 ) ");
        assertThat(tokens)
                .containsExactly(
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
    }

    @Test
    void testExpressionWithDecimalNumbers() {
        List<Token> tokens = underTest.tokenize("3.14 + 2.71");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("3.14"),
                        new SumBinaryOperatorToken(),
                        new NumberToken("2.71")
                );
    }

    @Test
    void testInvalidCharacter() {
        assertThatThrownBy(() -> underTest.tokenize("2 + x"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unexpected character: x");
    }

    @Test
    void testEmptyExpression() {
        List<Token> tokens = underTest.tokenize("");
        assertThat(tokens).isEmpty();
    }

    @Test
    void testExpressionWithMultipleOperators() {
        List<Token> tokens = underTest.tokenize("1+2-3*4/5");
        assertThat(tokens)
                .containsExactly(
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
    }

    @Test
    void testNestedParentheses() {
        List<Token> tokens = underTest.tokenize("((1+2)*3)/(4-5)");
        assertThat(tokens)
                .containsExactly(
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
    }

    @Test
    void testConsecutiveSpaces() {
        List<Token> tokens = underTest.tokenize("1   +  2   *    (3   - 4)");
        assertThat(tokens)
                .containsExactly(
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
    }

    @Test
    void shouldBeValidExpression1() {
        List<Token> tokens = underTest.tokenize("1+2*3");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("1"),
                        new SumBinaryOperatorToken(),
                        new NumberToken("2"),
                        new MultiplicationBinaryOperatorToken(),
                        new NumberToken("3")
                );
    }

    @Test
    void shouldBeValidExpression2() {
        List<Token> tokens = underTest.tokenize("(45+20) * 2 - 15");
        assertThat(tokens)
                .containsExactly(
                        new ParenthesisBinaryOperatorToken('('),
                        new NumberToken("45"),
                        new SumBinaryOperatorToken(),
                        new NumberToken("20"),
                        new ParenthesisBinaryOperatorToken(')'),
                        new MultiplicationBinaryOperatorToken(),
                        new NumberToken("2"),
                        new SubtractionBinaryOperatorToken(),
                        new NumberToken("15")
                );
    }

    @Test
    void shouldBeValidExpression3() {
        List<Token> tokens = underTest.tokenize("        0.5*3/0.25");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("0.5"),
                        new MultiplicationBinaryOperatorToken(),
                        new NumberToken("3"),
                        new DivisionBinaryOperatorToken(),
                        new NumberToken("0.25")
                );
    }

    @Test
    void shouldBeValidExpression4() {
        List<Token> tokens = underTest.tokenize("        0,5*3/0,25");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("0.5"),
                        new MultiplicationBinaryOperatorToken(),
                        new NumberToken("3"),
                        new DivisionBinaryOperatorToken(),
                        new NumberToken("0.25")
                );
    }

    @Test
    void shouldThrowErrorForInvalidCharactersInMultiplicationExpression() {
        assertThatThrownBy(() -> underTest.tokenize("x * y"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unexpected character: x");
    }

    @Test
    void shouldThrowErrorForInvalidModuloOperator() {
        assertThatThrownBy(() -> underTest.tokenize("5 % 2"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unexpected character: %");
    }

    @Test
    void shouldThrowErrorForInvalidEqualsOperator() {
        assertThatThrownBy(() -> underTest.tokenize("1 + 2 = 3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unexpected character: =");
    }

    @Test
    void shouldConvertExpression1() {
        List<Token> tokens = underTest.tokenize("-9-(-8)");
        assertThat(tokens)
                .containsExactly(
                        new NegativeOperatorToken(),
                        new NumberToken("9"),
                        new SubtractionBinaryOperatorToken(),
                        new ParenthesisBinaryOperatorToken('('),
                        new NegativeOperatorToken(),
                        new NumberToken("8"),
                        new ParenthesisBinaryOperatorToken(')')
                );
    }

    @Test
    void shouldConvertExpression2() {
        List<Token> tokens = underTest.tokenize("-4-(-(-3))");
        assertThat(tokens)
                .containsExactly(
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
    }
}