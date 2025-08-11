// core/src/main/java/com/yourgame/network/ClientConnectionManager.java
package com.yourgame.network;


public class ClientConnectionManager {
    private Client client;
    private Thread clientThread;

    public void startConnection(String serverIp, int serverPort) {
        if (clientThread != null && clientThread.isAlive()) {
            return; // Already connected
        }
        client = new Client(serverIp, serverPort);
        clientThread = new Thread(client);
        clientThread.start();
    }
    
    public void disconnect() {
        // TODO: Implement disconnection logic
    }
    
    public void sendDataToServer(String data) {
        if (client != null) {
            client.sendMessage(data);
        }
    }

	public void sendDataToServer(Object testPlayer) {
        client.sendData(testPlayer);
    }   

}