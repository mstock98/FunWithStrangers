package shaftware.funwithstrangers;

public class CheckersLogic {

    private square[][] board = new square[8][8];

    enum square {OPEN, RED, BLACK}

    private square piece = square.OPEN;
    private boolean turn = false;

    public CheckersLogic(square piece, boolean turn){
        this.piece = piece;
        this.turn = turn;
        ();
    }

    public int getPiece() {return piece}

    //Method only used for testing purposes
    @Deprecated
    public void swapPiece(){
        if (piece == square.BLACK)
            piece = square.RED;
        else
            piece = square.RED;
    }

    public void swapTurn(){
        if (turn)
            turn = false;
        else
            turn = true;
    }

    //Sets board to OPEN
    private void setBoard(){
        for (int i = 0; i < 8; i++){
            boolean skip = true;
            for (int j = 0; j < 8; j++){
                if (skip)
                    board[i][j] = square.BLACK;
                board[i][j] = square.OPEN;
            }
        }
    }
}
