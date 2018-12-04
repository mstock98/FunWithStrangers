package shaftware.funwithstrangers;

import java.util.ArrayList;
import java.util.Random;

import shaftware.funwithstrangers.CheckersLogic.Move;
import shaftware.funwithstrangers.CheckersLogic.outcome;
import shaftware.funwithstrangers.CheckersLogic.square;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CheckersAi {

    CheckersLogic game;

    enum difficulty {EASY, HARD, IMPOSSIBLE}

    difficulty diff;

    int DEPTH_LIMIT = 1;

    public CheckersAi(square piece, difficulty diff, boolean takeFirstTurn) {
        this.diff = diff;

        game = new CheckersLogic(piece, true);
    }

    public void CheckersAiTurn() {
        if (diff == difficulty.HARD || diff == difficulty.IMPOSSIBLE) {
            findBestMove();
        } else if (diff == difficulty.EASY) {
            randomMove();
        }
    }

    public void randomMove() {
        Random random = new Random();

        ArrayList<Move[]> moves;
        int index;

        do {
            moves = findLegalMoves();
            if (moves.size() < 1)
                return;
            index = random.nextInt(moves.size());
            game.validMove(moves.get(index)[0], moves.get(index)[1], true);
        } while (!game.checkEndTurn(moves.get(index)[1]));
    }

    private square[][] copyBoard(square[][] board) {
        square[][] newBoard = new square[8][8];

        for (int i = 0; i < 64; i++) {
            newBoard[i / 8][i % 8] = board[i / 8][i % 8];
        }

        return newBoard;
    }

    //TODO
    private void findBestMove() {
        Move[] bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        ArrayList<Move[]> legalMoves = findLegalMoves();

        square[][] backupBoard = copyBoard(game.getBoard());

        for (Move[] move : legalMoves) {
            game.validMove(move[0], move[1], true);
            int moveVal = minimax(0, false);
            if (moveVal > bestScore) {
                bestScore = moveVal;
                bestMove = move;
            }
            game.setBoard(backupBoard);
        }

        game.validMove(bestMove[0], bestMove[1], true);

    }

    private int minimax(int depth, boolean isMax) {
        int score = checkWinner(copyBoard(game.getBoard()), depth > DEPTH_LIMIT);
        System.out.println("Depth = " + depth + " | Score = " + score);

        if (score != 0)
            return score;
        else if (game.checkWinner() != outcome.IN_PROGRESS)
            return 0;

        square[][] backupBoard = copyBoard(game.getBoard());

        if (isMax) {
            int best = Integer.MIN_VALUE;
            ArrayList<Move[]> legalMoves = findLegalMoves();
            for (Move[] move : legalMoves) {
                game.validMove(move[0], move[1], true);
                if (game.checkEndTurn(move[1]))
                    best = max(best, minimax(depth++, !isMax));
                else
                    best = max(best, minimax(depth++, isMax));
                game.setBoard(backupBoard);
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            game.swapPiece();
            ArrayList<Move[]> legalMoves = findLegalMoves();
            game.swapPiece();
            for (Move[] move : legalMoves) {
                game.swapPiece();
                game.validMove(move[0], move[1], true);
                game.swapPiece();
                if (game.checkEndTurn(move[1]))
                    best = min(best, minimax(depth++, !isMax));
                else
                    best = min(best, minimax(depth++, isMax));
                game.setBoard(backupBoard);
            }
            return best;
        }
    }

    private int checkWinner(square[][] board, boolean maxDepthReached) {
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
            if (game.getPiece() == square.BLACK)
                return -10;
            else
                return 10;
        } else if (white == 0) {
            if (game.getPiece() == square.WHITE)
                return -10;
            else
                return 10;
        }

        if(maxDepthReached == true){
            if (game.getPiece() == square.BLACK && black > white)
                return 2;
            else if (game.getPiece() == square.WHITE && white > black)
                return 2;
            else
                return -2;
        }

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
        System.out.println("Legal Moves: " + legalMoves.size());
        return legalMoves;
    }
}
