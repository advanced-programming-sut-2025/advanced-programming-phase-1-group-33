package com.yourgame.controller.AppController;

import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.Result;
import com.yourgame.model.UserInfo.HandleStayedLoggedIn;
import com.yourgame.model.UserInfo.User;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.network.ResponseHolder;
import com.yourgame.network.protocol.RequestType;
import com.yourgame.network.protocol.Auth.ForgotPasswordRequest;
import com.yourgame.network.protocol.Auth.ForgotPasswordResponse;
import com.yourgame.network.protocol.Auth.LoginRequest;
import com.yourgame.network.protocol.Auth.LoginResponse;
import com.yourgame.network.protocol.Auth.SecurityAnswerRequest;
import com.yourgame.network.protocol.Auth.SecurityAnswerResponse;
import com.yourgame.network.protocol.Auth.SignupResponse;
import com.yourgame.persistence.UserDAO;
import com.yourgame.view.AppViews.LoginMenuView;
import com.yourgame.view.AppViews.MainMenuView;

import java.io.IOException;
import java.sql.SQLException;

public class LoginMenuController extends Controller {
    private LoginMenuView view;
    private User loggedInUser = null;

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    public void handleBackButton() {
        App.setCurrentMenu(MenuTypes.MainMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView());
    }

    public Result handleLoginButton(boolean isStayLoggedInActive) {

        String username = view.getUserInfo("username");
        String password = view.getUserInfo("password");

        if (username.isEmpty()) {
            return new Result(false, "Username is required!");
        }
        if (password.isEmpty()) {
            return new Result(false, "Password is required!");
        }

        // LoginRequest request = new LoginRequest(username, password);
        // Main.getMain().getConnectionManager().sendDataToServer(RequestType.LOGIN,
        // request);

        if (user_exist(username) == false) {
            return new Result(false, "User not found!");
        }
        String userPassword = get_user_password(username);
        System.out.println(userPassword);
        if (!userPassword.equals(password)) {
            return new Result(false, "Wrong password!");
        }

        User user = getUser(username);
        System.out.println(user.getUsername());
        if (isStayLoggedInActive) {

            try {
                HandleStayedLoggedIn.getInstance().addUser(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        App.setCurrentUser(user);
        App.setCurrentMenu(MenuTypes.MainMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView());
        return new Result(true, "Login successful!");
    }

    public Result handleForgetPasswordButton() {
        String username = view.getUserInfo("username");
        if (username.isEmpty())
            return new Result(false, "Username is required!");

        ForgotPasswordRequest request = new ForgotPasswordRequest(username);
        Main.getMain().getConnectionManager().sendDataToServer(RequestType.FORGOT_PASSWORD, request);

        if (!user_exist(username)){
            return new Result(false, "User not Exist"); 

        }


        loggedInUser = getUser(username); 

        
        try {
            ResponseHolder holder = Main.getMain().getResponseHolder();
            Object responseObject = holder.getResponse(5000);

            if (responseObject instanceof ForgotPasswordResponse) {
                ForgotPasswordResponse response = (ForgotPasswordResponse) responseObject;

                return new Result(response.isSuccess(), response.getMessage());
            } else {
                return new Result(false, "Error: Did not receive a valid response for password recovery.");
            }
        } catch (InterruptedException e) {
            return new Result(false, "Error: Process interrupted.");
        }
    }

    public Result handleFindButton(String answer) {
        if (answer.isEmpty()) {

            return new Result(false, "Answer field is empty!");
        }
        String username = view.getUserInfo("username");

        SecurityAnswerRequest request = new SecurityAnswerRequest(username, answer);
        Main.getMain().getConnectionManager().sendDataToServer(RequestType.SECURITY_ANSWER, request);

        try {
            ResponseHolder holder = Main.getMain().getResponseHolder();
            Object responseObject = holder.getResponse(5000);

            if (responseObject instanceof SecurityAnswerResponse) {
                SecurityAnswerResponse response = (SecurityAnswerResponse) responseObject;

                return new Result(response.isSuccess(), response.getMessage());
            } else {
                return new Result(false, "Error: Did not receive a valid response for the security answer.");
            }
        } catch (InterruptedException e) {
            return new Result(false, "Error: Process interrupted.");
        }
    }

    // public Result handleFindButton(String answer){
    // if(answer.isEmpty()){
    // return new Result(false, "Answer field is empty!");
    // }

    // else if(!loggedInUser.getAnswer().equals(answer)){
    // return new Result(false, "Wrong answer!");
    // }

    // else {
    // return new Result(true, loggedInUser.getPassword());
    // }
    // }
    private User getUser(String username) {
        LoginRequest request = new LoginRequest(username, "pass");
        Object response = null;

        try {
            response = Main.getMain().getConnectionManager()
                    .sendRequestAndWaitForResponse(RequestType.GET_USER_PASSWORD, request, 1000);
            if (response instanceof LoginResponse) {
                LoginResponse loginResponse = (LoginResponse) response;

                if (loginResponse.isSuccess()) {
                    System.out.println(loginResponse.getUser().getGender());
                    return new User(loginResponse.getUser());
                } else {
                    return null;
                }
            }

        } catch (Exception e) {
            System.out.println(e);

        }

        return null;
    }

    private String get_user_password(String username) {
        LoginRequest request = new LoginRequest(username, "pass");
        Object response = null;

        try {
            response = Main.getMain().getConnectionManager()
                    .sendRequestAndWaitForResponse(RequestType.GET_USER_PASSWORD, request, 1000);
            System.out.println(response);
            if (response instanceof LoginResponse) {
                LoginResponse loginResponse = (LoginResponse) response;

                if (loginResponse.isSuccess()) {
                    return loginResponse.getUser().getPassword();
                } else {
                    return loginResponse.getMessage();
                }
            }

        } catch (Exception e) {
            System.out.println(e);

        }

        return null;
    }

    private String get_user_Answer(String username) {
        LoginRequest request = new LoginRequest(username, "pass");
        Object response = null;

        try {
            response = Main.getMain().getConnectionManager()
                    .sendRequestAndWaitForResponse(RequestType.GET_USER_PASSWORD, request, 1000);
            System.out.println(response);
            if (response instanceof LoginResponse) {
                LoginResponse loginResponse = (LoginResponse) response;

                if (loginResponse.isSuccess()) {
                    return loginResponse.getUser().getAnswer();
                } else {
                    return loginResponse.getMessage();
                }
            }

        } catch (Exception e) {
            System.out.println(e);

        }

        return null;
    }

    private String get_user_sequrity(String username) {
        LoginRequest request = new LoginRequest(username, "pass");
        Object response = null;

        try {
            response = Main.getMain().getConnectionManager()
                    .sendRequestAndWaitForResponse(RequestType.GET_USER_PASSWORD, request, 1000);
            System.out.println(response);
            if (response instanceof LoginResponse) {
                LoginResponse loginResponse = (LoginResponse) response;

                if (loginResponse.isSuccess()) {
                    return loginResponse.getUser().getQuestion();
                } else {
                    return loginResponse.getMessage();
                }
            }

        } catch (Exception e) {
            System.out.println(e);

        }

        return null;
    }

    private static boolean user_exist(String username) {

        ForgotPasswordRequest request = new ForgotPasswordRequest(username);
        Object response = null;

        try {
            response = Main.getMain().getConnectionManager()
                    .sendRequestAndWaitForResponse(RequestType.USER_EXIST, request, 1000);
            System.out.println(response);
            if (response instanceof SignupResponse) {
                SignupResponse signupResponse = (SignupResponse) response;

                if (signupResponse.isSuccess()) {
                    return true;
                } else
                    return false;
            }

        } catch (Exception e) {
            System.out.println(e);

        }

        return false;
    }

}
