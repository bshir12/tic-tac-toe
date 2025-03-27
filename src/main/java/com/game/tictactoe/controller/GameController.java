package com.game.tictactoe.controller;

import com.game.tictactoe.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping("/reset")
    public ResponseEntity<Map<String, Object>> resetGame(
            @RequestParam(defaultValue = "3") int boardSize,
            @RequestParam(required = false) String firstPlayer) {

        if (firstPlayer == null) {
            firstPlayer = "X";
        }

        gameService.initializeGame(boardSize, firstPlayer);

        Map<String, Object> response = new HashMap<>();
        response.put("boardSize", gameService.getBoard().getSize());
        response.put("currentPlayer", gameService.getCurrentPlayer());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/move")
    public ResponseEntity<Map<String, Object>> makeMove(
            @RequestParam int row,
            @RequestParam int col) {

        boolean success = gameService.makeMove(row, col);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);

        if (success) {
            response.put("currentPlayer", gameService.getCurrentPlayer());
            response.put("gameOver", gameService.isGameOver());

            if (gameService.isGameOver()) {
                response.put("winner", gameService.getWinner());
            }
        }

        return ResponseEntity.ok(response);
    }
}
