package com.game.tictactoe.service;

import com.game.tictactoe.config.GameConfiguration;
import com.game.tictactoe.constant.Player;
import com.game.tictactoe.handler.GameHandler;
import com.game.tictactoe.model.Board;
import com.game.tictactoe.service.WinCheck;
import com.game.tictactoe.service.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTests {
    @Mock
    private GameConfiguration gameConfig;

    @Mock
    private WinCheck winCheck;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        when(gameConfig.validateBoardSize(anyInt())).thenReturn(3);
    }

    @Test
    void testStartingGame_DefaultFirstPlayer() {
        Map<String, Object> response = gameService.startingGame(3, null);

        assertEquals(3, response.get("boardSize"));
        assertEquals(Player.X, response.get("currentPlayer"));
    }

    @Test
    void testStartingGame_SpecificFirstPlayer() {
        Map<String, Object> response = gameService.startingGame(3, "O");

        assertEquals(3, response.get("boardSize"));
        assertEquals(Player.O, response.get("currentPlayer"));
    }

    @Test
    void testMakeMove_ValidMove() {
        when(winCheck.checkWin(any(), any(), anyInt(), anyInt())).thenReturn(false);

        Map<String, Object> response = gameService.startingGame(3, "X");
        response = gameService.move(1, 1);

        assertTrue((Boolean) response.get("success"));
        assertEquals(Player.O, response.get("currentPlayer"));
        assertFalse((Boolean) response.get("gameOver"));
    }

    @Test
    void testMakeMove_WinningMove() {
        when(winCheck.checkWin(any(), any(), anyInt(), anyInt())).thenReturn(true);

        gameService.startingGame(3, "X");
        Map<String, Object> response = gameService.move(1, 1);

        assertTrue((Boolean) response.get("success"));
        assertTrue((Boolean) response.get("gameOver"));
        assertEquals("X", response.get("winner"));
    }

    @Test
    void testMakeMove_DrawGame() throws Exception {
        gameService.startingGame(3, "X");
        Board board = getPrivateField(gameService, "board", Board.class);
        String[][] grid = board.getGrid();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = (i + j) % 2 == 0 ? "X" : "O";
            }
        }

        grid[2][2] = " ";
        when(winCheck.checkWin(any(), any(), anyInt(), anyInt())).thenReturn(false);

        Map<String, Object> response = gameService.move(2, 2);

        assertTrue((Boolean) response.get("success"));
        assertTrue((Boolean) response.get("gameOver"));
        assertNull(response.get("winner"));
    }

    @Test
    void testValidateMove_GameOverThrowsException() throws Exception {
        gameService.startingGame(3, "X");

        setPrivateField(gameService, "gameOver", true);

        assertThrows(GameHandler.class, () -> {
            gameService.move(1, 1);
        });
    }

    @Test
    void testValidateMove_OutsideBoardThrowsException() {
        gameService.startingGame(3, "X");

        assertThrows(GameHandler.class, () -> {
            gameService.move(3, 3);
        });

        assertThrows(GameHandler.class, () -> {
            gameService.move(-1, -1);
        });
    }

    private <T> T getPrivateField(Object obj, String fieldName, Class<T> fieldType) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return fieldType.cast(field.get(obj));
    }

    private void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
}
