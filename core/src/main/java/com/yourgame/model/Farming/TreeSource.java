package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.App;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Item.Usable;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.UserInfo.Player;

import java.util.HashMap;

public enum TreeSource implements Ingredient {
    Apricot_Sapling,
    Cherry_Sapling,
    Banana_Sapling,
    Mango_Sapling,
    Orange_Sapling,
    Peach_Sapling,
    Apple_Sapling,
    Pomegranate_Sapling,
    Acorn,
    Maple_Seed,
    Pine_Cone,
    Mahogany_Seed,
    Mushroom_Tree_Seed,
    Mystic_Tree_Seed;

    public static final class TreeSourceItem extends Item implements Usable {
        private final TreeSource treeSource;

        public TreeSourceItem(TreeSource treeSource) {
            super(treeSource.name(), ItemType.INGREDIENT, 100, true);
            this.treeSource = treeSource;
        }

        @Override
        public TextureRegion getTextureRegion(GameAssetManager assetManager) {
            String treeName = TreeType.getTreeTypeBySource(treeSource).name().replace("Tree", "");
            String path = "Game/Tree/" + treeName + "/" + treeSource.name() + ".png";
            return new TextureRegion(assetManager.getTexture(path));
        }

        @Override
        public boolean use(Player player, Map map, Tile tile) {
            MapElement element = tile.getElement();
            if (element != null) return false;
            if (tile.getDirtState() == Tile.DirtState.NON_FARMABLE) return false;

            int x = tile.tileX * Tile.TILE_SIZE;
            int y = tile.tileY * Tile.TILE_SIZE;
            Tree tree = new Tree(map, TreeType.getTreeTypeBySource(treeSource), tile.getFertilizer(), x, y);
            tree.setWateredToday(tile.isWatered());

            App.getGameState().getGameTime().addPlant(tree);
            map.addElement(tree);

            player.getBackpack().getInventory().reduceItemQuantity(this, 1);

            return true;
        }
    }
}
