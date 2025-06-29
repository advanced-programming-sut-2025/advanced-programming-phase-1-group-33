package com.yourgame.view.AppViews;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;

import com.yourgame.controller.AppController.LoginMenuController;
import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.UserInfo.User;
import com.yourgame.model.enums.Commands.LoginMenuCommands;
import com.yourgame.model.enums.Commands.MenuTypes;

import static com.yourgame.view.AppViews.AppView.scanner;

public class LoginMenu implements AppMenu {
    Matcher matcher = null;
    @Override
    public String getPreview() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------\n");
        sb.append("Login Menu Commands:\n");
        sb.append("1. Register: register -u <username> -p <password> -pc <passwordConfirm> -n <nickname> -e <email> -g <gender>\n");
        sb.append("5. Enter Menu: enter <menuname>\n");
        sb.append("6. Show Menu: show_menu\n");
        sb.append("8. Go Back: go back\n");
        sb.append("8. Exit: exit_menu\n");
        sb.append("----------------------------");
        return sb.toString();
    }

    @Override
    public Response handleMenu(String input, Scanner scanner) throws SQLException {

        LoginMenuCommands command = LoginMenuCommands.parse(input);
        if (command == null) {
            return getInvalidCommand();
        }

        switch (command) {
            case LOGIN:
                return getLogin(input);
            case EXIT_MENU:
                return getExitMenu(input);
            case ENTER_MENU:
                return getEnterMenu(input);
            case SHOW_MENU:
                return getShowMenu(input);
            case REGISTER:
                return getRegister(input, scanner);
            case GO_Back:
                App.setCurrentMenu(MenuTypes.MainMenu);
                return new Response(true, "Going Back to MainMenu");
            case FORGET:
                return forgetPassword(input);
            default:
                return getInvalidCommand();
        }
    }

    private Response forgetPassword(String input) throws SQLException {
        String username = LoginMenuCommands.FORGET.getGroup(input, "username");
        var result = LoginMenuController.askSecurityQuestion(username);

        if (result.getSuccessful()) {
            System.out.println("answer the question");
            User user = App.getUserDAO().loadUser(username);
            System.out.println(user.getSecurityQuestion().getQuestion());

            String answer = scanner.nextLine().trim();
            System.out.println(LoginMenuController.checkAnswerQuestion(username, answer));

            if (LoginMenuController.checkAnswerQuestion(username, answer).getSuccessful()) {
                System.out.println("now enter a new password");
                String newPassword = scanner.nextLine().trim();
                return (LoginMenuController.setNewPasswordAfterForgetPassword(username, newPassword));
            }
        }
        return result;
    }

    private Response getLogin(String input) {
        Request request = new Request(input);
        request.body.put("username", LoginMenuCommands.LOGIN.getGroup(input, "username"));
        request.body.put("password", LoginMenuCommands.LOGIN.getGroup(input, "password"));

        Response response = LoginMenuController.handleLogin(request);
        App.setCurrentMenu(MenuTypes.MainMenu);
        return response;

    }



    private static Response getExitMenu(String input) {
        Request request = new Request(input);
        Response response = LoginMenuController.handleExitMenu(request);
        return response;
    }

    private static Response getEnterMenu(String input) {
        Request request = new Request(input);
        request.body.put("menuName", LoginMenuCommands.ENTER_MENU.getGroup(input, "menuName"));
        Response response = LoginMenuController.handleEnterMenu(request);
        return response;
    }

    private static Response getShowMenu(String input) {
        Request request = new Request(input);
        Response response = LoginMenuController.handleShowMenu(request);
        return response;
    }

    private static Response getRegister(String input, Scanner scanner) {
        Request request = new Request(input);
        request.body.put("username", LoginMenuCommands.REGISTER.getGroup(input, "username"));
        request.body.put("password", LoginMenuCommands.REGISTER.getGroup(input, "password"));
        request.body.put("passwordConfirm", LoginMenuCommands.REGISTER.getGroup(input, "passwordConfirm"));
        request.body.put("nickname", LoginMenuCommands.REGISTER.getGroup(input, "nickname"));
        request.body.put("email", LoginMenuCommands.REGISTER.getGroup(input, "email"));
        request.body.put("gender", LoginMenuCommands.REGISTER.getGroup(input, "gender"));
        Response response = LoginMenuController.handleRegister(request, scanner);
        return response;
    }


}
