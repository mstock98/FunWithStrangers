package shaftware.funwithstrangers;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TttAi {

    TttLogic game = null;
    int oppPIECE = TttLogic.OPEN;

    public TttAi(int PIECE, boolean MYTURN) {
        game = new TttLogic(PIECE, MYTURN);
        if (game.getPIECE() == TttLogic.X)
            oppPIECE = TttLogic.O;
        else
            oppPIECE = TttLogic.X;
    }

    public void TttAiTurn() {
        findBestMove(game.getBoard());
    }

    private int minimax(int[][] board, int depth, boolean isMax) {

        int score = evaluate(board);

        if (score == 10)
            return score;
        if(score == -10)
            return score;
        if (game.checkWinner() != TttLogic.IN_PROGRESS)
            return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == TttLogic.OPEN) {
                        board[i][j] = game.getPIECE();
                        best = max(best, minimax(board, depth++, !isMax));
                        board[i][j] = TttLogic.OPEN;
                    }
                }
            }
            return best;
        }
        else{
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == TttLogic.OPEN) {
                        board[i][j] = oppPIECE;
                        best = min(best, minimax(board, depth++, !isMax));
                        board[i][j] = TttLogic.OPEN;
                    }
                }
            }
            return best;

        }
    }

    private int evaluate(int[][] board) {
        int OPEN = TttLogic.OPEN;
        //Cols
        for (int i = 0; i < 3; i++){
            int col = board[i][0];
            if (col != OPEN && col == board[i][1] && col == board[i][2]){
                if (col == game.getPIECE())
                    return 10;
                if (col == oppPIECE)
                    return -10;
            }
        }

        //Rows
        for (int i = 0; i < 3; i++){
            int row = board[0][i];
            if (row != OPEN && row == board[1][i] && row == board[2][i]){
                if (row == game.getPIECE())
                    return 10;
                if (row == oppPIECE)
                    return -10;
            }
        }

        //Diagonals
        int middle = board[1][1];
        if (middle != OPEN && ((middle == board[0][0] && middle == board[2][2]) || (middle == board[0][2] && middle == board[2][0]))){
            if (middle == game.getPIECE())
                return 10;
            if (middle == oppPIECE)
                return -10;
        }

        return 0;
    }

    private void findBestMove(int[][] board){
        int bestVal = Integer.MIN_VALUE;
        int row = -1;
        int col = -1;

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j] == TttLogic.OPEN){
                    board[i][j] = game.getPIECE();
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = TttLogic.OPEN;
                    if (moveVal > bestVal){
                        row = i;
                        col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        boolean valid = game.pickSpot(row, col);
        if (!valid) //Loop and pick random
            System.out.println("not valid: " + row + " " + col);
    }

}
