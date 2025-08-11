package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Stores.Sellable;

public enum Fruit implements Ingredient, Sellable {
    Apricot(38, 59),
    Cherry(38, 80),
    Banana(75, 150),
    Mango(100, 130),
    Orange(38, 100),
    Peach(38, 140),
    Apple(38, 100),
    Pomegranate(38, 140),
    OakResin(0, 150),
    MapleSyrup(0, 200),
    PineTar(0, 100),
    Sap(-2, 2),
    CommonMushroom(38, 40),
    MysticSyrup(500, 1000);

    private final int energy;
    private final int baseSellPrice;

    Fruit(int energy, int baseSellPrice) {
        this.energy = energy;
        this.baseSellPrice = baseSellPrice;
    }

    public int getSellPrice() {
        return baseSellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    public static Fruit getByName(String name) {
        for (Fruit type : Fruit.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public Item getItem() {
        return new FruitItem(this);
    }

    public static final class FruitItem extends Item {
        private final Fruit fruitType;

        public FruitItem(Fruit fruit) {
            super(fruit.name(), ItemType.INGREDIENT, fruit.baseSellPrice, true);
            this.fruitType = fruit;
        }

        @Override
        public TextureRegion getTextureRegion(GameAssetManager assetManager) {
            String path = switch (fruitType) {
                case OakResin -> "Game/Tree/Oak/Oak_Resin.png";
                case MapleSyrup -> "Game/Tree/Maple/Maple_Syrup.png";
                case PineTar -> "Game/Tree/Pine/Pine_Tar.png";
                case Sap -> "Game/Tree/Sap.png";
                case CommonMushroom -> "Game/Foraging/Common_Mushroom.png";
                case MysticSyrup -> "Game/Tree/Mystic/Mystic_Syrup.png";
                default -> "Game/Tree/" + fruitType.name() + "/" + fruitType.name() + ".png";
            };
            return new TextureRegion(assetManager.getTexture(path));
        }

        public Fruit getFruitType() {
            return fruitType;
        }
    }
}
