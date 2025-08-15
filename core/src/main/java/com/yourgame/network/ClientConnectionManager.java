// core/src/main/java/com/yourgame/network/ClientConnectionManager.java
package com.yourgame.network;

import com.google.gson.Gson;
import com.yourgame.network.protocol.RequestType;
import com.yourgame.network.protocol.RequestWrapper;
import com.yourgame.network.protocol.Auth.CreateLobbyRequest;
import com.yourgame.network.protocol.Auth.LoginRequest;
import com.yourgame.network.protocol.Auth.SignupRequest;
import com.yourgame.network.protocol.Auth.Lobby.JoinLobbyRequest;
import com.yourgame.network.protocol.Auth.Lobby.LeaveLobbyRequest;
import com.yourgame.network.protocol.Auth.Lobby.SearchLobbyRequest;

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

        client = new Client(serverIp, serverPort, listener, this.responseHolder);
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

    public void sendDataToServer(RequestType type, Object data) {
        if (client != null) {

            String payload = gson.toJson(data);

            RequestWrapper wrapper = new RequestWrapper(type, payload);
            client.sendData(wrapper);
        }
    }
    public void sendRequest(Object data) {
        if (client == null) {
            System.err.println("Client is not connected. Cannot send request.");
            return;
        }

        RequestType type = null;
        // اضافه کردن بررسی برای انواع درخواست‌های لابی و سایر موارد
        if (data instanceof SignupRequest) {
            type = RequestType.SIGNUP;
        } else if (data instanceof LoginRequest) {
            type = RequestType.LOGIN;
        } else if (data instanceof CreateLobbyRequest) {
            type = RequestType.CREATE_LOBBY;
        } else if (data instanceof JoinLobbyRequest) {
            type = RequestType.JOIN_LOBBY;
        } else if (data instanceof SearchLobbyRequest) {
            type = RequestType.SEARCH_LOBBY;
        }
        else if (data instanceof RequestType){
            type = (RequestType)  data; 
        }
        else if (data instanceof LeaveLobbyRequest) {
            type = RequestType.LEAVE_LOBBY;
        } else if (data.getClass().getSimpleName().equals("REFRESH_LOBBIES")) {
            // برای RequestType های بدون payload
            type = RequestType.REFRESH_LOBBIES;
        }

        if (type != null) {
            String payload = gson.toJson(data);
            RequestWrapper wrapper = new RequestWrapper(type, payload);
            client.sendData(wrapper);
        } else {
            System.err.println("Unsupported request type: " + data.getClass().getName());
        }
    }

    public Object sendRequestAndWaitForResponse(RequestType type, Object data, long timeoutMillis) throws InterruptedException {
        if (client == null) {
            System.err.println("Client is not connected. Cannot send request.");
            return null;
        }

        if (type == null) {
            System.err.println("Request type cannot be null.");
            return null;
        }

        String payload = gson.toJson(data);
        RequestWrapper wrapper = new RequestWrapper(type, payload);
        client.sendData(wrapper);

        return responseHolder.getResponse(timeoutMillis);
     }

}