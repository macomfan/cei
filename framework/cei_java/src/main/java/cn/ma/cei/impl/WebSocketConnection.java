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
import java.util.concurrent.TimeUnit;

/**
 * @author u0151316
 */
public class WebSocketConnection extends WebSocketListener {

    private final OkHttpClient client;
    private WebSocket webSocket = null;
    private OnSystemEvent onConnect = null;
    private OnSystemEvent onClose = null;
    private Status status = Status.IDLE;
    enum Status{
        IDLE,
        CONNECTED,
        CLOSED,
    }

    private List<WebSocketEvent> event = new LinkedList<>();

    public WebSocketConnection() {
        client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build();
    }

    public void registerEvent(WebSocketEvent event) {
        this.event.add(event);
    }

    public void setOnConnect(OnSystemEvent onConnect) {
        this.onConnect = onConnect;
    }

    public void setOnClose(OnSystemEvent onClose) {
        this.onClose = onClose;
    }

    public boolean isConnected() {
        return true;
    }

    public void connect(String target, WebSocketOptions option) {
        //this.onConnect = onConnect;
        Request okhttpRequest = new Request.Builder().url(target).build();
        webSocket = client.newWebSocket(okhttpRequest, this);
        while (status == Status.IDLE) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(WebSocketOptions option) {

    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        System.err.println("onOpen");
        if (onConnect != null) {
            onConnect.onSystemEvent(this);
        }
        status = Status.CONNECTED;
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);

        String s = CEIUtils.gzip(bytes.toByteArray());
        System.err.println("Receive bytes " + s);
        WebSocketMessage msg = new WebSocketMessage(s);
        onMessage(msg);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        System.err.println("Receive " + text);
        WebSocketMessage msg = new WebSocketMessage(text);
        onMessage(msg);
    }

    private void onMessage(WebSocketMessage msg) {
        Iterator<WebSocketEvent> it = event.iterator();
        while (it.hasNext()) {
            WebSocketEvent cur = it.next();
            if (cur.check(msg)) {
                cur.invoke(this, msg);
                if (!cur.isPersistence()) {
                    event.remove(it);
                }
            }
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        System.out.println("Error");
        status = Status.CLOSED;
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        System.out.println("Closed");
        status = Status.CLOSED;
        if (onClose != null) {
            onClose.onSystemEvent(this);
        }
    }

    public void send(String msg) {
        if (msg != null || !msg.isEmpty()) {
            System.out.println("Send " + msg);
            webSocket.send(msg);
        }
    }

    @FunctionalInterface
    public interface OnSystemEvent {
        void onSystemEvent(WebSocketConnection connection);
    }
}
