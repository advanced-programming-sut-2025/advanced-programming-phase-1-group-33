package com.yourgame.model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StoreMenuCommands implements Command {
    ShowAllProducts("^\\s*show\\s+all\\s+products\\s*$"),
    ShowAvailableProducts("^\\s*show\\s+all\\s+available\\s+products\\s*$"),
    PurchaseProduct("^\\s*purchase\\s+(?<productName>.+?)(?:\\s+-n\\s+(?<count>\\d+))?\\s*$"),
    Exit("^exit$"),
    SHOW_MENU(Command.SHOW_MENU),
    ENTER_MENU(Command.ENTER_MENU),
    Go_Back(Command.Go_back);

    private final String pattern;

    StoreMenuCommands(String pattern) {
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

    public static StoreMenuCommands parse(String input) {
        for (StoreMenuCommands command : values()) {
            if (command.matches(input)) {
                return command;
            }
        }
        return null;
    }

}
