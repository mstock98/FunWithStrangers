package shaftware.funwithstrangers;

public abstract class TttLogicBase {
    protected Piece[][] board;
    protected Piece PIECE;

    public enum Piece {
        O, X, OPEN
    }

    public enum Winner {
        O, X, TIE, IN_PROGRESS
    }

    // Check to see who has won the board
    public abstract Winner checkWinner();

    // Set all cells in the board to open
    public abstract void clearBoard();

    public abstract boolean pickSpot(int row, int col);

    public Piece[][] getBoard() { return board; }

    public Piece getBoardPiece(int row, int col) { return board[row][col]; }

    //Method only used for in progress builds and testing purposes.
    @Deprecated
    public void swapPiece() {
        if (PIECE == Piece.X)
            PIECE = Piece.O;
        else
            PIECE = Piece.X;
    }
}
