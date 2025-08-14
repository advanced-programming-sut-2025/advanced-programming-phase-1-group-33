package com.yourgame.model.NPC;

import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.Weather;

import java.util.List;

public record Dialogue(
    String text, List<Season> seasons, List<Weather> weathers, int minFriendShipLevel, boolean isQuestDialogue
) {
    public Dialogue(String text) {
        this(text, List.of(), List.of(), 0, false);
    }

    public Dialogue(String text, boolean isQuestDialogue) {
        this(text, List.of(), List.of(), 0, isQuestDialogue);
    }
}
