// core/src/main/java/com/yourgame/network/ResponseHolder.java
package com.yourgame.network;

public class ResponseHolder {
    private volatile Object response = null;

    public synchronized void setResponse(Object response) {
        this.response = response;
        notifyAll();
    }

    public synchronized Object getResponse(long timeoutMillis) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long remainingTime = timeoutMillis;

        while (response == null && remainingTime > 0) {
            wait(remainingTime);
            remainingTime = timeoutMillis - (System.currentTimeMillis() - startTime);
        }

        if (response == null) {
            return null; // مهلت به پایان رسیده
        }

        Object result = this.response;
        clear();
        return result;
    }

    public void clear() {
        this.response = null;
    }

    
}