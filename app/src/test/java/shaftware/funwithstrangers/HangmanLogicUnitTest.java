package shaftware.funwithstrangers;

import android.content.Context;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import shaftware.funwithstrangers.*;

public class HangmanLogicUnitTest {

    String initialString;
    InputStream targetStream;
    HangmanLogic logic;

    @Before
    public void initialize(){
        initialString = "text\nhello\nbye\npectin\n";
        targetStream = new ByteArrayInputStream(initialString.getBytes());
        logic = new HangmanLogic(targetStream);
        logic.getWord(2);
    }

    @Test
    public void constructorTest(){
        assertEquals(logic.getWord(2), "bye");
    }

    @Test
    public void letterGuessedTest(){
        char[] temp = logic.letterGuessed('y');
        char[] temp1 = "!y!".toCharArray();
        for(int i = 0; i < temp.length; i++){
            assertEquals(temp[i],temp1[i]);
        }
    }

    @Test
    public void getWordTest(){
        assertEquals(logic.getWord(3), "pectin");
    }

    @Test
    public void eventLogicTest(){
        char[] temp = {'b','!','!'};
        assertTrue(logic.eventLogic(temp));
        temp[0] = '!';
        assertFalse(logic.eventLogic(temp));
    }

    @Test
    public void setWordTest(){
        char[] temp = {'b','y','w'};
        logic.setWord(temp);
        assertEquals(temp, logic.word);
    }
}