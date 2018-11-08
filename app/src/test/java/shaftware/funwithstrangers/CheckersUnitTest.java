package shaftware.funwithstrangers;

import org.junit.Test;
import shaftware.funwithstrangers.CheckersLogic.square;
import shaftware.funwithstrangers.CheckersLogic.Move;
import shaftware.funwithstrangers.CheckersLogic.outcome;

import static org.junit.Assert.assertEquals;

public class CheckersUnitTest {

    CheckersLogic game = new CheckersLogic(square.WHITE, true);

    @Test
    public void validateMoveTest(){
        Move selected = new Move(5, 2);
        Move destination = new Move(4, 1);

        boolean result = game.validMove(selected, destination, false);
        assertEquals(true, result);
    }

    @Test
    public void doubleJumpMoveTest(){
        square[][] board = new square[8][8];
        for (int i = 0; i < 64; i++){
            board[i / 8][i % 8] = square.OPEN;
        }
        board[1][1] = square.BLACK;
        board[3][3] = square.BLACK;
        board[4][4] = square.WHITE;

        game.setBoard(board);

        boolean result;

        Move selected = new Move(4, 4);
        Move destination = new Move(2, 2);
        result = game.validMove(selected, destination, true);

        selected = new Move(2, 2);
        destination = new Move(0, 0);

        result = game.validMove(selected, destination, true);
        assertEquals(true, result);
    }

    @Test
    public void checkWinnerTestWinner(){
        square[][] board = new square[8][8];
        for (int i = 0; i < 64; i++){
            board[i / 8][i % 8] = square.OPEN;
        }
        board[0][0] = square.WHITE;
        game.setBoard(board);
        outcome result = game.checkWinner();
        assertEquals(outcome.WHITE, result);
    }

    @Test
    public void checkWinnerTestTie(){
        square[][] board = new square[8][8];
        for (int i = 0; i < 64; i++){
            board[i / 8][i % 8] = square.OPEN;
        }
        game.setBoard(board);
        outcome result = game.checkWinner();
        assertEquals(outcome.TIE, result);
    }

    @Test
    public void checkEndTurnTest(){
        square[][] board = new square[8][8];
        for (int i = 0; i < 64; i++){
            board[i / 8][i % 8] = square.OPEN;
        }
        board[1][1] = square.BLACK;
        board[3][3] = square.BLACK;
        board[4][4] = square.WHITE;

        game.setBoard(board);
        boolean result = game.checkEndTurn(new Move(4, 4));
        assertEquals(true, result);
    }
}
