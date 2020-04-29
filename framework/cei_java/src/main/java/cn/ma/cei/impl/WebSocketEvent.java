/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.impl;

/**
 * @author u0151316
 */
public class WebSocketEvent {
    private ITrigger checker;
    private ICallback callback;
    private boolean persistence = false;

    public void setTrigger(ITrigger checker) {
        this.checker = checker;
    }

    public void setEvent(ICallback callback) {
        this.callback = callback;
    }

    public boolean isPersistence() {
        return persistence;
    }

    public void setPersistent(boolean persistence) {
        this.persistence = persistence;
    }

    public boolean check(WebSocketMessage msg) {
        return checker.check(msg);
    }

    public void invoke(WebSocketConnection connection, WebSocketMessage msg) {
        callback.onEvent(connection, msg);
    }

    @FunctionalInterface
    public interface ITrigger {
        boolean check(WebSocketMessage msg);
    }

    @FunctionalInterface
    public interface ICallback {
        void onEvent(WebSocketConnection connection, WebSocketMessage msg);
    }
}
