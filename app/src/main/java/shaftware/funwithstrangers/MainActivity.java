package shaftware.funwithstrangers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    //define buttons
    Button UTTTButton;
    Button ticTacToeButton;
    Button chessButton;
    Button checkersButton;
    Button hangManButton;
    Button onlineButton;
    Button UsernameButton;
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
        UsernameButton = findViewById(R.id.UserNameButton);



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

        UsernameButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                prompt();
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

    @Override
    public void onStart(){
        super.onStart();

        readUsern();

        if(Globals.MultClient.getOnline()){
            onlineButton.setText("Online");
        }else{
            onlineButton.setText("Offline");
        }
    }

    private void writeUsern(String s){
        File Usern;
        try{
            Usern = File.createTempFile("Usern", null, getApplication().getCacheDir());
            PrintWriter pw = new PrintWriter(new FileOutputStream(Usern));
            pw.print(s);
        }
        catch(FileNotFoundException f){

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void readUsern(){
        try{
            File directory = getApplication().getCacheDir();
            File Usern = new File(directory,"Usern");
            FileReader fr = new FileReader(Usern);
            String s = "";
            while(fr.ready()){
                s = s + (char)fr.read();
            }
            Globals.MultClient.setUsern(s);
        }
        catch(FileNotFoundException f){
            Globals.MultClient.setUsern("NoName");
            writeUsern("NoName");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void prompt(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        alert.setTitle("Change username");
        alert.setMessage("Enter new username or hit cancel. Hit okay to confirm");

        // Set an EditText view to get user input
        final EditText input = new EditText(MainActivity.this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                char[] value = new char[input.length()];
                input.getText().getChars(0, input.length(), value, 0);
                String s = new String(value);
                Globals.MultClient.setUsern(s);
                writeUsern(s);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
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
