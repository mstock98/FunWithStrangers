package shaftware.funwithstrangers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

// This is a test comment, please ignore/remove me if you wish
public class MainActivity extends AppCompatActivity {
    Button ticTacToeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ticTacToeButton =  findViewById(R.id.ticTacToeButton);

        // Capture button clicks
        ticTacToeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this, opponentSearch.class);
                startActivity(myIntent);
            }
        });
    }
}
