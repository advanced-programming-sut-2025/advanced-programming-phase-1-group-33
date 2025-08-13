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
        while (response == null) {
            wait(timeoutMillis);
            
            if (System.currentTimeMillis() - startTime >= timeoutMillis) {
                return null;
            }
        }
        
        Object result = this.response;
        clear(); 
        return result;
    }


    public void clear() {
        this.response = null;
    }
}