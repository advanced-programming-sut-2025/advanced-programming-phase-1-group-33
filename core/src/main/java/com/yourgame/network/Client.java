// core/src/main/java/com/yourgame/network/Client.java
package com.yourgame.network;

import java.io.IOException;
import java.net.Socket;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Client implements Runnable {
    private final String serverIp;
    private final int serverPort;
    private Socket socket;
    private PrintWriter out;
    // Use a GsonBuilder to exclude fields without the @Expose annotation
    private final Gson gson = new GsonBuilder()
                                    .excludeFieldsWithoutExposeAnnotation()
                                    .create();
    public Client(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(serverIp, serverPort);
            System.out.println("Connected to server: " + serverIp);

            out = new PrintWriter(socket.getOutputStream(), true);

            // TODO: Implement communication logic here (e.g., send/receive data)

        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
        }
    }

    // Add methods for sending data to the server
    public void sendMessage(String message) {
        // TODO: Implement sending logic
    }

    public void sendData(Object data) {
        if (out != null && socket != null && !socket.isClosed()) {
            String jsonString = gson.toJson(data);
            out.println(jsonString);
            System.out.println("sedned");
        }
        else{
            System.out.println("data was not sended :)‌ fuck ");
        }
        
    }
}