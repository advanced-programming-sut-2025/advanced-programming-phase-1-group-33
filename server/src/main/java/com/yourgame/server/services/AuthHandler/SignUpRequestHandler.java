package com.yourgame.server.services.AuthHandler;

import com.yourgame.model.UserInfo.User;
import com.yourgame.model.enums.Avatar;
import com.yourgame.model.enums.Gender;
import com.yourgame.model.enums.SecurityQuestion;
import com.yourgame.network.protocol.Auth.SignupRequest;
import com.yourgame.network.protocol.Auth.SignupResponse;
import com.yourgame.server.ClientHandler;
import com.yourgame.server.services.RequestHandler;
import com.yourgame.server.services.UserService;

public class SignUpRequestHandler implements RequestHandler<SignupRequest> {

    private final UserService userService = new UserService();

    @Override
    public void handle(ClientHandler client, SignupRequest request) {

        User newUser = new User(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getNickname(),
                Gender.valueOf(request.getGender()),
                SecurityQuestion.getQuestion(request.getSecurityQuestion()),
                request.getSecurityAnswer(), // Corrected from getAnswer() to getSecurityAnswer()
                Avatar.fromString(request.getAvatarName()));
        String message = userService.registerUser(newUser);
        SignupResponse response;

        if (message.startsWith("Success")) {
            // Registration was successful.
            response = new SignupResponse(true, message);
        } else {
            // Registration failed.
            response = new SignupResponse(false, message);
        }

        // Send the final response back to the client.
        client.sendResponse(response);
    }
}
