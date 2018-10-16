package shaftware.funwithstrangers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class ticTacToe extends AppCompatActivity {

    Button ttt00, ttt01, ttt02,
           ttt10, ttt11, ttt12,
           ttt20, ttt21, ttt22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        ttt00 = findViewById(R.id.ttt00);
        ttt01 = findViewById(R.id.ttt01);
        ttt02 = findViewById(R.id.ttt02);

        ttt10 = findViewById(R.id.ttt10);
        ttt11 = findViewById(R.id.ttt11);
        ttt12 = findViewById(R.id.ttt12);

        ttt20 = findViewById(R.id.ttt20);
        ttt21 = findViewById(R.id.ttt21);
        ttt22 = findViewById(R.id.ttt22);

    }
}
