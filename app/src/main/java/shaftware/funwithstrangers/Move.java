package shaftware.funwithstrangers;

public class Move {
    private int row, col;

    public Move(){
        this(-1, -1);
    }

    public Move(int row, int col){
        this.row = row;
        this.col = col;
    }

    public void setRow(int row) { this.row = row; }

    public void setCol(int col) { this.col = col; }

    public int getRow() { return row; }

    public int getCol() { return col; }
}
