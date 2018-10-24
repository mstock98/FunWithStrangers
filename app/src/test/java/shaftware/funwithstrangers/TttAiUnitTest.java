package shaftware.funwithstrangers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class TttAiUnitTest {

    @Test
    public void ExampleGame(){
        TttAi ai = new TttAi(TttLogic.X, true);

        //Prepare game state
        int[][] board = {{1, 0, 1}, {0, 0, 1}, {-1, -1, -1}};
        ai.game.recieveBoard(board);
        ai.TttAiTurn();
        int[][] temp = ai.game.getBoard();
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(temp[i][j] + " ");
            }
            System.out.print("\n");
        }
        assertEquals(1, temp[2][2]);
    }

    @Test
    public void ExampleFirstTurn(){
        TttAi ai = new TttAi(TttLogic.X, true);
        int[][] board = {{0, -1, 0}, {-1, 0, -1}, {-1, 0, 0}};
        ai.game.recieveBoard(board);
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
}
