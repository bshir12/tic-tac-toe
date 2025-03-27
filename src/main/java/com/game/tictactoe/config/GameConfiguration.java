package com.game.tictactoe.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class GameConfiguration {
    @Value("${game.win-condition}")
    private int winCondition;

    @Value("${game.min-board-size}")
    private int MIN_BOARD_SIZE;

    @Value("${game.max-board-size}")
    private int MAX_BOARD_SIZE;

    public int validateBoardSize(int boardSize) {
        return Math.min(MAX_BOARD_SIZE, Math.max(MIN_BOARD_SIZE, boardSize));
    }

}
