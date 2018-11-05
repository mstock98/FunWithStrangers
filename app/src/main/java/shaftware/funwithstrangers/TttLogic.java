package shaftware.funwithstrangers;

public class TttLogic extends SmallTTTLogic {
    private boolean MYTURN;

    public TttLogic(Piece PIECE, boolean MYTURN) {
        super(PIECE);
        //Decide on turn
        this.MYTURN = MYTURN;
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
}
