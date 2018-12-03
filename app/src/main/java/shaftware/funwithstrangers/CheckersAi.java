package shaftware.funwithstrangers;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;
import shaftware.funwithstrangers.CheckersLogic.square;
import shaftware.funwithstrangers.CheckersLogic.Move;
import shaftware.funwithstrangers.CheckersLogic.outcome;

public class CheckersAi {

    CheckersLogic game;
    square piece;
    square opPiece;

    enum difficulty {EASY, HARD, IMPOSSIBLE}

    difficulty diff;

    private boolean takeFirstTurn;

    public CheckersAi(square piece, difficulty diff, boolean takeFirstTurn) {
        this.piece = piece;
        if (piece == square.WHITE) {
            opPiece = square.BLACK;
        } else if (piece == square.BLACK) {
            opPiece = square.WHITE;
        }
        this.diff = diff;
        this.takeFirstTurn = takeFirstTurn;

        game = new CheckersLogic(piece, true);
    }

    public void CheckersAiTurn() {
        if (takeFirstTurn) {
            CheckersAiTurnFirst();
        } else if (diff == difficulty.HARD || diff == difficulty.IMPOSSIBLE) {
            findBestMove();
        } else if (diff == difficulty.EASY) {
            while (!randomMove()) ;
        }
    }

    //TODO
    public boolean randomMove() {
        Random random = new Random();

        ArrayList<Move[]> moves = findLegalMoves();

        int index = random.nextInt(moves.size());

        if(game.validMove(moves.get(index)[0], moves.get(index)[1], true))
            return true;

        return false;
    }

    //TODO
    private void findBestMove() {
        Move[] bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        ArrayList<Move[]> legalMoves = findLegalMoves();

        square[][] board = game.getBoard();

        for (Move[] move : legalMoves){
            game.validMove(move[0], move[1], true);
            int moveVal = minimax(board, 0, false);
            if (moveVal > bestScore) {
                bestScore = moveVal;
                bestMove = move;
            }
            game.setBoard(board);
        }

        game.validMove(bestMove[0], bestMove[1], true);

    }

    private int minimax(square[][] board, int depth, boolean isMax){
        int score = checkWinner();

        if (score == 10 || score == -10)
            return score;
        if (game.checkWinner() != outcome.IN_PROGRESS)
            return 0;

        square[][] tempBoard = board;

        if (isMax){
            int best = Integer.MIN_VALUE;

            ArrayList<Move[]> legalMoves = findLegalMoves();

            for (Move[] move : legalMoves){
                game.validMove(move[0], move[1], true);
                best = max(best, minimax(tempBoard, depth++, !isMax));
                game.setBoard(board);
            }
            return best;
        } else{
            int best = Integer.MAX_VALUE;

            ArrayList<Move[]> legalMoves = findLegalMoves();

            for (Move[] move : legalMoves){
                game.validMove(move[0], move[1], true);
                best = min(best, minimax(tempBoard, depth++, !isMax));
                game.setBoard(board);
            }
            return best;
        }
    }

    private int checkWinner(){
        outcome result = game.checkWinner();

        if (result.ordinal() == piece.ordinal())
            return 10;
        else if (result.ordinal() == opPiece.ordinal())
            return -10;

        return 0;
    }

    private ArrayList<Move[]> findLegalMoves() {
        ArrayList<Move[]> legalMoves = new ArrayList<>();

        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                Move moveI = new Move(i / 8, i % 8);
                Move moveJ = new Move(j / 8, j % 8);

                if (game.validMove(moveI, moveJ, false)) {
                    legalMoves.add(new Move[]{moveI, moveJ});
                }
            }
        }

        return legalMoves;
    }


    //TODO
    private void CheckersAiTurnFirst() {
        findBestMove();
    }
}
