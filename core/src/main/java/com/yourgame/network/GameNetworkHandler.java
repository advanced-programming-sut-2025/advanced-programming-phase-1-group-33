package com.yourgame.network;

public class GameNetworkHandler implements NetworkListener {

    private final ResponseHolder responseHolder;

    public GameNetworkHandler(ResponseHolder responseHolder) {
        this.responseHolder = responseHolder;
    }

    @Override
    public void received(Object object) {
        responseHolder.setResponse(object);
    }
}