// // core/src/main/java/com/yourgame/network/ClientTester.java
// package com.yourgame.network;

// import com.yourgame.model.UserInfo.Player;

// public class ClientTester {
//     public static void main(String[] args) {
//         String serverIp = "localhost";
//         int serverPort = 8080;

//         System.out.println("Starting client tester...");
//         // ساخت یک Listener ساده برای تست که فقط پیام‌ها را چاپ می‌کند
//         NetworkListener testListener = new NetworkListener() {
//             @Override
//             public void received(Object object) {
//                 System.out.println("Test Listener Received: " + object.toString());
//             }
//         };

//         ClientConnectionManager connectionManager = new ClientConnectionManager();
//         // پاس دادن listener به متد startConnection
//         connectionManager.startConnection(serverIp, serverPort, testListener);

//         try {
//             Thread.sleep(2000); // زمان برای برقراری اتصال

//             // ارسال یک آبجکت Player برای تست
//             Player testPlayer = Player.guest();
//             connectionManager.sendDataToServer(testPlayer);
//             System.out.println("Sent test player data.");

//             Thread.sleep(2000);

//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         } finally {
//             connectionManager.disconnect();
//             System.out.println("Client tester finished.");
//         }
//     }
// }