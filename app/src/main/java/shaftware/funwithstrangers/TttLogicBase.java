package shaftware.funwithstrangers;

public abstract class TttLogicBase {

    private int[][] board = new int[3][3];

    public static final int TIE = 2;
    public  static final int IN_PROGRESS = -1;
    public static final int OPEN = -1;
    public static final int X = 1;
    public static final int O = 0;

    private int PIECE = OPEN;
    private boolean MYTURN = false;

    /*public TttLogicBase(int PIECE, boolean MYTURN){
        //Decide on PIECE (0 for o, 1 for x)
        this.PIECE = PIECE;
        //Decide on turn
        this.MYTURN = MYTURN;
    }*/

    public int getPIECE(){
        return PIECE;
    }

    public void receiveBoard(int[][] board){
        this.board = board;
    }

    //Method only used for in progress builds and testing purposes.
    @Deprecated
    public void swapPiece(){
        if (PIECE == X)
            PIECE = O;
        else
            PIECE = X;
    }

    //Turns turn on and off
    public void swapTurn(){
        if (MYTURN)
            MYTURN = false;
        else
            MYTURN = true;
    }

    public int[][] getBoard(){
        return board;
    }

    public int getBoardPiece(int row, int col){
        return board[row][col];
    }

    public boolean isTurn(){ return MYTURN; }

    //Sets board to OPEN
    public void clearBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                board[i][j] = OPEN;
            }
        }
    }

    //Tries to square a move, returns false if invalid and if it failed
    public boolean pickSpot(int row, int col){
        if (row > -1 && row < 3 && col > -1 && col < 3 && board[row][col] == OPEN){
            board[row][col] = PIECE;
            return true;
        }
        return false;
    }

    //Return O: '0', X: '1', Tie: '2'
    public int checkWinner(){

        //Cols
        for (int i = 0; i < 3; i++){
            int col = board[i][0];
            if (col != OPEN && col == board[i][1] && col == board[i][2]){
                return col;
            }
        }

        //Rows
        for (int i = 0; i < 3; i++){
            int row = board[0][i];
            if (row != OPEN && row == board[1][i] && row == board[2][i]){
                return row;
            }
        }

        //Diagonals
        int middle = board[1][1];
        if (middle != OPEN && ((middle == board[0][0] && middle == board[2][2]) || (middle == board[0][2] && middle == board[2][0]))){
            return middle;
        }

        if (checkTie())
            return TIE;

        return IN_PROGRESS;
    }

    //Returns true if the board runs out of possible pieces
    private boolean checkTie(){

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j] == OPEN){
                    return false;
                }
            }
        }
        return true;
    }
}
