package shaftware.funwithstrangers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TicTacToeUnitTest {

    @Test
    public void ExampleGame(){
        TttLogic game = new TttLogic(TttLogic.X, true);

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
        assertEquals(TttLogic.O, game.checkWinner());
    }
}
