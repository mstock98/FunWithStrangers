package shaftware.funwithstrangers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class TttAiUnitTest {

    private void printBoard(int[][] board){
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    @Test
    public void ExampleGame(){
        TttAi ai = new TttAi(TttLogic.X, true, TttAi.IMPOSSIBLE, false);

        //Prepare game state
        int[][] board = {{1, 0, 1}, {0, 0, 1}, {-1, -1, -1}};
        ai.game.receiveBoard(board);
        ai.TttAiTurn();
        int[][] temp = ai.game.getBoard();
        printBoard(temp);
        assertEquals(1, temp[2][2]);
    }

    @Test
    public void ExampleFirstTurn(){
        TttAi ai = new TttAi(TttLogic.X, true, TttAi.EZ, false);
        int[][] board = {{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};
        ai.game.receiveBoard(board);
        ai.TttAiTurn();
        int[][] temp = ai.game.getBoard();
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
            int result = aiHardVsAiImpossible();
            if (result == 1)
                x++;
            else if (result == 0)
                o++;
            else if (result == 2)
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

    public int aiHardVsAiImpossible(){
        TttAi aiH = new TttAi(TttLogic.O, true, TttAi.HARD, true);
        TttAi aiI = new TttAi(TttLogic.X, true, TttAi.IMPOSSIBLE, false);

        boolean inProgress = true;
        int result;
        do{
            aiH.TttAiTurn();
            aiI.game.receiveBoard(aiH.game.getBoard());

            if ((result = aiH.game.checkWinner()) != TttLogic.IN_PROGRESS) {
                inProgress = false;
                continue;
            }

            aiI.TttAiTurn();
            aiH.game.receiveBoard(aiI.game.getBoard());

            if ((result = aiH.game.checkWinner()) != TttLogic.IN_PROGRESS) {
                inProgress = false;
                continue;
            }
        }while(inProgress);

        System.out.println(result);
        return result;
    }

    public int aiVsSelf(int difficulty, boolean print){
        TttAi ai = new TttAi(TttLogic.X, true, difficulty, true);
        boolean inProgress = true;
        int result;
        do {
            ai.TttAiTurn();
            ai.game.swapPiece();
            if ((result = ai.game.checkWinner()) != TttLogic.IN_PROGRESS)
                inProgress = false;
        }while(inProgress);

        if (print)
            printBoard(ai.game.getBoard());

        return result;
    }
}
