/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.impl;

/**
 *
 * @author u0151316
 */
public class WebSocketAction {
    private IChecker checker;
    private IUserCallback callback;

    @FunctionalInterface
    public interface IChecker {
        boolean check(WebSocketMessage msg);
    }

    @FunctionalInterface
    public interface IUserCallback {
        void onReceive(WebSocketMessage msg);
    }

    @FunctionalInterface
    public interface ISystemCallback {
        void onReceive(WebSocketMessage msg);
    }

    public void setTrigger(IChecker checker) {

    }
    public void setEvent(ISystemCallback callback) {

    }

    public void registerUser(IChecker when, IUserCallback callback) {

    }

    public void registerSystem(IChecker when, ISystemCallback callback) {

    }

    public boolean check(WebSocketMessage msg) {
        return checker.check(msg);
    }

    public void invoke(WebSocketMessage msg) {
        callback.onReceive(msg);
    }
}
