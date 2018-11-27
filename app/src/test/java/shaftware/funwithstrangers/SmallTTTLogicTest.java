package shaftware.funwithstrangers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shaftware.funwithstrangers.TttLogicBase.Piece;

public class SmallTTTLogicTest {
    @Test
    public void ShouldSetPieces() {
        SmallTTTLogic testBoard = new SmallTTTLogic();

        testBoard.setBoardPiece(Piece.O, 0, 0);
        testBoard.setBoardPiece(Piece.X, 0, 1);
        testBoard.setBoardPiece(Piece.OPEN, 0, 2);
        testBoard.setBoardPiece(Piece.DISABLED, 1, 0);
        testBoard.setBoardPiece(Piece.O, 1, 1);
        testBoard.setBoardPiece(Piece.DISABLED, 1, 2);
        testBoard.setBoardPiece(Piece.X, 2, 0);
        testBoard.setBoardPiece(Piece.X, 2, 1);
        testBoard.setBoardPiece(Piece.X, 2, 2);


        assertEquals(Piece.O, testBoard.getBoardPiece(0,0));
        assertEquals(Piece.X, testBoard.getBoardPiece(0,1));
        assertEquals(Piece.OPEN, testBoard.getBoardPiece(0,2));
        assertEquals(Piece.DISABLED, testBoard.getBoardPiece(1,0));
        assertEquals(Piece.O, testBoard.getBoardPiece(1,1));
        assertEquals(Piece.DISABLED, testBoard.getBoardPiece(1,2));
        assertEquals(Piece.X, testBoard.getBoardPiece(2,0));
        assertEquals(Piece.X, testBoard.getBoardPiece(2,1));
        assertEquals(Piece.X, testBoard.getBoardPiece(2,2));
    }
}
