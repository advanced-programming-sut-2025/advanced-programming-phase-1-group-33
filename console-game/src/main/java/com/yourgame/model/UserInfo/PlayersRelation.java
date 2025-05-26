package com.yourgame.model.UserInfo;

import java.util.ArrayList;

public class PlayersRelation {

    private int xp = 0;
    private PlayersFriendshipLevel friendshipLevel = PlayersFriendshipLevel.LevelZero;
    private boolean gaveFlower = false;
    private boolean haveTalkedToday = false;
    private boolean marriage = false;
    private boolean haveGaveGiftToday = false;
    private boolean haveTradedToday = false;
    private boolean haveGaveFlowerToday = false;
    private boolean haveHuggedToday = false;
    private final ArrayList<PlayersDialogues> dialogues = new ArrayList<>();

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
                    friendshipLevel = PlayersFriendshipLevel.LevelOne;
                    xp = 0;
                }
            }
            break;

            case LevelOne: {
                xp = Math.min(xp, 200);
                if (xp == 0) {
                    friendshipLevel = PlayersFriendshipLevel.LevelZero;
                }else if (xp == 200) {
                    friendshipLevel = PlayersFriendshipLevel.LevelTwo;
                    xp = 0;
                }
            }
            break;

            case LevelTwo: {
                xp = Math.min(xp, 300);
                if (xp == 0) {
                    friendshipLevel = PlayersFriendshipLevel.LevelOne;
                }else if (xp == 300 && gaveFlower) {
                    friendshipLevel = PlayersFriendshipLevel.LevelThree;
                    xp = 0;
                }
            }
            break;

            case LevelThree: {
                if (xp == 0) {
                    friendshipLevel = PlayersFriendshipLevel.LevelTwo;
                }
                xp = Math.min(xp, 400);
            }
            break;
        }
    }

    public PlayersFriendshipLevel getFriendshipLevel() {
        return friendshipLevel;
    }

    public void setFriendshipLevel(PlayersFriendshipLevel friendshipLevel) {
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
        this.friendshipLevel = PlayersFriendshipLevel.LevelFour;
        this.marriage = true;
    }

    public boolean canGiveFlower() {
        return this.friendshipLevel.equals(PlayersFriendshipLevel.LevelTwo) && xp == 300;
    }

    public boolean canRequestMarriage() {
        return this.friendshipLevel.equals(PlayersFriendshipLevel.LevelThree) && xp == 400;
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
                        friendshipLevel = PlayersFriendshipLevel.LevelZero;
                        xp = 90;
                    }
                    break;

                    case LevelTwo: {
                        friendshipLevel = PlayersFriendshipLevel.LevelOne;
                        xp = 190;
                    }
                    break;

                    case LevelThree: {
                        friendshipLevel = PlayersFriendshipLevel.LevelTwo;
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


    public void addDialogue(PlayersDialogues dialogue) {
        this.dialogues.add(dialogue);
    }

    public boolean canHug() {
        if (this.friendshipLevel.equals(PlayersFriendshipLevel.LevelTwo)) {
            return true;
        }
        if (this.friendshipLevel.equals(PlayersFriendshipLevel.LevelThree)) {
            return true;
        }
        return this.friendshipLevel.equals(PlayersFriendshipLevel.LevelFour);
    }

    public String getTalkHistory() {

        StringBuilder talkHistory = new StringBuilder();
        for (PlayersDialogues dialogue: dialogues) {
            talkHistory.append(dialogue.toString());
        }
        return talkHistory.toString();
    }

    @Override
    public String toString() {
        return  friendshipLevel.toString() + "      xp: " + this.xp ;
    }

}
