package shaftware.funwithstrangers;

public class UltimateTTTLogic extends TttLogicBase {
    SmallTTTLogic[][] largeBoard;
    private Piece PIECE;

    public UltimateTTTLogic(Piece PIECE) {
        this.PIECE = PIECE;
        largeBoard = new SmallTTTLogic[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                largeBoard[i][j] = new SmallTTTLogic();
            }
        }
    }

    public Winner checkWinner() {
        return null;
    }

    public void clearBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                largeBoard[i][j].clearBoard();
            }
        }
    }

    // TODO: Write unit test
    public boolean pickSpot(int row, int col) {
        return false;
    }

    @Override
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
}
