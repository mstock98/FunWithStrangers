package shaftware.funwithstrangers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    //define buttons
    Button UTTTButton;
    Button ticTacToeButton;
    Button chessButton;
    Button checkersButton;
    Button hangManButton;
    Button onlineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instantiate buttons based on the buttons specified in our layout
        UTTTButton = findViewById(R.id.UTTTButton);
        ticTacToeButton =  findViewById(R.id.ticTacToeButton);
        chessButton = findViewById(R.id.checkersButton);
        hangManButton = findViewById(R.id.hangManButton);
        checkersButton = findViewById(R.id.checkersButton);
        onlineButton = findViewById(R.id.Onlinebutton);
        if(Globals.MultClient.getOnline()){
            onlineButton.setText("Online");
        }

        onlineButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if(Globals.MultClient.getOnline()){
                    onlineButton.setText("Offline");
                    Globals.MultClient.setOnline(false);
                }else{
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            42609);
                    onlineButton.setText("Online");
                    Globals.MultClient.setOnline(true);
                }
            }
        });

        UTTTButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
              Globals.setMode(Globals.Mode.ULTIMATETICTACTOE);
                switchScreen();
            }
        });

            // Capture button clicks
        ticTacToeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
               Globals.setMode(Globals.Mode.TICTACTOE);
                switchScreen();
            }
        });

        // Capture button clicks
        hangManButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Globals.setMode(Globals.Mode.HANGMAN);
                switchScreen();
            }
        });

        // Capture button clicks
        checkersButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Globals.setMode(Globals.Mode.CHECKERS);
                switchScreen();
            }
        });
    }

    private void switchScreen(){
        Class nextClass = MainActivity.class;
        // Start NewActivity.class
        if(Globals.MultClient.getOnline()){
            nextClass = titleScreen.class;
        }else{
            switch(Globals.getMode()){
                case CHECKERS:
                    nextClass = Checkers.class;
                    break;
                case TICTACTOE:
                    nextClass = TicTacToe.class;
                    break;
                case ULTIMATETICTACTOE:
                    nextClass = UltimateTicTacToe.class;
                    break;
                case HANGMAN:
                    nextClass = Hangman.class;
                    break;
            }
        }
        Intent myIntent = new Intent(MainActivity.this, nextClass);
        startActivity(myIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onlineButton.setText("Online");
            Globals.MultClient.setOnline(true);
        }
    }
}
