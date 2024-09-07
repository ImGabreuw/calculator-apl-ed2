package com.github.imgabreuw;

import com.github.imgabreuw.token.number.NumberToken;
import com.github.imgabreuw.token.OperatorToken;
import com.github.imgabreuw.token.Token;
import com.github.imgabreuw.token.operator.ParenthesisOperatorToken;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PostfixNotation {

    public Deque<Token> convert(List<Token> infix) {
        Deque<Token> outputQueue = new LinkedList<>();
        Deque<OperatorToken> operators = new LinkedList<>();

        for (Token token : infix) {
            if (token instanceof NumberToken) {
                // Números vão diretamente para a fila de saída
                outputQueue.add(token);
                continue;
            }

            if (token instanceof ParenthesisOperatorToken pToken) {
                if (pToken.isOpening()) {
                    // Parêntese aberto é empilhado
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

            if (token instanceof OperatorToken o1) {
                // Desempilha operadores de maior ou igual precedência
                while (!operators.isEmpty() && operators.peek() != null) {
                    OperatorToken o2 = operators.peek();
                    boolean isLeftAssociative = o1.getAssociative().isLeft() && o1.getPrecedence() <= o2.getPrecedence();
                    boolean isRightAssociative = o1.getAssociative().isRight() && o1.getPrecedence() < o2.getPrecedence();

                    if (!isLeftAssociative && !isRightAssociative) {
                        break;
                    }

                    outputQueue.add(operators.pop());
                }

                operators.push(o1);  // Empilha o operador atual
            }

        }

        while (!operators.isEmpty()) {
            outputQueue.add(operators.pop());
        }

        return outputQueue;
    }

}
