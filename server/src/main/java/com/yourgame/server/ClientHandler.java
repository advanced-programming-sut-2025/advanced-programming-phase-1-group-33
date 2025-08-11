package com.yourgame.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// This class handles communication for a single client in a dedicated thread.
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Server server;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (
            // Use a BufferedReader to read data sent from the client line by line.
            // This is matched with the PrintWriter.println() on the client side.
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String message;
            // The while loop continuously reads messages from the client until the connection is closed.
            while ((message = reader.readLine()) != null) {
                // Print the received message to the server's console.
                System.out.println("Received from client: " + message);
                // Here is where you would process the message, update the game state,
                // and potentially broadcast the changes to other clients.
            }
        } catch (IOException e) {
            // This is a normal scenario when a client disconnects.
            System.err.println("Client disconnected from " + clientSocket.getInetAddress());
        } finally {
            try {
                // Ensure the client socket is closed cleanly.
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket for client " + clientSocket.getInetAddress());
            }
            // Remove the handler from the server's list of active clients.
            server.removeClient(this);
        }
    }
}