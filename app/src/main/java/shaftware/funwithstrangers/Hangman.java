package shaftware.funwithstrangers;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class Hangman extends AppCompatActivity {

    HangmanLogic logic;

    Button hangmanButton;
    Button hangmanGuess;
    EditText hangmanText;
    Button c00, c01, c02, c03, c04, c05, c06, c07, c08, c09,
            c10, c11, c12, c13, c14, c15, c16, c17, c18, c19,
            c20, c21, c22, c23, c24, c25;
    Button[] buttons = {c00, c01, c02, c03, c04, c05, c06, c07, c08, c09,
            c10, c11, c12, c13, c14, c15, c16, c17, c18, c19,
            c20, c21, c22, c23, c24, c25};
    String[] buttonsID = {"c00", "c01", "c02", "c03", "c04", "c05", "c06", "c07", "c08", "c09",
            "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18", "c19",
            "c20", "c21", "c22", "c23", "c24", "c25"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        // Private Classes
        logic = new HangmanLogic(getResources().openRawResource(R.raw.word));

        // UI
        initialize(0);

        hangmanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                boolean t = logic.wordAuth(hangmanText.getEditableText().toString().trim());
                if(t){
                    Game(hangmanText.getEditableText().toString().trim());
                } else {
                    Toast.makeText(getApplicationContext(), "Word not accepted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Game(String word){
        initialize(1);
    }

    private void addLimb(){

    }

    private void reveal(String reveal){

    }

    private void initialize(int state) {
        if(state == 0){
            hangmanButton = findViewById(R.id.hangmanButton);
            hangmanText = findViewById(R.id.hangmanText);
        } else if(state == 1) {
            hangmanButton.setVisibility(View.GONE);
            hangmanText.setVisibility(View.GONE);
            hangmanGuess = findViewById(R.id.hangmanGuess);
            hangmanGuess.setVisibility(View.VISIBLE);
            for (int i = 0; i < buttons.length; i++) {
                String temp = Character.toString((char)(65+i));
                buttons[i] = getButton(i);
                buttons[i].setVisibility(View.VISIBLE);
                buttons[i].setText(temp);
            }
            events();

        }
    }

    private Button getButton(int i){
        int resID = getResources().getIdentifier(buttonsID[i], "id", getPackageName());
        return findViewById(resID);
    }

    private void events(){
        buttons[0].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                char[] temp = logic.letterGuessed(buttons[0].getText().charAt(0));
                if(temp.equals(null)){
                    //addLimb();
                } else {
                    //reveal(temp);
                }
            }
        });

        buttons[0].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                char[] temp = logic.letterGuessed(buttons[0].getText().charAt(0));
                if(temp.equals(null)){
                    addLimb();
                } else {
                    //reveal(temp);
                }
            }
        });

        buttons[2].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[2].getText().charAt(0));
            }
        });

        buttons[3].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[3].getText().charAt(0));
            }
        });

        buttons[4].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[4].getText().charAt(0));
            }
        });

        buttons[5].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[5].getText().charAt(0));
            }
        });

        buttons[6].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[6].getText().charAt(0));
            }
        });

        buttons[7].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[7].getText().charAt(0));
            }
        });

        buttons[8].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[8].getText().charAt(0));
            }
        });

        buttons[9].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[9].getText().charAt(0));
            }
        });

        buttons[10].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[10].getText().charAt(0));
            }
        });

        buttons[11].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[11].getText().charAt(0));
            }
        });

        buttons[12].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[12].getText().charAt(0));
            }
        });

        buttons[13].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[13].getText().charAt(0));
            }
        });

        buttons[14].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[14].getText().charAt(0));
            }
        });

        buttons[15].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[15].getText().charAt(0));
            }
        });

        buttons[16].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[16].getText().charAt(0));
            }
        });

        buttons[17].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[17].getText().charAt(0));
            }
        });

        buttons[18].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[18].getText().charAt(0));
            }
        });

        buttons[19].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[19].getText().charAt(0));
            }
        });

        buttons[20].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[20].getText().charAt(0));
            }
        });

        buttons[21].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[21].getText().charAt(0));
            }
        });

        buttons[22].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[22].getText().charAt(0));
            }
        });

        buttons[23].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[23].getText().charAt(0));
            }
        });

        buttons[24].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[24].getText().charAt(0));
            }
        });

        buttons[25].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                logic.letterGuessed(buttons[25].getText().charAt(0));
            }
        });
    }
}
