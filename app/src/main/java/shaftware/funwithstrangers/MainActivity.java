package shaftware.funwithstrangers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    //define buttons
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
        ticTacToeButton =  findViewById(R.id.ticTacToeButton);
        chessButton = findViewById(R.id.chessButton);
        hangManButton = findViewById(R.id.hangManButton);
        checkersButton = findViewById(R.id.checkersButton);
        onlineButton = findViewById(R.id.onlineButton);


        onlineButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        42069);
            }
        });

            // Capture button clicks
        ticTacToeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Globals g = (Globals)getApplication();
                g.setMode(Globals.Mode.TICTACTOE);
                switchToTitleScreen();
            }
        });

        // Capture button clicks
        chessButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Globals g = (Globals)getApplication();
                g.setMode(Globals.Mode.CHESS);
                switchToTitleScreen();
            }
        });

        // Capture button clicks
        hangManButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Globals g = (Globals)getApplication();
                g.setMode(Globals.Mode.HANGMAN);
                switchToTitleScreen();
            }
        });

        // Capture button clicks
        checkersButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Globals g = (Globals)getApplication();
                g.setMode(Globals.Mode.CHECKERS);
                switchToTitleScreen();
            }
        });
    }

    private void switchToTitleScreen(){
        // Start NewActivity.class
        Intent myIntent = new Intent(MainActivity.this, titleScreen.class);
        startActivity(myIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onlineButton.setText("Online");
            Globals.setOnline(true);
        } else {
            onlineButton.setText("Offline");
            Globals.setOnline(false);
        }

    }
}
