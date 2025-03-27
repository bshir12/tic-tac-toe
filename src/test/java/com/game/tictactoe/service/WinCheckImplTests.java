package com.game.tictactoe.service;

import com.game.tictactoe.config.GameConfiguration;
import com.game.tictactoe.constant.Player;
import com.game.tictactoe.model.Board;
import com.game.tictactoe.service.impl.WinCheckImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WinCheckImplTests {
    @Mock
    private GameConfiguration gameConfig;

    @InjectMocks
    private WinCheckImpl winCheckImpl;

    private Board board;

    @BeforeEach
    void setUp() {
        when(gameConfig.getWinCondition()).thenReturn(3);
    }

    @Test
    void testHorizontalWin() {
        board = new Board(3);
        String[][] grid = board.getGrid();
        grid[1][0] = Player.X.getSymbol();
        grid[1][1] = Player.X.getSymbol();
        grid[1][2] = Player.X.getSymbol();

        assertTrue(winCheckImpl.checkWin(board, Player.X, 1, 0));
        assertTrue(winCheckImpl.checkWin(board, Player.X, 1, 1));
        assertTrue(winCheckImpl.checkWin(board, Player.X, 1, 2));
    }

    @Test
    void testVerticalWin() {
        board = new Board(3);
        String[][] grid = board.getGrid();
        grid[0][1] = Player.O.getSymbol();
        grid[1][1] = Player.O.getSymbol();
        grid[2][1] = Player.O.getSymbol();

        assertTrue(winCheckImpl.checkWin(board, Player.O, 0, 1));
        assertTrue(winCheckImpl.checkWin(board, Player.O, 1, 1));
        assertTrue(winCheckImpl.checkWin(board, Player.O, 2, 1));
    }

    @Test
    void testMainDiagonalWin() {
        board = new Board(3);
        String[][] grid = board.getGrid();
        grid[0][0] = Player.X.getSymbol();
        grid[1][1] = Player.X.getSymbol();
        grid[2][2] = Player.X.getSymbol();

        assertTrue(winCheckImpl.checkWin(board, Player.X, 0, 0));
        assertTrue(winCheckImpl.checkWin(board, Player.X, 1, 1));
        assertTrue(winCheckImpl.checkWin(board, Player.X, 2, 2));
    }

    @Test
    void testAntiDiagonalWin() {
        board = new Board(3);
        String[][] grid = board.getGrid();
        grid[0][2] = Player.O.getSymbol();
        grid[1][1] = Player.O.getSymbol();
        grid[2][0] = Player.O.getSymbol();

        assertTrue(winCheckImpl.checkWin(board, Player.O, 0, 2));
        assertTrue(winCheckImpl.checkWin(board, Player.O, 1, 1));
        assertTrue(winCheckImpl.checkWin(board, Player.O, 2, 0));
    }

    @Test
    void testNoWin() {
        board = new Board(3);
        String[][] grid = board.getGrid();
        grid[0][0] = Player.X.getSymbol();
        grid[1][1] = Player.O.getSymbol();
        grid[2][2] = Player.X.getSymbol();

        assertFalse(winCheckImpl.checkWin(board, Player.X, 0, 0));
        assertFalse(winCheckImpl.checkWin(board, Player.O, 1, 1));
    }

    @Test
    void testPartialWin() {
        board = new Board(3);
        String[][] grid = board.getGrid();
        grid[1][0] = Player.X.getSymbol();
        grid[1][1] = Player.X.getSymbol();

        assertFalse(winCheckImpl.checkWin(board, Player.X, 1, 0));
        assertFalse(winCheckImpl.checkWin(board, Player.X, 1, 1));
    }

    @Test
    void testWinConditionWithDifferentSize() {
        when(gameConfig.getWinCondition()).thenReturn(4);

        board = new Board(5);
        String[][] grid = board.getGrid();
        grid[1][1] = Player.X.getSymbol();
        grid[1][2] = Player.X.getSymbol();
        grid[1][3] = Player.X.getSymbol();
        grid[1][4] = Player.X.getSymbol();

        assertTrue(winCheckImpl.checkWin(board, Player.X, 1, 1));
        assertTrue(winCheckImpl.checkWin(board, Player.X, 1, 2));
        assertTrue(winCheckImpl.checkWin(board, Player.X, 1, 3));
        assertTrue(winCheckImpl.checkWin(board, Player.X, 1, 4));
    }
}
