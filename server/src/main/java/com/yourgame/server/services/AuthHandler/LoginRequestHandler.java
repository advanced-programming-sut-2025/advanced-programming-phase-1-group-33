package com.yourgame.server.services.AuthHandler;

import com.yourgame.model.UserInfo.User;
import com.yourgame.network.protocol.ResponseType;
import com.yourgame.network.protocol.Auth.LoginRequest;
import com.yourgame.network.protocol.Auth.LoginResponse;
import com.yourgame.network.protocol.Auth.UserInfoDTO;
import com.yourgame.server.ClientHandler;
import com.yourgame.server.services.RequestHandler;
import com.yourgame.server.services.UserService;

public class LoginRequestHandler implements RequestHandler<LoginRequest> {
    private final UserService userService = new UserService(); // In a real app, inject this

    @Override
    public void handle(ClientHandler client, LoginRequest request) {

        User the_user = userService.findUserForPasswordRecovery(request.getUsername());

        ResponseType responseType = ResponseType.FAILURE;
        LoginResponse response;
        if (the_user != null) {
            responseType = ResponseType.LOGIN_SUCCESS;
            response = new LoginResponse(true, the_user.getPassword(), new UserInfoDTO(the_user));
        } else {
            responseType = ResponseType.LOGIN_FAILURE;
            response = new LoginResponse(false, "", new UserInfoDTO(the_user));
        }

        client.sendResponse(responseType, response);

    }
}