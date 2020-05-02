/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service;

import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;

/**
 *
 * @author u0151316
 */
public class WebSocketClient {

    private final ServerWebSocket ws;
    private final String clientID;
    private final WebSocketService.NotificationRegister notificationRegister;


    public WebSocketClient(String clientID, ServerWebSocket ws, WebSocketService.NotificationRegister notificationRegister) {
        this.ws = ws;
        this.clientID = clientID;
        this.notificationRegister = notificationRegister;
    }

    public String id() {
        return clientID;
    }

    public void send(JsonObject json) {
        String text = json.toString();
        System.err.println("[Client] Send: " + text);
        ws.writeTextMessage(text);
    }

    public void send(String text) {
        System.err.println("Send " + text);
        ws.writeTextMessage(text);
    }

    public boolean registerNotification(String notificationName) {
        return notificationRegister.registerNotification(notificationName, this);
    }

    public boolean unregisterNotification(String notificationName) {
        return notificationRegister.unregisterNotification(notificationName, this);
    }

    public void onClose() {
        notificationRegister.unregisterAll(this);
    }
}
