package shaftware.funwithstrangers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shaftware.funwithstrangers.TttLogicBase.Piece;
import static shaftware.funwithstrangers.TttLogicBase.Winner;
import shaftware.funwithstrangers.TttLogic.*;

public class TicTacToeUnitTest {

    @Test
    public void ExampleGame(){
        TttLogic game = new TttLogic(Piece.X, true);

        game.clearBoard();

        game.pickSpot(0, 0); //x
        game.swapPiece();
        game.pickSpot(0, 0); //x
        game.pickSpot(0, 1); //XO
        game.swapPiece();
        game.pickSpot(0, 2); //XOX
        game.swapPiece();
        game.pickSpot(1, 1); //XOX, ?o
        game.swapPiece();
        game.pickSpot(1, 0); //XOX, XO
        game.swapPiece();
        game.pickSpot(2, 1); //XOX, XO?, ?o
        assertEquals(Winner.O, game.checkWinner());
    }

    @Test
    public void getIntBoardTest(){
        TttLogic game = new TttLogic(Piece.X, true);
        game.clearBoard();
        int board[][] = game.getIntBoard();

        int expected[][] = new int[3][3];
        for (int i = 0; i < 9; i++){
            expected[i / 3][i % 3] = Piece.OPEN.ordinal();
        }

        assertEquals(expected, board);
    }
}
