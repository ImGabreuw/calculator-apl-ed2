package com.github.imgabreuw;

import com.github.imgabreuw.processor.*;
import com.github.imgabreuw.token.NumberToken;
import com.github.imgabreuw.token.OperatorToken;
import com.github.imgabreuw.token.ParenthesisToken;
import com.github.imgabreuw.token.Token;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LexerTest {

    private Lexer underTest;

    @BeforeEach
    void setUp() {
        List<TokenProcessor> pipeline = List.of(
                new NumberProcessor(),
                new OperatorProcessor(),
                new ParenthesisProcessor(),
                new WhitespaceProcessor()
        );

        underTest = new Lexer(pipeline);
    }


    @Test
    void testSimpleExpression() {
        List<Token> tokens = underTest.tokenize("1+2");
        assertThat(tokens)
                .containsExactly(new NumberToken("1"), new OperatorToken('+'), new NumberToken("2"));
    }

    @Test
    void testExpressionWithParentheses() {
        List<Token> tokens = underTest.tokenize("(3+4)*5");
        assertThat(tokens)
                .containsExactly(
                        new ParenthesisToken('('),
                        new NumberToken("3"),
                        new OperatorToken('+'),
                        new NumberToken("4"),
                        new ParenthesisToken(')'),
                        new OperatorToken('*'),
                        new NumberToken("5")
                );
    }

    @Test
    void testComptokenizeExpression() {
        List<Token> tokens = underTest.tokenize("10+(2*3)-7/1.5");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("10"),
                        new OperatorToken('+'),
                        new ParenthesisToken('('),
                        new NumberToken("2"),
                        new OperatorToken('*'),
                        new NumberToken("3"),
                        new ParenthesisToken(')'),
                        new OperatorToken('-'),
                        new NumberToken("7"),
                        new OperatorToken('/'),
                        new NumberToken("1.5")
                );
    }

    @Test
    void testExpressionWithSpaces() {
        List<Token> tokens = underTest.tokenize(" 12   +  4  * ( 3 - 1 ) ");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("12"),
                        new OperatorToken('+'),
                        new NumberToken("4"),
                        new OperatorToken('*'),
                        new ParenthesisToken('('),
                        new NumberToken("3"),
                        new OperatorToken('-'),
                        new NumberToken("1"),
                        new ParenthesisToken(')')
                );
    }

    @Test
    void testExpressionWithDecimalNumbers() {
        List<Token> tokens = underTest.tokenize("3.14 + 2.71");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("3.14"),
                        new OperatorToken('+'),
                        new NumberToken("2.71")
                );
    }

    @Test
    void testInvalidCharacter() {
        Assertions.assertThatThrownBy(() -> underTest.tokenize("2 + x"))
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
                        new OperatorToken('+'),
                        new NumberToken("2"),
                        new OperatorToken('-'),
                        new NumberToken("3"),
                        new OperatorToken('*'),
                        new NumberToken("4"),
                        new OperatorToken('/'),
                        new NumberToken("5")
                );
    }

    @Test
    void testNestedParentheses() {
        List<Token> tokens = underTest.tokenize("((1+2)*3)/(4-5)");
        assertThat(tokens)
                .containsExactly(
                        new ParenthesisToken('('),
                        new ParenthesisToken('('),
                        new NumberToken("1"),
                        new OperatorToken('+'),
                        new NumberToken("2"),
                        new ParenthesisToken(')'),
                        new OperatorToken('*'),
                        new NumberToken("3"),
                        new ParenthesisToken(')'),
                        new OperatorToken('/'),
                        new ParenthesisToken('('),
                        new NumberToken("4"),
                        new OperatorToken('-'),
                        new NumberToken("5"),
                        new ParenthesisToken(')')
                );
    }

    @Test
    void testConsecutiveSpaces() {
        List<Token> tokens = underTest.tokenize("1   +  2   *    (3   - 4)");
        assertThat(tokens)
                .containsExactly(
                        new NumberToken("1"),
                        new OperatorToken('+'),
                        new NumberToken("2"),
                        new OperatorToken('*'),
                        new ParenthesisToken('('),
                        new NumberToken("3"),
                        new OperatorToken('-'),
                        new NumberToken("4"),
                        new ParenthesisToken(')')
                );
    }

}