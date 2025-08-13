package com.yourgame.server.services.AuthHandler;

import com.yourgame.network.protocol.Auth.LoginRequest;
import com.yourgame.network.protocol.Auth.LoginResponse;
import com.yourgame.server.ClientHandler;
import com.yourgame.server.services.RequestHandler;
import com.yourgame.server.services.UserService;

public class LoginRequestHandler implements RequestHandler<LoginRequest> {
    private final UserService userService = new UserService(); // In a real app, inject this

    @Override
    public void handle(ClientHandler client, LoginRequest request) {
        // boolean success = userService.loginUser(request.getUsername(), request.getPassword());
        // LoginResponse response;
        // if (success) {
        //     // ... create successful response DTO
        //     response = new LoginResponse(true, "Welcome!", dto);
        // } else {
        //     response = new LoginResponse(false, "Invalid credentials.", null);
        // }
        // client.sendResponse(response); // Method in ClientHandler to send data back
    }
}