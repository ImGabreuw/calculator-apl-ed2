package com.github.imgabreuw;

import com.github.imgabreuw.processor.*;
import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.operator.*;
import com.github.imgabreuw.token.operator.ParenthesisOperatorToken;
import com.github.imgabreuw.token.Token;
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
                new NumberProcessor()
        );

        underTest = new Lexer(pipeline);
    }


    @Test
    void testSimpleExpression() {
        List<Token> tokens = underTest.tokenize("1+2");
        assertThat(tokens)
                .containsExactly(new NumberToken("1"), new SumOperatorToken(), new NumberToken("2"));
    }

    @Test
    void testExpressionWithParentheses() {
        List<Token> tokens = underTest.tokenize("(3+4)*5");
        assertThat(tokens)
                .containsExactly(
                        new ParenthesisOperatorToken('('),
                        new NumberToken("3"),
                        new SumOperatorToken(),
                        new NumberToken("4"),
                        new ParenthesisOperatorToken(')'),
                        new MultiplicationOperatorToken(),
                        new NumberToken("5")
                );
    }

    @Test
    void testComptokenizeExpression() {
        List<Token> tokens = underTest.tokenize("10+(2*3)-7/1.5");
        assertThat(tokens)
                .containsExactly(
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
    }

    @Test
    void testExpressionWithSpaces() {
        List<Token> tokens = underTest.tokenize(" 12   +  4  * ( 3 - 1 ) ");
        assertThat(tokens)
                .containsExactly(
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
    }

    @Test
    void testExpressionWithDecimalNumbers() {
        List<Token> tokens = underTest.tokenize("3.14 + 2.71");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("3.14"),
                        new SumOperatorToken(),
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
                        new SumOperatorToken(),
                        new NumberToken("2"),
                        new SubtractionOperatorToken(),
                        new NumberToken("3"),
                        new MultiplicationOperatorToken(),
                        new NumberToken("4"),
                        new DivisionOperatorToken(),
                        new NumberToken("5")
                );
    }

    @Test
    void testNestedParentheses() {
        List<Token> tokens = underTest.tokenize("((1+2)*3)/(4-5)");
        assertThat(tokens)
                .containsExactly(
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
    }

    @Test
    void testConsecutiveSpaces() {
        List<Token> tokens = underTest.tokenize("1   +  2   *    (3   - 4)");
        assertThat(tokens)
                .containsExactly(
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
    }

    @Test
    void shouldBeValidExpression1() {
        List<Token> tokens = underTest.tokenize("1+2*3");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("1"),
                        new SumOperatorToken(),
                        new NumberToken("2"),
                        new MultiplicationOperatorToken(),
                        new NumberToken("3")
                );
    }

    @Test
    void shouldBeValidExpression2() {
        List<Token> tokens = underTest.tokenize("(45+20) * 2 - 15");
        assertThat(tokens)
                .containsExactly(
                        new ParenthesisOperatorToken('('),
                        new NumberToken("45"),
                        new SumOperatorToken(),
                        new NumberToken("20"),
                        new ParenthesisOperatorToken(')'),
                        new MultiplicationOperatorToken(),
                        new NumberToken("2"),
                        new SubtractionOperatorToken(),
                        new NumberToken("15")
                );
    }

    @Test
    void shouldBeValidExpression3() {
        List<Token> tokens = underTest.tokenize("        0.5*3/0.25");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("0.5"),
                        new MultiplicationOperatorToken(),
                        new NumberToken("3"),
                        new DivisionOperatorToken(),
                        new NumberToken("0.25")
                );
    }

    @Test
    void shouldBeValidExpression4() {
        List<Token> tokens = underTest.tokenize("        0,5*3/0,25");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("0.5"),
                        new MultiplicationOperatorToken(),
                        new NumberToken("3"),
                        new DivisionOperatorToken(),
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
                        new SubtractionOperatorToken(),
                        new NumberToken("9"),
                        new SubtractionOperatorToken(),
                        new ParenthesisOperatorToken('('),
                        new SubtractionOperatorToken(),
                        new NumberToken("8"),
                        new ParenthesisOperatorToken(')')
                );
    }

    @Test
    void shouldConvertExpression2() {
        List<Token> tokens = underTest.tokenize("-4-(-(-3))");
        assertThat(tokens)
                .containsExactly(
                        new SubtractionOperatorToken(),
                        new NumberToken("4"),
                        new SubtractionOperatorToken(),
                        new ParenthesisOperatorToken('('),
                        new SubtractionOperatorToken(),
                        new ParenthesisOperatorToken('('),
                        new SubtractionOperatorToken(),
                        new NumberToken("3"),
                        new ParenthesisOperatorToken(')'),
                        new ParenthesisOperatorToken(')')
                );
    }
}