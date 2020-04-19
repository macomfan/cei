package cn.ma.cei.service;

public abstract class WebSocketMessageProcessor {
    public abstract <T extends WebSocketMessage> void process(T message, WebSocketClient client);
}
