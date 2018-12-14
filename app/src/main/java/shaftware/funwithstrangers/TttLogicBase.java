package shaftware.funwithstrangers;

public abstract class TttLogicBase {

    public enum Piece {
        O, X, OPEN, DISABLED // Disabled state is used for UltimateTTT
    }

    public enum Winner {
        O, X, TIE, IN_PROGRESS
    }

    // Check to see who has won the board
    public abstract Winner checkWinner();

    // Set all cells in the board to open
    public abstract void clearBoard();

    public abstract boolean setBoardPiece(Piece piece, int row, int col);
}