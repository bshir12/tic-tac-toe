package com.game.tictactoe.service.impl;

import com.game.tictactoe.config.GameConfiguration;
import com.game.tictactoe.constant.Player;
import com.game.tictactoe.model.Board;
import com.game.tictactoe.service.WinCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WinCheckImpl implements WinCheck {
    private final GameConfiguration gameConfig;

    @Override
    public boolean checkWin(Board board, Player player, int row, int col){
        return checkHorizontalFromMove(board, player, row, col) ||
                checkVerticalFromMove(board, player, row, col) ||
                checkDiagonalsFromMove(board, player, row, col);
    }

    private boolean checkHorizontalFromMove(Board board, Player player, int row, int col) {
        String[][] grid = board.getGrid();
        int count = 0;

        for (int c = col; c >= 0 && grid[row][c].equals(player.getSymbol()); c--) {
            count++;
        }

        for (int c = col + 1; c < grid[row].length && grid[row][c].equals(player.getSymbol()); c++) {
            count++;
        }

        return count >= gameConfig.getWinCondition();
    }

    private boolean checkVerticalFromMove(Board board, Player player, int row, int col) {
        String[][] grid = board.getGrid();
        int count = 0;

        for (int r = row; r >= 0 && grid[r][col].equals(player.getSymbol()); r--) {
            count++;
        }

        for (int r = row + 1; r < grid.length && grid[r][col].equals(player.getSymbol()); r++) {
            count++;
        }

        return count >= gameConfig.getWinCondition();
    }

    private boolean checkDiagonalsFromMove(Board board, Player player, int row, int col) {
        return checkMainDiagonal(board, player, row, col) ||
                checkAntiDiagonal(board, player, row, col);
    }

    private boolean checkMainDiagonal(Board board, Player player, int row, int col) {
        String[][] grid = board.getGrid();
        int count = 0;

        for (int r = row, c = col;
             r >= 0 && c >= 0 && grid[r][c].equals(player.getSymbol());
             r--, c--) {
            count++;
        }

        for (int r = row + 1, c = col + 1;
             r < grid.length && c < grid[r].length && grid[r][c].equals(player.getSymbol());
             r++, c++) {
            count++;
        }

        return count >= gameConfig.getWinCondition();
    }

    private boolean checkAntiDiagonal(Board board, Player player, int row, int col) {
        String[][] grid = board.getGrid();
        int count = 0;

        for (int r = row, c = col;
             r >= 0 && c < grid[r].length && grid[r][c].equals(player.getSymbol());
             r--, c++) {
            count++;
        }

        for (int r = row + 1, c = col - 1;
             r < grid.length && c >= 0 && grid[r][c].equals(player.getSymbol());
             r++, c--) {
            count++;
        }

        return count >= gameConfig.getWinCondition();
    }

}
