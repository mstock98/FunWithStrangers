package shaftware.funwithstrangers;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
//import com.google.android.gms.games.multiplayer;
//import com.google.android.gms.games.multipalyer.realtime;
//import com.google.android.gms.games;

public class opponentSearch extends AppCompatActivity {
    Button cancelButton, playButton;
    TextView gameTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Globals.Mode mode;
        Globals g = (Globals)getApplication();
        mode = g.getMode();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent_search);
        // Locate the button in activity_main.xml
        cancelButton =  findViewById(R.id.cancelSearchButton);
        gameTitle = findViewById(R.id.gameTitle);

      switch(mode){
            case CHECKERS:
                    gameTitle.setText("Checkers");
                    break;
            case TICTACTOE:
                    gameTitle.setText("Tic Tac Toe");
                    break;
            case CHESS:
                    gameTitle.setText("Chess");
                    break;
            case HANGMAN:
                    gameTitle.setText("Hang Man");
                    break;
        }


        // Capture button clicks
        cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(opponentSearch.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Class next = MainActivity.class;
                switch(mode){
                    case CHECKERS:
                        //switch to checkers activity, goes back to main by default
                        break;
                    case TICTACTOE:
                        next = ticTacToe.class;
                        break;
                    case CHESS:
                        //switch to chess activity, goes back to main by default
                        break;
                    case HANGMAN:
                        //switch to HangMan activity, goes back to main by default
                        break;
                }

                Intent myIntent = new Intent(opponentSearch.this, next);
                startActivity(myIntent);
            }
        });
    }
}
