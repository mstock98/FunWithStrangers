package shaftware.funwithstrangers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class ticTacToe extends AppCompatActivity {

    Button ttt00, ttt01, ttt02,
            ttt10, ttt11, ttt12,
            ttt20, ttt21, ttt22;
    Button[] buttons = {ttt00, ttt01, ttt02, ttt10, ttt11, ttt12,
            ttt20, ttt21, ttt22};

    TttLogic TttGame = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        startAdvertising();

        //Creates on click listeners and everything for the TTT grid buttons
        for (int i = 0; i < buttons.length; i++) {
            int resID = getResources().getIdentifier(buttons[i].getText().toString(), "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    tttPressed(v);
                }
            });
        }

        //Initializing the TttGame
        TttGame = new TttLogic(TttLogic.X, true);
        TttGame.clearBoard();
        updateGameView(TttGame);
    }

    //Interface between button pressed and TttLogic
    private void tttPressed(View v) {

        Button pressed = findViewById(v.getId());

        boolean validSpot = false;

        while (!validSpot) {
            if (TttGame.isTurn()) {

                int row = -1, col = -1;

                switch (pressed.getId()) {
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

                validSpot = TttGame.pickSpot(row, col);
            }
        }
        //TttGame.swapTurn()
        updateGameView(TttGame);

    }

    private void updateGameView(TttLogic TttGame) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int piece = TttGame.getBoardPiece(i, j);
                if (piece == TttLogic.X) {
                    buttons[i].setText("X");
                }
                if (piece == TttLogic.O) {
                    buttons[i].setText("O");
                }
                if (piece == TttLogic.OPEN) {
                    buttons[i].setText("");
                }
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
                            break;
                        case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                            // The connection was rejected by one or both sides.
                            break;
                        case ConnectionsStatusCodes.STATUS_ERROR:
                            // The connection broke before it was able to be accepted.
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
                                // We're advertising!
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // We were unable to start advertising.
                            }
                        });
    }
}
