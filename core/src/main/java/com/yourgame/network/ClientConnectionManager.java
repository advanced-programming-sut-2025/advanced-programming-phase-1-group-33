// core/src/main/java/com/yourgame/network/ClientConnectionManager.java
package com.yourgame.network;

public class ClientConnectionManager {
    private Client client;
    private Thread clientThread;

    public void startConnection(String serverIp, int serverPort, NetworkListener listener) {
        if (clientThread != null && clientThread.isAlive()) {
            return; // Already connected
        }
        client = new Client(serverIp, serverPort, listener);
        clientThread = new Thread(client);
        clientThread.start();
    }

    public void disconnect() {
        if (client != null) {
            client.disconnect(); // متد جدیدی که در Client اضافه کردیم را صدا می‌زند
        }
        if (clientThread != null && clientThread.isAlive()) {
            clientThread.interrupt(); // ترد را متوقف می‌کند
        }
    }

    public void sendDataToServer(Object data) {
        if (client != null) {
            client.sendData(data);
        }
    }

}