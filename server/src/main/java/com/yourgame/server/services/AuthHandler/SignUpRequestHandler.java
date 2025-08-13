package com.yourgame.server.services.AuthHandler;

import com.yourgame.model.UserInfo.User;
import com.yourgame.network.protocol.Auth.SignupRequest;
import com.yourgame.server.ClientHandler;
import com.yourgame.server.services.RequestHandler;

public class SignUpRequestHandler implements RequestHandler<SignupRequest> {

    @Override
    public void handle(ClientHandler client, SignupRequest request) {
        
        User newUser = new User(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getNickname(),
                request.getGender(),
                request.getSecurityQuestion(),
                request.getAnswer(),
                request.getAvatar());
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

}
