package com.yourgame.model.AppModel.enums;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PreGameMenuCommands {
    GAME_NEW("enter"),
    GAME_MAP("exit"),
    LOAD_GAME("enter"),
    GO_BACK("enter"),
    EXIT_GAME("enter");

    private final String regex;
    private final Pattern pattern;

    PreGameMenuCommands(String regex) {
        this.regex = regex;
        // using CASE_INSENSITIVE to permit flexibility in input case
        this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public Matcher getMatcher(String input) {
        return pattern.matcher(input);
    }

    public boolean matches(String input) {
        return pattern.matcher(input).matches();
    }

    /**
     * Extracts regex groups if available.
     * Returns null if the input doesn't match.
     */
    public String[] getArguments(String input) {
        Matcher matcher = getMatcher(input);
        if (matcher.matches()) {
            String[] args = new String[matcher.groupCount()];
            for (int i = 1; i <= matcher.groupCount(); i++) {
                args[i - 1] = matcher.group(i);
            }
            return args;
        }
        return null;
    }

    public String getRegex() {
        return regex;
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