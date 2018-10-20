package shaftware.funwithstrangers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class ticTacToe extends AppCompatActivity {

    Button ttt00, ttt01, ttt02,
           ttt10, ttt11, ttt12,
           ttt20, ttt21, ttt22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        startAdvertising();

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
