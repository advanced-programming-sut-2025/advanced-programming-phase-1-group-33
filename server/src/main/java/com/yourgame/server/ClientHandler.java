package com.yourgame.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import com.google.gson.Gson;
import com.yourgame.model.UserInfo.User;
import com.yourgame.network.protocol.RequestWrapper;
import com.yourgame.network.protocol.ResponseType;
import com.yourgame.network.protocol.ResponseWrapper;

import com.yourgame.network.protocol.Auth.ForgotPasswordRequest;
import com.yourgame.network.protocol.Auth.ForgotPasswordResponse;
import com.yourgame.network.protocol.Auth.LoginRequest;
import com.yourgame.network.protocol.Auth.LoginResponse;
import com.yourgame.network.protocol.Auth.SecurityAnswerRequest;
import com.yourgame.network.protocol.Auth.SecurityAnswerResponse;
import com.yourgame.network.protocol.Auth.SignupRequest;
import com.yourgame.network.protocol.Auth.UserInfoDTO;
import com.yourgame.server.services.UserService;
import com.yourgame.server.services.AuthHandler.LoginRequestHandler;
import com.yourgame.server.services.AuthHandler.SignUpRequestHandler;

// This class handles communication for a single client in a dedicated thread.
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Server server;
    private final UserService userService;
    private final Gson gson = new Gson();

    private PrintWriter out;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
        this.userService = new UserService();
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);

            String jsonRequest;

            while ((jsonRequest = reader.readLine()) != null) {
                System.out.println("Received from client " + clientSocket.getInetAddress() + ": " + jsonRequest);

                dispatchRequest(jsonRequest);
            }
        } catch (SocketException e) {
            System.err.println("Client disconnected from " + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        } finally {
            server.removeClient(this);
        }

    }

    private void dispatchRequest(String json) {
        try {
            RequestWrapper wrapper = gson.fromJson(json, RequestWrapper.class);
            String payload = wrapper.getPayload();

            switch (wrapper.getType()) {
                case LOGIN:
                    LoginRequest loginRequest = gson.fromJson(payload, LoginRequest.class);
                    handleLoginRequest(loginRequest);
                    break;
                case USER_EXIST:
                    ForgotPasswordRequest request = gson.fromJson(payload, ForgotPasswordRequest.class);

                    new SignUpRequestHandler().user_exist(this, request);
                    break;
                case SIGNUP:
                    SignupRequest signupRequest = gson.fromJson(payload, SignupRequest.class);
                    new SignUpRequestHandler().handle(this, signupRequest);
                    break;
                case GET_USER_PASSWORD:
                    LoginRequest get_PasswordRequest = gson.fromJson(payload, LoginRequest.class);
                    new LoginRequestHandler().handle(this, get_PasswordRequest);
                case FORGOT_PASSWORD:
                    ForgotPasswordRequest forgotRequest = gson.fromJson(payload, ForgotPasswordRequest.class);
                    handleForgotPasswordRequest(forgotRequest);
                    break;

                case SECURITY_ANSWER:
                    SecurityAnswerRequest answerRequest = gson.fromJson(payload, SecurityAnswerRequest.class);
                    handleSecurityAnswerRequest(answerRequest);
                    break;

                default:
                    System.err.println("Unknown request type received: " + wrapper.getType());
                    sendResponse(ResponseType.FAILURE, "Unknown Respones");

            }
        } catch (Exception e) {
            System.err.println("Error dispatching request: " + e.getMessage());
        }
    }

    private void handleLoginRequest(LoginRequest request) {
        User user = userService.loginUser(request.getUsername(), request.getPassword());
        ResponseType responseType = ResponseType.FAILURE;

        LoginResponse response;
        if (user != null) {
            UserInfoDTO userInfo = new UserInfoDTO(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getGender(),
                    user.getGender(),
                    user.getAvatar().getName(),
                    user.getSecurityQuestion().getQuestion(),
                    user.getAnswer());
            response = new LoginResponse(true, "Login successful!", userInfo);
            responseType = ResponseType.SUCCESSFUL;
        } else {
            response = new LoginResponse(false, "Invalid username or password.", null);
        }

        sendResponse(responseType, response);
    }

    private void handleForgotPasswordRequest(ForgotPasswordRequest request) {
        User user = userService.findUserForPasswordRecovery(request.getUsername());
        ForgotPasswordResponse response;

        ResponseType responseType = ResponseType.FAILURE;

        if (user != null) {
            response = new ForgotPasswordResponse(true, user.getSecurityQuestion().getQuestion());
            responseType = ResponseType.FAILURE;

        } else {
            response = new ForgotPasswordResponse(false, "User not found!");
        }
        sendResponse(responseType, response);
    }

    private void handleSecurityAnswerRequest(SecurityAnswerRequest request) {
        boolean isAnswerCorrect = userService.verifySecurityAnswer(request.getUsername(), request.getAnswer());
        SecurityAnswerResponse response;
        ResponseType responseType = ResponseType.FAILURE;
        if (isAnswerCorrect) {
            User user = userService.findUserForPasswordRecovery(request.getUsername());
            response = new SecurityAnswerResponse(true, user.getPassword());
            responseType = ResponseType.SUCCESSFUL;
        } else {
            response = new SecurityAnswerResponse(false, "Wrong answer!");
        }
        sendResponse(responseType, response);
    }

    public void sendResponse(ResponseType type, Object data) {
        if (out != null) {
            String payload = gson.toJson(data);
            ResponseWrapper wrapper = new ResponseWrapper(type, payload);
            String jsonResponse = gson.toJson(wrapper);
            out.println(jsonResponse);
            System.out.println("Sent to client: " + jsonResponse);
        }
    }

}