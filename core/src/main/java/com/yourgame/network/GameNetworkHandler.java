package com.yourgame.network;

/**
 * این کلاس حالا فقط نقش یک پستچی را دارد.
 * پاسخ را از شبکه می‌گیرد و در ResponseHolder قرار می‌دهد.
 */
public class GameNetworkHandler implements NetworkListener {

    private final ResponseHolder responseHolder;

    public GameNetworkHandler(ResponseHolder responseHolder) {
        this.responseHolder = responseHolder;
    }

    @Override
    public void received(Object object) {
        // به همین سادگی! فقط پاسخ را در Holder قرار بده.
        responseHolder.setResponse(object);
    }
}