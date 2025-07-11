package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.yourgame.Main;

public class GameScreen  implements Screen{
    private Main game; 
    public GameScreen(){
        this.game = Main.getMain();
    }
    @Override
    public void show() {}

        @Override
    public void render(float delta) {
        // This is the main game loop
        // Clear the screen
        Gdx.gl.glClearColor(0.1f, 0.25f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 1. Handle player input & update logic
        // 2. Update camera

        // 3. Draw the game world (map, player, etc.)
        Main.getBatch().begin();
        // ... drawing calls go here ...
        Main.getBatch().end();

        // 4. Draw the HUD on top of everything
    }
    
    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        // Dispose of assets loaded specifically for this screen
    }
    
    

}
