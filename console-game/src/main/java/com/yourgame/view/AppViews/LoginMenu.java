package com.yourgame.view.AppViews;

import com.yourgame.controller.AppController.LoginMenuController;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.LoginMenuCommands;


public class LoginMenu implements AppMenu {

    public void handleMenu(String input) {
        Response response = null;
        if (LoginMenuCommands.LIST_QUESTIONS.matches(input)) {
            response = getListQuestions(input);
        } else if (LoginMenuController.isWaitingForQuestion) {
            if (LoginMenuCommands.PICK_QUESTION.matches(input)) {
                response = getPickQuestion(input);
            } else {
                response = getInvalidCommand();
            }
        } else if (LoginMenuController.getUserOfForgetPassword() != null && !LoginMenuController.isProgramWaitingForAnswer) {
            response = getChangePassword(input);
        } else if (LoginMenuCommands.EXIT_MENU.matches(input)) {
            response = getExitMenu(input);
        } else if (LoginMenuCommands.ENTER_MENU.matches(input)) {
            response = getEnterMenu(input);
        } else if (LoginMenuCommands.SHOW_MENU.matches(input)) {
            response = getShowMenu(input);
        } else if (LoginMenuCommands.ANSWER.matches(input)) {
            response = getAnswer(input);
        } else if (LoginMenuCommands.REGISTER.matches(input)) {
            response = getRegister(input);
        } else {
            response = getInvalidCommand();
        }
        printResponse(response);
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

    private static Response getRegister(String input) {
        Request request = new Request(input);
        request.body.put("username", LoginMenuCommands.REGISTER.getGroup(input, "username"));
        request.body.put("password", LoginMenuCommands.REGISTER.getGroup(input, "password"));
        request.body.put("passwordConfirm", LoginMenuCommands.REGISTER.getGroup(input, "passwordConfirm"));
        request.body.put("nickname", LoginMenuCommands.REGISTER.getGroup(input, "nickname"));
        request.body.put("email", LoginMenuCommands.REGISTER.getGroup(input, "email"));
        request.body.put("gender", LoginMenuCommands.REGISTER.getGroup(input, "gender"));
        Response response = LoginMenuController.handleRegister(request);
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