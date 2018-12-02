package shaftware.funwithstrangers;

import java.util.ArrayList;

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
            findBestMove(game.getBoard());
        } else if (diff == difficulty.EASY) {
            while (!randomMove()) ;
        }
    }

    //TODO
    private boolean randomMove() {
        return true;
    }

    //TODO
    private void findBestMove(square[][] board) {
        ArrayList<Move[]> bestMove = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;

        ArrayList<Move[]> legalMoves = findLegalMoves();

        for (Move[] move : legalMoves){
            game.validMove(move[0], move[1], true);
            int moveVal = minimax(0, false);
        }
    }

    private int minimax(int depth, boolean isMax){
        int score = checkWinner();

        return 0; //TEMP
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
    }
}
