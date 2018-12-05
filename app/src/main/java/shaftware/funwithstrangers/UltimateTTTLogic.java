package shaftware.funwithstrangers;

public class UltimateTTTLogic extends TttLogicBase {
    SmallTTTLogic[][] largeBoard;
    private Piece PIECE;
    private boolean MYTURN;

    public UltimateTTTLogic(Piece PIECE) {
        this.PIECE = PIECE;
        largeBoard = new SmallTTTLogic[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                largeBoard[i][j] = new SmallTTTLogic();
            }
        }

        clearBoard();
    }

    public SmallTTTLogic[][] getBoard() { return largeBoard; }

    public void swapTurn() {
        if (MYTURN)
            MYTURN = false;
        else
            MYTURN = true;
    }

    public boolean isTurn() {
        return MYTURN;
    }

    public Winner checkWinner() {
        //Rows
        for (int i = 0; i < 3; i++) {
            Winner row = largeBoard[i][0].checkWinner();
            if ((row != Winner.IN_PROGRESS && row != Winner.TIE) && row == largeBoard[i][1].checkWinner() && row == largeBoard[i][2].checkWinner()) {
                return row;
            }
        }

        //Cols
        for (int i = 0; i < 3; i++) {
            Winner col = largeBoard[0][i].checkWinner();
            if ((col != Winner.IN_PROGRESS && col != Winner.TIE) && col == largeBoard[1][i].checkWinner() && col == largeBoard[2][i].checkWinner()) {
                return col;
            }
        }

        //Diagonals
        Winner middle = largeBoard[1][1].checkWinner();
        if ((middle != Winner.IN_PROGRESS && middle != Winner.TIE) && ((middle == largeBoard[0][0].checkWinner() && middle == largeBoard[2][2].checkWinner()) || (middle == largeBoard[0][2].checkWinner() && middle == largeBoard[2][0].checkWinner()))) {
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
                if (largeBoard[i][j].checkWinner() == Winner.IN_PROGRESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // Resets board to starting state
    public void clearBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                largeBoard[i / 3][j / 3].setBoardPiece(Piece.OPEN, i % 3, j % 3);
            }
        }
    }

    public Piece getBoardPiece(int row, int col) {
        return largeBoard[row / 3][col / 3].getBoardPiece(row % 3, col % 3);
    }

    public boolean setBoardPiece(Piece piece, int row, int col) {
        if (((row > -1) && (row < 9)) && ((col > -1) && (col < 9))) {
            largeBoard[row / 3][col / 3].setBoardPiece(piece,row % 3, col % 3);
            return true;
        } else {
            return false;
        }
    }

    //Tries to place a move, returns false if invalid and if it failed
    public boolean pickSpot(int row, int col) {
        if (((row > - 1) && (row < 9)) && ((col > -1) && (col < 9)) && largeBoard[row / 3][col / 3].getBoardPiece(row % 3, col % 3) == Piece.OPEN) {
            setBoardPiece(PIECE, row, col);

            if (largeBoard[row % 3][col % 3].checkWinner() == Winner.IN_PROGRESS) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        disableGrid(i, j);
                    }
                }

                enableGrid(row % 3, col % 3);
            } else {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (largeBoard[i][j].checkWinner() == Winner.IN_PROGRESS) {
                            enableGrid(i, j);
                        } else {
                            disableGrid(i, j);
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    // Enables a small TTT grid for play
    // Returns true if any cell states were changed, false otherwise
    public boolean enableGrid(int row, int col) {
        boolean changesMade = false;

        // if coordinates are out of range, don't do anything
        if ((row > 2 || row < 0) || (col > 2 || col < 0)) {
            return changesMade;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (largeBoard[row][col].getBoardPiece(i, j) == Piece.DISABLED) {
                    largeBoard[row][col].setBoardPiece(Piece.OPEN, i, j);
                    changesMade = true;
                }
            }
        }

        return changesMade;
    }

    // Disables a small TTT grid from play
    // Returns true if any cell states were changed, false otherwise
    public boolean disableGrid(int row, int col) {
        boolean changesMade = false;

        // if coordinates are out of range, don't do anything
        if ((row > 2 || row < 0) || (col > 2 || col < 0)) {
            return changesMade;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (largeBoard[row][col].getBoardPiece(i, j) == Piece.OPEN) {
                    largeBoard[row][col].setBoardPiece(Piece.DISABLED, i, j);
                    changesMade = true;
                }
            }
        }

        return changesMade;
    }

    public int[][] getIntBoard() {
        int[][] intBoard = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                intBoard[i][j] = getBoardPiece(i, j).ordinal();
            }
        }

        return intBoard;
    }

    public void receiveBoard(int[][] incomingBoard) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setBoardPiece(Piece.values()[incomingBoard[i][j]], i, j);
            }
        }
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
