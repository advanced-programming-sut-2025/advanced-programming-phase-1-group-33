package com.yourgame.model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public enum GameViewCommands implements Command{
    TIME("^time$"),
    DATE("^date$"),
    DATETIME("^datetime$"),
    DAY_OF_WEEK("^day\\s+of\\s+the\\s+week$"),
    SEASON("^season$"),
    CHEAT_ADVANCE_TIME("^cheat\\s+advance\\s+time\\s+(?<amount>\\d+)h$"),
    CHEAT_ADVANCE_DATE("^cheat\\s+advance\\s+date\\s+(?<amount>\\d+)d$"),
    CHEAT_THOR("^cheat\\s+Thor\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)$"),
    WEATHER("^weather$"),
    WEATHER_FORECAST("^weather\\s+forecast$"),
    CHEAT_WEATHER_SET("^cheat\\s+weather\\s+set\\s+(?<Type>sunny|rain|storm|snow)$"),
    GREEN_HOUSE_BUILD("^greenhouse\\s+build$"),
    NEXT_TURN("^next\\s+turn$"),
    ENERGY_SHOW("^energy\\s+show$"),
    ENERGY_SET_VALUE("^energy\\s+set\\s+-v\\s+(?<value>\\d+)$"),
    ENERGY_UNLIMITED("^energy\\s+unlimited$"),
    WALK("^walk\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)$"),
    PRINT_MAP("^print\\s+map\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s+-s\\s+(?<size>.+)$"),
    HELP_READING_MAP("^help\\s+reading\\s+map$"),
    // ENERGY_SHOW(""),
    // INVENTORY_SHOW(""),
    // INVENTORY_TRASH(""),
    // TOOLS_EQUIP(""),
    // TOOLS_SHOW_CURRENT(""),
    // TOOLS_SHOW_AVAILABLE(""),
    // TOOLS_UPGRADE(""),
    // TOOLS_USE_DIRECTION(""),
    // CRAFT_INFO(""),
    // PLANT(""),
    // SHOW_PLANT(""),
    // FERTILIZE(""),
    // HOW_MUCH_WATER(""),
    // CRAFTING_SHOW_RECIPES(""),
    // CRAFTING_CRAFT(""),
    // PLACE_ITEM(""),
    // CHEAT_ADD_ITEM(""),
    // COOKING_REFRIGERATOR_PICK(""),
    // COOKING_REFRIGERATOR_PUT(""),
    // COOKING_SHOW_RECIPES(""),
    // EAT(""),
    // BUILD(""),
    // BUY_ANIMAL(""),
    // PET(""),
    // CHEAT_SET_FRIENDSHIP(""),
    // ANIMALS(""),
    // SHEPHERD(""),
    // FEED_HAY(""),
    // PRODUCES(""),
    // COLLECT_PRODUCE(""),
    // SELL_ANIMAL(""),
    // FISHING(""),
    // ARTISAN_USE(""),
    // ARTISAN_GET(""),
    // SHOW_ALL_PRODUCTS(""),
    // SHOW_ALL_AVAILABLE_PRODUCTS(""),
    // PURCHASE(""),
    // CHEAT_ADD_DOLLARS(""),
    // SELL_PRODUCT(""),
    // FRIENDSHIPS(""),
    // TALK(""),
    // TALK_HISTORY(""),
    // GIFT(""),
    // GIFT_LIST(""),
    // GIFT_HISTORY(""),
    // GIFT_RATE(""),
    // HUG(""),
    // FLOWER(""),
    // ASK_MARRIAGE(""),
    // RESPOND_MARRIAGE_ACCEPT(""),
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
            if (!value.equals("-stay-logged-in")) value = null;
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
