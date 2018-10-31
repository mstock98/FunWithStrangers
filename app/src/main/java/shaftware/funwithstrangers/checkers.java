package shaftware.funwithstrangers;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


public class checkers extends AppCompatActivity {

    //Fuckin hell dudes look at all of these
    ImageButton c00, c01, c02, c03, c04, c05, c06, c07,
            c10, c11, c12, c13, c14, c15, c16, c17,
            c20, c21, c22, c23, c24, c25, c26, c27,
            c30, c31, c32, c33, c34, c35, c36, c37,
            c40, c41, c42, c43, c44, c45, c46, c47,
            c50, c51, c52, c53, c54, c55, c56, c57,
            c60, c61, c62, c63, c64, c65, c66, c67,
            c70, c71, c72, c73, c74, c75, c76, c77;
    ImageButton[] buttons = {c00, c01, c02, c03, c04, c05, c06, c07,
            c10, c11, c12, c13, c14, c15, c16, c17,
            c20, c21, c22, c23, c24, c25, c26, c27,
            c30, c31, c32, c33, c34, c35, c36, c37,
            c40, c41, c42, c43, c44, c45, c46, c47,
            c50, c51, c52, c53, c54, c55, c56, c57,
            c60, c61, c62, c63, c64, c65, c66, c67,
            c70, c71, c72, c73, c74, c75, c76, c77};
    String[] buttonsID = {"c00", "c01", "c02", "c03", "c04", "c05", "c06", "c07",
            "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17",
            "c20", "c21", "c22", "c23", "c24", "c25", "c26", "c27",
            "c30", "c31", "c32", "c33", "c34", "c35", "c36", "c37",
            "c40", "c41", "c42", "c43", "c44", "c45", "c46", "c47",
            "c50", "c51", "c52", "c53", "c54", "c55", "c56", "c57",
            "c60", "c61", "c62", "c63", "c64", "c65", "c66", "c67",
            "c70", "c71", "c72", "c73", "c74", "c75", "c76", "c77"};
    Move[] moves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers);

        createMovesArray();
        initializeButtons();

    }

    private void createMovesArray() {
        moves = new Move[64];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                moves[i+j] = new Move(i, j);
            }
        }
    }

    private void initializeButtons(){
        for (int i = 0; i < buttons.length; i++) {
            int resID = getResources().getIdentifier(buttonsID[i], "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onImageButtonPressed(v);
                }
            });
        }
    }

    private void onImageButtonPressed(View v) {
        ImageButton b = null;
        Move move = null;
        for (int i = 0; i < buttons.length; i++){
            if (buttons[i].getId() == v.getId()){
                b = buttons[i];
                move = moves[i];
            }
        }
        if (b == null || move == null) {
            return;
        }

    }
}
