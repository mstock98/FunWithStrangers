package shaftware.funwithstrangers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HangmanLogic {
    private char[] word;
    private String[] words = new String[41173];
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    /* Constructor
     * creates an array of white-listed words
     */
    public HangmanLogic(InputStream inputStream){

        word = "ass".toCharArray();

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
            if(attempt.toLowerCase().equals(words[i].trim().toLowerCase())){
                t = true;
                break;
            }
        }
        return t;
    }

    public char[] letterGuessed(char letter){
        char[] temp = word.clone();
        for(int i = 0; i < temp.length; i++){
            if(letter != word[i])
                temp[i] = '!';
        }
        return temp;
    }

    public int wordGuessed(char[] attempt){
        int j = 0;
        for(int i = 0; i < word.length; i++){
            if(attempt[i] == word[i]) {
                j++;
            }
        }
        if(j == word.length){
            return 1;
        } else {
            return 0;
        }
    }

    public boolean eventLogic(char[] temp){
        int j = 0;
        for(int i = 0; i < temp.length; i++){
            if(temp[i] == '!'){
                j++;
            }
        }
        if(j == temp.length){
            return false;
        } else {
            return true;
        }
    }
}