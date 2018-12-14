package shaftware.funwithstrangers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HangmanLogic {
    public char[] word;
    private ArrayList<String> words = new ArrayList();
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
                    words.add(byteArrayOutputStream.toString());
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

        for(int i = 0; i < words.size()-2; i++){
            words.set(i, words.get(i).trim());
        }
    }

    // English Word Authentication method
    public boolean wordAuth(String attempt){
        boolean t = false;
        for(int i = 0; i < words.size()-2; i++){
            if(attempt.toLowerCase().equals(words.get(i))){
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

    public String getWord(int i){
        word = words.get(i).toCharArray();
        return words.get(i);
    }

    public void setWord(char[] temp){
        word = temp;
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