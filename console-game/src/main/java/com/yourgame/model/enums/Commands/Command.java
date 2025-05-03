package com.yourgame.model.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {
    String ENTER_MENU = "menu\\s+enter\\s+(?<menuName>.*)";
    String EXIT_MENU = "menu\\s+exit";
    String SHOW_MENU = "show\\s+current\\s+menu";

    boolean matches(String input);

    String getGroup(String input, String group);
}

