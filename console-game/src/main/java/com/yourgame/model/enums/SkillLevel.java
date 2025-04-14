package com.yourgame.model.enums;

/**
 * Models a hierarchical set of skill levels.
 * Levels are defined from the lowest (ZERO) to the highest (FOUR).
 * Each level, except the highest, defines the XP required to reach the next level.
 */
public enum SkillLevel {
    ZERO(150),    // XP required to go from ZERO to ONE
    ONE(250),     // XP required to go from ONE to TWO
    TWO(350),     // XP required to go from TWO to THREE
    THREE(450),   // XP required to go from THREE to FOUR
    FOUR(Double.POSITIVE_INFINITY); // Highest level; no further promotion

    private final double xpToNextLevel;

    /**
     * Constructs a SkillLevel with a given XP required for the next level.
     *
     * @param xpToNextLevel XP required to move to the next level.
     */
    SkillLevel(double xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }

    /**
     * Returns the XP required to advance to the next skill level.
     * For the highest level, this value is set to infinity.
     *
     * @return the required XP to reach the next level.
     */
    public double getXpToNextLevel() {
        return xpToNextLevel;
    }

    /**
     * Returns the next SkillLevel in order, or null if this is the highest level.
     *
     * @return the next skill level, or null when at the maximum level.
     */
    public SkillLevel getNextLevel() {
        SkillLevel[] levels = SkillLevel.values();
        int currentIndex = this.ordinal();
        if (currentIndex < levels.length - 1) {
            return levels[currentIndex + 1];
        }
        return null;
    }
    
    /**
     * Checks if this level is the highest defined level.
     *
     * @return true if this is the maximum level, otherwise false.
     */
    public boolean isMaxLevel() {
        return this == FOUR;
    }
}
