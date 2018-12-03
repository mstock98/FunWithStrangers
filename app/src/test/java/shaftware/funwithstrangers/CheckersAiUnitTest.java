package shaftware.funwithstrangers;

import org.junit.Test;
import shaftware.funwithstrangers.CheckersLogic.square;
import shaftware.funwithstrangers.CheckersAi.difficulty;

import static org.junit.Assert.assertEquals;

public class CheckersAiUnitTest {

    @Test
    public void randomMoveTest(){
        CheckersAi ai = new CheckersAi(square.WHITE, difficulty.IMPOSSIBLE, true);
        assertEquals(true, ai.randomMove());
    }

}
