package com.yourgame.model.enums;

public enum TileType {
    GRASS('.', ANSI.GREEN, true),
    BUILDING('B', ANSI.YELLOW, false),
    WATER('~', ANSI.CYAN, false),
    ROCK('^', ANSI.WHITE, true),
    DIRT('.', ANSI.RED, true),
    SAND('.', ANSI.YELLOW, true),
    PORTAL('P', ANSI.PURPLE, true);

    private final char symbol;
    private final String colorCode;
    private final boolean walkable;
    // simple ANSI escape‐code helper
    private static class ANSI {
        static final String RESET  = "\u001B[0m";
        static final String BLACK  = "\u001B[30m";
        static final String RED    = "\u001B[31m";
        static final String GREEN  = "\u001B[32m";
        static final String YELLOW = "\u001B[33m";
        static final String BLUE   = "\u001B[34m";
        static final String PURPLE = "\u001B[35m";
        static final String CYAN   = "\u001B[36m";
        static final String WHITE  = "\u001B[37m";
    }

    TileType(char symbol, String colorCode, boolean walkable) {
        this.symbol = symbol;
        this.colorCode = colorCode;
        this.walkable = walkable; 
    }

    /** Just the raw character for simple rendering */
    public char getSymbol() {
        return symbol;
    }

    /** Colored string, using ANSI codes (only works in terminals that support it) */
    public String getColoredSymbol() {
        return colorCode + symbol + ANSI.RESET;
    }

    public boolean isWalkable() {
        return walkable;
    }

}
