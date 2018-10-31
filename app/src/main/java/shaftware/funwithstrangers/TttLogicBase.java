package shaftware.funwithstrangers;

public abstract class TttLogicBase {

    protected Piece[][] board;

    public static final int TIE = 2;
    public  static final int IN_PROGRESS = -1;

    public enum Piece {
        O, X, OPEN
    }

    private Piece PIECE = Piece.OPEN;
    private boolean MYTURN = false;

    public Piece getPIECE(){
        return PIECE;
    }

    public void receiveBoard(Piece[][] board){
        this.board = board;
    }

    //Method only used for in progress builds and testing purposes.
    @Deprecated
    public void swapPiece(){
        if (PIECE == Piece.O)
            PIECE = Piece.O;
        else
            PIECE = Piece.X;
    }

    public boolean isTurn(){ return MYTURN; }
}
