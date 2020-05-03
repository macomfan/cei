/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.impl;

import cn.ma.cei.exception.CEILog;
import okhttp3.*;
import okio.ByteString;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author u0151316
 */
public class WebSocketConnection extends WebSocketListener {

    private static int ClientID = 0;

    private final OkHttpClient client;
    private WebSocket webSocket = null;
    private OnSystemEvent onConnect = null;
    private OnSystemEvent onClose = null;
    private Status status = Status.IDLE;
    private int id = 0;
    private final WebSocketOptions option;
    private final Object connectedNotification = new Object();

    enum Status {
        IDLE,
        CONNECTED,
        CLOSED,
    }

    private final CopyOnWriteArrayList<WebSocketEvent> event = new CopyOnWriteArrayList<>();

    public WebSocketConnection(WebSocketOptions option) {
        id = ClientID++;
        client = new OkHttpClient.Builder().connectTimeout(option.connectTimeout_s, TimeUnit.SECONDS).build();
        this.option = option;
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

    public void connect(String target) {
        Request okhttpRequest = new Request.Builder().url(option.url + target).build();
        webSocket = client.newWebSocket(okhttpRequest, this);

        synchronized (connectedNotification) {
            try {
                connectedNotification.wait(option.connectTimeout_s * 1000);
            } catch (Exception e) {
                System.err.println("ERROR wait");
            }

        }

    }


    public void close() {

    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        CEILog.showInfo("");
        if (onConnect != null) {
            onConnect.onSystemEvent(this);
        }
        synchronized (connectedNotification) {
            status = Status.CONNECTED;
            connectedNotification.notify();
        }
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
        System.err.println("Receive " + text + " " + Thread.currentThread().getId());
        WebSocketMessage msg = new WebSocketMessage(text);
        onMessage(msg);
    }


    private void onMessage(WebSocketMessage msg) {
        event.forEach(item -> {
            if (item.check(msg)) {
                item.invoke(this, msg);
                if (!item.isPersistence()) {
                    event.remove(item);
                }
            }
        });
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        System.out.println("Closing");
        webSocket.close(code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        System.out.println("Error");
        t.printStackTrace();
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
