package shaftware.funwithstrangers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static shaftware.funwithstrangers.TttLogicBase.Piece;
import static shaftware.funwithstrangers.TttLogicBase.Winner;

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
                if (((i > 2) && (i < 6)) && ((j > 2) && (j < 6))) {
                    // Check to see that the middle grid is enabled
                    assertEquals(Piece.OPEN, board.getBoardPiece(i, j));
                } else {
                    // Check to see that every other grid is disabled
                    assertEquals(Piece.DISABLED, board.getBoardPiece(i, j));
                }
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertNotEquals(Piece.DISABLED, board.getBoardPiece(i, j));
            }
        }

        board.enableGrid(1, 1);
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                assertNotEquals(Piece.DISABLED, board.getBoardPiece(i, j));
            }
        }

        board.enableGrid(2, 2);
        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                assertNotEquals(Piece.DISABLED, board.getBoardPiece(i, j));
            }
        }

        assertEquals(false, board.enableGrid(0,0));
    }

    @Test
    public void shouldDisableGrid() {
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

        board.setBoardPiece(Piece.OPEN, 2, 3);
        board.setBoardPiece(Piece.OPEN, 1, 1);

        board.setBoardPiece(Piece.OPEN, 4, 4);
        board.setBoardPiece(Piece.OPEN, 5, 5);

        board.setBoardPiece(Piece.OPEN, 7, 7);
        board.setBoardPiece(Piece.OPEN, 8, 4);

        board.disableGrid(0,0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertNotEquals(Piece.OPEN, board.getBoardPiece(i, j));
            }
        }

        board.disableGrid(1, 1);
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                assertNotEquals(Piece.OPEN, board.getBoardPiece(i, j));
            }
        }

        board.disableGrid(2, 2);
        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                assertNotEquals(Piece.OPEN, board.getBoardPiece(i, j));
            }
        }

        assertEquals(false, board.disableGrid(0,0));
    }

    @Test
    public void shouldDetectDiagonalWinner() {
        UltimateTTTLogic board = new UltimateTTTLogic(Piece.X);

        board.setBoardPiece(Piece.X, 0, 0);
        board.setBoardPiece(Piece.X, 1, 1);
        board.setBoardPiece(Piece.X, 2, 2);

        board.setBoardPiece(Piece.X, 4, 3);
        board.setBoardPiece(Piece.X, 4, 4);
        board.setBoardPiece(Piece.X, 4, 5);

        board.setBoardPiece(Piece.X, 6, 6);
        board.setBoardPiece(Piece.X, 7, 6);
        board.setBoardPiece(Piece.X, 8, 6);

        assertEquals(Winner.X, board.checkWinner());
    }

    @Test
    public void shouldDetectRowWinner() {
        UltimateTTTLogic board = new UltimateTTTLogic(Piece.X);

        board.setBoardPiece(Piece.X, 0, 0);
        board.setBoardPiece(Piece.X, 0, 1);
        board.setBoardPiece(Piece.X, 0, 2);

        board.setBoardPiece(Piece.X, 0, 5);
        board.setBoardPiece(Piece.X, 1, 5);
        board.setBoardPiece(Piece.X, 2, 5);

        board.setBoardPiece(Piece.X, 0, 6);
        board.setBoardPiece(Piece.X, 1, 7);
        board.setBoardPiece(Piece.X, 2, 8);

        assertEquals(Winner.X, board.checkWinner());
    }

    @Test
    public void shouldDetectColWinner() {
        UltimateTTTLogic board = new UltimateTTTLogic(Piece.X);

        board.setBoardPiece(Piece.X, 0, 6);
        board.setBoardPiece(Piece.X, 1, 6);
        board.setBoardPiece(Piece.X, 2, 6);

        board.setBoardPiece(Piece.X, 3, 7);
        board.setBoardPiece(Piece.X, 4, 7);
        board.setBoardPiece(Piece.X, 5, 7);

        board.setBoardPiece(Piece.X, 6, 6);
        board.setBoardPiece(Piece.X, 7, 7);
        board.setBoardPiece(Piece.X, 8, 8);

        assertEquals(Winner.X, board.checkWinner());
    }

    @Test
    public void shouldPickSpot() {
        UltimateTTTLogic board = new UltimateTTTLogic(Piece.X);

        board.disableGrid(1,1);
        board.enableGrid(1,0);

        board.pickSpot(4,2);

        // Assert that old grid (1, 0) is disabled
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                assertNotEquals(Piece.OPEN, board.getBoardPiece(i, j));
            }
        }

        // Assert that new grid (1, 2) is enabled
        for (int i = 3; i < 6; i++) {
            for (int j = 6; j < 9; j++) {
                assertNotEquals(Piece.DISABLED, board.getBoardPiece(i, j));
            }
        }
    }

    @Test
    public void shouldPickSpotAndEnableAll() {
        UltimateTTTLogic board = new UltimateTTTLogic(Piece.X);

        // Claim all of the cells at grid (0, 1)
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 6; j++) {
                board.setBoardPiece(Piece.O, i, j);
            }
        }

        board.pickSpot(3, 4);

        // Assert that there are no disabled cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(Piece.DISABLED, board.getBoardPiece(i, j));
            }
        }
    }

    @Test
    public void shouldFailToPickSpot() {
        UltimateTTTLogic board = new UltimateTTTLogic(Piece.X);

        board.setBoardPiece(Piece.O, 0, 0);
        board.setBoardPiece(Piece.X, 0,1);
        board.setBoardPiece(Piece.DISABLED, 0, 2);

        assertEquals(false, board.pickSpot(0,0));
        assertEquals(false, board.pickSpot(0,1));
        assertEquals(false, board.pickSpot(0,2));
    }

    @Test
    public void shouldGetIntBoard() {
        UltimateTTTLogic board = new UltimateTTTLogic(Piece.X);
        board.disableGrid(1,1);

        board.setBoardPiece(Piece.X, 4, 0);
        board.setBoardPiece(Piece.X, 4, 8);
        board.setBoardPiece(Piece.O, 0, 4);
        board.setBoardPiece(Piece.O, 8, 4);
        board.setBoardPiece(Piece.OPEN, 4, 4);

        int[][] intBoard = board.getIntBoard();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (((i == 4) && (j == 0)) || ((i == 4) && (j == 8))) {
                    assertEquals(1, intBoard[i][j]);
                } else if (((i == 0) && (j == 4)) || ((i == 8) && (j == 4))) {
                    assertEquals(0, intBoard[i][j]);
                } else if ((i == 4) && (j == 4)) {
                    assertEquals(2, intBoard[i][j]);
                } else {
                    assertEquals(3, intBoard[i][j]);
                }
            }
        }
    }

    @Test
    public void shouldReceiveBoard() {
        UltimateTTTLogic board = new UltimateTTTLogic(Piece.X);
        int[][] intBoard = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                intBoard[i][j] = 3;
            }
        }
        intBoard[4][0] = 1;
        intBoard[4][8] = 1;
        intBoard[0][4] = 0;
        intBoard[8][4] = 0;
        intBoard[4][4] = 2;

        board.receiveBoard(intBoard);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (((i == 4) && (j == 0)) || ((i == 4) && (j == 8))) {
                    assertEquals(Piece.X, board.getBoardPiece(i, j));
                } else if (((i == 0) && (j == 4)) || ((i == 8) && (j == 4))) {
                    assertEquals(Piece.O, board.getBoardPiece(i, j));
                } else if ((i == 4) && (j == 4)) {
                    assertEquals(Piece.OPEN, board.getBoardPiece(i, j));
                } else {
                    assertEquals(Piece.DISABLED, board.getBoardPiece(i, j));
                }
            }
        }
    }


}
