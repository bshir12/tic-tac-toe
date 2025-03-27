package com.game.tictactoe.service;

import com.game.tictactoe.constant.Player;
import com.game.tictactoe.model.Board;

public interface WinCheck {
    boolean checkWin(Board board, Player player, int row, int col);
}
