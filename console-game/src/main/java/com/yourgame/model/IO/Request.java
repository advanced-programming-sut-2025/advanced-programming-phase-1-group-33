package com.yourgame.model.IO;

import java.util.HashMap;

public class Request {
    public final String command;
    public final HashMap<String, String> body = new HashMap<>();

    public Request(String command) {
        this.command = command;
    }
}
