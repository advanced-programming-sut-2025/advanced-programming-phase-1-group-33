package com.yourgame.model.Item;

import com.yourgame.model.ManuFactor.Ingredient;

import java.util.HashMap;

public enum TreeSource implements Ingredient {
    ApricotSapling(TreeType.ApricotTree),
    CherrySapling(TreeType.CherryTree),
    BananaSapling(TreeType.BananaTree),
    MangoSapling(TreeType.MangoTree),
    OrangeSapling(TreeType.OrangeTree),
    PeachSapling(TreeType.PeachTree),
    AppleSapling(TreeType.AppleTree),
    PomegranateSapling(TreeType.PomegranateTree),
    Acorns(TreeType.OakTree),
    MapleSeeds(TreeType.MapleTree),
    PineCones(TreeType.PineTree),
    MahoganySeeds(TreeType.MahoganyTree),
    MushroomTreeSeeds(TreeType.MushroomTree),
    MysticTreeSeeds(TreeType.MysticTree),;

    private final TreeType treeType;
    private final static HashMap<String, TreeSource> stringToTreeSource = new HashMap<>();

    static {
        for (TreeSource value : TreeSource.values()) {
            stringToTreeSource.put(value.name().toLowerCase(), value);
        }
    }

    TreeSource(TreeType treeType) {
        this.treeType = treeType;
    }

    public TreeType getTreeType() {
        return treeType;
    }

    public static TreeSource getTreeSourceByName(String treeSource) {
        if (treeSource == null || treeSource.isEmpty())
            return null;
        return stringToTreeSource.getOrDefault(treeSource.toLowerCase(), null);
    }
}
