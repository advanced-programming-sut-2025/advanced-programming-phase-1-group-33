// core/src/main/java/com/yourgame/network/ClientConnectionManager.java
package com.yourgame.network;

import com.google.gson.Gson;
import com.yourgame.network.protocol.RequestType;
import com.yourgame.network.protocol.RequestWrapper;

public class ClientConnectionManager {
    private Client client;
    private Thread clientThread;
    private final ResponseHolder responseHolder;
    private final Gson gson = new Gson();

    public ClientConnectionManager(ResponseHolder responseHolder) {
        this.responseHolder = responseHolder;
    }

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

    public void sendDataToServer(RequestType type  , Object data) {
        if (client != null) {

            String payload = gson.toJson(data);

            RequestWrapper wrapper = new RequestWrapper(type, payload);
            client.sendData(wrapper); 
        }
    }

}