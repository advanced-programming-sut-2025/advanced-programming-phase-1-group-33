package com.yourgame.model.enums.Commands;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PreGameMenuCommands implements Command{
    New_GAM("^\\s*game\\s+new\\s+(?<usernames>.+)*\\s*$"),
    GAME_MAP("^\\s*game\\s+map\\s+(?<map_number>\\d+)$"),
    LOAD_GAME("^\\s*load\\s+game\\s*$"),
    EXIT_GAME("^\\s*exit\\s+game\\s*$"), 
    GO_Back(Command.Go_back),
    SHOW_MENU(Command.SHOW_MENU),
    EXIT_MENU(Command.EXIT_MENU),
    ENTER_MENU(Command.ENTER_MENU);

    private final String pattern;

    PreGameMenuCommands(String pattern) {
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


    public static PreGameMenuCommands parse(String input) {
        for (PreGameMenuCommands command : values()) {
            if (command.matches(input)) {
                return command;
            }
        }
        return null;
    }
}