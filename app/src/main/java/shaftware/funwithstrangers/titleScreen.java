package shaftware.funwithstrangers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class titleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);


        final Globals.Mode mode;
        Globals g = (Globals)getApplication();
        mode = g.getMode();


        TextView title = findViewById(R.id.titleText);
        switch(mode){
            case CHECKERS:
                title.setText("Checkers");
                break;
            case TICTACTOE:
                title.setText("Tic Tac Toe");
                break;
            case CHESS:
                title.setText("Chess");
                break;
            case HANGMAN:
                title.setText("Hang Man");
                break;
        }

        Button hostButton = findViewById(R.id.hostButton);
        hostButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Class next = titleScreen.class;
                switch(mode){
                    case CHECKERS:
                        //switch to checkers activity, goes no where
                        break;
                    case TICTACTOE:
                        next = ticTacToe.class;
                        break;
                    case CHESS:
                        //switch to chess activity, goes no where
                        break;
                    case HANGMAN:
                        //switch to HangMan activity, goes no where
                        break;
                }

                Intent myIntent = new Intent(titleScreen.this, next);
                startActivity(myIntent);
            }
        });


        Button joinButton = findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                //head back to title screen of game
                Intent myIntent = new Intent(titleScreen.this, opponentSearch.class);
                startActivity(myIntent);
            }
        });


        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                //head back to title screen of game
                Intent myIntent = new Intent(titleScreen.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }

}