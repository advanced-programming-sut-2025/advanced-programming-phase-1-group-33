package com.yourgame.network.protocol;

public class RequestWrapper {
    private final RequestType type;
    private final String payload; // خود آبجکت به صورت رشته JSON

    public RequestWrapper(RequestType type, String payload) {
        this.type = type;
        this.payload = payload;
    }

    public RequestType getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }
}