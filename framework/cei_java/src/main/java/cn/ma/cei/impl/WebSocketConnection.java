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

    private List<WebSocketEvent> events = new LinkedList<>();

    @FunctionalInterface
    public interface Function<T> {

        public void onEvent(T t);
    }

    public void registerEvent(WebSocketEvent event) {

    }

    public void connect(WebSocketOption request) {
        Request okhttpRequest = new Request.Builder().url(request.url).build();
        webSocket = client.newWebSocket(okhttpRequest, this);
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        System.err.println("onOpen");
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
    
    public void send(JsonWrapper msg) {
        if (webSocket != null) {
            System.out.println(msg);
            webSocket.send(msg.toJsonString());
        }
    }

}
