package shaftware.funwithstrangers;

import android.app.Application;


public class Globals extends Application {
    enum Mode
    {
        ULTIMATETICTACTOE, CHECKERS, HANGMAN, TICTACTOE
    }
    private static Mode mode;



    public static void setMode(Mode m) {
        mode = m;
    }

    public static Mode getMode() {
        return mode;
    }

    public static MultiplayerClient MultClient = new MultiplayerClient();

}
