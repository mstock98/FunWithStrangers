package shaftware.funwithstrangers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.games.multiplayer.turnbased.*;
import com.google.android.gms.games.*;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;


public class opponentSearch extends AppCompatActivity {
    Button cancelButton, playButton;
    TextView gameTitle;
    ListView opponentListView;
    ArrayList<String> opponentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Globals.Mode mode;
        Globals g = (Globals)getApplication();
        mode = g.getMode();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent_search);
        // Locate the button in activity_main.xml
        cancelButton =  findViewById(R.id.cancelSearchButton);
        gameTitle = findViewById(R.id.gameTitle);

        // Locate opponent list
        opponentListView = findViewById(R.id.opponentList);

        // Opponent list UI setup/binding
        opponentList = new ArrayList<>();
        final ArrayAdapter<String> opponentArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opponentList);
        opponentListView.setAdapter(opponentArrayAdapter);

        startDiscovery();

      switch(mode){
            case CHECKERS:
                    gameTitle.setText("Checkers");
                    break;
            case TICTACTOE:
                    gameTitle.setText("Tic Tac Toe");
                    break;
            case CHESS:
                    gameTitle.setText("Chess");
                    break;
            case HANGMAN:
                    gameTitle.setText("Hang Man");
                    break;
        }


        // Capture button clicks
        cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(opponentSearch.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Class next = MainActivity.class;
                switch(mode){
                    case CHECKERS:
                        //switch to checkers activity, goes back to main by default
                        break;
                    case TICTACTOE:
                        next = ticTacToe.class;
                        break;
                    case CHESS:
                        //switch to chess activity, goes back to main by default
                        break;
                    case HANGMAN:
                        //switch to HangMan activity, goes back to main by default
                        break;
                }

                Intent myIntent = new Intent(opponentSearch.this, next);
                startActivity(myIntent);




            }
        });
    }

    // Local player discovery code
    // See: https://developers.google.com/nearby/connections/android/discover-devices

    // Defines what to do when a local player is found and what to do when a local player cannot be
    // found anymore
    private final EndpointDiscoveryCallback mEndpointDiscoveryCallback =
            new EndpointDiscoveryCallback() {
                @Override
                public void onEndpointFound(String endpointId, DiscoveredEndpointInfo discoveredEndpointInfo) {
                    // We may want to make opponentList a list of key value pairs later
                    // (key = endpointId, value = discoveredEndpointInfo)
                    opponentList.add(endpointId);
                    Toast toast = Toast.makeText(getApplicationContext(), "found endpoint", Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void onEndpointLost(String endpointId) {
                    Toast toast = Toast.makeText(getApplicationContext(), "endpoint lost", Toast.LENGTH_SHORT);
                    toast.show();
                    opponentList.remove(endpointId);
                }
            };

    private void startDiscovery() {
        Nearby.getConnectionsClient(this).startDiscovery(
                Globals.getServiceId(),
                mEndpointDiscoveryCallback,
                new DiscoveryOptions(Strategy.P2P_CLUSTER))
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unusedResult) {
                                // We're discovering!
                                Toast toast = Toast.makeText(getApplicationContext(), "discovering", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // We were unable to start discovering.
                                Toast toast = Toast.makeText(getApplicationContext(), "can't discover", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
    }
}
