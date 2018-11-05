package shaftware.funwithstrangers;

import shaftware.funwithstrangers.CheckersLogic.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CheckersAi {

    CheckersLogic game;
    square piece;
    square opPiece;

    enum difficulty {EASY, HARD, IMPOSSIBLE}

    difficulty diff;

    private boolean takeFirstTurn;

    public CheckersAi(square PIECE, difficulty diff, boolean takeFirstTurn) {
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
        int bestVal = Integer.MIN_VALUE;
        Move bestSelected = null;
        Move bestDestination = null;

        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                //TODO
                square[][] temp = board;
                Move selectedMove = new Move(i / 8, i % 8);
                Move destinationMove = new Move(j / 8, j % 8);
                if (game.validMove(selectedMove, destinationMove, true)) {
                    int moveVal = minimax(board, 0, false);
                    if (moveVal > bestVal){
                        bestSelected = selectedMove;
                        bestDestination = destinationMove;
                        bestVal = moveVal;
                    }
                }
                game.receiveBoard(temp);
            }
        }
        //TODO
        boolean valid = game.validMove(bestSelected, bestDestination, true);
        System.out.println(valid);
    }


    //TODO
    private void CheckersAiTurnFirst() {
        takeFirstTurn = false;
        if (diff == difficulty.IMPOSSIBLE) {
            //TODO
            //Put code here to vary initial move for impossible difficulty
            CheckersAiTurn();
        } else {
            CheckersAiTurn();
        }
    }

    private int minimax(square[][] board, int depth, boolean isMax) {
        //TODO
        int score = checkWinner(board);

        if (score == 10)
            return score;
        if (score == -10)
            return score;
        if (game.checkWinner() != CheckersLogic.outcome.IN_PROGRESS)
            return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;

            //TODO
            //make efficient
            game.setPiece(piece);
            for (int i = 0; i < 64; i++) {
                for (int j = 0; j < 64; j++) {
                    //TODO
                    square[][] tempBoard = board;
                    Move selectedMove = new Move(i / 8, i % 8);
                    Move destinationMove = new Move(j / 8, j % 8);
                    if (game.validMove(selectedMove, destinationMove, true)) {
                        best = max(best, minimax(board, depth++, !isMax));
//                        if (game.checkEndTurn(destinationMove)) {
//                            best = max(best, minimax(board, depth++, isMax));
//                        }
                    }
                    game.receiveBoard(tempBoard);
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            //TODO
//            if (diff != difficulty.IMPOSSIBLE)
//                best = Integer.MIN_VALUE;

            //TODO
            //make efficient
            game.setPiece(opPiece);
            for (int i = 0; i < 64; i++) {
                for (int j = 0; j < 64; j++) {
                    //TODO
                    square[][] tempBoard = board;
                    Move selectedMove = new Move(i / 8, i % 8);
                    Move destinationMove = new Move(j / 8, j % 8);
                    if (game.validMove(selectedMove, destinationMove, true)) {
                        best = min(best, minimax(board, depth++, !isMax));
//                        if (game.checkEndTurn(destinationMove)) {
//                            best = min(best, minimax(board, depth++, isMax));
//                        }
                    }
                    game.receiveBoard(tempBoard);
                }
            }
            return best;
        }
    }

    public int checkWinner(CheckersLogic.square[][] board) {
        //TODO
        int black = 0, white = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == square.BLACK || board[i][j] == square.BKING) {
                    black++;
                } else if (board[i][j] == square.WHITE || board[i][j] == square.WKING) {
                    white++;
                }
            }
        }
        if (black == 0) {
            //return 10 + white;
            return 10;
        }
        if (white == 0) {
            //return 10 + black;
            return 10;
        }

        return 0;
    }
}