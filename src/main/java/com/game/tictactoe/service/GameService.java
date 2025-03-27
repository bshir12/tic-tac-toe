package com.game.tictactoe.service;

import com.game.tictactoe.config.GameConfiguration;
import com.game.tictactoe.model.Board;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class GameService {
    private final GameConfiguration gameConfig;
    private Board board;
    private String currentPlayer;
    private boolean gameOver;
    private String winner;

    @Autowired
    public GameService(GameConfiguration gameConfig) {
        this.gameConfig = gameConfig;
    }

    public void initializeGame(int boardSize, String firstPlayer) {

        log.info("Game Start With Size: {}, First Player: {}", boardSize, firstPlayer);

        int validatedBoardSize = gameConfig.validateBoardSize(boardSize);

        board = new Board(validatedBoardSize);
        currentPlayer = validateFirstPlayer(firstPlayer);
        gameOver = false;
        winner = null;
    }

    public boolean makeMove(int row, int col) {
        if (gameOver || !board.makeMove(row, col, currentPlayer)) {
            log.warn("Invalid mov at row: {}, col: {}", row, col);
            return false;
        }

        if (checkWinner(row, col)) {
            gameOver = true;
            winner = currentPlayer;
        } else if (isBoardFull()) {
            gameOver = true;
        } else {
            switchPlayer();
        }

        return true;
    }

    private String validateFirstPlayer(String firstPlayer){
        return firstPlayer != null && (firstPlayer.equals("X") || firstPlayer.equals("O"))
                ? firstPlayer
                : "X";
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    private boolean checkWinner(int row, int col) {
        return checkHorizontal(row) ||
                checkVertical(col) ||
                checkDiagonals();
    }

    private boolean checkHorizontal(int row) {
        log.info("Horizontal check at row: {}", row);
        String[][] grid = board.getGrid();
        for (int c = 0; c <= grid[row].length - gameConfig.getWinCondition(); c++) {
            boolean win = true;
            for (int k = 0; k < gameConfig.getWinCondition(); k++) {
                if (!grid[row][c + k].equals(currentPlayer)) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }
        return false;
    }

    private boolean checkVertical(int col) {
        log.info("Vertical check at col: {}", col);
        String[][] grid = board.getGrid();
        for (int r = 0; r <= grid.length - gameConfig.getWinCondition(); r++) {
            boolean win = true;
            for (int k = 0; k < gameConfig.getWinCondition(); k++) {
                if (!grid[r + k][col].equals(currentPlayer)) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }
        return false;
    }

    private boolean checkDiagonals() {
        return checkMainDiagonal() || checkAntiDiagonal();
    }

    private boolean checkMainDiagonal() {
        log.info("=====checkMainDiagonal=====");
        String[][] grid = board.getGrid();
        for (int r = 0; r <= grid.length - gameConfig.getWinCondition(); r++) {
            for (int c = 0; c <= grid[r].length - gameConfig.getWinCondition(); c++) {
                boolean win = true;
                for (int k = 0; k < gameConfig.getWinCondition(); k++) {
                    if (!grid[r + k][c + k].equals(currentPlayer)) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        return false;
    }

    private boolean checkAntiDiagonal() {
        log.info("=====checkAntiDiagonal=====");
        String[][] grid = board.getGrid();
        for (int r = 0; r <= grid.length - gameConfig.getWinCondition(); r++) {
            for (int c = gameConfig.getWinCondition() - 1; c < grid[r].length; c++) {
                boolean win = true;
                for (int k = 0; k < gameConfig.getWinCondition(); k++) {
                    if (!grid[r + k][c - k].equals(currentPlayer)) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        String[][] grid = board.getGrid();
        for (String[] row : grid) {
            for (String cell : row) {
                if (cell.equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }
}
