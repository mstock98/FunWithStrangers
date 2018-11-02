package shaftware.funwithstrangers;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TttAi {

    TttLogic game = null;
    TttLogic.Piece oppPIECE = TttLogic.Piece.OPEN;

    public enum Difficulty {
        EZ, HARD, IMPOSSIBLE, DEBUG
    }

    public Difficulty DIFFICULTY = Difficulty.DEBUG;

    private boolean takeFirstTurn = false;

    public TttAi(TttLogic.Piece PIECE, boolean MYTURN, Difficulty DIFFICULTY, boolean takeFirstTurn) {
        this.takeFirstTurn = takeFirstTurn;
        game = new TttLogic(PIECE, MYTURN);
        this.DIFFICULTY = DIFFICULTY;
        if (game.getPIECE() == TttLogic.Piece.X)
            oppPIECE = TttLogic.Piece.O;
        else
            oppPIECE = TttLogic.Piece.X;
        game.clearBoard();
    }

    public void TttAiTurn() {
        if (takeFirstTurn) {
            TttAiTurnFirst();
        } else if (DIFFICULTY == Difficulty.HARD || DIFFICULTY == Difficulty.IMPOSSIBLE) {
            findBestMove(game.getBoard());
        } else if (DIFFICULTY == Difficulty.EZ){
            while (!randomMove());
        }
    }

    private void TttAiTurnFirst(){
         takeFirstTurn = false;
        if (DIFFICULTY == Difficulty.IMPOSSIBLE){
            int ran = (int)(Math.random()*4) + 1;
            int row = -1, col = -1;
            switch(ran){
                case 1:
                    row = 0;
                    col = 0;
                    break;
                case 2:
                    row = 0;
                    col = 2;
                    break;
                case 3:
                    row = 2;
                    col = 0;
                    break;
                case 4:
                    row = 2;
                    col = 2;
                    break;
            }
            game.pickSpot(row, col);
        } else{
            TttAiTurn();
        }

    }

    private int minimax(TttLogic.Piece[][] board, int depth, boolean isMax) {

        int score = checkWinner(board);

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
                    if (board[i][j] == TttLogic.Piece.OPEN) {
                        board[i][j] = game.getPIECE();
                        best = max(best, minimax(board, depth++, !isMax));
                        board[i][j] = TttLogic.Piece.OPEN;
                    }
                }
            }
            return best;
        }
        else{
            int best = Integer.MAX_VALUE;
            //if difficulty is hard, remove min
            if (DIFFICULTY == Difficulty.HARD)
                best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == TttLogic.Piece.OPEN) {
                        board[i][j] = oppPIECE;
                        best = min(best, minimax(board, depth++, !isMax));
                        board[i][j] = TttLogic.Piece.OPEN;
                    }
                }
            }
            return best;

        }
    }

    private int checkWinner(TttLogic.Piece[][] board) {
        //Cols
        for (int i = 0; i < 3; i++){
            TttLogic.Piece col = board[i][0];
            if (col != TttLogic.Piece.OPEN && col == board[i][1] && col == board[i][2]){
                if (col == game.getPIECE())
                    return 10;
                if (col == oppPIECE)
                    return -10;
            }
        }

        //Rows
        for (int i = 0; i < 3; i++){
            TttLogic.Piece row = board[0][i];
            if (row != TttLogic.Piece.OPEN && row == board[1][i] && row == board[2][i]){
                if (row == game.getPIECE())
                    return 10;
                if (row == oppPIECE)
                    return -10;
            }
        }

        //Diagonals
        TttLogic.Piece middle = board[1][1];
        if (middle != TttLogic.Piece.OPEN && ((middle == board[0][0] && middle == board[2][2]) || (middle == board[0][2] && middle == board[2][0]))){
            if (middle == game.getPIECE())
                return 10;
            if (middle == oppPIECE)
                return -10;
        }

        return 0;
    }

    private void findBestMove(TttLogic.Piece[][] board){
        int bestVal = Integer.MIN_VALUE;
        int row = -1;
        int col = -1;

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j] == TttLogic.Piece.OPEN){
                    board[i][j] = game.getPIECE();
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = TttLogic.Piece.OPEN;
                    if (moveVal > bestVal){
                        row = i;
                        col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        boolean valid = game.pickSpot(row, col);
        while (!valid && DIFFICULTY == Difficulty.HARD) { //Loop and pick random
            valid = randomMove();
        }
    }

    public boolean randomMove(){
        int row  = (int)(Math.random()*3);
        int col = (int)(Math.random()*3);
        System.out.println("random move");
        return game.pickSpot(row, col);
    }

}
