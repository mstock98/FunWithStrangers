package shaftware.funwithstrangers;

public interface Receiver {
     void receive(byte[] b);
     void onConnection();
     void onDisconnect();
}
