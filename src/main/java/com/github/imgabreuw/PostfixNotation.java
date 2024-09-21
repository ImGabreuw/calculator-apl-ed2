package com.github.imgabreuw;

import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.operator.binary.BinaryOperatorToken;
import com.github.imgabreuw.token.operator.binary.ParenthesisBinaryOperatorToken;
import com.github.imgabreuw.token.operator.unary.UnaryOperatorToken;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PostfixNotation {

    public Deque<Token> convert(List<Token> infix) {
        Deque<Token> outputQueue = new LinkedList<>();
        Deque<Token> operators = new LinkedList<>();

        for (Token token : infix) {
            if (token instanceof NumberToken) {
                outputQueue.add(token);
                continue;
            }

            if (token instanceof ParenthesisBinaryOperatorToken pToken) {
                if (pToken.isOpening()) {
                    operators.push(null);  // Indica parêntese aberto
                    continue;
                }

                // Parêntese fechado -> desempilha até encontrar parêntese aberto
                while (!operators.isEmpty() && operators.peek() != null) {
                    outputQueue.add(operators.pop());
                }

                if (operators.isEmpty()) {
                    throw new IllegalArgumentException("Parênteses desbalanceados");
                }

                operators.pop();  // Remove o parêntese aberto
                continue;
            }

            if (token instanceof UnaryOperatorToken uToken) {
                operators.push(uToken);
                continue;
            }

            if (token instanceof BinaryOperatorToken bToken) {
                // Desempilha operadores com precedência maior ou igual
                while (!operators.isEmpty()) {
                    Token topOperator = operators.peek();
                    if (topOperator instanceof BinaryOperatorToken op) {
                        boolean isLeftAssociative = bToken.getAssociative().isLeft() && bToken.getPrecedence() <= op.getPrecedence();
                        boolean isRightAssociative = bToken.getAssociative().isRight() && bToken.getPrecedence() < op.getPrecedence();

                        if (!isLeftAssociative && !isRightAssociative) {
                            break;
                        }

                        outputQueue.add(operators.pop());
                    } else if (topOperator instanceof UnaryOperatorToken) {
                        outputQueue.add(operators.pop());
                    } else {
                        break;
                    }
                }

                operators.push(bToken);
            }
        }

        while (!operators.isEmpty()) {
            outputQueue.add(operators.pop());
        }

        return outputQueue;
    }

}
