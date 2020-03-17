/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.impl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author u0151316
 */
public class WebSocketConnection extends WebSocketListener {

    private static final OkHttpClient client = new OkHttpClient();
    private WebSocket webSocket = null;
    private OnConnect onConnect = null;

    private List<WebSocketAction> events = new LinkedList<>();

    @FunctionalInterface
    public interface OnConnect {
        void onConnect(WebSocketConnection connection);
    }

    @FunctionalInterface
    public interface Function<T> {
        public void onEvent(T t);
    }


    public void registerAction(WebSocketAction action) {

    }

    public void setOnConnect(OnConnect onConnect) {

    }

    public void connectWebSocket(String url, WebSocketOptions option) {
        //this.onConnect = onConnect;
        Request okhttpRequest = new Request.Builder().url(url).build();
        webSocket = client.newWebSocket(okhttpRequest, this);
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
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        WebSocketMessage msg = new WebSocketMessage();
        events.forEach(item->{
            if (item.check(msg)) {
                item.invoke(msg);
            }
        });
    }
    
    public void send(String msg) {
        if (msg != null || !msg.isEmpty()) {
            System.out.println(msg);
            webSocket.send(msg);
        }
    }

}
