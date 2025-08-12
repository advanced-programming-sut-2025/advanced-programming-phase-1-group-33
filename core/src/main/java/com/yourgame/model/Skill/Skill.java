package com.yourgame.model.Skill;

public abstract class Skill {
    public enum SkillType {FARMING, MINING, FORAGING, FISHING}

    public static final int MAX_LEVEL = 4;

    protected final SkillType type;
    protected int level = 0;
    protected int experience = 0;

    public Skill(SkillType type) {
        this.type = type;
    }

    public void addExperience(int xp) {
        if (level >= MAX_LEVEL) return;

        experience += xp;
        while (experience >= requiredXpForNextLevel() && level < MAX_LEVEL) {
            experience -= requiredXpForNextLevel();
            level++;
        }

        if (level == MAX_LEVEL) {
            experience = 0;
        }
    }

    protected int requiredXpForNextLevel() {
        return 100 * level + 50;
    }

    public int level() {
        return level;
    }

    public boolean isMaxLevel() {
        return level == MAX_LEVEL;
    }

    public static class FarmingSkill extends Skill {
        public FarmingSkill() {
            super(SkillType.FARMING);
        }
    }

    public static class MiningSkill extends Skill {
        public MiningSkill() {
            super(SkillType.MINING);
        }
    }

    public static class ForagingSkill extends Skill {
        public ForagingSkill() {
            super(SkillType.FORAGING);
        }
    }

    public static class FishingSkill extends Skill {
        public FishingSkill() {
            super(SkillType.FISHING);
        }
    }
}
