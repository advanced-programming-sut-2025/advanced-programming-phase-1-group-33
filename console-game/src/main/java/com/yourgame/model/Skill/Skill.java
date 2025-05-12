package com.yourgame.model.Skill;
import com.yourgame.model.enums.SkillLevel;

public abstract class Skill {
    protected double Cost;
    protected SkillLevel level;
    protected int xp;

    public void addExperience(int xp) {
        this.xp += xp;
    }



    abstract public void learn();
    
}
