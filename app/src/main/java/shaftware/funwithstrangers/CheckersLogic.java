package shaftware.funwithstrangers;

public class CheckersLogic {

    private square[][] board = new square[8][8];

    enum square {OPEN, RED, BLACK}

    enum outcome {TIE, RED, BLACK, IN_PROGRESS}

    private square piece = square.OPEN;
    private square opPiece = square.OPEN;
    private boolean turn = false;

    public CheckersLogic(square piece, boolean turn) {
        this.piece = piece;
        this.turn = turn;
        if (piece == square.RED)
            opPiece = square.BLACK;
        else
            opPiece = square.RED;
        initializeBoard();
    }

    public void setBoard(square[][] board) {
        this.board = board;
    }

    public square getPiece() {
        return piece;
    }

    //Method only used for testing purposes
    @Deprecated
    public void swapPiece() {
        if (piece == square.BLACK)
            piece = square.RED;
        else
            piece = square.RED;
    }

    public void swapTurn() {
        if (turn)
            turn = false;
        else
            turn = true;
    }

    //Sets board to OPEN
    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            boolean skip = true;
            for (int j = 0; j < 8; j++) {
                if (skip)
                    board[i][j] = square.BLACK;
                board[i][j] = square.OPEN;
            }
        }
    }

    //TODO
    //checks whether there are any more valid moves available e.g. another jump that could be made
    public boolean checkEndTurn() {
        return true;
    }

    //TODO
    //checks to see if the new move is valid
    //checks everything: jumps, whether the opposing piece is jumped over, etc
    public boolean validMove(checkers.Move selectedMove, checkers.Move destinationMove) {

        //checks to see if the coordinates are even on the board. Should never reach here
        if (!validCoordinates(selectedMove.getRow(), selectedMove.getCol()))
            return false;
        if (!validCoordinates(destinationMove.getRow(), destinationMove.getCol()))
            return false;

        //if the selected piece is the players...
        if (board[selectedMove.getRow()][selectedMove.getCol()] == piece) {
            //if the destination piece is open...
            if (board[destinationMove.getRow()][destinationMove.getCol()] == square.OPEN) {
                //TODO
                //Check if jumps and if over opponent piece

                int rowDiff = Math.abs(selectedMove.getRow() - destinationMove.getRow());
                int colDiff = Math.abs(selectedMove.getCol() - destinationMove.getCol());
                if (rowDiff == 1 && colDiff == 1) {
                    //TODO
                    //if only moving the piece without jumping...
                    board[selectedMove.getRow()][selectedMove.getCol()] = square.OPEN;
                    board[destinationMove.getRow()][destinationMove.getCol()] = piece;
                    return true;
                } else if (rowDiff == 2 && colDiff == 2) {
                    //TODO
                    //if jumping over a piece...
                    //Calculate that piece's coordinates
                    int rowDiff = 0, colDiff = 0;
                    if (selectedMove.getRow() > destinationMove.getRow())
                        rowDiff = -1;
                    else if (destinationMove.getRow() > selectedMove.getRow())
                        rowDiff = 1;
                    if (selectedMove.getCol() > destinationMove.getCol())
                        colDiff = -1;
                    else if (destinationMove.getCol() > selectedMove.getCol())
                        colDiff = 1;

                    board[rowDiff + selectedMove.getRow()][colDiff + selectedMove.getCol()] = square.OPEN;

                    board[selectedMove.getRow()][selectedMove.getCol()] = square.OPEN;
                    board[destinationMove.getRow()][destinationMove.getCol()] = piece;

                    return true;
                }
            }
        }
        return false;
    }

    //returns true if the coordinates are valid, false otherwise
    private boolean validCoordinates(int row, int col) {
        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
            return true;
        }
        return false;
    }

    public outcome checkWinner() {
        int black = 0, red = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == square.BLACK) {
                    black++;
                } else if (board[i][j] == square.RED) {
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
