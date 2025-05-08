package com.yourgame.controller.AppController;

import com.yourgame.model.*;
import com.yourgame.model.IO.*;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.model.enums.SecurityQuestion;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class LoginMenuController extends Controller {

    private static User userOfForgetPassword = null;
    private static String userPassword;
    public static boolean isWaitingForQuestion = false;
    public static boolean isProgramWaitingForAnswer = false;
    private static User userWaitingForQuestion = null;

    public static User getUserOfForgetPassword() {
        return userOfForgetPassword;
    }

    public static Response handleAccountRecovery(Request request) {
        User user = userOfForgetPassword;
        String newPass = request.command;
        if (hashPassword(newPass).equals(user.getPassword())) {
            return new Response(false, "Select a new password!");
        }

        if (newPass.compareToIgnoreCase("random") == 0) {
            newPass = createRandomPassword();
        } else {
            if (!validatePasswordFormat(newPass)) {
                return new Response(false, "Password Format is invalid!");
            }
            if (!validatePasswordSecurity(newPass).equals("Success")) {
                return new Response(false, "Password isn't secure! " +
                        validatePasswordSecurity(newPass));
            }
        }
        user.setPassword(hashPassword(newPass));
        //Save User
        userOfForgetPassword = null;
        App.setCurrentUser(user);
        App.setCurrentMenu(MenuTypes.MainMenu);
        return new Response(true, "Successfully logged in! Password updated to: " + newPass);
    }

    public static Response handleRegister(Request request, Scanner scanner) {
        String username = request.body.get("username");
        String password = request.body.get("password");
        String email = request.body.get("email");
        String passwordConfirm = request.body.get("passwordConfirm");
        String nickname = request.body.get("nickname");
        String gender = request.body.get("gender");
        if (!validateUsername(username)) {
            return new Response(false, "Username is invalid!");
        }

        if (password.equalsIgnoreCase(passwordConfirm) && password.compareToIgnoreCase("random") == 0) {
            password = createRandomPassword();
            passwordConfirm = password;
        } else {
            if (!validatePasswordFormat(password)) {
                return new Response(false, "Password Format is invalid!");
            }
            if (!validatePasswordSecurity(password).equals("Success")) {
                return new Response(false, "Password isn't secure! " +
                        validatePasswordSecurity(password));
            }
            if (!password.trim().equals(passwordConfirm.trim())) {
                return  new Response(false, "Passwords do not match! Password : " + password + " And pass to conf : " + passwordConfirm);
            }
        }
        if (!validateEmail(email)) {
            return new Response(false, "Email is invalid!");
        }
        User user = new User(username, hashPassword(password),email, nickname, gender);
        userWaitingForQuestion = user;
        isWaitingForQuestion = true;
        // Persist the new user using USERDao
        try {
            App.getUserDAO().saveUser(user);
            System.out.println("user saved");
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed: users.username")) {
                return new Response(false, "Username already exists!");
            }
            e.printStackTrace();
            return new Response(false, "An error occurred during registration.");
        }

        if (System.getenv("APP_MODE") != null && System.getenv("APP_MODE").equals("TEST")) {
            userPassword = password;
        }
        String message = "User created! Password is: " + password + "\n" +
                "Enter 'pick question -q <question number> -a <answer> -c <confirm answer>' to choose security question\n" +
                "You can enter 'list questions' command to see possible security questions\n";
        return new Response(true, message);
    }

    public static Response handlePickQuestion(Request request) {
        int questionNumber = Integer.parseInt(request.body.get("questionNumber"));
        String answer = request.body.get("answer");
        String answerConfirm = request.body.get("answerConfirm");
        if (questionNumber < 1 || questionNumber > 4) {
            return new Response(false, "Invalid question number!");
        }
        if (!answer.equals(answerConfirm)) {
            return new Response(false, "Answer doesn't match!");
        }
        User user = getUserWaitingForQuestion();
        user.setAnswer(answer);
        user.setQuestion(SecurityQuestion.values()[questionNumber - 1]);
//save user
        isWaitingForQuestion = false;
        userWaitingForQuestion = null;
        App.setCurrentMenu(MenuTypes.MainMenu);
        App.setCurrentUser(user);
        return new Response(true, "Question Picked! Logging in...");
    }

//    public static Response handleLogin(Request request)

//    public static Response handleForgetPassword(Request request)

    public static Response handleAnswer(Request request) {
        if (userOfForgetPassword == null) {
            return new Response(false, "You haven't entered your username.");
        }
        String answer = request.body.get("answer");
        User user = userOfForgetPassword;
        if (!answer.equals(user.getAnswer())) {
            userOfForgetPassword = null;
            isProgramWaitingForAnswer = false;
            return new Response(false, "Answer doesn't match!");
        }
        isProgramWaitingForAnswer = false;
        return new Response(true, "Your answer is correct; select your new password.");
    }

    public static Response handleListQuestions(Request request) {
        Response response = new Response();
        response.setSuccess(true);

        StringBuilder stringBuilder = new StringBuilder("List of questions:\n");
        int index = 1;
        for (SecurityQuestion question : SecurityQuestion.values()) {
            stringBuilder.append(index).append("- ").append(question).append("\n");
            index++;
        }
        response.setMessage(stringBuilder.toString());
        return response;
    }

    public static User getUserWaitingForQuestion() {
        return userWaitingForQuestion;
    }

    public static String getUserPassword() {
        return userPassword;
    }
    private static int thisCharCount(char ch, String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (ch == str.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    public static boolean validatePasswordFormat(String password) {
        String regex = "[a-zA-Z\\d?><,\"';:\\\\/|\\]\\[}{+=)(*@&^%$#!]+";
        return password.matches(regex);
    }

    public static String validatePasswordSecurity(String password) {
        if (password.length() < 8) {
            return "Password must be at least 8 characters";
        }
        Matcher matcher = Pattern.compile("[a-z]").matcher(password);
        if (!matcher.find()) {
            return "Password must contain a lowercase letter";
        }
        Matcher matcher2 = Pattern.compile("[0-9]").matcher(password);
        if (!matcher2.find()) {
            return "Password must contain a number";
        }
        Matcher matcher3 = Pattern.compile("[A-Z]").matcher(password);
        if (!matcher3.find()) {
            return "Password must contain a uppercase letter";
        }
        Matcher matcher1 = Pattern.compile("[?><,\"';:\\\\/|\\]\\[}{+=)(@*&^%$#!]").matcher(password);
        if (!matcher1.find()) {
            return "Password must contain a special character";
        }
        return "Success";
    }

    public static String createRandomPassword() {
        int length = (int) (Math.random() * 15 + 8);
        String password = "";
        password += (char) ((int) (Math.random() * 26) + 'A');
        password += (char) ((int) (Math.random() * 26) + 'a');
        password += (char) ((int) (Math.random() * 10) + '0');
        password += (char) ((int) (Math.random() * 14) + '!');

        for (int i = 4; i < length; i++) {
            password += (char) ((int) (Math.random() * 93) + '!');
        }
        return password;
    }

    public static boolean validateUsername(String username) {
        String regex = "^[a-zA-Z\\d-]+$";
        return username.matches(regex);
    }

    public static boolean validateEmail(String email) {
        String regex = "^(?<username>.+?)@(?<domain>.+?)\\.(?<tail>.+?)$";
        Matcher matcher = Pattern.compile(regex).matcher(email);
        if (!matcher.matches()) {
            return false;
        }
        String username = matcher.group("username");
        String domain = matcher.group("domain");
        String tail = matcher.group("tail");
        if (thisCharCount('@', email) != 1) {
            return false;
        }
        if (!username.matches("^[a-zA-Z\\d_.-]+$")) {
            return false;
        }
        if (email.contains("..")) {
            return false;
        }
        if (invalidFrontAndEndChars(username)) return false;

        if (!tail.matches("[a-zA-Z]{2,}")) {
            return false;
        }
        if (!domain.matches("[a-zA-Z\\d-]+")) {
            return false;
        }
        if (invalidFrontAndEndChars(domain)) return false;
        if (invalidFrontAndEndChars(tail)) return false;
        return true;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean invalidFrontAndEndChars(String string) {
        if (!string.substring(0, 1).matches("[a-zA-Z\\d]")
                || !string.substring(string.length() - 1).matches("[a-zA-Z\\d]")) {
            return true;
        }
        return false;
    }
}