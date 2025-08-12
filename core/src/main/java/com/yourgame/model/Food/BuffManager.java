package com.yourgame.model.Food;

import com.yourgame.model.Skill.Skill;
import com.yourgame.model.WeatherAndTime.TimeObserver;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.util.ArrayList;
import java.util.List;

public class BuffManager implements TimeObserver {
    private final List<Buff> buffs = new ArrayList<>();

    private Buff miningBuff;
    private Buff fishingBuff;
    private Buff farmingBuff;
    private Buff foragingBuff;
    private Buff maxEnergyBuff;

    public void addBuff(Buff buff) {
        switch (buff.skillType) {
            case MINING -> miningBuff = buff;
            case FISHING -> fishingBuff = buff;
            case FARMING -> farmingBuff = buff;
            case FORAGING -> foragingBuff = buff;
            case null -> maxEnergyBuff = buff;
        }
    }

    /**
     * @param type null for MAX ENERGY
     * */
    public int getBuffValue(Skill.SkillType type) {
        return switch (type) {
            case MINING -> miningBuff != null ? miningBuff.value : 0;
            case FISHING -> fishingBuff != null ? fishingBuff.value : 0;
            case FARMING -> farmingBuff != null ? farmingBuff.value : 0;
            case FORAGING -> foragingBuff != null ? foragingBuff.value : 0;
            case null -> maxEnergyBuff != null ? maxEnergyBuff.value : 0;
        };
    }

    public List<Buff> getActiveBuffs() {
        buffs.clear();
        if (miningBuff != null) buffs.add(miningBuff);
        if (fishingBuff != null) buffs.add(fishingBuff);
        if (farmingBuff != null) buffs.add(farmingBuff);
        if (foragingBuff != null) buffs.add(foragingBuff);
        if (maxEnergyBuff != null) buffs.add(maxEnergyBuff);
        return buffs;
    }

    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        if (miningBuff != null) {
            miningBuff.update();
            if (miningBuff.isFinished()) {
                miningBuff = null;
            }
        }
        if (fishingBuff != null) {
            fishingBuff.update();
            if (fishingBuff.isFinished()) {
                fishingBuff = null;
            }
        }
        if (farmingBuff != null) {
            farmingBuff.update();
            if (farmingBuff.isFinished()) {
                farmingBuff = null;
            }
        }
        if (foragingBuff != null) {
            foragingBuff.update();
            if (foragingBuff.isFinished()) {
                foragingBuff = null;
            }
        }
        if (maxEnergyBuff != null) {
            maxEnergyBuff.update();
            if (maxEnergyBuff.isFinished()) {
                maxEnergyBuff = null;
            }
        }
    }
}
