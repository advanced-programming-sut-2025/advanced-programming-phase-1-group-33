package com.yourgame.controller.AppController;

import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.Result;
import com.yourgame.model.UserInfo.User;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.persistence.UserDAO;
import com.yourgame.view.AppViews.LoginMenuView;
import com.yourgame.view.AppViews.MainMenuView;

import java.sql.SQLException;

public class LoginMenuController extends Controller {
    private LoginMenuView view;
    private User loggedInUser = null;

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    public void handleBackButton(){
        App.setCurrentMenu(MenuTypes.MainMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView());
    }

    public Result handleLoginButton(){
        String username = view.getUserInfo("username");
        String password = view.getUserInfo("password");

        if(username.isEmpty())
            return new Result(false, "Username is required!");
        else if(password.isEmpty())
            return new Result(false, "Password is required!");

        try {
            User user = App.getUserDAO().loadUser(username);
            if(user == null)
                return new Result(false, "User not found!");
            else{
                App.setCurrentUser(user);
                App.setCurrentMenu(MenuTypes.MainMenu);
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView());
                return new Result(true, "Login successful!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Result handleForgetPasswordButton(String username){
        if(username.isEmpty()){
            return new Result(false, "Username is required!");
        }

        UserDAO userDAO = App.getUserDAO();
        try {
            User user = userDAO.loadUser(username);
            if(user == null){
                return new Result(false, "User not found!");
            }
            else{
                loggedInUser = user;
                return new Result(true, user.getSecurityQuestion().getQuestion());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Result handleFindButton(String answer){
        if(answer.isEmpty()){
            return new Result(false, "Answer field is empty!");
        }

        else if(!loggedInUser.getAnswer().equals(answer)){
            return new Result(false, "Wrong answer!");
        }

        else {
            return new Result(true, loggedInUser.getPassword());
        }
    }
}
