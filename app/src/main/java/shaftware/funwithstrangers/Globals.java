package shaftware.funwithstrangers;

import android.app.Application;


public class Globals extends Application {
    enum Mode
    {
        CHESS, CHECKERS, HANGMAN, TICTACTOE
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

    private static String endpointID = "";

    public static void setEndPointID(String s){
        endpointID = s;
    }

    public static String getEndPointID(){ return endpointID; }

    // A value that uniquely identifies the app. Used for multiplayer functionality
    public static String getServiceId() {return "shaftware.funwithstrangers";}

    private static boolean host;

    public static boolean getHost(){
        return host;
    }

    public static void setHost(boolean b){
        host = b;
    }

    private static String Usern = "Standard boi";

    public static String getUsern(){
        return Usern;
    }

    public static void setUsern(String s){
        Usern = s;
    }

    private static boolean connected = false;

    public static boolean getConnected(){
        return connected;
    }

    public static void setConnected(boolean b){
       connected = b;
    }




    //We need the connection life cycle call back to be global, this might change in the future

}
