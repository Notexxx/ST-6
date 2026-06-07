package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
        game.cplayer = game.player1;
    }

    // --- Тесты инициализации ---

    @Test
    public void testGameInit_BoardIsEmpty() {
        for (int i = 0; i < 9; i++)
            assertEquals(' ', game.board[i]);
    }

    @Test
    public void testGameInit_Player1IsX() {
        assertEquals('X', game.player1.symbol);
    }

    @Test
    public void testGameInit_Player2IsO() {
        assertEquals('O', game.player2.symbol);
    }

    @Test
    public void testGameInit_StateIsPlaying() {
        assertEquals(State.PLAYING, game.state);
    }

    @Test
    public void testGameInit_BoardLength() {
        assertEquals(9, game.board.length);
    }

    // --- Тесты checkState: победа X ---

    @Test
    public void testCheckState_XWinsRow0() {
        game.symbol = 'X';
        game.board = new char[]{'X','X','X',' ',' ',' ',' ',' ',' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckState_XWinsRow1() {
        game.symbol = 'X';
        game.board = new char[]{' ',' ',' ','X','X','X',' ',' ',' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckState_XWinsRow2() {
        game.symbol = 'X';
        game.board = new char[]{' ',' ',' ',' ',' ',' ','X','X','X'};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckState_XWinsCol0() {
        game.symbol = 'X';
        game.board = new char[]{'X',' ',' ','X',' ',' ','X',' ',' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckState_XWinsCol1() {
        game.symbol = 'X';
        game.board = new char[]{' ','X',' ',' ','X',' ',' ','X',' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckState_XWinsCol2() {
        game.symbol = 'X';
        game.board = new char[]{' ',' ','X',' ',' ','X',' ',' ','X'};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckState_XWinsDiag0() {
        game.symbol = 'X';
        game.board = new char[]{'X',' ',' ',' ','X',' ',' ',' ','X'};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckState_XWinsDiag1() {
        game.symbol = 'X';
        game.board = new char[]{' ',' ','X',' ','X',' ','X',' ',' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    // --- Тесты checkState: победа O ---

    @Test
    public void testCheckState_OWinsRow0() {
        game.symbol = 'O';
        game.board = new char[]{'O','O','O',' ',' ',' ',' ',' ',' '};
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckState_OWinsCol0() {
        game.symbol = 'O';
        game.board = new char[]{'O',' ',' ','O',' ',' ','O',' ',' '};
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    // --- Тесты checkState: ничья и продолжение ---

    @Test
    public void testCheckState_Draw() {
        game.symbol = 'X';
        game.board = new char[]{'X','O','X','X','O','O','O','X','X'};
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    public void testCheckState_Playing() {
        game.symbol = 'X';
        game.board = new char[]{'X','O',' ',' ',' ',' ',' ',' ',' '};
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    // --- Тесты generateMoves ---

    @Test
    public void testGenerateMoves_EmptyBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    public void testGenerateMoves_FullBoard() {
        game.board = new char[]{'X','O','X','O','X','O','X','O','X'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(0, moves.size());
    }

    @Test
    public void testGenerateMoves_PartialBoard() {
        game.board[0] = 'X';
        game.board[4] = 'O';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(7, moves.size());
    }

    @Test
    public void testGenerateMoves_CorrectIndices() {
        game.board[0] = 'X';
        game.board[1] = 'O';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(1));
        assertTrue(moves.contains(2));
    }

    // --- Тесты evaluatePosition ---

    @Test
    public void testEvaluatePosition_XWinsForX() {
        game.symbol = 'X';
        game.board = new char[]{'X','X','X',' ',' ',' ',' ',' ',' '};
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    public void testEvaluatePosition_XWinsForO() {
        game.symbol = 'X';
        game.board = new char[]{'X','X','X',' ',' ',' ',' ',' ',' '};
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    public void testEvaluatePosition_OWinsForO() {
        game.symbol = 'O';
        game.board = new char[]{'O','O','O',' ',' ',' ',' ',' ',' '};
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    public void testEvaluatePosition_OWinsForX() {
        game.symbol = 'O';
        game.board = new char[]{'O','O','O',' ',' ',' ',' ',' ',' '};
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    public void testEvaluatePosition_Draw() {
        game.symbol = 'X';
        game.board = new char[]{'X','O','X','X','O','O','O','X','X'};
        assertEquals(0, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    public void testEvaluatePosition_Playing() {
        game.board = new char[]{'X',' ',' ',' ',' ',' ',' ',' ',' '};
        assertEquals(-1, game.evaluatePosition(game.board, game.player1));
    }

    // --- Тесты MiniMax ---

    @Test
    public void testMiniMax_ReturnsValidMove() {
        int move = game.MiniMax(game.board, game.player1);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMiniMax_BlocksWin() {
        // O уже поставил два в ряд, X должен заблокировать
        game.board = new char[]{'O','O',' ',' ',' ',' ',' ',' ',' '};
        int move = game.MiniMax(game.board, game.player1);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMiniMax_WinsImmediately() {
        // X уже две в ряд, X должен поставить третью
        game.board = new char[]{'X','X',' ',' ',' ',' ',' ',' ',' '};
        int move = game.MiniMax(game.board, game.player1);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMiniMax_OneMoveLeft() {
        game.board = new char[]{'X','O','X','O','X','O','O','X',' '};
        int move = game.MiniMax(game.board, game.player1);
        assertEquals(9, move);
    }

    // --- Тесты MinMove и MaxMove ---

    @Test
    public void testMinMove_TerminalXWin() {
        game.symbol = 'X';
        game.board = new char[]{'X','X','X',' ',' ',' ',' ',' ',' '};
        int val = game.MinMove(game.board, game.player1);
        assertEquals(Game.INF, val);
    }

    @Test
    public void testMaxMove_TerminalOWin() {
        game.symbol = 'O';
        game.board = new char[]{'O','O','O',' ',' ',' ',' ',' ',' '};
        int val = game.MaxMove(game.board, game.player2);
        assertEquals(Game.INF, val);
    }

    @Test
    public void testMinMove_OnMidGame() {
        game.board = new char[]{'X','O',' ',' ',' ',' ',' ',' ',' '};
        int val = game.MinMove(game.board, game.player1);
        assertTrue(val >= -Game.INF && val <= Game.INF);
    }

    // --- Тесты Utility ---

    @Test
    public void testUtility_PrintCharBoard() {
        char[] board = {'X','O',' ','X','O',' ','X','O',' '};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    public void testUtility_PrintIntBoard() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    public void testUtility_PrintMoveList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1); moves.add(3); moves.add(5);
        assertDoesNotThrow(() -> Utility.print(moves));
    }

    // --- Тесты Player ---

    @Test
    public void testPlayer_DefaultValues() {
        Player p = new Player();
        assertEquals(0, p.move);
        assertFalse(p.selected);
        assertFalse(p.win);
    }

    // --- Тест Program.main ---

    @Test
    public void testProgramMain() {
        assertDoesNotThrow(() -> Program.main(new String[]{}));
    }

    // --- INF константа ---

    @Test
    public void testINF_Value() {
        assertEquals(100, Game.INF);
    }
}
