package shaftware.funwithstrangers;

import android.app.Application;


public class Globals extends Application {
    enum Mode
    {
        CHESS, CHECKERS, HANGMAN, TICTACTOE;
    }
    private Mode mode;

    private static boolean online; //permission for ACCESS_COARSE_LOCATION has been granted

    public static void setOnline(boolean s){
        online = s;
    }

    public static Boolean getOnline(){
        return online;
    }

    public void setMode(Mode s){
       mode = s;
    }

    public Mode getMode(){
        return mode;
    }

    private static String endppointID;

    public static void setEndPointID(String s){
        endppointID = s;
    }

    public static String getEndPointID(){ return endppointID; }


    // A value that uniquely identifies the app. Used for multiplayer functionality
    public static String getServiceId() {return "shaftware.funwithstrangers";}


}
