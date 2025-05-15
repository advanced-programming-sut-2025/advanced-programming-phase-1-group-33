package com.yourgame.model.Npc;

public class RelationWithNPC {
    private final NPCType type;
    private NPCFriendshipLevel npcFriendshipLevel;
    private int numericalFriendShipLevel;
    private boolean isSecondQuestLocked = true;
    private boolean isThirdQuestLocked = true;
    private int numOfDaysAfterUnlockingSecondQuest = 0;
    private boolean isFirstTimeToSpeakWithNPC = true;
    private boolean isFirstTimeGiftToNPC = true;

    public RelationWithNPC(NPCType type) {
        this.type = type;
        this.npcFriendshipLevel = NPCFriendshipLevel.LevelZero;
    }

    public NPCFriendshipLevel getNpcFriendshipLevel() {
        return npcFriendshipLevel;
    }

    public NPCType getType() {
        return type;
    }

    public boolean isSecondQuestLocked() {
        return isSecondQuestLocked;
    }

    public boolean isThirdQuestLocked() {
        return isThirdQuestLocked;
    }

    public boolean isFirstTimeToSpeakWithNPC() {
        return isFirstTimeToSpeakWithNPC;
    }

    public boolean isFirstTimeGiftToNPC() {
        return isFirstTimeGiftToNPC;
    }

    public void setFirstTimeGiftToNPC(boolean firstTimeGiftToNPC) {
        isFirstTimeGiftToNPC = firstTimeGiftToNPC;
    }

    public void setFirstTimeToSpeakWithNPC(boolean firstTimeToSpeakWithNPC) {
        isFirstTimeToSpeakWithNPC = firstTimeToSpeakWithNPC;
    }

    public void increaseNumericalFriendShipLevel(int level) {
        this.numericalFriendShipLevel += level;
        this.numericalFriendShipLevel = Math.min(this.numericalFriendShipLevel, this.type.getMaxFriendShipLevel());

        int tempLevel = this.numericalFriendShipLevel / 200;

        switch (tempLevel) {
            case 0:
                this.npcFriendshipLevel = NPCFriendshipLevel.LevelZero;
                break;
            case 1: {
                this.npcFriendshipLevel = NPCFriendshipLevel.LevelOne;
                this.isSecondQuestLocked = false;
            }
            break;
            case 2:
                this.npcFriendshipLevel = NPCFriendshipLevel.LevelTwo;
                break;
            case 3:
                this.npcFriendshipLevel = NPCFriendshipLevel.LevelThree;
                break;
        }
    }

    public void increaseFriendshipLevel() {

        if (this.npcFriendshipLevel == NPCFriendshipLevel.LevelZero) {

            this.npcFriendshipLevel = NPCFriendshipLevel.LevelOne;
            this.isSecondQuestLocked = false;
            this.numericalFriendShipLevel += 200;
            this.numericalFriendShipLevel = Math.min(this.numericalFriendShipLevel, this.type.getMaxFriendShipLevel());

        } else if (this.npcFriendshipLevel == NPCFriendshipLevel.LevelOne) {

            this.npcFriendshipLevel = NPCFriendshipLevel.LevelTwo;
            this.numericalFriendShipLevel += 200;
            this.numericalFriendShipLevel = Math.min(this.numericalFriendShipLevel, this.type.getMaxFriendShipLevel());

        } else if (this.npcFriendshipLevel == NPCFriendshipLevel.LevelTwo) {

            this.npcFriendshipLevel = NPCFriendshipLevel.LevelThree;
            this.numericalFriendShipLevel += 200;
            this.numericalFriendShipLevel = Math.min(this.numericalFriendShipLevel, this.type.getMaxFriendShipLevel());

        }
    }

    public void increaseNumOfDaysAfterUnlockingSecondQuest() {
        if (!this.isSecondQuestLocked) {
            this.numOfDaysAfterUnlockingSecondQuest++;
        }
    }

    public void checkUnlockingThirdQuest() {
        if (this.type.equals(NPCType.Abigail)) {
            if (this.numOfDaysAfterUnlockingSecondQuest >= 100) {this.isThirdQuestLocked = false;}
        } else if (this.type.equals(NPCType.Sebastian)) {
            if (this.numOfDaysAfterUnlockingSecondQuest >= 110) {this.isThirdQuestLocked = false;}
        } else if (this.type.equals(NPCType.Harvey)) {
            if (this.numOfDaysAfterUnlockingSecondQuest >= 120) {this.isThirdQuestLocked = false;}
        } else if (this.type.equals(NPCType.Leah)) {
            if (this.numOfDaysAfterUnlockingSecondQuest >= 130) {this.isThirdQuestLocked = false;}
        } else if (this.type.equals(NPCType.Robin)) {
            if (this.numOfDaysAfterUnlockingSecondQuest >= 140) {this.isThirdQuestLocked = false;}
        }

    }
}