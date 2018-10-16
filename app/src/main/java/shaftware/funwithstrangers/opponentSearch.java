package shaftware.funwithstrangers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class opponentSearch extends AppCompatActivity {
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent_search);
        // Locate the button in activity_main.xml
        cancelButton =  findViewById(R.id.cancelSearchButton);

        // Capture button clicks
        cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(opponentSearch.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
