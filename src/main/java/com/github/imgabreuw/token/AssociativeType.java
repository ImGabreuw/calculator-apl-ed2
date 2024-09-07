package com.github.imgabreuw.token;

public enum AssociativeType {

    LEFT, // Associatividade à esquerda (left-associative): Se um operador é associativo à esquerda, em expressões que têm múltiplos operadores de mesma precedência, o agrupamento dos operandos é feito da esquerda para a direita. Operadores como +, -, *, e / geralmente são associativos à esquerda.
    RIGHT; //Associatividade à direita (right-associative): Em operadores associativos à direita, o agrupamento dos operandos é feito da direita para a esquerda. Um exemplo comum é o operador de exponenciação ^ em algumas linguagens de programação.

    public boolean isLeft() {
        return this == LEFT;
    }
    public boolean isRight() {
        return this == RIGHT;
    }
}
