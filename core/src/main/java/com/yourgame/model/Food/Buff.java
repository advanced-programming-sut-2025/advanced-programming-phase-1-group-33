package com.yourgame.model.Food;

import com.yourgame.model.Skill.Skill;

public class Buff {
    public final Skill.SkillType skillType;
    public final int value;
    public final int durationInHours;
    private int hoursRemaining;

    /**
     * @param skillType null for MAX ENERGY
     * */
    public Buff(Skill.SkillType skillType, int value, int durationInHours) {
        this.skillType = skillType;
        this.value = value;
        this.durationInHours = durationInHours;
        this.hoursRemaining = durationInHours;
    }

    public boolean isFinished() {
        return hoursRemaining <= 0;
    }

    public void update() {
        hoursRemaining -= 1;
    }

    public String getIconPath() {
        return switch (skillType) {
            case MINING -> "Game/Buff/Mining_Skill_Icon.png";
            case FISHING -> "Game/Buff/Fishing_Skill_Icon.png";
            case FARMING -> "Game/Buff/Farming_Skill_Icon.png";
            case FORAGING -> "Game/Buff/Foraging_Skill_Icon.png";
            case null -> "Game/Buff/Max_Energy_Buff.png";
        };
    }
}
