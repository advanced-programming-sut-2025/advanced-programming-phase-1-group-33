package com.yourgame.model.Item;

import com.yourgame.model.ManuFactor.Ingredient;

import java.util.HashMap;

public enum TreeSource implements Ingredient {
    // Store the name of the TreeType as a String
    ApricotSapling("ApricotTree"),
    CherrySapling("CherryTree"),
    BananaSapling("BananaTree"),
    MangoSapling("MangoTree"),
    OrangeSapling("OrangeTree"),
    PeachSapling("PeachTree"),
    AppleSapling("AppleTree"),
    PomegranateSapling("PomegranateTree"),
    Acorns("OakTree"),
    MapleSeeds("MapleTree"),
    PineCones("PineTree"),
    MahoganySeeds("MahoganyTree"),
    MushroomTreeSeeds("MushroomTree"),
    MysticTreeSeeds("MysticTree"),;

    // Change the field type to String
    private final String treeTypeName;
    private final static HashMap<String, TreeSource> stringToTreeSource = new HashMap<>();

    static {
        for (TreeSource value : TreeSource.values()) {
            stringToTreeSource.put(value.name().toLowerCase(), value);
        }
    }

    // Constructor accepts the String name
    TreeSource(String treeTypeName) {
        this.treeTypeName = treeTypeName;
    }

    // Method to get the associated TreeType using the lookup in TreeType
    public TreeType getTreeType() {
        // Use the static lookup method from TreeType
        return TreeType.getTreeTypeByName(this.treeTypeName);
    }

    public static TreeSource getTreeSourceByName(String treeSource) {
        if (treeSource == null || treeSource.isEmpty())
            return null;
        return stringToTreeSource.getOrDefault(treeSource.toLowerCase(), null);
    }

}
