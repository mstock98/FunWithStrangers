package shaftware.funwithstrangers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //define buttons
    Button ticTacToeButton;
    Button chessButton;
    Button checkersButton;
    Button hangManButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate buttons based on the buttons specified in our layout
        ticTacToeButton =  findViewById(R.id.ticTacToeButton);
        chessButton =  findViewById(R.id.chessButton);
        hangManButton =  findViewById(R.id.hangManButton);
        checkersButton =  findViewById(R.id.checkersButton);


        // Capture button clicks
        ticTacToeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Globals g = (Globals)getApplication();
                g.setMode(Globals.Mode.TICTACTOE);
                switchToOpponentSearch();
            }
        });

        // Capture button clicks
        chessButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Globals g = (Globals)getApplication();
                g.setMode(Globals.Mode.CHESS);
                switchToOpponentSearch();
            }
        });

        // Capture button clicks
        hangManButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Globals g = (Globals)getApplication();
                g.setMode(Globals.Mode.HANGMAN);
                switchToOpponentSearch();
            }
        });

        // Capture button clicks
        checkersButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Globals g = (Globals)getApplication();
                g.setMode(Globals.Mode.CHECKERS);
                switchToOpponentSearch();
            }
        });
    }

    private void switchToOpponentSearch(){
        // Start NewActivity.class
        Intent myIntent = new Intent(MainActivity.this, opponentSearch.class);
        startActivity(myIntent);
    }
}
