package com.yourgame.model.NPC;

import com.yourgame.model.Farming.CropItem;
import com.yourgame.model.Farming.CropType;
import com.yourgame.model.Farming.ForagingCrop;
import com.yourgame.model.Farming.Wood;

import java.util.HashMap;
import java.util.Map;

public class QuestDatabase {
    public static final Map<String, Quest> QUESTS = new HashMap<>();

    static {
        QUESTS.put("robin_wood", new Quest(
            "robin_wood",
            "Robin needs wood for a new project.",
            NPCType.Robin,
            Map.of(new Wood.WoodItem(), 50),
            new Dialogue("Hey, I'm running a bit low on wood for my next project. Could you bring me 50 pieces? I'd make it worth your while!", true),
            new Dialogue("Wow, this is perfect! Thanks a bunch. Here's something for your trouble.", true),
            500,
            150
        ));

        QUESTS.put("leah_parsnip", new Quest(
            "leah_parsnip",
            "Leah is feeling peckish and would love a fresh parsnip.",
            NPCType.Leah,
            Map.of(new CropItem(CropType.Parsnip), 1),
            new Dialogue("I've been so focused on my sculpting, I completely forgot to eat! A fresh parsnip would really hit the spot right now.", true),
            new Dialogue("Oh, a parsnip! You're a lifesaver. Thank you so much!", true),
            100,
            100
        ));

        QUESTS.put("sebastian_spice_berry", new Quest(
            "sebastian_spice_berry",
            "Sebastian is craving a Spice Berry.",
            NPCType.Sebastian,
            Map.of(new ForagingCrop.ForagingCropItem(ForagingCrop.Spice_Berry), 1),
            new Dialogue("Hey... weird request, but have you found any Spice Berries? I could really go for one. Don't ask why.", true),
            new Dialogue("Whoa, you actually found one. Cool. Thanks.", true),
            120,
            100
        ));

        QUESTS.put("harvey_daffodil", new Quest(
            "harvey_daffodil",
            "Harvey is studying spring allergens and needs a daffodil.",
            NPCType.Harvey,
            Map.of(new ForagingCrop.ForagingCropItem(ForagingCrop.Daffodil), 1),
            new Dialogue("My seasonal allergy study is underway, but I'm missing a key sample. Could you find a fresh daffodil for my research?", true),
            new Dialogue("This is a perfect specimen! Thank you for your contribution to science... and public health!", true),
            150,
            120
        ));

        QUESTS.put("pierre_garlic", new Quest(
            "pierre_garlic",
            "Pierre wants to display a high-quality garlic in his shop.",
            NPCType.Pierre,
            Map.of(new CropItem(CropType.Garlic), 1),
            new Dialogue("I want to show everyone the quality of produce our valley can grow! Could you bring me a nice-looking garlic to put on display?", true),
            new Dialogue("Magnificent! This will surely bring in more customers. Here's a little something for helping promote local goods!", true),
            250,
            150
        ));
    }

    public static Quest getQuest(String questId) {
        return QUESTS.get(questId);
    }
}
