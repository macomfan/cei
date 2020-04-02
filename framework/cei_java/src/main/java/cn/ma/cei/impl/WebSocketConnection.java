/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.impl;

import okhttp3.*;
import okio.ByteString;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author u0151316
 */
public class WebSocketConnection extends WebSocketListener {

    private static final OkHttpClient client = new OkHttpClient();
    private WebSocket webSocket = null;
    private OnConnect onConnect = null;

    private List<WebSocketAction> actions = new LinkedList<>();

    public void registerAction(WebSocketAction action) {
        actions.add(action);
    }

    public void setOnConnect(OnConnect onConnect) {
        this.onConnect = onConnect;
    }

    public boolean isConnected() {
        return true;
    }

    public void connectWS(String target, WebSocketOptions option) {
        //this.onConnect = onConnect;
        Request okhttpRequest = new Request.Builder().url(target).build();
        webSocket = client.newWebSocket(okhttpRequest, this);
    }

    public void closeWS(WebSocketOptions option) {

    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        System.err.println("onOpen");
        if (onConnect != null) {
            onConnect.onConnect(this);
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        System.err.println("onMessage byte");
        WebSocketMessage msg = new WebSocketMessage(bytes);
        Iterator<WebSocketAction> it = actions.iterator();
        while (it.hasNext()) {
            WebSocketAction cur = it.next();
            if (cur.check(msg)) {
                cur.invoke(msg);
                if (!cur.isPersistence()) {
                    actions.remove(it);
                }
            }
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        WebSocketMessage msg = new WebSocketMessage(text);
        Iterator<WebSocketAction> it = actions.iterator();
        while (it.hasNext()) {
            WebSocketAction cur = it.next();
            if (cur.check(msg)) {
                cur.invoke(msg);
                if (!cur.isPersistence()) {
                    actions.remove(it);
                }
            }
        }
    }

    public void sendWS(String msg) {
        if (msg != null || !msg.isEmpty()) {
            System.out.println(msg);
            webSocket.send(msg);
        }
    }

    @FunctionalInterface
    public interface OnConnect {
        void onConnect(WebSocketConnection connection);
    }
}
