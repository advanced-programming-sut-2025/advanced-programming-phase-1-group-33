// core/src/main/java/com/yourgame/network/GameNetworkHandler.java
package com.yourgame.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.network.protocol.Auth.*;
import com.yourgame.view.AppViews.LoginMenuView;
import com.yourgame.view.AppViews.MainMenuView;

/**
 * این کلاس مسئولیت مدیریت تمام پاسخ‌های دریافتی از سرور را بر عهده دارد.
 */
public class GameNetworkHandler implements NetworkListener {

    private final Main game;

    public GameNetworkHandler(Main game) {
        this.game = game;
    }

    @Override
    public void received(Object object) {
        if (object instanceof LoginResponse) {
            handleLoginResponse((LoginResponse) object);
        }
        else if (object instanceof SignupResponse) {
            handleSignupResponse((SignupResponse) object);
        }
    }

    private void handleLoginResponse(LoginResponse response) {
        if (response.isSuccess()) {
            // لاگین موفق بود
            Gdx.app.postRunnable(() -> {
                UserInfoDTO userDTO = response.getUser();
                // App.setCurrentUserFromDTO(userDTO); // این متد کمکی را باید در App بسازید
                App.setCurrentMenu(MenuTypes.MainMenu);
                game.getScreen().dispose();
                game.setScreen(new MainMenuView());
            });
        } else {
            // لاگین ناموفق بود، نمایش پیام خطا
            Gdx.app.postRunnable(() -> {
                Screen currentScreen = game.getScreen();
                if (currentScreen instanceof LoginMenuView) {
                    // ((LoginMenuView) currentScreen).showMessage(response.getMessage(), 0, 20);
                }
            });
        }
    }

    private void handleSignupResponse(SignupResponse response) {
        // منطق مشابه برای نمایش پیام موفقیت یا شکست ثبت‌نام
        Gdx.app.postRunnable(() -> {
            // کد نمایش پیام به کاربر در اینجا قرار می‌گیرد
            // برای مثال:
            // game.getCurrentScreenAs(SignupMenuView.class).showMessage(response.getMessage());
        });
    }
}