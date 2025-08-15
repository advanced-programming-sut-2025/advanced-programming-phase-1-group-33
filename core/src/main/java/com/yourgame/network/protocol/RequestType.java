package com.yourgame.network.protocol;

public enum RequestType {
    LOGIN,
    USER_EXIST, 
    SIGNUP,
    FORGOT_PASSWORD,
    SECURITY_ANSWER, 
    GET_USER_PASSWORD, CREATE_LOBBY, JOIN_LOBBY, LEAVE_LOBBY, REFRESH_LOBBIES, SEARCH_LOBBY
}