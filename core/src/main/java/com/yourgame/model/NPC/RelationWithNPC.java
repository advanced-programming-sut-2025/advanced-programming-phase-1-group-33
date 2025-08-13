package com.yourgame.model.NPC;

public class RelationWithNPC {

    private final NPCType type;
    private int numericalFriendShipLevel;
    private boolean isSecondQuestLocked = true;
    private boolean isThirdQuestLocked = true;
    private int numOfDaysAfterUnlockingSecondQuest = 0;
    private boolean isFirstTimeToSpeakWithNPC = true;
    private boolean isFirstTimeGiftToNPC = true;

    public RelationWithNPC(NPCType type) {
        this.type = type;
        this.numericalFriendShipLevel = 0;
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

    private void increaseNumOfDaysAfterUnlockingSecondQuest() {
        if (!this.isSecondQuestLocked) {
            this.numOfDaysAfterUnlockingSecondQuest++;
        }
    }

    public int getNumericalFriendShipLevel() {
        return numericalFriendShipLevel;
    }

    private void checkUnlockingThirdQuest() {
        if (this.type.equals(NPCType.Pierre)) {
            if (this.numOfDaysAfterUnlockingSecondQuest >= 100) {this.isThirdQuestLocked = false;}
        } else if (this.type.equals(NPCType.Harvey)) {
            if (this.numOfDaysAfterUnlockingSecondQuest >= 120) {this.isThirdQuestLocked = false;}
        } else if (this.type.equals(NPCType.Leah)) {
            if (this.numOfDaysAfterUnlockingSecondQuest >= 130) {this.isThirdQuestLocked = false;}
        } else if (this.type.equals(NPCType.Sebastian)) {
            if (this.numOfDaysAfterUnlockingSecondQuest >= 110) {this.isThirdQuestLocked = false;}
        } else if (this.type.equals(NPCType.Robin)) {
            if (this.numOfDaysAfterUnlockingSecondQuest >= 140) {this.isThirdQuestLocked = false;}
        }

    }
}
