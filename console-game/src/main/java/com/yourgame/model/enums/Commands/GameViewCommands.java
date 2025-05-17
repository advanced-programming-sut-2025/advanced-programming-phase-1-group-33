package com.yourgame.model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameViewCommands implements Command {
    TIME("^time$"),
    DATE("^date$"),
    DATETIME("^datetime$"),
    DAY_OF_WEEK("^day\\s+of\\s+the\\s+week$"),
    SEASON("^season$"),
    CHEAT_ADVANCE_TIME("^cheat\\s+advance\\s+time\\s+(?<amount>\\d+)h$"),
    CHEAT_ADVANCE_DATE("^cheat\\s+advance\\s+date\\s+(?<amount>\\d+)d$"),
    CHEAT_THOR("^cheat\\s+thor\\s+-l\\s+(?<X>\\d+)\\s*,\\s*(?<Y>\\d+)$"),
    WEATHER("^weather$"),
    WEATHER_FORECAST("^weather\\s+forecast$"),
    CHEAT_WEATHER_SET("^cheat\\s+weather\\s+set\\s+(?<Type>Sunny|Rainy|Stormy|Snowy)$"),
    GREEN_HOUSE_BUILD("^greenhouse\\s+build$"),
    NEXT_TURN("^next\\s+turn$"),
    ENERGY_SHOW("^energy\\s+show$"),
    ENERGY_SET_VALUE("^energy\\s+set\\s+-v\\s+(?<value>\\d+)$"),
    ENERGY_UNLIMITED("^energy\\s+unlimited$"),
    INVENTORY_SHOW("^inventory\\s+show$"),
    INVENTORY_TRASH("^inventory\\s+trash\\s+-i\\s+(?<itemName>.+?)(\\s+-n\\s+(?<number>\\d+))?$"),
    TOOLS_EQUIP("^tools\\s+equip\\s+(?<toolName>.+)$"),
    TOOLS_SHOW_CURRENT("^tools\\s+show\\s+current$"),
    TOOLS_SHOW_AVAILABLE("^tools\\s+show\\s+available$"),
    TOOLS_UPGRADE("\\s*tools\\s+upgrade\\s+(?<name>\\S+)\\s*"),
    TOOLS_USE_DIRECTION("^tools\\s+use\\s+-d\\s+(?<direction>.+)$"),
    WALK("^walk\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)$"),
    WALK_FROM_HERE_AND_SHOW_MAP("^walk\\s+(?<x>-?\\d+)\\s*,\\s*(?<y>-?\\d+)$"),
    SHOW_MY_POSITION("^show\\s+my\\s+position$"),
    PRINT_MAP("^print\\s+map\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s+-s\\s+(?<size>.+)$"),
    PRINT_Whole_MAP("^print\\s+map\\s+-h$"),
    Print_map_for_current_player("^print\\s+map$"), 
    HELP_READING_MAP("^help\\s+reading\\s+map$"),
    CRAFT_INFO("^craftinfo\\s+-n\\s+(?<craftName>.+)$"),
    Plant("plant\\s+-s\\s+(?<seed>\\S+)\\s+-d\\s+(?<direction>\\S+)\\s*"),
    FERTILIZE("fertilize\\s+-f\\s+(?<fertilizer>\\S+)\\s+-d\\s+(?<direction>\\S+)\\s*"),
    SHOWPLANT("show\\s+plant\\s+-l\\s+(?<X>\\d+)\\s*,\\s*(?<Y>\\d+)\\s*"),
    HowMuchWater("howmuch\\s+water\\s*"),
    CRAFTING_SHOW_RECIPES("^crafting\\s+show\\s+recipes$"),
    CRAFTING_CRAFT("^crafting\\s+craft\\s+(?<itemName>.+)$"),
    // PLACE_ITEM(""),
    CHEAT_ADD_ITEM("^cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>.+?)\\s+-c\\s+(?<count>\\d+)$"),

    COOKING_REFRIGERATOR_PICK_PUT("^cooking\\s+refrigerator\\s+(?<action>pick|put)\\s+(?<item>.+)$"),
    COOKING_SHOW_RECIPES("^cooking\\s+show\\s+recipes$"),
    COOKING_PREPARE("^cooking\\s+prepare\\s+(?<itemName>.+)$"),
    EAT("^eat\\s+(?<foodName>.+)$"),
    BUILD("build\\s+-a\\s+(?<buildingName>.+?)\\s+-l\\s+(?<X>\\d+)\\s*,\\s*(?<Y>\\d+)\\s*"),
    BUYANIMAL("buy\\s+animal\\s+-a\\s+(?<animal>\\S+)\\s+-n\\s+(?<name>.+)"),
    PET("pet\\s+-n\\s+(?<name>.+)"),
    CHEAT_SET_FRIENDSHIP("^cheat\\s+set\\s+friendship\\s+-n\\s+(?<name>.+)\\s+-c\\s+(?<amount>\\d+)$"),
    ANIMALS("^animals$"),
    SHEPHERD("^shepherd\\s+animals\\s+-n\\s+(?<animalName>.+?)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)$"),
    FEED_HAY("^feed\\s+hay\\s+-n\\s+(?<animalName>.+)$"),
    PRODUCES("^produces$"),
    COLLECT_PRODUCE("^collect\\s+produce\\s+-n\\s+(?<name>.+)$"),
    SELL_ANIMAL("^sell\\s+animal\\s+-n\\s+(?<name>.+)$"),
    // FISHING(""),
    ARTISAN_USE("^artisan\\s+use\\s+(?<artisanName>.+?)(\\s+-i\\s+(?<item1Name>.+))?$"),
    ARTISAN_GET("^artisan\\s+get\\s+(?<artisanName>.+)$"),

    // go to store
    StoreMenu("^go\\s+to\\s+store$"), 
    // SHOW_ALL_PRODUCTS(""),
    // SHOW_ALL_AVAILABLE_PRODUCTS(""),
    // PURCHASE(""),
    CHEAT_ADD_DOLLARS("\\s*cheat\\s+add\\s+(?<amount>-?\\d+)\\s+dollars"),
    SELL_PRODUCT("^sell\\s+(?<productName>.+?)\\s+-n\\s+(?<count>\\d+)$"),
    FRIENDSHIPS("^friendships$"),
    TALK("^talk\\s+-u\\s+(?<username>.+?)\\s+-m\\s+(?<message>.+)$"),
    TALK_HISTORY("^talk\\s+history\\s+-u\\s+(?<username>.+)$"),
    GIFT("^gift\\s+-u\\s+(?<username>.+?)\\s+-i\\s+(?<item>.+?)\\s+-a\\s+(?<amount>\\d+)$"),
    GIFT_LIST("^gift\\s+list$"),
    GIFT_HISTORY("^gift\\s+history\\s+-u\\s+(?<username>.+)$"),
    GIFT_RATE("^gift\\s+rate\\s+-i\\s+(?<giftNumber>\\d+)\\s+-r\\s+(?<rate>\\d+)$"),
    HUG("^hug\\s+-u\\s+(?<username>.+)$"),
    FLOWER("^flower\\s+-u\\s+(?<username>.+?)\\s+-f\\s+(?<flowerName>.+)$"),
    ASK_MARRIAGE("^ask\\s+marriage\\s+-u\\s+(?<username>.+?)\\s+-r\\s+(?<ring>.+)$"),
    RESPOND_MARRIAGE("^respond\\s+-(?<state>accept|reject)\\s+-u\\s+(?<username>.+)$"),
    // RESPOND_MARRIAGE_REJECT(""),
    // START_TRADE(""),
    // TRADE(""),
    // TRADE_LIST(""),
    // TRADE_RESPOND_ACCEPT(""),
    // TRADE_RESPOND_REJECT(""),
    // TRADE_HISTORY(""),
    // MEET_NPC(""),
    // GIFT_NPC(""),
    // FRIENDSHIP_NPC_LIST(""),
    // QUESTS_LIST(""),
    // QUESTS_FINISH(""),
    SHOW_MENU(Command.SHOW_MENU),
    EXIT_MENU(Command.EXIT_MENU),
    ENTER_MENU(Command.ENTER_MENU),
    Go_Back(Command.Go_back);

    private final String pattern;

    GameViewCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean matches(String input) {
        return getMatcher(input).matches();
    }

    public Matcher getMatcher(String input) {
        return Pattern.compile(pattern).matcher(input);
    }

    @Override
    public String getGroup(String input, String group) {
        Matcher matcher = getMatcher(input);
        matcher.find();
        String value = matcher.group(group);
        if (value != null && group.equals("loginFlag")) {
            if (!value.equals("-stay-logged-in"))
                value = null;
        }
        return value != null ? value.trim() : null;
    }

    public static GameViewCommands parse(String input) {
        for (GameViewCommands command : values()) {
            if (command.matches(input)) {
                return command;
            }
        }
        return null;
    }
}
