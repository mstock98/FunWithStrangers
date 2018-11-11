package shaftware.funwithstrangers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;


public class opponentSearch extends AppCompatActivity {
    Button cancelButton;
    TextView gameTitle;
    ListView opponentListView;
    ArrayList<String> opponentList;
    ArrayList<String> endpointIds;
    Class nextScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Globals.Mode mode;
        Globals g = (Globals)getApplication();
        mode = g.getMode();

        Globals.setHost(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent_search);


        // Locate the button in activity_main.xml
        cancelButton =  findViewById(R.id.cancelSearchButton);
        gameTitle = findViewById(R.id.gameTitle);






      switch(mode){
            case CHECKERS:
                    gameTitle.setText("Checkers");
                    nextScreen = Checkers.class;
                    break;
            case TICTACTOE:
                    gameTitle.setText("Tic Tac Toe");
                    nextScreen = TicTacToe.class;
                    break;
            case CHESS:
                    gameTitle.setText("Chess");
                    break;
            case HANGMAN:
                    gameTitle.setText("Hang Man");
                    break;
        }

        cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                //head back to title screen of game
                Intent myIntent = new Intent(opponentSearch.this, titleScreen.class);
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

                    // We actually need two lists to store these things, key and value won't work because what we store in the 'selector' array
                    //that is displayed is the nickname of the endpoint, which can have duplicates, so it can't work as the key
                    //Instead I will set it up like this:
                    //One list, endpointIds will hold the ids, and opponentList will hold the nick names, they are tied together through index
                    //That way when the user clicks on the x position in the list, we can connect to the x endpoint in our endpointIds

                    endpointIds.add(endpointId);
                    opponentList.add(discoveredEndpointInfo.getEndpointName());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(opponentSearch.this,
                            android.R.layout.simple_list_item_1, opponentList);

                    opponentListView.setAdapter(adapter);

                    Toast.makeText(getApplicationContext(), "found endpoint", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onEndpointLost(String endpointId) {

                    int pos = 0;
                    for(int i = 0; i < endpointIds.size(); i++){
                        if(endpointIds.get(i).equals(endpointId)){
                            pos = i;
                        }
                    }
                    Toast.makeText(getApplicationContext(), "endpoint lost", Toast.LENGTH_SHORT).show();
                    opponentList.remove(pos);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(opponentSearch.this,
                            android.R.layout.simple_list_item_1, opponentList);
                    opponentListView.setAdapter(adapter);
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
                                Toast.makeText(getApplicationContext(), "discovering", Toast.LENGTH_SHORT).show();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // We were unable to start discovering.
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
    }
    @Override
    protected void onStart(){
        super.onStart();

        Nearby.getConnectionsClient(this).stopDiscovery();
        Nearby.getConnectionsClient(this).stopAllEndpoints();

        if(Globals.getEndPointID() != ""){
            Nearby.getConnectionsClient(this).disconnectFromEndpoint(Globals.getEndPointID());
            Globals.setEndPointID("");
        }

        // Locate opponent list
        opponentListView = findViewById(R.id.opponentList);

        // Opponent list UI setup/binding
        opponentList = new ArrayList<>();
        endpointIds = new ArrayList<>();
        final ArrayAdapter<String> opponentArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opponentList);
        opponentListView.setAdapter(opponentArrayAdapter);

        opponentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> Aview, View view, int position, long id) {
                Globals.setEndPointID(endpointIds.get(position));
                Nearby.getConnectionsClient(opponentSearch.this).stopDiscovery();
                Intent myIntent = new Intent(opponentSearch.this, nextScreen);
                startActivity(myIntent);
            }
        });


        startDiscovery();
    }
}
