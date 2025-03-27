package com.game.tictactoe.controller;

import com.game.tictactoe.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameControllerTests {
    @InjectMocks
    private GameController gameController;
    @Mock
    private GameService gameService;

    @Test
    void testResetGame(){
        when(gameService.startingGame(anyInt(), any())).thenReturn(new HashMap<>());

        ResponseEntity<Map<String, Object>> response = gameController.resetGame(anyInt(), any());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testMakeMove(){
        when(gameService.move(anyInt(), anyInt())).thenReturn(new HashMap<>());

        ResponseEntity<Map<String, Object>> response = gameController.makeMove(anyInt(), anyInt());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
