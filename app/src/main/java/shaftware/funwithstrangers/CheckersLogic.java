package shaftware.funwithstrangers;

public class CheckersLogic {

    private square[][] board = new square[8][8];

    enum square {OPEN, RED, BLACK}
    enum outcome {TIE, RED, BLACK, IN_PROGRESS}

    private square piece = square.OPEN;
    private square opPiece = square.OPEN;
    private boolean turn = false;

    public CheckersLogic(square piece, boolean turn){
        this.piece = piece;
        this.turn = turn;
        if (piece == square.RED)
            opPiece = square.BLACK;
        else
            opPiece = square.RED;
        initializeBoard();
    }

    public void setBoard(square[][] board){
        this.board = board;
    }

    public square getPiece() {return piece;}

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
    private void initializeBoard(){
        for (int i = 0; i < 8; i++){
            boolean skip = true;
            for (int j = 0; j < 8; j++){
                if (skip)
                    board[i][j] = square.BLACK;
                board[i][j] = square.OPEN;
            }
        }
    }

    //TODO
    //checks whether there are any more valid moves available e.g. another jump that could be made
    private boolean checkEndTurn(){
        return true;
    }

    //TODO
    //checks to see if the new move is valid
    //checks everything: jumps, whether the opposing piece is jumped over, etc
    public boolean validMove(int row, int col, int newRow, int newCol){

        return false;
    }

    public outcome checkWinner(){
        int black = 0, red = 0;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (board[i][j] == square.BLACK){
                    black++;
                } else if (board[i][j] == square.RED){
                    red++;
                }
            }
        }
        if (black == 0)
            return outcome.RED;
        if (red == 0)
            return outcome.BLACK;
        return outcome.IN_PROGRESS;
    }

}
