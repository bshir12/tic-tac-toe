package com.game.tictactoe.service;

import java.util.Map;

public interface GameService {
    Map<String, Object> startingGame(int boardSize, String firstPlayer);

    Map<String, Object> move(int row, int col);
}
