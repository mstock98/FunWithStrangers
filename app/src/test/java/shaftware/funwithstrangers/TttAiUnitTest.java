package shaftware.funwithstrangers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static shaftware.funwithstrangers.TttLogicBase.Piece;
import static shaftware.funwithstrangers.TttLogicBase.Winner;

import org.junit.Test;

public class TttAiUnitTest {

    private void printBoard(Piece[][] board){
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(board[i][j].ordinal() + " ");
            }
            System.out.print("\n");
        }
    }

    @Test
    public void ExampleGame(){
        TttAi ai = new TttAi(Piece.X, true, TttAi.IMPOSSIBLE, false);

        //Prepare game state
        Piece[][] board = {{Piece.X, Piece.O, Piece.X},
                           {Piece.O, Piece.O, Piece.X},
                           {Piece.OPEN, Piece.OPEN, Piece.OPEN}};
        ai.game.receiveBoard(board);
        ai.TttAiTurn();
        Piece[][] temp = ai.game.getBoard();
        printBoard(temp);
        assertEquals(Piece.X, temp[2][2]);
    }

    @Test
    public void ExampleFirstTurn(){
        TttAi ai = new TttAi(Piece.X, true, TttAi.EZ, false);

        // Create a new board with all positions open
        Piece[][] board = new Piece[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Piece.OPEN;
            }
        }

        ai.game.receiveBoard(board);
        ai.TttAiTurn();
        Piece[][] temp = ai.game.getBoard();
        boolean val = false;
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(temp[i][j] + " ");
                if (temp[i][j] == ai.game.getPIECE())
                    val = true;
            }
            System.out.print("\n");
        }

        assertEquals(true, val);
    }

    @Test
    public void hundredGames(){
        int x = 0;
        int o = 0;
        int tie = 0;
        for (int i = 0; i < 100; i++){
            Winner result = aiHardVsAiImpossible();
            if (result == Winner.X)
                x++;
            else if (result == Winner.O)
                o++;
            else if (result == Winner.TIE)
                tie++;
            else
                System.out.println("error");
        }
        System.out.println("X wins: " + x);
        System.out.println("O wins: " + o);
        System.out.println("Ties: " + tie);
    }
    @Test
    public void aiVsSelfTest(){
        aiVsSelf(2, true);
    }

    @Test
    public void aiHardVsAiImpossibleTest(){
        aiHardVsAiImpossible();
    }

    public Winner aiHardVsAiImpossible(){
        TttAi aiH = new TttAi(Piece.O, true, TttAi.HARD, true);
        TttAi aiI = new TttAi(Piece.X, true, TttAi.IMPOSSIBLE, false);

        boolean inProgress = true;
        Winner result;
        do{
            aiH.TttAiTurn();
            aiI.game.receiveBoard(aiH.game.getBoard());

            if ((result = aiH.game.checkWinner()) != Winner.IN_PROGRESS) {
                inProgress = false;
                continue;
            }

            aiI.TttAiTurn();
            aiH.game.receiveBoard(aiI.game.getBoard());

            if ((result = aiH.game.checkWinner()) != Winner.IN_PROGRESS) {
                inProgress = false;
                continue;
            }
        }while(inProgress);

        System.out.println(result);
        return result;
    }

    public Winner aiVsSelf(int difficulty, boolean print){
        TttAi ai = new TttAi(Piece.X, true, difficulty, true);
        boolean inProgress = true;
        Winner result;
        do {
            ai.TttAiTurn();
            ai.game.swapPiece();
            if ((result = ai.game.checkWinner()) != Winner.IN_PROGRESS)
                inProgress = false;
        }while(inProgress);

        if (print)
            printBoard(ai.game.getBoard());

        return result;
    }
}
