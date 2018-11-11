package shaftware.funwithstrangers;

public class HangmanLogic {

    private char[] word = new char[20];
    private boolean turn;

    public void HangmanLogic(char[] word, boolean turn){
        this.word = word;
        this.turn = turn;
    }

    public void letterGuessed(char letter, boolean turn){
        int j = 0;
        for(int i : word){
            if(letter == word[i]) {
                //Hangman.letterReveal();     not done yet ui stuff
                j++;
            }
        }
        if(j == 0)
            //Hangman.addLimb();              not done yet ui stuff
        this.turn = turn;
    }

    public int wordGuessed(char[] word){
        int j = 0;
        for(int i : word){
            if(word[i] == word[i]) {
                j++;
            }
        }
        if(j == word.length){
            return 1;
        } else {
            return 0;
        }
    }

    public int checkDead(int revealed){
        if(revealed == 6){
            return 1;
        } else {
            return 0;
        }
    }
}
