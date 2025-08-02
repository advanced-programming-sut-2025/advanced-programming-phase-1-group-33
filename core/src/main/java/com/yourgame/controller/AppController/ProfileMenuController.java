package com.yourgame.controller.AppController;

import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.Result;
import com.yourgame.model.UserInfo.UserInfoChecking;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.persistence.UserDAO;
import com.yourgame.view.AppViews.AvatarMenuView;
import com.yourgame.view.AppViews.MainMenuView;

import java.sql.SQLException;

public class ProfileMenuController {
    private final static UserDAO userDAO = App.getUserDAO();

    public Result handleChangeUsername(String newUsername){
        if(newUsername.isEmpty()){
            return new Result(false, "Username field cannot be empty!");
        }
        else if(!UserInfoChecking.ValidName.matcher(newUsername)){
            return new Result(false, "Username is not a valid username!");
        }
        else if(newUsername.equals(App.getCurrentUser().getUsername())){
            return new Result(false, "The new username must be different!");
        }
        try {
            userDAO.updateUsername(App.getCurrentUser().getUsername(),newUsername);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        App.getCurrentUser().setUsername(newUsername);
        return new Result(true, "Username has been changed!");
    }

    public Result handleChangePassword(String newPassword,String oldPassword){
        if(newPassword.isEmpty()){
            return new Result(false, "New password field cannot be empty!");
        }
        else if(oldPassword.isEmpty()){
            return new Result(false, "Old password field cannot be empty!");
        }
        else if(!oldPassword.equals(App.getCurrentUser().getPassword())){
            return new Result(false, "Old password does not match!");
        }
        else if(!UserInfoChecking.StrongPassword.matcher(newPassword)){
            return new Result(false, "Password is not a strong password!");
        }
        else if(newPassword.equals(App.getCurrentUser().getPassword())){
            return new Result(false, "The new password must be different!");
        }
        App.getCurrentUser().setPassword(newPassword);
        return new Result(true, "Password has been changed!");
    }

    public Result handleChangeEmail(String newEmail){
        if(newEmail.isEmpty()){
            return new Result(false, "Email field cannot be empty!");
        }
        else if(!UserInfoChecking.ValidEmail.matcher(newEmail)){
            return new Result(false, "Email is not a valid email!");
        }
        else if(newEmail.equals(App.getCurrentUser().getEmail())){
            return new Result(false, "The new email must be different!");
        }
        App.getCurrentUser().setEmail(newEmail);
        return new Result(true, "Email has been changed!");
    }

    public Result handleChangeNickname(String newNickname){
        if(newNickname.isEmpty()){
            return new Result(false, "Nickname field cannot be empty!");
        }
        else if(newNickname.equals(App.getCurrentUser().getNickname())){
            return new Result(false, "The new nickname must be different!");
        }
        App.getCurrentUser().setNickname(newNickname);
        return new Result(true, "Nickname has been changed!");
    }

    public void handleSubmitButton(){
        UserDAO userDAO = App.getUserDAO();
        try {
            userDAO.updateUserById(App.getCurrentUser().getUsername(),App.getCurrentUser());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        App.setCurrentMenu(MenuTypes.MainMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView());
    }

    public void handleBackButton(){
        App.setCurrentMenu(MenuTypes.MainMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView());
    }

    public void handleAvatarButton(){
        App.setCurrentMenu(MenuTypes.AvatarMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new AvatarMenuView());
    }
}
