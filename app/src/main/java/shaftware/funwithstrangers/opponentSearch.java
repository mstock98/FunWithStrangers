package shaftware.funwithstrangers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class opponentSearch extends AppCompatActivity {
    Button cancelButton, playButton;
    TextView gameTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Globals.Mode mode;
        Globals g = (Globals)getApplication();
        mode = g.getMode();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent_search);
        // Locate the button in activity_main.xml
        cancelButton =  findViewById(R.id.cancelSearchButton);
        gameTitle = findViewById(R.id.gameTitle);

        gameTitle.setText(0); // you need a res id to store the string values anyway
        switch(mode){
            case CHECKERS:
                    gameTitle.setText(R.string.checkers_text);
                    break;
            case TICTACTOE:
                    gameTitle.setText(R.string.TicTacToe_text);
                    break;
            case CHESS:
                    gameTitle.setText(R.string.chess_text);
                    break;
            case HANGMAN:
                    gameTitle.setText(R.string.hangMan_text);
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

                // Start NewActivity.class
                Intent myIntent = new Intent(opponentSearch.this, ticTacToe.class);
                startActivity(myIntent);
            }
        });
    }
}
