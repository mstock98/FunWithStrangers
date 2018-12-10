package shaftware.funwithstrangers;

import org.junit.Test;

import java.util.ArrayList;

import shaftware.funwithstrangers.CheckersLogic.square;
import shaftware.funwithstrangers.CheckersAi.difficulty;
import shaftware.funwithstrangers.CheckersLogic.Move;
import shaftware.funwithstrangers.*;

import static org.junit.Assert.assertEquals;

public class CheckersAiUnitTest {

    @Test
    public void findLegalMovesTest(){
        CheckersAi ai = new CheckersAi(square.WHITE, difficulty.IMPOSSIBLE, true);
        ArrayList<Move[]> moves = ai.findLegalMoves();
        boolean result;
        if (moves.size() != 0)
            result = true;
        else
            result = false;
        assertEquals(true, result);
    }

    @Test
    public void checkWinnerAiTest(){
        CheckersAi ai = new CheckersAi(square.WHITE, difficulty.IMPOSSIBLE, true);

        int total = 0;

        square[][] board = new square[8][8];
        for (int i = 0; i < 64; i++){
            board[i / 8][i % 8] = square.OPEN;
        }

        int result = ai.checkWinner(board, true);
        total += result;
        System.out.println(result);

        board[0][0] = square.WHITE;
        board[1][1] = square.WHITE;
        board[2][2] = square.BLACK;
        result = ai.checkWinner(board, true);
        total += result;


        assertEquals(total, 0);
    }

    @Test
    public void EasyAiPlayEasyAi(){
        CheckersAi ai = new CheckersAi(square.WHITE, difficulty.EASY, false);
        do{
            ai.randomMove();
            ai.game.swapPiece();
            ai.randomMove();
            ai.game.swapPiece();
        } while (ai.game.checkWinner() == CheckersLogic.outcome.IN_PROGRESS);
        System.out.println(ai.game.checkWinner());
    }

    @Test
    public void minimaxTest(){
        CheckersAi ai = new CheckersAi(square.WHITE, difficulty.IMPOSSIBLE, true);
        square[][] board = ai.game.getBoard();
        board[0][0] = square.WHITE;
        ai.game.setBoard(board);

        int result = ai.minimax(1, false);
        System.out.println(result);

        assertEquals(2, result);
    }
}
