package shaftware.funwithstrangers;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MultiplayerClient {
   private boolean connected = false;

   public boolean getConnected(){
      return connected;
   }

    private boolean host;

    public boolean getHost(){
        return host;
    }

    public void setHost(boolean b){
        host = b;
    }

    private boolean online; //permission for ACCESS_COARSE_LOCATION has been granted

    public void setOnline(boolean s){
        online = s;
    }

    public Boolean getOnline(){
        return online;
    }

    private String endpointID = "";

    public void setEndPointID(String s){
        endpointID = s;
    }

    public String getEndPointID(){ return endpointID; }

    // A value that uniquely identifies the app. Used for multiplayer functionality
    public String getServiceId() {return "shaftware.funwithstrangers";}

    private String Usern = "Standard boi";

    public String getUsern(){
        return Usern;
    }

    public void setUsern(String s){
        Usern = s;
    }

    private Receiver receiver;

    private Watcher watcher;

    private Context C;

    public void startDiscovery(final Context c){
        Nearby.getConnectionsClient(c).startDiscovery(
                getServiceId(),
                mEndpointDiscoveryCallback,
                new DiscoveryOptions(Strategy.P2P_CLUSTER))
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unusedResult) {
                                // We're discovering!
                                //Toast.makeText(c, "discovering", Toast.LENGTH_SHORT).show();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // We were unable to start discovering.
                                //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    private final EndpointDiscoveryCallback mEndpointDiscoveryCallback =
            new EndpointDiscoveryCallback() {
                @Override
                public void onEndpointFound(String endpointId, DiscoveredEndpointInfo discoveredEndpointInfo) {
                    // We may want to make opponentList a list of key value pairs later
                    // (key = endpointId, value = discoveredEndpointInfo)
                    watcher.onDiscover(endpointId, discoveredEndpointInfo);
                }

                @Override
                public void onEndpointLost(String endpointId) {
                    watcher.onLost(endpointId);
                }
            };


    public void stopDiscovery(){
        Nearby.getConnectionsClient(C).stopDiscovery();
    }

    public void advertise(final Context c){
        int code = 0;
        switch(Globals.getMode()){
            case CHECKERS:
                code = 1;
                break;
            case TICTACTOE:
                code = 2;
                break;
            case ULTIMATETICTACTOE:
                code = 3;
                break;
            case HANGMAN:
                code = 4;
                break;
        }

      Nearby.getConnectionsClient(c).startAdvertising(
              Usern + code,
              "shaftware.funwithstrangers", // serviceId
              mConnectionLifecycleCallback,
              new AdvertisingOptions(Strategy.P2P_CLUSTER))
              .addOnSuccessListener(
                      new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void unusedResult) {
                            //Toast toast = Toast.makeText(c, "Successful Advert", Toast.LENGTH_SHORT);
                            //toast.show();
                         }
                      })
              .addOnFailureListener(
                      new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
//                            Toast toast = Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT);
//                            toast.show();
                         }
                      });
    }
    public void stopAdvert(Context c){
        Nearby.getConnectionsClient(c).stopAdvertising();
    }

    public void setContext(Context c){
        C = c;
    }

    public void connect(){
        Nearby.getConnectionsClient(C).requestConnection("swag", endpointID, mConnectionLifecycleCallback)
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

    public void disconnect(Context c){
        if( endpointID != ""){
            Nearby.getConnectionsClient(c).disconnectFromEndpoint(endpointID);
            endpointID = "";
        }
        Nearby.getConnectionsClient(c).stopAllEndpoints();
    }

    private final ConnectionLifecycleCallback mConnectionLifecycleCallback = new ConnectionLifecycleCallback() {
                @Override
                public void onConnectionInitiated(
                        String endpointId, ConnectionInfo connectionInfo) {
                    // Automatically accept the connection on both sides.
                    if(host){
                        Nearby.getConnectionsClient(C).stopAdvertising();
                        endpointID = endpointId;
                    }
                    Nearby.getConnectionsClient(C).acceptConnection(endpointId, mPayloadCallback);
                }

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    switch (result.getStatus().getStatusCode()) {
                        case ConnectionsStatusCodes.STATUS_OK:
                            connected = true;
                            receiver.onConnection();
                            break;
                        case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                            // The connection was rejected by one or both sides.
                            break;
                        case ConnectionsStatusCodes.STATUS_ERROR:
                            // The connection broke before it was able to be accepted.
                            break;
                        default:
                            // Unknown status code
                    }
                }
                @Override
                public void onDisconnected(String endpointId){
                    connected = false;
                   receiver.onDisconnect();
                }
            };

    public void sendPayload(byte[] b){
        Payload p = Payload.fromBytes(b);
        Nearby.getConnectionsClient(C).sendPayload(endpointID, p);
    }

    private final PayloadCallback mPayloadCallback = new PayloadCallback() {
        @Override
        public void onPayloadReceived(@NonNull String s, @NonNull Payload payload) {
            //Toast toast1 = Toast.makeText(C,"got something", Toast.LENGTH_SHORT);
           // toast1.show();
            byte[] b = payload.asBytes();
            receiver.receive(b);
        }

        @Override
        public void onPayloadTransferUpdate(@NonNull String s, @NonNull PayloadTransferUpdate payloadTransferUpdate) {
        }
    };

    public void setCallback(Receiver r){
        receiver = r;
    }

    public void setWatcher(Watcher w){
        watcher = w;
    }
}
