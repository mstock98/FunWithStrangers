package shaftware.funwithstrangers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class ticTacToe extends AppCompatActivity {

    ImageButton ttt00, ttt01, ttt02,
            ttt10, ttt11, ttt12,
            ttt20, ttt21, ttt22;
    ImageButton[] buttons = {ttt00, ttt01, ttt02, ttt10, ttt11, ttt12, ttt20, ttt21, ttt22};
    String[] buttonsID = {"ttt00", "ttt01", "ttt02", "ttt10", "ttt11", "ttt12", "ttt20", "ttt21", "ttt22"};

    TttLogic TttGame = null;
    TttAi ai = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        startAdvertising();

        //Creates on click listeners and everything for the TTT grid buttons
        for (int i = 0; i < buttons.length; i++) {
            int resID = getResources().getIdentifier(buttonsID[i], "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    tttPressed(v);
                }
            });
        }

        //****These are the two variables to be chosen in the UI******
        boolean playerFirst = true;
        int difficulty = TttAi.IMPOSSIBLE;

        //By default whoever goes first gets X
        int playerPIECE = TttLogic.OPEN;
        int aiPIECE = TttLogic.OPEN;

        if (playerFirst){
            playerPIECE = TttLogic.X;
            aiPIECE = TttLogic.O;
        } else{
            playerPIECE = TttLogic.O;
            aiPIECE = TttLogic.X;
        }
        //Initializing the TttGame
        //Who is x and who goes first must be decided at the constructor
        TttGame = new TttLogic(playerPIECE, playerFirst);
        TttGame.clearBoard();
        updateGameView(TttGame);
        //MyTurn always set to true for AI, even though it doesn't mean anything to the AI.
        ai = new TttAi(aiPIECE, true, difficulty, !playerFirst);
        //AI takes its turn here if it is going first
        if (!playerFirst) {
            ai.game.recieveBoard(TttGame.getBoard());
            ai.TttAiTurn();
            TttGame.recieveBoard(ai.game.getBoard());
            updateGameView(TttGame);
            TttGame.swapTurn();
        }
    }

    //Interface between button pressed and TttLogic
    private void tttPressed(View v) {

        int row = -1, col = -1;

        if (TttGame.isTurn()) {
            //Button locations
            switch (v.getId()) {
                case R.id.ttt00:
                    row = 0;
                    col = 0;
                    break;
                case R.id.ttt01:
                    row = 0;
                    col = 1;
                    break;
                case R.id.ttt02:
                    row = 0;
                    col = 2;
                    break;
                case R.id.ttt10:
                    row = 1;
                    col = 0;
                    break;
                case R.id.ttt11:
                    row = 1;
                    col = 1;
                    break;
                case R.id.ttt12:
                    row = 1;
                    col = 2;
                    break;
                case R.id.ttt20:
                    row = 2;
                    col = 0;
                    break;
                case R.id.ttt21:
                    row = 2;
                    col = 1;
                    break;
                case R.id.ttt22:
                    row = 2;
                    col = 2;
                    break;
            }
        }
        //Game does not accept an invalid input and will wait for player to enter in something that is valid
        boolean validSpot = TttGame.pickSpot(row, col);

        if (validSpot) {
            TttGame.swapTurn();
            ai.game.recieveBoard(TttGame.getBoard());
            updateGameView(TttGame);

            //If someone has one stop the AI from playing
            if (!checkWinner()) {
                ai.TttAiTurn();
                TttGame.recieveBoard(ai.game.getBoard());
                updateGameView(TttGame);
            }

            //Disable player from doing anything if someone has won
            if (!checkWinner())
                TttGame.swapTurn();
        }


    }

    //Toasts if someone has won / tied and returns true if the game has ended, false otherwise
    private boolean checkWinner() {
        int winner = TttGame.checkWinner();
        if (winner == TttLogic.O) {
            Toast.makeText(getApplicationContext(), "O Won!", Toast.LENGTH_LONG).show();
            return true;
        } else if (winner == TttLogic.X) {
            Toast.makeText(getApplicationContext(), "X Won!", Toast.LENGTH_LONG).show();
            return true;
        } else if (winner == TttLogic.TIE) {
            Toast.makeText(getApplicationContext(), "Tie!", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    //Refreshes every button's image to correspond with the gameboard
    private void updateGameView(TttLogic game) {
        for (int i = 0; i < buttons.length; i++) {
            int piece = game.getBoardPiece(i / 3, i % 3);
            if (piece == TttLogic.X) {
                buttons[i].setImageResource(R.drawable.x);
            }
            if (piece == TttLogic.O) {
                buttons[i].setImageResource(R.drawable.o);
            }
            if (piece == TttLogic.OPEN) {
                buttons[i].setImageResource(R.drawable.b);
            }
        }
    }

    // Local player advertisement code
    // See: https://developers.google.com/nearby/connections/android/discover-devices
    // We may want to make a separate class for this later


    private final ConnectionLifecycleCallback mConnectionLifecycleCallback =
            new ConnectionLifecycleCallback() {

                @Override
                public void onConnectionInitiated(
                        String endpointId, ConnectionInfo connectionInfo) {
                    // Automatically accept the connection on both sides.
                    //Nearby.getConnectionsClient(this).acceptConnection(endpointId, mPayloadCallback);
                }

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    switch (result.getStatus().getStatusCode()) {
                        case ConnectionsStatusCodes.STATUS_OK:
                            // We're connected! Can now start sending and receiving data.
                            Toast toast = Toast.makeText(getApplicationContext(), "Connection Established", Toast.LENGTH_SHORT);
                            toast.show();
                            break;
                        case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                            // The connection was rejected by one or both sides.
                            Toast toast1 = Toast.makeText(getApplicationContext(), "Rejected Connection", Toast.LENGTH_SHORT);
                            toast1.show();
                            break;
                        case ConnectionsStatusCodes.STATUS_ERROR:
                            // The connection broke before it was able to be accepted.
                            Toast toast2 = Toast.makeText(getApplicationContext(), "Broken Connection", Toast.LENGTH_SHORT);
                            toast2.show();
                            break;
                    }
                }

                @Override
                public void onDisconnected(String endpointId) {
                    // We've been disconnected from this endpoint. No more data can be
                    // sent or received.
                }
            };

    private void startAdvertising() {
        Nearby.getConnectionsClient(this).startAdvertising(
                "TestNicknameChangeLater",
                Globals.getServiceId(),
                mConnectionLifecycleCallback,
                new AdvertisingOptions(Strategy.P2P_CLUSTER))
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unusedResult) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Successful Advert", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Unsuccessful Advert", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
    }
}
