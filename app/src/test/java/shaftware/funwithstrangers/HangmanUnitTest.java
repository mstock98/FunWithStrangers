package shaftware.funwithstrangers;

import android.view.View;

import static org.junit.Assert.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HangmanUnitTest {

    Hangman hangman = new Hangman();

    @Test
    public void onCreateTest(){
        assertEquals(hangman.hangmanButton.getText(), "Done");
    }

    @Test
    public void getWordTest(){
        hangman.getWord();
        assertTrue(hangman.word != null);
    }

    @Test
    public void addLimbTest(){
        hangman.addLimb();
        assertEquals(hangman.headImage.getVisibility(), View.VISIBLE);
        hangman.addLimb();
        assertEquals(hangman.bodyImage.getVisibility(), View.VISIBLE);
        hangman.addLimb();
        assertEquals(hangman.larmImage.getVisibility(), View.VISIBLE);
        hangman.addLimb();
        assertEquals(hangman.rarmImage.getVisibility(), View.VISIBLE);
        hangman.addLimb();
        assertEquals(hangman.llegImage.getVisibility(), View.VISIBLE);
        hangman.addLimb();
        assertEquals(hangman.rlegImage.getVisibility(), View.VISIBLE);
    }

    @Test
    public void revealTest(){
        hangman.texts[0].setText('b');
        hangman.texts[2].setText('e');
        char[] temp = {'!','y','!'};
        hangman.word = "bye".toCharArray();
        hangman.reveal(temp);
        assertTrue(hangman.texts[1].getText().charAt(0)=='y');
    }


}