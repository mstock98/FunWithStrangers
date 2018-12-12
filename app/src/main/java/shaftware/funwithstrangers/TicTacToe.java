package shaftware.funwithstrangers;

import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.ImageButton;
import android.widget.Toast;

        import com.google.android.gms.nearby.Nearby;
        import com.google.android.gms.nearby.connection.AdvertisingOptions;
        import com.google.android.gms.nearby.connection.ConnectionInfo;
        import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
        import com.google.android.gms.nearby.connection.ConnectionResolution;
        import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
        import com.google.android.gms.nearby.connection.Payload;
        import com.google.android.gms.nearby.connection.PayloadCallback;
        import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
        import com.google.android.gms.nearby.connection.Strategy;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;

        import java.util.Random;

        import static shaftware.funwithstrangers.TttLogicBase.Piece;
        import static shaftware.funwithstrangers.TttLogicBase.Winner;
        import static shaftware.funwithstrangers.TttAi.Difficulty;

public class TicTacToe extends AppCompatActivity {

    ImageButton ttt00, ttt01, ttt02,
            ttt10, ttt11, ttt12,
            ttt20, ttt21, ttt22;
    ImageButton[] buttons = {ttt00, ttt01, ttt02, ttt10, ttt11, ttt12, ttt20, ttt21, ttt22};
    String[] buttonsID = {"ttt00", "ttt01", "ttt02", "ttt10", "ttt11", "ttt12", "ttt20", "ttt21", "ttt22"};

    TttLogic TttGame = null;
    TttAi ai = null;
    boolean playerFirst; // who goes first, true is me, false is the other person
    boolean begin; // can we start playing
    Piece playerPIECE;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
    }

    @Override
    protected void onStart(){
        super.onStart();

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
        //Initializing the TttGame
        //Who is x and who goes first must be decided at the constructor


        if(Globals.getOnline()){
            if(Globals.getHost()){
                playerFirst = (new Random()).nextBoolean();
                if(playerFirst){
                    playerPIECE = Piece.X;
                }else{

                    playerPIECE = Piece.O;
                }
                startAdvertising();

            }else{
                Nearby.getConnectionsClient(TicTacToe.this).requestConnection("swag", Globals.getEndPointID(), mConnectionLifecycleCallback)
                        .addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unusedResult) {
                                        // We successfully requested a connection. Now both sides
                                        // must accept before the connection is established.
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Nearby Connections failed to request the connection.
                                    }
                                });
            }

            TttGame = new TttLogic(playerPIECE, playerFirst);
            TttGame.clearBoard();
            updateGameView(TttGame);
        }






        //if we are offline, prepare for AI game
        if(!Globals.getOnline()){
            begin = true;
            Piece aiPIECE;
            playerFirst = (new Random()).nextBoolean(); // not chosen in the UI, randomly decided
            //****These are the variables to be chosen in the UI******
            Difficulty difficulty = Difficulty.IMPOSSIBLE;

            //Configure board for AI and Player
            if (playerFirst){
                playerPIECE = Piece.X;
                aiPIECE = Piece.O;
            } else{
                playerPIECE = Piece.O;
                aiPIECE = Piece.X;
            }
            //MyTurn always set to true for AI, even though it doesn't mean anything to the AI.
            ai = new TttAi(aiPIECE, true, difficulty, !playerFirst);
            TttGame = new TttLogic(playerPIECE, playerFirst);
            TttGame.clearBoard();
            updateGameView(TttGame);
            //AI takes its turn here if it is going first
            if (!playerFirst) {
                ai.game.receiveBoard(TttGame.getBoard());
                ai.TttAiTurn();
                TttGame.receiveBoard(ai.game.getBoard());
                updateGameView(TttGame);
                TttGame.swapTurn();
            }
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
            if(begin){

                //Game does not accept an invalid input and will wait for player to enter in something that is valid
                boolean validSpot = TttGame.pickSpot(row, col);// choose a location
                if(Globals.getConnected()){
                    if (validSpot) {
                        Toast toast = Toast.makeText(getApplicationContext(), "boop", Toast.LENGTH_SHORT);
                        toast.show();
                        TttGame.swapTurn();
                        getWindow().getDecorView().setBackgroundColor(Color.RED);
                        updateGameView(TttGame);
                        checkWinner();
                        sendBoard();
                    }
                }else{
                    if (validSpot) {
                        TttGame.swapTurn();
                        ai.game.receiveBoard(TttGame.getBoard());
                        updateGameView(TttGame);

                        //If someone has won stop the AI from playing
                        if (!checkWinner()) {
                            ai.TttAiTurn();
                            TttGame.receiveBoard(ai.game.getBoard());
                            updateGameView(TttGame);
                        }

                        //Disable player from doing anything if someone has won
                        if (!checkWinner())
                            TttGame.swapTurn();
                    }
                }
            }
        }
    }



    //Toasts if someone has won / tied and returns true if the game has ended, false otherwise
    private boolean checkWinner() {
        Winner winner = TttGame.checkWinner();
        if (winner == Winner.O) {
            Toast.makeText(getApplicationContext(), "O Won!", Toast.LENGTH_LONG).show();
            return true;
        } else if (winner == Winner.X) {
            Toast.makeText(getApplicationContext(), "X Won!", Toast.LENGTH_LONG).show();
            return true;
        } else if (winner == Winner.TIE) {
            Toast.makeText(getApplicationContext(), "Tie!", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    //Refreshes every button's image to correspond with the gameboard
    private void updateGameView(TttLogic game) {
        for (int i = 0; i < buttons.length; i++) {
            Piece piece = game.getBoardPiece(i / 3, i % 3);
            if (piece == Piece.X) {
                buttons[i].setImageResource(R.drawable.x);
            }
            if (piece == Piece.O) {
                buttons[i].setImageResource(R.drawable.o);
            }
            if (piece == Piece.OPEN) {
                buttons[i].setImageResource(R.drawable.blank);
            }
        }
    }

    // Local player advertisement code
    // See: https://developers.google.com/nearby/connections/android/discover-devices
    // We may want to make a separate class for this later

    private final PayloadCallback mPayloadCallback = new PayloadCallback() {
        @Override
        public void onPayloadReceived(@NonNull String s, @NonNull Payload payload) {
            Toast toast1 = Toast.makeText(getApplicationContext(),"got something", Toast.LENGTH_SHORT);
            toast1.show();
            byte[] b = payload.asBytes();
            if(begin){
                if(payload.getType() == Payload.Type.BYTES && b.length == 9){

                    Piece[][] board = new Piece[3][3];
                    for (int i = 0; i < 9; i++){
                        //board[i/2][i%2] = payload.asBytes()[i];
                        board[i/3][i%3] = Piece.values()[payload.asBytes()[i]];
                    }
                    TttGame.receiveBoard(board);
                    updateGameView(TttGame);
                    //Disable player from doing anything if someone has won, if no one has won, its this players turn
                    if (!checkWinner())
                        TttGame.swapTurn();
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }else{
                if(b.length == 1){
                    if(b[0] == 1){
                        playerFirst = true;
                        TttGame.swapTurn(); // Host says we are to go first
                    }
                }
                if(playerFirst){
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    playerPIECE = Piece.X;
                }else{
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    playerPIECE = Piece.O;
                }
                begin = true;
                TttGame = new TttLogic(playerPIECE, playerFirst);
                TttGame.clearBoard();
                updateGameView(TttGame);
            }

        }

        @Override
        public void onPayloadTransferUpdate(@NonNull String s, @NonNull PayloadTransferUpdate payloadTransferUpdate) {
            //don't need to track byte progress
        }
    };

    private final ConnectionLifecycleCallback mConnectionLifecycleCallback =
            new ConnectionLifecycleCallback() {

                @Override
                public void onConnectionInitiated(
                        String endpointId, ConnectionInfo connectionInfo) {
                    // Automatically accept the connection on both sides.
                    if(Globals.getHost()){
                        Nearby.getConnectionsClient(getApplicationContext()).stopAdvertising();
                        Globals.setEndPointID(endpointId);
                    }
                    Nearby.getConnectionsClient(getApplicationContext()).acceptConnection(endpointId, mPayloadCallback);
                }

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    switch (result.getStatus().getStatusCode()) {
                        case ConnectionsStatusCodes.STATUS_OK:
                            // We're connected! Can now start sending and receiving data.
                            Globals.setConnected(true);
                            if(Globals.getHost()){
                                byte[] b = new byte[1];
                                if(playerFirst){
                                    b[0] = 0;
                                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                                }else{
                                    b[0] = 1;
                                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                                }
                                Payload p = Payload.fromBytes(b);
                                Nearby.getConnectionsClient(getApplicationContext()).sendPayload(Globals.getEndPointID(), p);
                                begin = true;
                            }
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
                    Globals.setConnected(false);
                    Intent myIntent = new Intent(getApplicationContext(), titleScreen.class);
                    startActivity(myIntent);
                }
            };

    private void startAdvertising() {
        Nearby.getConnectionsClient(this).startAdvertising(
                Globals.getUsern(),
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
                                Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
    }

    private void sendBoard(){

        int[][] board = TttGame.getIntBoard();
        byte[] b = new byte[9];
        for (int i = 0; i < 9; i++){
            b[i] = (byte)board[i/3][i%3];
        }
        Payload p = Payload.fromBytes(b);
        Nearby.getConnectionsClient(getApplicationContext()).sendPayload(Globals.getEndPointID(), p);
    }

    @Override
    protected void onStop(){
        super.onStop();
        Nearby.getConnectionsClient(this).stopAdvertising();

        if(Globals.getEndPointID() != ""){
            Nearby.getConnectionsClient(this).disconnectFromEndpoint(Globals.getEndPointID());
            Globals.setEndPointID("");
        }
        Nearby.getConnectionsClient(this).stopAllEndpoints();
    }

}
