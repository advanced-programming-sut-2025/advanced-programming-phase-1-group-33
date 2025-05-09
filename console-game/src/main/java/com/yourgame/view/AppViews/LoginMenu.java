package com.yourgame.view.AppViews;

import java.util.Scanner;

import com.yourgame.controller.AppController.LoginMenuController;
import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.LoginMenuCommands;
import com.yourgame.model.enums.Commands.MenuTypes;

public class LoginMenu implements AppMenu {

    @Override
    public String getPreview() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------\n");
        sb.append("Login Menu Commands:\n");
        sb.append(
                "1. Register: register -u <username> -p <password> -pc <passwordConfirm> -n <nickname> -e <email> -g <gender>\n");
        sb.append("2. List Questions: list_questions\n");
        sb.append("3. Pick Question: pick_question -q <questionNumber> -a <answer> -ac <answerConfirm>\n");
        sb.append("4. Answer: answer -a <answer>\n");
        sb.append("5. Enter Menu: enter <menuname>\n");
        sb.append("6. Show Menu: show_menu\n");
        sb.append("8. Go Back: go back\n");
        sb.append("8. Exit: exit_menu\n");
        sb.append("----------------------------");
        return sb.toString();
    }

    @Override
    public Response handleMenu(String input, Scanner scanner) {
        LoginMenuCommands command = LoginMenuCommands.parse(input);
        if (command == null) {
            return getInvalidCommand();
        }

        switch (command) {
            case LOGIN:
                return getLogin(input); 
            case LIST_QUESTIONS:
                return getListQuestions(input);
            case PICK_QUESTION:
                if (LoginMenuController.isWaitingForQuestion) {
                    return getPickQuestion(input);
                } else {
                    return getInvalidCommand();
                }
            case EXIT_MENU:
                return getExitMenu(input);
            case ENTER_MENU:
                return getEnterMenu(input);
            case SHOW_MENU:
                return getShowMenu(input);
            case ANSWER:
                return getAnswer(input);
            case REGISTER:
                return getRegister(input, scanner);
            case GO_Back:
                App.setCurrentMenu(MenuTypes.MainMenu);
                return new Response(true, "Going Back to MainMenu"); 
            default:
                // Special case for change password logic
                if (LoginMenuController.getUserOfForgetPassword() != null
                        && !LoginMenuController.isProgramWaitingForAnswer) {
                    return getChangePassword(input);
                }
                return getInvalidCommand();
        }
    }

    private Response getLogin(String input) {
        Request request = new Request(input);
        request.body.put("username", LoginMenuCommands.LOGIN.getGroup(input, "username"));
        request.body.put("password", LoginMenuCommands.LOGIN.getGroup(input, "password"));

        Response response = LoginMenuController.handleLogin(request);
        return response;
        
    }

    private static Response getChangePassword(String input) {
        Request request = new Request(input);
        Response response = LoginMenuController.handleAccountRecovery(request);
        return response;
    }

    private static Response getListQuestions(String input) {
        Request request = new Request(input);
        Response response = LoginMenuController.handleListQuestions(request);
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

    private static Response getPickQuestion(String input) {
        Request request = new Request(input);
        request.body.put("questionNumber", LoginMenuCommands.PICK_QUESTION.getGroup(input, "questionNumber"));
        request.body.put("answer", LoginMenuCommands.PICK_QUESTION.getGroup(input, "answer"));
        request.body.put("answerConfirm", LoginMenuCommands.PICK_QUESTION.getGroup(input, "answerConfirm"));
        Response response = LoginMenuController.handlePickQuestion(request);
        return response;
    }

    private static Response getAnswer(String input) {
        Request request = new Request(input);
        request.body.put("answer", LoginMenuCommands.ANSWER.getGroup(input, "answer"));
        Response response = LoginMenuController.handleAnswer(request);
        return response;
    }
}