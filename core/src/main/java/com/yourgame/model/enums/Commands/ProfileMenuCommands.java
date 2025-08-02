package com.yourgame.model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands implements Command{
    UserInfo("user\\s+info"),
    CHANGE_USERNAME("^change\\s+username\\s+-u\\s+(?<username>\\S+)$"),
    CHANGE_NICKNAME("^change\\s+nickname\\s+-u\\s+(?<nickname>\\S+)$"),
    CHANGE_EMAIL("^change\\s+email\\s+-e\\s+(?<email>\\S+)$"),
    CHANGE_PASSWORD("^change\\s+password\\s+-p\\s+(?<newPassword>\\S+)\\s+-o\\s+(?<oldPassword>\\S+)$"),
    SHOW_MENU(Command.SHOW_MENU),
    EXIT_MENU(Command.EXIT_MENU),
    GO_Back(Command.Go_back),
    ENTER_MENU(Command.ENTER_MENU);

    private final String pattern;

    ProfileMenuCommands(String pattern) {
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


    public static ProfileMenuCommands parse(String input) {
        for (ProfileMenuCommands command : values()) {
            if (command.matches(input)) {
                return command;
            }
        }
        return null;
    }

}
