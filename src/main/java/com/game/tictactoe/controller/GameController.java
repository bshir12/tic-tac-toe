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
        Map<String, Object> response = gameService.startingGame(boardSize, firstPlayer);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/move")
    public ResponseEntity<Map<String, Object>> makeMove(
            @RequestParam int row,
            @RequestParam int col) {
        Map<String, Object> response = gameService.move(row, col);
        return ResponseEntity.ok(response);
    }
}
