// core/src/main/java/com/yourgame/network/ClientTester.java
package com.yourgame.network;

import com.yourgame.model.UserInfo.Player;

public class ClientTester {
    public static void main(String[] args) {
        String serverIp = "localhost"; // IP address of the server
        int serverPort = 8080; // Port the server is listening on

        System.out.println("Starting client tester...");
        ClientConnectionManager connectionManager = new ClientConnectionManager();
        connectionManager.startConnection(serverIp, serverPort);

        try {
            // Simulate some time to allow the client thread to connect and run
            Thread.sleep(5000);

            Player testPlayer = Player.guest();
            // You can add logic to send a test message here
            connectionManager.sendDataToServer(testPlayer);
            System.out.println("sent data");
            connectionManager.sendDataToServer("Hello from test client!");

            // Allow more time for communication
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }  
        finally {
            // Disconnect the client
            connectionManager.disconnect();
            System.out.println("Client tester finished.");
        }
    }
}