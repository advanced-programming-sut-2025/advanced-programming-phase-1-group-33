package com.yourgame.model.UserInfo;

import java.util.ArrayList;

public class RelationWithPlayers {

    private int xp = 0;
    private FriendshipLevelsWithPlayers friendshipLevel = FriendshipLevelsWithPlayers.LevelZero;
    private boolean gaveFlower = false;
    private boolean marriage = false;
    private boolean haveTalkedToday = false;
    private boolean haveTradedToday = false;
    private boolean haveGaveGiftToday = false;
    private boolean haveHuggedToday = false;
    private boolean haveGaveFlowerToday = false;
    private final ArrayList<DialoguesBetweenPlayers> dialogues = new ArrayList<>();

    public boolean HaveTradedToday() {
        return haveTradedToday;
    }

    public void setHaveTradedToday(boolean haveTradedToday) {
        this.haveTradedToday = haveTradedToday;
    }

    public boolean HaveTalkedToday() {
        return haveTalkedToday;
    }

    public void setHaveTalkedToday(boolean haveTalkedToday) {
        this.haveTalkedToday = haveTalkedToday;
    }

    public boolean HaveGaveGiftToday() {
        return haveGaveGiftToday;
    }

    public void setHaveGaveGiftToday(boolean haveGaveGiftToday) {
        this.haveGaveGiftToday = haveGaveGiftToday;
    }

    public boolean HaveHuggedToday() {
        return haveHuggedToday;
    }

    public void setHaveHuggedToday(boolean haveHuggedToday) {
        this.haveHuggedToday = haveHuggedToday;
    }

    public void setHaveGaveFlowerToday(boolean haveGaveFlowerToday) {
        this.haveGaveFlowerToday = haveGaveFlowerToday;
    }

    private void checkXp() {

        if (this.xp < 0) {
            this.xp = 0;
        }

        switch (friendshipLevel) {
            case LevelZero: {
                xp = Math.min(xp, 100);
                if (xp == 100) {
                    friendshipLevel = FriendshipLevelsWithPlayers.LevelOne;
                    xp = 0;
                }
            }
            break;

            case LevelOne: {
                xp = Math.min(xp, 200);
                if (xp == 0) {
                    friendshipLevel = FriendshipLevelsWithPlayers.LevelZero;
                }else if (xp == 200) {
                    friendshipLevel = FriendshipLevelsWithPlayers.LevelTwo;
                    xp = 0;
                }
            }
            break;

            case LevelTwo: {
                xp = Math.min(xp, 300);
                if (xp == 0) {
                    friendshipLevel = FriendshipLevelsWithPlayers.LevelOne;
                }else if (xp == 300 && gaveFlower) {
                    friendshipLevel = FriendshipLevelsWithPlayers.LevelThree;
                    xp = 0;
                }
            }
            break;

            case LevelThree: {
                if (xp == 0) {
                    friendshipLevel = FriendshipLevelsWithPlayers.LevelTwo;
                }
                xp = Math.min(xp, 400);
            }
            break;
        }
    }

    public FriendshipLevelsWithPlayers getFriendshipLevel() {
        return friendshipLevel;
    }

    public void setFriendshipLevel(FriendshipLevelsWithPlayers friendshipLevel) {
        this.xp = 0;
        this.friendshipLevel = friendshipLevel;
    }

    public void setGaveFlower() {
        this.gaveFlower = true;
    }

    public boolean isMarriage() {
        return marriage;
    }

    public void setMarriage() {
        this.friendshipLevel = FriendshipLevelsWithPlayers.LevelFour;
        this.marriage = true;
    }

    public boolean canGiveFlower() {
        return this.friendshipLevel.equals(FriendshipLevelsWithPlayers.LevelTwo) && xp == 300;
    }

    public boolean canRequestMarriage() {
        return this.friendshipLevel.equals(FriendshipLevelsWithPlayers.LevelThree) && xp == 400;
    }

    public void checkEveryNight() {

        if (marriage) {
            return;
        }
        if (!haveGaveFlowerToday && !haveHuggedToday && !haveGaveGiftToday && !haveTalkedToday && !haveTradedToday) {
            if (xp != 0) {
                xp -= 10;
                xp = Math.max(0, xp);
            } else {
                switch (friendshipLevel) {
                    case LevelOne: {
                        friendshipLevel = FriendshipLevelsWithPlayers.LevelZero;
                        xp = 90;
                    }
                    break;

                    case LevelTwo: {
                        friendshipLevel = FriendshipLevelsWithPlayers.LevelOne;
                        xp = 190;
                    }
                    break;

                    case LevelThree: {
                        friendshipLevel = FriendshipLevelsWithPlayers.LevelTwo;
                        xp = 290;
                    }
                    break;
                }
            }
        }

        this.haveTalkedToday = false;
        this.haveTradedToday = false;
        this.haveGaveGiftToday = false;
        this.haveHuggedToday = false;
        this.haveGaveFlowerToday = false;

    }

    public void changeXp(int value) {
        this.xp += value;
        checkXp();
    }

    public void addDialogue(DialoguesBetweenPlayers dialogue) {
        this.dialogues.add(dialogue);
    }

    public boolean canHug() {
        if (this.friendshipLevel.equals(FriendshipLevelsWithPlayers.LevelTwo)) {
            return true;
        }
        if (this.friendshipLevel.equals(FriendshipLevelsWithPlayers.LevelThree)) {
            return true;
        }
        return this.friendshipLevel.equals(FriendshipLevelsWithPlayers.LevelFour);
    }

    public String getTalkHistory() {

        StringBuilder talkHistory = new StringBuilder();
        for (DialoguesBetweenPlayers dialogue: dialogues) {
            talkHistory.append(dialogue.toString());
        }
        return talkHistory.toString();
    }

    @Override
    public String toString() {
        return  friendshipLevel.toString();
    }

}
