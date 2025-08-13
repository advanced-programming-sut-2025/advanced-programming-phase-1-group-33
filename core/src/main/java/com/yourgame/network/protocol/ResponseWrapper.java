// core/src/main/java/com/yourgame/network/protocol/ResponseWrapper.java

package com.yourgame.network.protocol;

import com.yourgame.network.protocol.ResponseType;

public class ResponseWrapper {
    private ResponseType type;
    private String payload;

    public ResponseWrapper(ResponseType type, String payload) {
        this.type = type;
        this.payload = payload;
    }

    // Getters and Setters
    public ResponseType getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }
}