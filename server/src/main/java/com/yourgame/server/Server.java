package com.yourgame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Server {

    private final int port;
    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("Client disconnected. Active clients: " + clients.size());
    }

    public static void main(String[] args) {
        int port = 8080;
        Server server = new Server(port);
        server.start();
    }
}

// ClientHandler.java (to be implemented)
// This class will handle communication for a single client.
class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Server server;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            // Implement logic for communication with the client here
            // Example: reading from and writing to the clientSocket streams
            
            // For now, let's just simulate some work and then disconnect.
            Thread.sleep(5000); // Simulate client session
        } catch (InterruptedException e) {
            System.err.println("Client handler interrupted for " + clientSocket.getInetAddress());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket for client " + clientSocket.getInetAddress());
            }
            server.removeClient(this);
        }
    }
}