package com.game.tictactoe.controller;

import com.game.tictactoe.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping("/reset")
    public ResponseEntity<Map<String, Object>> resetGame(
            @RequestParam(defaultValue = "3") int boardSize,
            @RequestParam(required = false) String firstPlayer
    ) {
        return ResponseEntity.ok(gameService.startingGame(boardSize, firstPlayer));
    }

    @PostMapping("/move")
    public ResponseEntity<?> makeMove(
            @RequestParam int row,
            @RequestParam int col
    ) {
        return ResponseEntity.ok(gameService.move(row, col));
    }

}
