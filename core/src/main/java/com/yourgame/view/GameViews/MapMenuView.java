package com.yourgame.view.GameViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Map.Map;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

public class MapMenuView extends Window {
    private final Player player;
    private final Image mapImage;
    //private final Image playerMarker;
    private final float mapPixelWidth;
    private final float mapPixelHeight;

    public MapMenuView(Stage stage, GameScreen gameScreen, Player player, Map currentMap) {
        super(currentMap.getName(), MenuAssetManager.getInstance().getSkin(3));

        this.player = player;

        // --- Window Setup ---
        setSize(1200, 900);
        setModal(true);
        setMovable(true);
        padTop(40f);

        // --- Render the Map to a Texture ---
        TiledMap tiledMap = currentMap.getTiledMap();
        mapPixelWidth = tiledMap.getProperties().get("width", Integer.class) * tiledMap.getProperties().get("tilewidth", Integer.class);
        mapPixelHeight = tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class);

        Texture mapTexture = renderMapToTexture(tiledMap, mapPixelWidth, mapPixelHeight);
        this.mapImage = new Image(mapTexture);
        this.mapImage.setScaling(Scaling.fit);

        // --- Create the Player Marker ---
        //this.playerMarker = new Image(GameAssetManager.getInstance().getTexture("Game/Player/PlayerPin.png"));

        // --- Layout ---
        Stack stack = new Stack();
        stack.add(mapImage);
        //stack.add(playerMarker); // Add the marker on top of the map image

        Container<Stack> mapContainer = new Container<>(stack);
        mapContainer.maxSize(1200, 900);

        this.add(mapContainer).expand().fill();
        //this.pack();
        this.setPosition((stage.getWidth() - getWidth()) / 2f, (stage.getHeight() - getHeight()) / 2f);

        // --- Close Button ---
        TextButton closeButton = new TextButton("X", MenuAssetManager.getInstance().getSkin(1));
        this.getTitleTable().add(closeButton).size(30, 30).padRight(5);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new MainMenuView(MenuAssetManager.getInstance().getSkin(3), stage, gameScreen));
            }
        });
    }

    /**
     * Renders a TiledMap to an off-screen FrameBuffer and returns it as a Texture.
     */
    private Texture renderMapToTexture(TiledMap mapToRender, float width, float height) {
        // Create a framebuffer to draw the map onto
        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGB888, (int) width, (int) height, false);
        OrthographicCamera mapCamera = new OrthographicCamera(width, height);
        mapCamera.setToOrtho(true, width, height);

        OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(mapToRender);

        fbo.begin();
        // Clear the buffer
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(mapCamera);
        mapRenderer.render();
        fbo.end();

        Texture texture = fbo.getColorBufferTexture();
        // The texture is upside down, so we need to wrap it in a TextureRegion and flip it
        TextureRegion region = new TextureRegion(texture);
        region.flip(false, true);

        return region.getTexture();
    }
}
