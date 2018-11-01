package shaftware.funwithstrangers;

import static shaftware.funwithstrangers.TttLogicBase.Piece;

public class TttLogic extends TttLogicBase {

    public static final int TIE = 2;
    public static final int IN_PROGRESS = -1;

    private Piece PIECE = Piece.OPEN;
    private boolean MYTURN = false;

    public TttLogic(Piece PIECE, boolean MYTURN){
        //Decide on PIECE (0 for o, 1 for x)
        this.PIECE = PIECE;
        //Decide on turn
        this.MYTURN = MYTURN;

        board = new Piece[3][3];
    }

    //Turns turn on and off
    public void swapTurn(){
        if (MYTURN)
            MYTURN = false;
        else
            MYTURN = true;
    }

    public Piece[][] getBoard(){
        return board;
    }

    public Piece getBoardPiece(int row, int col){
        return board[row][col];
    }

    public boolean isTurn(){ return MYTURN; }

    //Sets board to OPEN
    public void clearBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                board[i][j] = Piece.OPEN;
            }
        }
    }

    //Tries to square a move, returns false if invalid and if it failed
    public boolean pickSpot(int row, int col){
        if (row > -1 && row < 3 && col > -1 && col < 3 && board[row][col] == Piece.OPEN){
            board[row][col] = PIECE;
            return true;
        }
        return false;
    }

    //Return O: '0', X: '1', Tie: '2'
    public int checkWinner(){

        //Cols
        for (int i = 0; i < 3; i++){
            Piece col = board[i][0];
            if (col != Piece.OPEN && col == board[i][1] && col == board[i][2]){
                return col.ordinal();
            }
        }

        //Rows
        for (int i = 0; i < 3; i++){
            Piece row = board[0][i];
            if (row != Piece.OPEN && row == board[1][i] && row == board[2][i]){
                return row.ordinal();
            }
        }

        //Diagonals
        Piece middle = board[1][1];
        if (middle != Piece.OPEN && ((middle == board[0][0] && middle == board[2][2]) || (middle == board[0][2] && middle == board[2][0]))){
            return middle.ordinal();
        }

        if (checkTie())
            return 2;

        return -1;
    }

    //Returns true if the board runs out of possible board
    private boolean checkTie(){

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j] == Piece.OPEN) {
                    return false;
                }
            }
        }
        return true;
    }
}
