package com.yourgame.model.UserInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UserInfoChecking {
    StrongPassword("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[?><,\"';:\\\\/\\[\\]{}|=+()\\-*&^%$#!]).{8,}$"),
    ValidEmail("^(?!.*\\.\\.)[a-zA-Z0-9](?:[a-zA-Z0-9._-]*[a-zA-Z0-9])?@" +
                    "(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+" +
                    "[a-zA-Z]{2,}$"),
    ValidName("[\\w\\-]{1,8}");

    private final String regex;

    UserInfoChecking(String regex) {
        this.regex = regex;
    }

    public boolean matcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
