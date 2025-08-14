package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.Resource.Wood;
import com.yourgame.model.WeatherAndTime.Season;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForagingTree extends MapElement {
    private static final List<TreeType> foragings = List.of(
        TreeType.OakTree, TreeType.MapleTree, TreeType.PineTree, TreeType.MahoganyTree, TreeType.MushroomTree
    );
    private static final Random random = new Random();
    public static ForagingTree getRandomForaging() {
        return new ForagingTree(foragings.get(random.nextInt(foragings.size())), 0, 0);
    }

    private final TreeType treeType;

    private ForagingTree(TreeType treeType, int worldX, int worldY) {
        super(ElementType.TREE, new Rectangle(worldX, worldY, 48, 80), 5);
        this.treeType = treeType;
    }

    @Override
    public TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason) {
        String treeName = treeType.name().replace("Tree", "");
        String path = "Game/Tree/" + treeName + "/" + treeName + "_Stage_5.png";
        Texture seasonalSheet = assetManager.getTexture(path);

        if (seasonalSheet == null) return null;

        // The sheet is 384x160, with each season being 96x160.
        int regionWidth = 96;
        int regionHeight = 160;
        int seasonIndex = switch (currentSeason) {
            case Spring -> 0;
            case Summer -> 1;
            case Fall -> 2;
            case Winter -> 3;
        };

        int regionX = seasonIndex * regionWidth;
        return new TextureRegion(seasonalSheet, regionX, 0, regionWidth, regionHeight);
    }

    @Override
    public MapElement clone(int tileX, int tileY) {
        return new ForagingTree(treeType, tileX * Tile.TILE_SIZE, tileY * Tile.TILE_SIZE);
    }

    @Override
    public List<Item> drop() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) items.add(new Wood());
        items.add(new TreeSource.TreeSourceItem(treeType.getSource()));
        return items;
    }
}
