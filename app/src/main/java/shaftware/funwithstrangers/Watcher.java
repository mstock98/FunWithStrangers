package shaftware.funwithstrangers;

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;

public interface Watcher {
    public void onDiscover(String endpointId, DiscoveredEndpointInfo discoveredEndpointInfo);
    public void onLost(String endpointId);
}
