package com.yourgame.model.enums;

import com.yourgame.model.Item.Crop;

public enum SymbolType {
    PLAYER('P', ANSI.GREEN, "The main Player"),
    ABIGAIL('A', ANSI.GREEN, "Abigail Npc the gamer"),
    SEBASTIAN('S', ANSI.GREEN, "Sebastian the FreeLancer"),
    HARVEY('H', ANSI.GREEN, "Harvey the Twon doctor"),
    LEAH('L', ANSI.GREEN, "Leah Npc the Artist"),
    ROBIN('R', ANSI.GREEN, "Robin Npc the Carpenter"),
    WALL('#', ANSI.BLACK, "Default Symbol for the Walls"),
    NpcVillageFLOOR('.', ANSI.GRAY, "NPC DEFAULT VILLAGE FARM"),
    DefaultFloor('.', ANSI.BLUE, "Default Floor"),
    FARMBORDER('.', ANSI.BRIGHT_YELLOW, "Farm Border"),
    WalkableDoor('d', ANSI.BRIGHT_CYAN, "WalkableDoor"),
    Thor('⚡', ANSI.BRIGHT_YELLOW, "Thor, the Bringer of Storms and Chaos"),
    HabitatBarn('b', ANSI.BRIGHT_GREEN, "Habitat Barn"),
    HabitatDefault('c', ANSI.BRIGHT_GREEN, "Habitat Default"),
    Crop('c', ANSI.BRIGHT_GREEN, "Crop"),
    GRASS('.', ANSI.GREEN, "Grass"),
    Tree('T', ANSI.BRIGHT_GREEN, "Tree"),
    BurnedTree('T', ANSI.RED, "BurnedTree"),
    Cottage('C', ANSI.YELLOW, "Default for Cottage"),
    BUILDING('B', ANSI.YELLOW, "Default for Buildings"),
    BrokenGreenHouse('G', ANSI.YELLOW, "Broken GreenHouse"),
    GreenHouse('G', ANSI.BRIGHT_BLUE, "Default for GreenHouse"),
    CarpenterShop('W', ANSI.WHITE, "Default for CarpenterShop"),
    Blacksmith('⚒', ANSI.GRAY, "Default for BlackSmith"),
    FishShop('≈', ANSI.BLUE, "Default for Fish Shop"),
    JojaMart('J', ANSI.WHITE, "Default for Joja mart"),
    MarnieRanch('♞', ANSI.WHITE, "Default for Marnie Rench"),
    PierreGeneralStore('⚙', ANSI.WHITE, "Default for PierreGeneralStore"),
    StardropSaloon('☕', ANSI.WHITE, "Default for StardropSaloon"),
    Quarry('Q', ANSI.RED, "Default for Querry"),
    WATER('~', ANSI.CYAN, "Defualt for water "),
    Lake('L', ANSI.CYAN, "Defualt for Lake"),
    Stone('^', ANSI.WHITE, "Default for Stones"),
    StoneItem('S', ANSI.WHITE, "Default for Stone as Item"),
    DIRT('.', ANSI.RED, "Default For Dirt"),
    SAND('.', ANSI.YELLOW, "Default for Sand"),
    PORTAL('@', ANSI.PURPLE, "Default for Portal"), 
    BrokenStone('.', ANSI.GRAY, "A cracked and broken stone"),
    SIPPINGBIN('ø', ANSI.GRAY, "Default for SIPPINGBIN"),;

    private final char symbol;
    private final String colorCode;
    private final String decription;

    // simple ANSI escape‐code helper
    private static class ANSI {
        static final String RESET = "\u001B[0m";
        static final String BLACK = "\u001B[30m";
        static final String RED = "\u001B[31m";
        static final String GREEN = "\u001B[32m";
        static final String YELLOW = "\u001B[33m";
        static final String BLUE = "\u001B[34m";
        static final String PURPLE = "\u001B[35m";
        static final String CYAN = "\u001B[36m";
        static final String WHITE = "\u001B[37m";
        static final String GRAY = "\u001B[90m";

        // Extra bright colors
        static final String BRIGHT_BLACK = "\u001B[90m";
        static final String BRIGHT_RED = "\u001B[91m";
        static final String BRIGHT_GREEN = "\u001B[92m";
        static final String BRIGHT_YELLOW = "\u001B[93m";
        static final String BRIGHT_BLUE = "\u001B[94m";
        static final String BRIGHT_PURPLE = "\u001B[95m";
        static final String BRIGHT_CYAN = "\u001B[96m";
        static final String BRIGHT_WHITE = "\u001B[97m";
    }

    SymbolType(char symbol, String colorCode, String description) {
        this.symbol = symbol;
        this.colorCode = colorCode;
        this.decription = description;
    }

    /** Just the raw character for simple rendering */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Colored string, using ANSI codes (only works in terminals that support it)
     */
    public String getColoredSymbol() {
        return colorCode + symbol + ANSI.RESET;
    }

    public String getDecription() {
        return decription;
    }

    @Override
    public String toString() {
        return getFixedWidthColoredSymbol();
    }

    /**
     * Returns a formatted string for every SymbolType, combining the colored symbol
     * and its description.
     * 
     * @return formatted symbols info.
     */
    public static String getSymbolsInfo() {
        StringBuilder info = new StringBuilder();
        for (SymbolType symbol : SymbolType.values()) {
            info.append(symbol.getColoredSymbol())
                    .append(" : ")
                    .append(symbol.getDecription())
                    .append("\n");
        }
        return info.toString();
    }


    /**
     * Returns the colored symbol formatted to a fixed width.
     * Adjust the width (example uses 3) according to your needs.
     */
    public String getFixedWidthColoredSymbol() {
        // Using String.format to pad the symbol string.
        String padded = String.format("%-3s", String.valueOf(symbol));
        return colorCode + padded + ANSI.RESET;
    }
    
    // Example usage - you can run this to see the output in your terminal.
    public static void main(String[] args) {
        System.out.println(getSymbolsInfo());
    }
}
