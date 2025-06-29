package com.yourgame.model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements Command{
    REGISTER("^register\\s+-u\\s+(?<username>.+?)\\s+-p\\s+(?<password>.+?)\\s+-pc\\s+(?<passwordConfirm>.+?)\\s+-n\\s+(?<nickname>.+?)\\s+-e\\s+(?<email>.+?)\\s+-g\\s+(?<gender>.+)$"),
    PICK_QUESTION("^pick\\s+question\\s+-q\\s+(?<questionNumber>\\d+)\\s+-a\\s+(?<answer>.+?)\\s+-c\\s+(?<answerConfirm>.+)$"),
    LOGIN("^login\\s+-u\\s+(?<username>.+?)\\s+-p\\s+(?<password>\\S+)\\s*(?<loginFlag>\\S+)?$"),
    FORGET("^forget\\s+password\\s+-u\\s+(?<username>.+)$"),
    GO_Back(Command.Go_back),
    SHOW_MENU(Command.SHOW_MENU),
    EXIT_MENU(Command.EXIT_MENU),
    ENTER_MENU(Command.ENTER_MENU);

    private final String pattern;

    LoginMenuCommands(String pattern) {
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


    public static LoginMenuCommands parse(String input) {
        for (LoginMenuCommands command : values()) {
            if (command.matches(input)) {
                return command;
            }
        }
        return null;
    }

}
