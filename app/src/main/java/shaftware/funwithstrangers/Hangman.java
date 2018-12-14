package shaftware.funwithstrangers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Hangman extends AppCompatActivity {

    HangmanLogic logic;
    ImageView postImage;
    ImageView headImage;
    ImageView bodyImage;
    ImageView larmImage;
    ImageView rarmImage;
    ImageView llegImage;
    ImageView rlegImage;
    Button returnButton;
    Button hangmanButton;
    Button hangmanGuess;
    Button guessButton;
    EditText guessText;
    EditText hangmanText;
    TextView t0, t1, t2, t3, t4, t5, t6, t7, t8, t9;
    TextView[] texts = {t0, t1, t2, t3, t4, t5, t6, t7, t8, t9};
    String[] textsID = {"t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9"};
    Button c00, c01, c02, c03, c04, c05, c06, c07, c08, c09,
            c10, c11, c12, c13, c14, c15, c16, c17, c18, c19,
            c20, c21, c22, c23, c24, c25;
    Button[] buttons = {c00, c01, c02, c03, c04, c05, c06, c07, c08, c09,
            c10, c11, c12, c13, c14, c15, c16, c17, c18, c19,
            c20, c21, c22, c23, c24, c25};
    String[] buttonsID = {"c00", "c01", "c02", "c03", "c04", "c05", "c06", "c07", "c08", "c09",
            "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17", "c18", "c19",
            "c20", "c21", "c22", "c23", "c24", "c25"};

    int level;
    char[] word;

    receiver r = new receiver();
    String Sendword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        // Private Classes
        logic = new HangmanLogic(getResources().openRawResource(R.raw.word));

        // UI
        level = 0;
        initialize(0);
        if(!Globals.MultClient.getOnline()){
            getWord();
        }else{
            Globals.MultClient.setContext(getApplicationContext());
            Globals.MultClient.setCallback(r);
        }


        hangmanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                boolean t = logic.wordAuth(hangmanText.getEditableText().toString().trim());
                if(t){
                    Sendword = hangmanText.getEditableText().toString().trim();
                    if(!Globals.MultClient.getOnline()){
                        initialize(1);
                    }else{
                        if(Globals.MultClient.getHost()){
                            Globals.MultClient.advertise(getApplicationContext());
                        }else{
                            Globals.MultClient.connect();
                        }
                    }
                    closeKeyboard();
                } else {
                    Toast.makeText(getApplicationContext(), "Word not accepted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getRootView().getWindowToken(),0);
    }

    public void getWord(){
            int random = (new Random()).nextInt(41171);
            word = logic.getWord(random).toCharArray();
    }

    public void addLimb(){
        level++;
        switch(level){
            case 1: headImage.setVisibility(View.VISIBLE);
                    break;
            case 2: bodyImage.setVisibility(View.VISIBLE);
                    break;
            case 3: larmImage.setVisibility(View.VISIBLE);
                    break;
            case 4: rarmImage.setVisibility(View.VISIBLE);
                    break;
            case 5: llegImage.setVisibility(View.VISIBLE);
                    break;
            case 6: rlegImage.setVisibility(View.VISIBLE);
                    gameOver(false);
                    break;
        }
    }

    public void reveal(char[] reveal){
        for (int i = 0; i < word.length; i++) {
            if(reveal[i] != '!') {
                texts[i].setText(Character.toString(word[i]));
            }
        }
        int j = 0;
        for(int i = 0; i < word.length; i++){
            if(texts[i].getText().charAt(0) == word[i]){
                j++;
            }
        }
        if(j == word.length){
            gameOver(true);
        }
    }

    private void gameOver(boolean state){
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setVisibility(View.GONE);
        }
        for (int i = 0; i < word.length; i++) {
            texts[i].setText(Character.toString(word[i]));
        }
        hangmanGuess.setVisibility(View.GONE);
        postImage.setVisibility(View.GONE);
        headImage.setVisibility(View.GONE);
        bodyImage.setVisibility(View.GONE);
        larmImage.setVisibility(View.GONE);
        rarmImage.setVisibility(View.GONE);
        llegImage.setVisibility(View.GONE);
        rlegImage.setVisibility(View.GONE);
        returnButton.setVisibility(View.VISIBLE);
        if(state){
            returnButton.setText("You Win");
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
        } else {
            returnButton.setText("You Lose");
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        }
    }

    private void initialize(int state) {
        if(state == 0){
            hangmanButton = findViewById(R.id.hangmanButton);
            hangmanText = findViewById(R.id.hangmanText);
            returnButton = findViewById(R.id.returnButton);
            guessButton = findViewById(R.id.guessButton);
            guessText = findViewById(R.id.guessText);
        } else if(state == 1) {
            hangmanButton.setVisibility(View.GONE);
            hangmanText.setVisibility(View.GONE);
            hangmanGuess = findViewById(R.id.hangmanGuess);
            postImage = findViewById(R.id.postImage);
            headImage = findViewById(R.id.headImage);
            bodyImage = findViewById(R.id.bodyImage);
            larmImage = findViewById(R.id.larmImage);
            rarmImage = findViewById(R.id.rarmImage);
            llegImage = findViewById(R.id.llegImage);
            rlegImage = findViewById(R.id.rlegImage);
            hangmanGuess.setVisibility(View.VISIBLE);
            postImage.setVisibility(View.VISIBLE);
            for (int i = 0; i < buttons.length; i++) {
                String temp = Character.toString((char)(65+i));
                buttons[i] = getButton(i);
                buttons[i].setVisibility(View.VISIBLE);
                buttons[i].setText(temp);
            }
            for (int i = 0; i < word.length; i++) {
                texts[i] = getTexts(i);
                texts[i].setVisibility(View.VISIBLE);
                texts[i].setText("_");
            }
            events();
        }
    }

    private Button getButton(int i){
        int resID = getResources().getIdentifier(buttonsID[i], "id", getPackageName());
        return findViewById(resID);
    }

    private TextView getTexts(int i){
        int resID = getResources().getIdentifier(textsID[i], "id", getPackageName());
        return findViewById(resID);
    }

    private void events(){
        returnButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });

        hangmanGuess.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setVisibility(View.GONE);
                }
                hangmanGuess.setVisibility(View.GONE);
                postImage.setVisibility(View.GONE);
                headImage.setVisibility(View.GONE);
                bodyImage.setVisibility(View.GONE);
                larmImage.setVisibility(View.GONE);
                rarmImage.setVisibility(View.GONE);
                llegImage.setVisibility(View.GONE);
                rlegImage.setVisibility(View.GONE);
                guessButton.setVisibility(View.VISIBLE);
                guessText.setVisibility(View.VISIBLE);
            }
        });

        guessButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                guessText.setVisibility(View.GONE);
                guessButton.setVisibility(View.GONE);
                int j = 0;
                char[] temp = guessText.getText().toString().toCharArray();
                if(temp.length == word.length){
                    for(int i = 0; i < word.length; i++){
                        if(temp[i] == word[i]){
                            j++;
                        }
                    }
                    if(j == word.length){
                        closeKeyboard();
                        gameOver(true);
                    } else {
                        closeKeyboard();
                        gameOver(false);
                    }
                } else {
                    closeKeyboard();
                    gameOver(false);
                }
            }
        });

        buttons[0].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[0].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[0].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[1].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[1].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[1].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[2].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[2].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[2].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[3].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[3].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[3].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[4].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[4].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[4].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[5].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[5].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[5].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[6].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[6].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[6].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[7].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[7].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[7].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[8].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[8].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[8].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[9].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[9].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[9].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[10].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[10].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[10].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[11].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[11].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[11].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[12].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[12].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[12].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[13].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[13].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[13].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[14].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[14].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[14].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[15].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[15].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[15].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[16].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[16].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[16].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[17].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[17].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[17].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[18].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[18].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[18].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[19].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[19].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[19].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[20].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[20].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[20].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[21].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[21].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[21].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[22].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[22].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[22].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[23].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[23].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[23].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[24].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[24].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[24].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });

        buttons[25].setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                buttons[25].setVisibility(View.INVISIBLE);
                char[] temp = logic.letterGuessed((char)(buttons[25].getText().charAt(0) + 32));
                if(logic.eventLogic(temp)){
                    reveal(temp);
                } else {
                    addLimb();
                }
            }
        });
    }

    public void PayloadReceived(byte[] b){
        word = new char[b.length];
        for(int i = 0; i < b.length; i++){
            word[i] = (char)b[i];
        }
        logic.setWord(word);
        initialize(1);
    }

    public void theyDisconnected(){
       //in this game it doesn't matter if they leave, you just connect to pass words
    }

    public void connected(){
        if(Globals.MultClient.getHost()) {
            Globals.MultClient.stopAdvert(getApplication());
        }
        byte[] b = new byte[Sendword.length()];
        for(int i = 0; i < b.length; i++){
            b[i] = (byte)Sendword.charAt(i);
        }
        Globals.MultClient.sendPayload(b);
    }

    public class receiver implements Receiver{
        public void receive(byte[] b){
            PayloadReceived(b);
        }
        public void onConnection(){
            connected();
        }
        public void onDisconnect(){theyDisconnected();}
    }
}
