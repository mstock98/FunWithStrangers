package shaftware.funwithstrangers;

public class UltimateTTTLogic extends TttLogicBase {
    SmallTTTLogic[][] largeBoard;

    public UltimateTTTLogic(Piece PIECE) {
        this.PIECE = PIECE;
        largeBoard = new SmallTTTLogic[3][3];
    }

    public Winner checkWinner() {
        return null;
    }

    public void clearBoard() {

    }

    // TODO: Write unit test
    public boolean pickSpot(int row, int col) {
        if (row < 9 && col < 9) {
            return largeBoard[row / 3][col / 3].pickSpot(row % 3, col % 3);
        } else {
            return false;
        }
    }
}
