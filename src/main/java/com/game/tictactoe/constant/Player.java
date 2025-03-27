package com.game.tictactoe.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Player {
    X("X"),
    O("O");

    private final String symbol;

    public Player switchPlayer(){
        return this == X ? O : X;
    }
}
