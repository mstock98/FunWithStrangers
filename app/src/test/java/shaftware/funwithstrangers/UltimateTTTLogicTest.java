package shaftware.funwithstrangers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shaftware.funwithstrangers.TttLogicBase.Piece;

public class UltimateTTTLogicTest {
    @Test
    public void shouldSetPieces() {
        UltimateTTTLogic board = new UltimateTTTLogic(Piece.X);

        board.setBoardPiece(Piece.X, 0, 0);
        board.setBoardPiece(Piece.O, 0, 8);
        board.setBoardPiece(Piece.O, 8, 0);
        board.setBoardPiece(Piece.DISABLED, 8, 8);
        board.setBoardPiece(Piece.OPEN, 4, 4);
        board.setBoardPiece(Piece.X, 6, 8);
        board.setBoardPiece(Piece.O, 5, 8);

        assertEquals(Piece.X, board.getBoardPiece(0, 0));
        assertEquals(Piece.O, board.getBoardPiece(0, 8));
        assertEquals(Piece.O, board.getBoardPiece(8, 0));
        assertEquals(Piece.DISABLED, board.getBoardPiece(8, 8));
        assertEquals(Piece.OPEN, board.getBoardPiece(4, 4));
        assertEquals(Piece.X, board.getBoardPiece(6, 8));
        assertEquals(Piece.O, board.getBoardPiece(5, 8));
    }


    @Test
    public void shouldClearBoard() {
        UltimateTTTLogic board = new UltimateTTTLogic(Piece.X);

        board.setBoardPiece(Piece.X, 0, 0);
        board.setBoardPiece(Piece.O, 0, 8);
        board.setBoardPiece(Piece.O, 8, 0);
        board.setBoardPiece(Piece.DISABLED, 8, 8);
        board.setBoardPiece(Piece.OPEN, 4, 4);
        board.setBoardPiece(Piece.X, 6, 8);
        board.setBoardPiece(Piece.O, 5, 8);

        board.clearBoard();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(Piece.OPEN, board.getBoardPiece(i, j));
            }
        }
    }

    @Test
    public void shouldEnableGrid() {
        UltimateTTTLogic board = new UltimateTTTLogic(Piece.X);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.setBoardPiece(Piece.X, i, j);
            }
        }

        board.setBoardPiece(Piece.O, 0, 0);
        board.setBoardPiece(Piece.O, 1, 5);
        board.setBoardPiece(Piece.O, 7, 0);
        board.setBoardPiece(Piece.O, 8, 4);

        board.setBoardPiece(Piece.DISABLED, 2, 3);
        board.setBoardPiece(Piece.DISABLED, 1, 1);

        board.setBoardPiece(Piece.DISABLED, 4, 4);
        board.setBoardPiece(Piece.DISABLED, 5, 5);

        board.setBoardPiece(Piece.DISABLED, 7, 7);
        board.setBoardPiece(Piece.DISABLED, 8, 4);

        board.enableGrid(0,0);
    }
}
