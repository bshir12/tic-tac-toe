package com.game.tictactoe.service.impl;

import com.game.tictactoe.config.GameConfiguration;
import com.game.tictactoe.constant.Player;
import com.game.tictactoe.handler.GameHandler;
import com.game.tictactoe.model.Board;
import com.game.tictactoe.service.GameService;
import com.game.tictactoe.service.WinCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameConfiguration gameConfig;
    private final WinCheck winCheck;

    private Board board;
    private Player currentPlayer;
    private boolean gameOver;
    private String winner;

    @Override
    public Map<String, Object> startingGame(int boardSize, String firstPlayer){
        if (firstPlayer == null) {
            firstPlayer = "X";
        }

        Player player = Player.O;
        if(firstPlayer.equals("X")){
            player = Player.X;
        }
        initializeGame(boardSize, player);

        Map<String, Object> response = new HashMap<>();
        response.put("boardSize", board.getSize());
        response.put("currentPlayer", currentPlayer);

        return response;
    }

    @Override
    public Map<String, Object> move(int row, int col){
        boolean success = makeMove(row, col);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);

        if (success) {
            response.put("currentPlayer", currentPlayer);
            response.put("gameOver", gameOver);

            if (gameOver) {
                response.put("winner", winner);
            }
        }
        return response;
    }

    private void initializeGame(int boardSize, Player firstPlayer) {

        log.info("Game Start With Size: {}, First Player: {}", boardSize, firstPlayer);

        int validatedBoardSize = gameConfig.validateBoardSize(boardSize);

        board = new Board(validatedBoardSize);
        currentPlayer = firstPlayer != null ? firstPlayer : Player.X;
        gameOver = false;
        winner = null;
    }

    public boolean makeMove(int row, int col) {
        validateMove(row, col);

        if (!board.makeMove(row, col, currentPlayer.getSymbol())) {
            log.warn("Invalid move attempted at row: {}, col: {}", row, col);
            return false;
        }

        if (winCheck.checkWin(board, currentPlayer, row, col)) {
            gameOver = true;
            winner = currentPlayer.getSymbol();
            log.info("Game won by player: {}", winner);
        } else if (isBoardFull()) {
            gameOver = true;
            log.info("Game ended in a draw");
        } else {
            currentPlayer = currentPlayer.switchPlayer();
        }

        return true;
    }

    private void validateMove(int row, int col){
        log.info("Check Mock");
        if(gameOver){
            throw new GameHandler("Game is Over");
        }

        if(row < 0 || col < 0 ||
                row >= board.getSize() || col >= board.getSize()){
            throw new GameHandler("Move is outside Box");
        }
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
