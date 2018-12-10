package shaftware.funwithstrangers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import static shaftware.funwithstrangers.TttLogicBase.Piece;
import static shaftware.funwithstrangers.TttLogicBase.Winner;

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

public class UltimateTicTacToe extends AppCompatActivity {

    UltimateTTTLogic UltimateTTTGame = null;
    boolean playerFirst; // who goes first, true is me, false is the other person
    boolean begin; // can we start playing
    Piece playerPIECE;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimate_tic_tac_toe);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private boolean checkWinner() {
        Winner winner = UltimateTTTGame.checkWinner();
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
}