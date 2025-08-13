// core/src/main/java/com/yourgame/network/Client.java
package com.yourgame.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.yourgame.network.protocol.ResponseType;
import com.yourgame.network.protocol.ResponseWrapper;
import com.yourgame.network.protocol.Auth.LoginResponse;
import com.yourgame.network.protocol.Auth.SignupResponse;

public class Client implements Runnable {
    private final String serverIp;
    private final int serverPort;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in; // ADDED: To read messages from the server
    private final NetworkListener listener; // ADDED: To notify the game of events
    private final ResponseHolder responseHolder;

    private final Gson gson = new Gson();

    // UPDATED: The constructor now accepts the NetworkListener
    public Client(String serverIp, int serverPort, NetworkListener listener, ResponseHolder responseHolder) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.listener = listener;
        this.responseHolder = responseHolder;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(serverIp, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            // ADDED: Initialize the input reader
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Connected to server: " + serverIp);

            // ADDED: This loop constantly listens for messages from the server
            String fromServer;
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Received from server: " + fromServer);
                handleResponse(fromServer); // Process the received message
            }
        } catch (SocketException e) {
            System.err.println("Connection closed: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            // Optional: Notify listener of disconnection
            // if (listener != null) listener.received("disconnected");
        }
    }

    /**
     * ADDED: This new method deserializes the JSON from the server
     * and passes the resulting object to the listener.
     */
    private void handleResponse(String jsonResponse) {
        if (listener == null)
            return;

        try {

            ResponseWrapper wrapper = gson.fromJson(jsonResponse, ResponseWrapper.class);
            Object response = null;

            switch (wrapper.getType()) {
                case LOGIN_SUCCESS:
                case LOGIN_FAILURE:
                case USER_EXIST_SIGNUP: 
                case SIGNUP_SUCCESS:
                    response = gson.fromJson(wrapper.getPayload(), getResponseType(wrapper.getType()));
                    responseHolder.setResponse(response);
                    break;
                case SIGNUP_FAILURE:
                    response = gson.fromJson(wrapper.getPayload(), getResponseType(wrapper.getType()));
                    responseHolder.setResponse(response);
                    break;
                default:
                    break;
            }

        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing JSON from server: " + e.getMessage());
        }
    }

    // This method for sending data remains the same
    public void sendData(Object data) {
        if (out != null && !socket.isClosed()) {
            String jsonString = gson.toJson(data);
            out.println(jsonString);
        } else {
            System.out.println("Could not send data: Client not connected.");
        }
    }

    public void disconnect() {
        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Class<?> getResponseType(ResponseType type) {
        switch (type) {

            case LOGIN_SUCCESS:
            case LOGIN_FAILURE:
                return LoginResponse.class;
            case USER_EXIST_SIGNUP:
            case USER_NOTEXIST_SIGNUP:
            case SIGNUP_SUCCESS:
            case SIGNUP_FAILURE:
                return SignupResponse.class;

            default:
                return null;
        }
    }
}