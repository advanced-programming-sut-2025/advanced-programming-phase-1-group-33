package com.yourgame.controller.AppController;

import com.badlogic.gdx.Gdx;
import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.Result;
import com.yourgame.model.UserInfo.User;
import com.yourgame.model.UserInfo.UserInfoChecking;
import com.yourgame.model.enums.Avatar;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.network.protocol.RequestType;
import com.yourgame.network.protocol.ResponseWrapper;
import com.yourgame.network.protocol.Auth.ForgotPasswordRequest;
import com.yourgame.network.protocol.Auth.SignupRequest;
import com.yourgame.network.protocol.Auth.SignupResponse;
import com.yourgame.model.enums.Gender;
import com.yourgame.model.enums.SecurityQuestion;
import com.yourgame.persistence.UserDAO;
import com.yourgame.view.AppViews.MainMenuView;
import com.yourgame.view.AppViews.SignupMenuView;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SignUpMenuController {
    private SignupMenuView view;

    private static final SecureRandom random = new SecureRandom();

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIALS = "?><,\"';:\\/[]{}|=+()-*&^%$#!";
    private static final String ALL = LOWERCASE + UPPERCASE + NUMBERS + SPECIALS;

    public void setView(SignupMenuView view) {
        this.view = view;
    }

    public void handleBackButton() {
        App.setCurrentMenu(MenuTypes.MainMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView());
    }

    public Result handleSignUpButton() {
        String username = view.getUserInfo("username");
        String password = view.getUserInfo("password");
        String confirmPassword = view.getUserInfo("confirmPassword");
        String email = view.getUserInfo("email");
        String nickname = view.getUserInfo("nickname");
        Gender gender = view.getGender();

        if (username.isEmpty()) {
            return new Result(false, "Username field is empty!");
        } else if (password.isEmpty()) {
            return new Result(false, "Password field is empty!");
        } else if (confirmPassword.isEmpty()) {
            return new Result(false, "Password confirming field is empty!");
        } else if (email.isEmpty()) {
            return new Result(false, "Email field is empty!");
        } else if (nickname.isEmpty()) {
            return new Result(false, "Nickname field is empty!");
        } else if (!UserInfoChecking.ValidName.matcher(username)) {
            return new Result(false, "Username is not valid!");
        } else if (!UserInfoChecking.StrongPassword.matcher(password)) {
            return new Result(false, validatePassword(password));
        } else if (!password.equals(confirmPassword)) {
            return new Result(false, "Passwords do not match!");
        } else if (!UserInfoChecking.ValidEmail.matcher(email)) {
            return new Result(false, "Email is invalid!");
        }

        UserDAO userDAO = App.getUserDAO();
        User existingUser = null;

        // try {
        // existingUser = userDAO.loadUser(username);
        // } catch (Exception e) {
        // throw new RuntimeException(e);
        // }

        if (user_exist(username)) {
            try {
                while (true) {
                    if (username.length() == 8)
                        username = username.substring(0, 7);
                    username = username + NUMBERS.charAt(random.nextInt(NUMBERS.length()));
                    if (user_exist(username) == false) {
                        return new Result(false, "There exists a user with that username!\n"
                                + "you can use : " + username);
                    }
                }
                // if (userDAO.loadUser(username) == null)
                // return new Result(false, "There exists a user with that username!\n"
                // + "you can use : " + username);
                // }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return new Result(true, "");
    }

    public Result handleSecurityAnswer() {
        String username = view.getUserInfo("username");
        String password = view.getUserInfo("password");
        String email = view.getUserInfo("email");
        String answer = view.getUserInfo("security answer");
        String nickname = view.getUserInfo("nickname");
        SecurityQuestion question = view.getSecurityQuestion();
        Gender gender = view.getGender();
        // Avatar avatar = view.getAvatar(); // Assuming you have a getAvatar() method
        // in your view

        if (answer.isEmpty()) {
            return new Result(false, "Security answer field is empty!");
        }

        SignupRequest signupRequest = new SignupRequest(
                username,
                password,
                email,
                nickname,
                gender.name(), // Assuming gender.name() returns the string representation
                question.getQuestion(), // Use getQuestion() to get the question string
                answer,
                Avatar.Sam.getName() // Assuming avatar.getName() returns the name string
        );

        Object response = null;
        try {
            response = (Main.getMain().getConnectionManager().sendRequestAndWaitForResponse(RequestType.SIGNUP,
                    signupRequest, 1000));
            // ResponseWrapper wrapper = gson.fromJson(response, ResponseWrapper.class);
            Gdx.app.log("signpu controlelr", "Response recived ");
        } catch (Exception e) {
            System.out.println(e);
        }

        if (response instanceof SignupResponse) {
            SignupResponse signupResponse = (SignupResponse) response;
            if (signupResponse.isSuccess()) {
                System.out.println(response);

            } else {
                Gdx.app.log("signpu controlelr", "I was not controlled ");
                return new Result(false, signupResponse.getMessage());
            }
            Gdx.app.log("signpu controlelr", "hiii I'm I was successful ");

            App.setCurrentMenu(MenuTypes.MainMenu);
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MainMenuView());
            return new Result(true, "Signed up successfully...");
        }

        return new Result(true, "Signed up Canceld...");

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

    private static String validatePassword(String password) {
        if (password.length() < 8)
            return ("Password must be at least 8 characters long!");
        if (!password.matches(".*[a-z].*"))
            return ("Password must include a lowercase letter!");
        if (!password.matches(".*[A-Z].*"))
            return ("Password must include an uppercase letter!");
        if (!password.matches(".*\\d.*"))
            return ("Password must include a digit!");
        if (!password.matches(".*[?><,\"';:\\\\/\\[\\]{}|=+()\\-*&^%$#!].*"))
            return ("Password must include a special character!");
        return null;
    }

    public String generateRandomPassword() {
        int length = random.nextInt(6) + 8;
        List<Character> password = new ArrayList<>();

        password.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.add(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        password.add(SPECIALS.charAt(random.nextInt(SPECIALS.length())));

        for (int i = 4; i < length; i++) {
            password.add(ALL.charAt(random.nextInt(ALL.length())));
        }

        Collections.shuffle(password);

        StringBuilder sb = new StringBuilder();
        for (char c : password) {
            sb.append(c);
        }

        return sb.toString();
    }
}
