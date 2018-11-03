package shaftware.funwithstrangers;

public class TttLogic extends TttLogicBase {
    private Piece[][] board = new Piece[3][3];

    public static final int TIE = 2;
    public static final int IN_PROGRESS = -1;

    private Piece PIECE;
    private boolean MYTURN;

    public TttLogic(Piece PIECE, boolean MYTURN) {
        //Decide on PIECE (0 for o, 1 for x)
        this.PIECE = PIECE;
        //Decide on turn
        this.MYTURN = MYTURN;
    }

    public Piece getPIECE() {
        return PIECE;
    }

    public void receiveBoard(Piece[][] board) {
        this.board = board;
    }

    //Method only used for in progress builds and testing purposes.
    @Deprecated
    public void swapPiece() {
        if (PIECE == Piece.X)
            PIECE = Piece.O;
        else
            PIECE = Piece.X;
    }

    //Turns turn on and off
    public void swapTurn() {
        if (MYTURN)
            MYTURN = false;
        else
            MYTURN = true;
    }

    public Piece[][] getBoard() {
        return board;
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

    public Piece getBoardPiece(int row, int col) { return board[row][col]; }

    public boolean isTurn() {
        return MYTURN;
    }

    //Sets board to OPEN
    public void clearBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Piece.OPEN;
            }
        }
    }

    //Tries to place a move, returns false if invalid and if it failed
    public boolean pickSpot(int row, int col) {
        if (row > -1 && row < 3 && col > -1 && col < 3 && board[row][col] == Piece.OPEN) {
            board[row][col] = PIECE;
            return true;
        }
        return false;
    }

    //Return O: '0', X: '1', Tie: '2'
    public Winner checkWinner() {

        //Cols
        for (int i = 0; i < 3; i++) {
            Piece col = board[i][0];
            if (col != Piece.OPEN && col == board[i][1] && col == board[i][2]) {
                return Winner.values()[col.ordinal()];
            }
        }

        //Rows
        for (int i = 0; i < 3; i++) {
            Piece row = board[0][i];
            if (row != Piece.OPEN && row == board[1][i] && row == board[2][i]) {
                return Winner.values()[row.ordinal()];
            }
        }

        //Diagonals
        Piece middle = board[1][1];
        if (middle != Piece.OPEN && ((middle == board[0][0] && middle == board[2][2]) || (middle == board[0][2] && middle == board[2][0]))) {
            return Winner.values()[middle.ordinal()];
        }

        if (checkTie())
            return Winner.TIE;

        return Winner.IN_PROGRESS;
    }

    //Returns true if the board runs out of possible moves
    private boolean checkTie() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Piece.OPEN) {
                    return false;
                }
            }
        }
        return true;
    }
}
