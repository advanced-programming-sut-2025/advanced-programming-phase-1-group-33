package com.yourgame.server.services;

import com.yourgame.server.ClientHandler;

public interface RequestHandler<T> {
    void handle(ClientHandler client, T request);
}