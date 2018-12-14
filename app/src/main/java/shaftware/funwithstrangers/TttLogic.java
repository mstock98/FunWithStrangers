package shaftware.funwithstrangers;

public class TttLogic extends SmallTTTLogic {
    private boolean MYTURN;
    private Piece PIECE;

    public TttLogic(Piece PIECE, boolean MYTURN) {
        super();
        //Decide on turn
        this.MYTURN = MYTURN;
        this.PIECE = PIECE;
    }

    public Piece getPIECE() {
        return PIECE;
    }

    public void receiveBoard(Piece[][] board) {
        this.board = board;
    }

    //Turns turn on and off
    public void swapTurn() {
        if (MYTURN)
            MYTURN = false;
        else
            MYTURN = true;
    }

    // Returns the TTT board with all of the piece values converted into integers
    public int[][] getIntBoard() {
        int[][] intBoard = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                intBoard[i][j] = board[i][j].ordinal();
            }
        }

        return intBoard;
    }

    public boolean isTurn() {
        return MYTURN;
    }

    //Tries to place a move, returns false if invalid and if it failed
    public boolean pickSpot(int row, int col) {
        if (row > -1 && row < 3 && col > -1 && col < 3 && board[row][col] == Piece.OPEN) {
            board[row][col] = PIECE;
            return true;
        }
        return false;
    }

    //Method only used for in progress builds and testing purposes.
    @Deprecated
    public void swapPiece() {
        if (PIECE == Piece.X)
            PIECE = Piece.O;
        else
            PIECE = Piece.X;
    }
}