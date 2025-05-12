package com.yourgame.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.yourgame.model.Command;
public class CommandParser {

    private static final String COMMAND_PATTERN = "^(\\w+)\\s*(.*)$";

    public Command parse(String input) {
        Pattern pattern = Pattern.compile(COMMAND_PATTERN);
        Matcher matcher = pattern.matcher(input.trim());

        if (matcher.matches()) {
            String commandName = matcher.group(1);
            String arguments = matcher.group(2);
            return new Command(commandName, parseArguments(arguments));
        } else {
            throw new IllegalArgumentException("Invalid command format");
        }
    }

    private String[] parseArguments(String arguments) {
        return arguments.isEmpty() ? new String[0] : arguments.split("\\s+");
    }
}