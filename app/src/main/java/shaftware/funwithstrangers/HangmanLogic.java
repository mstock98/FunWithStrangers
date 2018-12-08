package shaftware.funwithstrangers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HangmanLogic {
    private char[] word = new char[10];
    private boolean turn;
    private String[] words = new String[41173];
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    /* Constructor
     * creates an array of white-listed words
     */
    public HangmanLogic(InputStream inputStream){
        try{
            int i = inputStream.read();
            int j = 0;
            while (i!=-1){
                if(i=='\n'){
                    words[j] = byteArrayOutputStream.toString();
                    byteArrayOutputStream.reset();
                    j++;
                    i = inputStream.read();
                }
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // English Word Authentication method
    public boolean wordAuth(String attempt){
        boolean t = false;
        for(int i = 0; i < words.length-2; i++){
            if(attempt.equals(words[i].trim())){
                t = true;
                break;
            }
        }
        return t;
    }

    public char[] letterGuessed(char letter){
        char[] temp = new char[10];
        for(int i : word){
            if(letter == word[i]) {
                temp[i] = letter;
            }
        }
        return temp;
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