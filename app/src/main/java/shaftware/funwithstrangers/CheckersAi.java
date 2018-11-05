package shaftware.funwithstrangers;

import shaftware.funwithstrangers.CheckersLogic.square;

public class CheckersAi {

    CheckersLogic game;
    square piece;
    square opPiece;

    enum difficulty {EASY, HARD, IMPOSSIBLE}

    difficulty diff;

    private boolean takeFirstTurn;

    public CheckersAi(square PIECE, difficulty diff, boolean takeFirstTurn){
        this.piece = piece;
        if (piece == square.WHITE){
            opPiece = square.BLACK;
        } else if (piece == square.BLACK){
            opPiece = square.WHITE;
        }
        this.diff = diff;
        this.takeFirstTurn = takeFirstTurn;

        game = new CheckersLogic(piece, true);
    }

    public void CheckersAiTurn(){
        if (takeFirstTurn){
            CheckersAiTurnFirst();
        } else if (diff == difficulty.HARD || diff == difficulty.IMPOSSIBLE){
            findBestMove(game.getBoard());
        } else if (diff == difficulty.EASY){
            while (!randomMove());
        }
    }

    //TODO
    private boolean randomMove() {
        return true;
    }

    //TODO
    private void findBestMove(square[][] board) {
    }

    //TODO
    private void CheckersAiTurnFirst() {
    }
}
